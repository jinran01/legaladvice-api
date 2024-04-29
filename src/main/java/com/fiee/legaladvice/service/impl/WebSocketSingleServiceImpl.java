package com.fiee.legaladvice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiee.legaladvice.dto.HistoryRecordDTO;
import com.fiee.legaladvice.dto.WebsocketMessageDTO;
import com.fiee.legaladvice.entity.ChatRecord;
import com.fiee.legaladvice.mapper.ChatRecordMapper;
import com.fiee.legaladvice.utils.HTMLUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.fiee.legaladvice.enums.ChatTypeEnum.*;

/**
 * @Author: Fiee
 * @ClassName: WebSocketSingleServiceImpl
 * @Date: 2024/4/22
 * @Version: v1.0.0
 **/
@Data
@Service
@ServerEndpoint(value = "/websocket/single/{userId}", configurator = WebSocketServiceImpl.ChatConfigurator.class)
public class WebSocketSingleServiceImpl {
    /**
     * 用户session
     */
    private Session session;

    /**
     * 用户session集合
     */
    private static ConcurrentHashMap<String, WebSocketSingleServiceImpl> webSocketSet = new ConcurrentHashMap<>();

    @Autowired
    public void setChatRecordMapper(ChatRecordMapper chatRecordMapper) {
        WebSocketSingleServiceImpl.chatRecordMapper = chatRecordMapper;
    }

    static ChatRecordMapper chatRecordMapper;

    /**
     * 获取客户端真实ip
     */
    public static class ChatConfigurator extends ServerEndpointConfig.Configurator {
        public static String HEADER_NAME = "X-Real-IP";

        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            try {
                String firstFoundHeader = request.getHeaders().get(HEADER_NAME.toLowerCase()).get(0);
                sec.getUserProperties().put(HEADER_NAME, firstFoundHeader);
            } catch (Exception e) {
                sec.getUserProperties().put(HEADER_NAME, "未知ip");
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        // 加入连接
        this.session = session;
        webSocketSet.put(userId, this);
        updateOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        WebsocketMessageDTO messageDTO = JSON.parseObject(message, WebsocketMessageDTO.class);
        switch (Objects.requireNonNull(getChatType(messageDTO.getType()))) {
            case SEND_MESSAGE:
                ChatRecord chatRecord = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), ChatRecord.class);
                // 过滤html标签
                chatRecord.setContent(HTMLUtils.filter(chatRecord.getContent()));
                chatRecord.setType(3);
                chatRecordMapper.insert(chatRecord);
                messageDTO.setData(chatRecord);
                webSocketSet.get(chatRecord.getToUserId().toString())
                        .getSession().getBasicRemote().sendText(JSON.toJSONString(messageDTO));
                break;
            case HISTORY_RECORD:
                HistoryRecordDTO historyRecordDTO = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), HistoryRecordDTO.class);
                LambdaQueryWrapper<ChatRecord> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ChatRecord::getUserId, historyRecordDTO.getUserId())
                        .eq(ChatRecord::getToUserId, historyRecordDTO.getToUserId())
                        .or()
                        .eq(ChatRecord::getUserId, historyRecordDTO.getToUserId())
                        .eq(ChatRecord::getToUserId, historyRecordDTO.getUserId());
                wrapper.isNotNull(true,ChatRecord::getToUserId);
                List<ChatRecord> chatRecordList = chatRecordMapper.selectList(wrapper);
                messageDTO.setData(chatRecordList);
                this.getSession().getBasicRemote().sendText(JSON.toJSONString(messageDTO));
                break;
            case HEART_BEAT:
                ArrayList<String> list = new ArrayList<>();
                for (String userId: webSocketSet.keySet()) {
                    list.add(userId);
                }
                System.out.println();
                messageDTO.setData(list);
                session.getBasicRemote().sendText(JSON.toJSONString(messageDTO));
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId") String userId) throws IOException {
        // 更新在线人数
        webSocketSet.remove(userId);
        updateOnlineCount();
    }

    /**
     * 更新在线人数
     *
     * @throws IOException io异常
     */
    @Async
    public void updateOnlineCount() throws IOException {
        // 获取当前在线人数
        System.out.println("现在人数为:" + webSocketSet.size());
    }

}
