package com.fiee.legaladvice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fiee.legaladvice.constant.MQPrefixConst.*;

/**
 * @Author: Fiee
 * @ClassName: RabbitMQConfig
 * @Date: 2024/4/17
 * @Version: v1.0.0
 **/
@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue articleQueue() {
        return new Queue(MAXWELL_QUEUE, true);
    }

    @Bean
    public FanoutExchange maxWellExchange() {
        return new FanoutExchange(MAXWELL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingArticleDirect() {
        return BindingBuilder.bind(articleQueue()).to(maxWellExchange());
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public FanoutExchange emailExchange() {
        return new FanoutExchange(EMAIL_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingEmailDirect() {
        return BindingBuilder.bind(emailQueue()).to(emailExchange());
    }

    @Bean
    public Queue phoneQueue() {
        return new Queue(PHONE_QUEUE, true);
    }

    @Bean
    public FanoutExchange phoneExchange() {
        return new FanoutExchange(PHONE_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingPhoneDirect() {
        return BindingBuilder.bind(phoneQueue()).to(phoneExchange());
    }
}
