package com.cskaoyan.service.configmall;



import com.cskaoyan.bean.configmall.FreightMsg;
import com.cskaoyan.bean.configmall.MallMsg;
import com.cskaoyan.bean.configmall.OrderMsg;
import com.cskaoyan.bean.configmall.WxMsg;
import com.cskaoyan.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cskaoyan.bean.System;
import java.util.HashMap;
import java.util.Map;

@Service
public class LiteMallServiceImpl implements LiteMallService{

    @Autowired
    SystemMapper systemMapper;

    @Override
    public Map queryMallMsg() {
        Map map = new HashMap();
        System system = systemMapper.selectByPrimaryKey(14);//id对应地址名

             map.put("cskaoyan_mall_mall_address",system.getKeyValue());
        System system1 = systemMapper.selectByPrimaryKey(6);//id对应商场名
            map.put("cskaoyan_mall_mall_name",system1.getKeyValue());
        System system2 = systemMapper.selectByPrimaryKey(8);//id对应qq
            map.put("cskaoyan_mall_mall_qq",system2.getKeyValue());
        System system3 = systemMapper.selectByPrimaryKey(12);//id对应电话
            map.put("cskaoyan_mall_mall_phone",system3.getKeyValue());
        return map;
    }

    @Override
    public boolean updateMallMsg(MallMsg mallMsg) {
        if(mallMsg != null){
            String mallname = mallMsg.getCskaoyan_mall_mall_name();
            System system = new System(); //将数据装进system对象
            system.setKeyValue(mallname);
            system.setId(6);        //根据表id修改相应的value 6 对应商场名字
            systemMapper.updateByPrimaryKeySelective(system);

            String malladdress = mallMsg.getCskaoyan_mall_mall_address();
            System system1 = new System();
            system1.setKeyValue(malladdress);
            system1.setId(14); //14对应 商场地址
            systemMapper.updateByPrimaryKeySelective(system1);

            String mallphone = mallMsg.getCskaoyan_mall_mall_phone();
            System system2 = new System();
            system2.setKeyValue(mallphone);
            system2.setId(12); //12 对应商场电话
            systemMapper.updateByPrimaryKeySelective(system2);

            String mallqq= mallMsg.getCskaoyan_mall_mall_qq();
            System system3 = new System();
            system3.setKeyValue(mallqq);
            system3.setId(8); // 对应 qq信息
            systemMapper.updateByPrimaryKeySelective(system3);


            return true;
        }
       return false;
    }

    @Override
    public Map queryExpressMsg() {
        Map map = new HashMap();
        System system = systemMapper.selectByPrimaryKey(5);// 对应最低消费表
        map.put("cskaoyan_mall_express_freight_min",system.getKeyValue());

        System system1 = systemMapper.selectByPrimaryKey(7);//对应运费表
        map.put("cskaoyan_mall_express_freight_value",system1.getKeyValue());

        return map;
    }

    @Override
    public boolean updateFreightMsg(FreightMsg freightMsg) {
        System system = new System();
        system.setKeyValue(freightMsg.getCskaoyan_mall_express_freight_min());
        system.setId(5);//对应最低运费表
        int i =systemMapper.updateByPrimaryKeySelective(system);

        System system1 = new System();
        system1.setKeyValue(freightMsg.getCskaoyan_mall_express_freight_value());
        system1.setId(7);//对应所需运费表
        int j = systemMapper.updateByPrimaryKeySelective(system1);
        if(i>0 || j >0){
            return true;
        }

        return false;
    }

    @Override
    public Map queryOrderMsg() {
        Map map = new HashMap();
        System system = systemMapper.selectByPrimaryKey(1);//超时下单的表id
        map.put("cskaoyan_mall_order_unpaid",system.getKeyValue());

        System system1 = systemMapper.selectByPrimaryKey(3);//未确认收货的表id
        map.put("cskaoyan_mall_order_unconfirm",system1.getKeyValue());

        System system2 = systemMapper.selectByPrimaryKey(10);//未评价商品的表id
        map.put("cskaoyan_mall_order_comment",system2.getKeyValue());

        return map;
    }

