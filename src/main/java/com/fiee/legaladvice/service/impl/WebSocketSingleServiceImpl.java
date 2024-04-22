package com.fiee.legaladvice.service.impl;

import com.alibaba.fastjson.JSON;
import com.fiee.legaladvice.dto.ChatRecordDTO;
import com.fiee.legaladvice.dto.RecallMessageDTO;
import com.fiee.legaladvice.dto.WebsocketMessageDTO;
import com.fiee.legaladvice.entity.ChatRecord;
import com.fiee.legaladvice.utils.HTMLUtils;
import com.fiee.legaladvice.utils.UserUtils;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.PongMessage;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
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
@ServerEndpoint(value = "/websocket/single/{userInfoId}", configurator = WebSocketServiceImpl.ChatConfigurator.class)
public class WebSocketSingleServiceImpl {
    /**
     * 用户session
     */
    private Session session;

    /**
     * 用户session集合
     */
    private static ConcurrentHashMap<String,WebSocketSingleServiceImpl> webSocketSet = new ConcurrentHashMap<>();

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
    public void onOpen(Session session, @PathParam("userInfoId") String userInfoId) throws IOException {
        // 加入连接
        this.session = session;
        webSocketSet.put(userInfoId,this);
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
        System.out.println(messageDTO);
//        String sendToUser = (String) message.get("sendToUser");
        ChatRecord chatRecord = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), ChatRecord.class);
        System.out.println(chatRecord);
        webSocketSet.get(chatRecord.getToUserId().toString()).getSession().getBasicRemote().sendText("hhhhh");
//        webSocketSet.get(chatRecord.getToUserId()).getSession().getBasicRemote().sendText("hhhh");

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
