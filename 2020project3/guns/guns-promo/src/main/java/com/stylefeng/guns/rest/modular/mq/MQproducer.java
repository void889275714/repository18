package com.stylefeng.guns.rest.modular.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;

@Slf4j
@Component
public class MQproducer {

    private DefaultMQProducer defaultMQProducer;


    @PostConstruct
    public void init(){
        defaultMQProducer = new DefaultMQProducer("stock_group");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("消息生产者启动成功...注册中心地址:{}","localhost:9876");
    }


    //提供一个发生 异步扣减数据库中库存的方法

    public Boolean sendMessageDecreaseStock(Integer promoId, Integer amount) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("promoId",promoId);
        hashMap.put("amount",amount);

        byte[] bytes = JSON.toJSONString(hashMap).getBytes(Charset.forName("utf-8"));
        Message message = new Message("stock", bytes);
        SendResult sendResult = defaultMQProducer.send(message);
        SendStatus sendStatus = sendResult.getSendStatus();
        if (SendStatus.SEND_OK.equals(sendStatus)) {
            return true;
        }
        return false;
    }


}