    @Override
    public boolean updateOrderMsg(OrderMsg orderMsg) {
        System system = new System();
        system.setKeyValue(orderMsg.getCskaoyan_mall_order_unpaid());
        system.setId(1); //未付款对应的表id
        int i = systemMapper.updateByPrimaryKeySelective(system);

        System system1 = new System();
        system1.setKeyValue(orderMsg.getCskaoyan_mall_order_unconfirm());
        system1.setId(3);//未确认收货的表id
        int j =systemMapper.updateByPrimaryKeySelective(system1);

        System system2 = new System();
        system2.setKeyValue(orderMsg.getCskaoyan_mall_order_comment());
        system2.setId(10);//未评价商品的表id
        int k = systemMapper.updateByPrimaryKeySelective(system2);

        if(i>0 || j>0 || k>0){
            return true;
        }

        return false;
    }

    @Override
    public Map queryWxMsg() {
        Map map = new HashMap();
        System system = systemMapper.selectByPrimaryKey(2);//新品首发栏目商品显示数量的表id
        map.put("cskaoyan_mall_wx_index_new",system.getKeyValue());

        System system1 = systemMapper.selectByPrimaryKey(9);
        map.put("cskaoyan_mall_wx_index_hot",system1.getKeyValue());//人气推荐栏目商品显示数量的表id

        System system2 = systemMapper.selectByPrimaryKey(15);
        map.put("cskaoyan_mall_wx_index_brand",system2.getKeyValue());//品牌制造商直供栏目品牌商显示数量的表id

        System system3 = systemMapper.selectByPrimaryKey(16);
        map.put("cskaoyan_mall_wx_index_topic",system3.getKeyValue());//专题精选栏目显示数量的表id

        System system4 = systemMapper.selectByPrimaryKey(13);
        map.put("cskaoyan_mall_wx_catlog_list",system4.getKeyValue());//分类栏目显示数量的表id

        System system5 = systemMapper.selectByPrimaryKey(11);
        map.put("cskaoyan_mall_wx_catlog_goods",system5.getKeyValue());//分类栏目商品显示数量的表id




        return map;
    }

    @Override
    public boolean updateWxMsg(WxMsg wxMsg) {


        System system = new System();
        system.setKeyValue(wxMsg.getCskaoyan_mall_wx_index_new());
        system.setId(2);
        int i = systemMapper.updateByPrimaryKeySelective(system);

        System system1 = new System();
        system1.setKeyValue(wxMsg.getCskaoyan_mall_wx_catlog_goods());
        system1.setId(11);
        int j = systemMapper.updateByPrimaryKeySelective(system1);

        System system2 = new System();
        system2.setKeyValue(wxMsg.getCskaoyan_mall_wx_catlog_list());
        system2.setId(13);
        int k = systemMapper.updateByPrimaryKeySelective(system2);

        System system3 = new System();
        system3.setKeyValue(wxMsg.getCskaoyan_mall_wx_index_brand());
        system3.setId(15);
        int o = systemMapper.updateByPrimaryKeySelective(system3);

        System system4 = new System();
        system4.setKeyValue(wxMsg.getCskaoyan_mall_wx_index_hot());
        system4.setId(9);
        int p = systemMapper.updateByPrimaryKeySelective(system4);

        System system5 = new System();
        system5.setKeyValue(wxMsg.getCskaoyan_mall_wx_index_topic());
        system5.setId(16);
        int u = systemMapper.updateByPrimaryKeySelective(system5);

        //判断其他配置的按钮是否为空
        //如果非空 则更新相应表信息
        if(wxMsg.getCskaoyan_mall_wx_share()!=null){
            System system6 = new System();
            system6.setKeyValue(wxMsg.getCskaoyan_mall_wx_share());
            system6.setId(4);
            systemMapper.updateByPrimaryKeySelective(system6);
        }
        if(i>0 || j>0 || k>0 || p>0 || o>0 || u>0){
            return true;
        }
        return false;
    }
}
