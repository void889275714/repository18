package com.stylefeng.guns.rest.modular.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimePromoStock;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MQconsumer {

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    private MtimePromoStockMapper stockMapper;

    @PostConstruct
    public void init() throws MQClientException {
        //defaultMQPushConsumer = new DefaultMQPushConsumer("consumer_group");
        //开启分布式事务group  待测试
        defaultMQPushConsumer = new DefaultMQPushConsumer("transaction_group");
        defaultMQPushConsumer.setNamesrvAddr("localhost:9876");

        //订阅
        try {
            defaultMQPushConsumer.subscribe("stock","*");
        } catch (MQClientException e) {
            e.printStackTrace();
            log.info("mqPushConsumer订阅失败！");
        }

        try {
            //注册一个消息监听器
            defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    //解析消息数据
                    MessageExt messageExt = msgs.get(0);
                    byte[] body = messageExt.getBody();
                    String bodyStr = new String(body);
                    Map map = JSON.parseObject(bodyStr, Map.class);
                    Integer promoId = (Integer) map.get("promoId");
                    Integer amount = (Integer) map.get("amount");
//                    System.out.println("收到消息.... promoId :" + promoId + ", amount :" + amount);
//                    EntityWrapper<MtimePromoStock> promoStockEntityWrapper = new EntityWrapper<>();
//                    promoStockEntityWrapper.eq("promo_id",promoId);
//                    List<MtimePromoStock> mtimePromoStocks = stockMapper.selectList(promoStockEntityWrapper);
//                    MtimePromoStock mtimePromoStock = mtimePromoStocks.get(0);
//                    Integer stock = mtimePromoStock.getStock();
//                    System.out.println("数据库中库存数之前是:" + stock);
//                    Integer stock1 = stock - amount;
//                    System.out.println("数据库中库存数之后是:" + stock1);
//                    mtimePromoStock.setStock(stock1);
//                    Integer update = stockMapper.update(mtimePromoStock, promoStockEntityWrapper);
                    Integer update = stockMapper.decreaseStock(promoId, amount);
                    if (update < 1) {
                        log.info("消费失败！扣减库存失败，promoId:{},amount:{}",promoId,amount);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    //如果一直消费不成功，默认会重试16次
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            });

            defaultMQPushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("消息消费者启动成功...注册中心地址:{}","localhost:9876");
    }




}
