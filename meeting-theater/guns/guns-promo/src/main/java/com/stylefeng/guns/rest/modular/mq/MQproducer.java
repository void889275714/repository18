package com.stylefeng.guns.rest.modular.mq;


import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.rest.common.persistence.dao.MtimePromoStockMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MtimeStockLogMapper;
import com.stylefeng.guns.rest.common.persistence.model.MtimeStockLog;
import com.stylefeng.guns.rest.promo.PromoService;
import com.stylefeng.guns.rest.promo.vo.StockLogStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import sun.rmi.runtime.Log;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;

@Slf4j
@Component
public class MQproducer {

    private DefaultMQProducer defaultMQProducer;

    //事务型消息生产者
    private TransactionMQProducer transactionMQProducer;

    @Autowired
    PromoService promoService;

    @Autowired
    MtimeStockLogMapper stockLogMapper;


    @PostConstruct
    public void init() throws MQClientException {
        defaultMQProducer = new DefaultMQProducer("stock_group");
        defaultMQProducer.setNamesrvAddr("localhost:9876");
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("消息生产者启动成功...注册中心地址:{}","localhost:9876");


        //初始化事务型消息
        //设置组名
        transactionMQProducer = new TransactionMQProducer("transaction_group");
        transactionMQProducer.setNamesrvAddr("localhost:9876");
        transactionMQProducer.start();
        log.info("transactionMQProducer started...");
        //设置事务的监听回调器
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            /**
             * 方法3   执行本地事务
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                // 第一个参数是 message 主要是用于消息被消费时提取数据  第二个是args 传递给本地事务时的参数
                //生成订单和扣减 redis中的库存
                HashMap<String,Object> argsMap = (HashMap)o;
                Integer promoId = (Integer) argsMap.get("promoId");
                Integer amount = (Integer) argsMap.get("amount");
                Integer userId = (Integer) argsMap.get("userId");
                //String stockLogId = (String) argsMap.get("stockLogId");

                //缺少返回判断
                try {
                    boolean secOrder = promoService.createSecOrder(String.valueOf(promoId), String.valueOf(amount), userId);
                    if (secOrder) {
                        //stockLogMapper.updateStatusById(stockLogId,StockLogStatus.SUCCESS.getIndex());
                        return LocalTransactionState.COMMIT_MESSAGE;
                    }else {
                        //stockLogMapper.updateStatusById(stockLogId,StockLogStatus.FAIL.getIndex());
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    }
                }catch (Exception e){
                    //stockLogMapper.updateStatusById(stockLogId,StockLogStatus.FAIL.getIndex());
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            /**
             * 方法6  检查本地事务的执行状态
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                byte[] body = msg.getBody();
                String bodyStr = new String(body);
                HashMap hashMap = JSON.parseObject(bodyStr, HashMap.class);
                String stockLogId = (String) hashMap.get("stockLogId");

                MtimeStockLog mtimeStockLog = stockLogMapper.selectById(stockLogId);
                if (mtimeStockLog == null) {
                    log.info("查询流水记录失败！");
                    return LocalTransactionState.UNKNOW;
                }
                Integer status = mtimeStockLog.getStatus();

                //根据 status 判断本地事务的执行状态
                //缺少一个 stockLogStatus的类
                if (StockLogStatus.FAIL.getIndex() == status) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                if (StockLogStatus.SUCCESS.getIndex() == status) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.UNKNOW;
            }
        });
    }


    /**
     * 发送一个事务型消息
     * @param promoId
     * @param amount
     * @return
     */
    public Boolean creatOrderInTransaction(Integer promoId, Integer amount, Integer userId) throws MQClientException {
        //message
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("promoId",promoId);
        hashMap.put("amount",amount);
        //hashMap.put("stockLogId",stockLogId);

        //map
        HashMap<String, Object> argsMap = new HashMap<>();
        argsMap.put("promoId",promoId);
        argsMap.put("amount",amount);
        argsMap.put("userId",userId);
        //argsMap.put("stockLogId",logId);

        byte[] bytes = JSON.toJSONString(hashMap).getBytes(Charset.forName("utf-8"));
        Message message = new Message("stock", bytes);


        TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(message, argsMap);
        //本地事务执行状态
        LocalTransactionState localTransactionState = sendResult.getLocalTransactionState();
        if (LocalTransactionState.COMMIT_MESSAGE.equals(localTransactionState)) {
            return Boolean.TRUE;
        }
        if (LocalTransactionState.ROLLBACK_MESSAGE.equals(localTransactionState)) {
            return Boolean.FALSE;
        }
        return false;
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
