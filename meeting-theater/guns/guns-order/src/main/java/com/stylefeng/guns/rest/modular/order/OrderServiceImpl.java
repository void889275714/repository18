package com.stylefeng.guns.rest.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.rest.cinema.CinemaService;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import com.stylefeng.guns.rest.order.OrderService;
import com.stylefeng.guns.rest.order.vo.OrderBuyTickets;
import com.stylefeng.guns.rest.order.vo.SeatAddress;
import com.stylefeng.guns.rest.pay.vo.OrderPayCinemaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.io.*;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {






    /*-------------------------------用户下单购票接口----------------------------------*/


    @Autowired
    private MtimeFieldTMapper fieldTMapper;

    @Autowired
    private MtimeHallDictTMapper hallDictTMapper;


    /**
     * 判断前端传来的代码是否为真
     * @param fieldId
     * @param seatId
     * @return
     */
    @Override
    public Boolean isTrueSeats(String fieldId, String seatId) {
        //我们拿到了 fieldId 就可以拿到 hallId  影厅信息
        EntityWrapper<MtimeFieldT> fieldTEntityWrapper = new EntityWrapper<>();
        fieldTEntityWrapper.eq("UUID",fieldId);
        //由主键查出来的其实就一条
        List<MtimeFieldT> mtimeFieldTS = fieldTMapper.selectList(fieldTEntityWrapper);
        Integer hallId = mtimeFieldTS.get(0).getHallId();

        //可以拿到seat_address
        EntityWrapper<MtimeHallDictT> hallDictTEntityWrapper = new EntityWrapper<>();
        hallDictTEntityWrapper.eq("UUID",hallId);
        List<MtimeHallDictT> mtimeHallDictTS = hallDictTMapper.selectList(hallDictTEntityWrapper);
        //文件路径
        String seatAddress = mtimeHallDictTS.get(0).getSeatAddress();

        //文件的内容读出来放到stringbuffer里
        File file = new File(seatAddress);
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer();

        try {
            reader = new BufferedReader(new FileReader(file));
            String temp = null;

            while((temp = reader.readLine())!=null){
                data.append(temp);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //拿到的data.toString() 即字符串，这个时候我们把他转化为JSON对象

        JSONObject jsonObject = JSON.parseObject(data.toString());

        //这个怎么转换？我们把文件的内容读出来放到stringbuffer里就可以返回字符串的形式。
        //那么字符串再转成json对象就可以很方便的拿到这个厅里的座位的ids信息。
        //有了这个厅的ids信息，就可以验证前端给我们的座位编号是不是合法的。

        return true;
    }



    @Autowired
    private MoocOrderTMapper moocOrderTMapper;

    /**
     * 判断前端传来的购票信息是否已经售卖
     * @param fieldId
     * @param seatId
     * @return
     */
    @Override
    public Boolean isSoldSeats(String fieldId, String seatId) {
        EntityWrapper<MoocOrderT> moocOrderTEntityWrapper = new EntityWrapper<>();
        moocOrderTEntityWrapper.eq("field_id",fieldId);
        List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(moocOrderTEntityWrapper);

        StringBuffer stringBuffer = new StringBuffer();
        for (MoocOrderT moocOrderT : moocOrderTS) {

            String seatsIds = moocOrderT.getSeatsIds();
            stringBuffer.append(seatsIds).append(",");
        }
        String s = stringBuffer.toString();
        //把 s 以 “,” 分开
        String [] stringArr= s.split(",");
        String [] seatIdArr= seatId.split(",");
        //如果传来的seatId 在StringArr里存在，则返回true  已出售 否则返回 false
        for (String s1 : stringArr) {
            for (String s2 : seatIdArr) {
                if (s1.equals(s2)) {
                    return true;
                }
            }
        }
        return false;
    }




    @Autowired
    MtimeFieldTMapper mtimeFieldTMapper;


    @Autowired
    MtimeCinemaTMapper mtimeCinemaTMapper;

    @Autowired
    MtimeHallFilmInfoTMapper mtimeHallFilmInfoTMapper;

    /**
     * 显示订单信息
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @return
     */
    @Override
    public OrderBuyTickets orderBuyTicketInfo(String fieldId, String soldSeats, String seatsName,Integer userId) {
        OrderBuyTickets orderBuyTickets = new OrderBuyTickets();

        //根据 fieldId 可以得到 cinemaName
        EntityWrapper<MtimeFieldT> mtimeFieldTEntityWrapper = new EntityWrapper<>();
        mtimeFieldTEntityWrapper.eq("UUID",fieldId);
        List<MtimeFieldT> mtimeFieldTS = mtimeFieldTMapper.selectList(mtimeFieldTEntityWrapper);
        //先拿到cinemaId
        Integer cinemaId = mtimeFieldTS.get(0).getCinemaId();
        EntityWrapper<MtimeCinemaT> mtimeCinemaTEntityWrapper = new EntityWrapper<>();
        mtimeCinemaTEntityWrapper.eq("UUID",cinemaId);
        List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(mtimeCinemaTEntityWrapper);
        String cinemaName = mtimeCinemaTS.get(0).getCinemaName();

        //2 8
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String format = simpleDateFormat.format(new Date());
        orderBuyTickets.setFieldTime(format);

        //3 8
        Integer filmId = mtimeFieldTS.get(0).getFilmId();
        EntityWrapper<MtimeHallFilmInfoT> hallFilmInfoTEntityWrapper = new EntityWrapper<>();
        hallFilmInfoTEntityWrapper.eq("film_id",filmId);
        List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(hallFilmInfoTEntityWrapper);
        String filmName = mtimeHallFilmInfoTS.get(0).getFilmName();
        orderBuyTickets.setFilmName(cinemaName);

        //第一个参数 共几个参数   1  8  前端数据写错了 这里修改一下，亮点吧
        orderBuyTickets.setCinemaName(filmName);

        //orderId  去order表里找吧  4  8
        EntityWrapper<MoocOrderT> moocOrderTEntityWrapper = new EntityWrapper<>();
        Integer integer = moocOrderTMapper.selectCount(moocOrderTEntityWrapper);
        Integer orderId = integer + 1;
        orderBuyTickets.setOrderId(String.valueOf(orderId));

        //5 8
        Integer price = mtimeFieldTS.get(0).getPrice();
        String [] soldSeatArr= soldSeats.split(",");
        int length = soldSeatArr.length;
        Integer orderPrice = price*length;
        orderBuyTickets.setOrderPrice(String.valueOf(orderPrice));

        //6 8    状态为0 是 未支付
        orderBuyTickets.setOrderStatus("未支付");

        //7 8   找不到，先写固定
        orderBuyTickets.setOrderTimestamp("1578727339303");

        //8 8
        orderBuyTickets.setSeatsName(seatsName);


        //在此之前，还要向订单表中插入一条数据
        MoocOrderT moocOrderT = new MoocOrderT();
        moocOrderT.setUuid(String.valueOf(orderId));
        moocOrderT.setCinemaId(cinemaId);
        moocOrderT.setFieldId(Integer.valueOf(fieldId));
        moocOrderT.setFilmId(filmId);
        moocOrderT.setSeatsIds(soldSeats);
        moocOrderT.setSeatsName(seatsName);
        moocOrderT.setFilmPrice(Double.valueOf(price));
        moocOrderT.setOrderPrice(Double.valueOf(orderPrice));
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format1 = simpleDateFormat1.format(new Date());
        try {
            Date parse = simpleDateFormat.parse(format1);
            moocOrderT.setOrderTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //需要登录信息，拿到 userId
       moocOrderT.setOrderUser(userId);
        moocOrderT.setOrderStatus(0);
        Integer insert = moocOrderTMapper.insert(moocOrderT);
        if (insert > 0) {
            System.out.println("插入数据成功");
        }

        return orderBuyTickets;
    }




    /*-------------------------------显示我的订单接口----------------------------------*/

    @Override
    public List<OrderBuyTickets> showOrderInfo(Integer userId) {
        ArrayList<OrderBuyTickets> ticketsArrayList = new ArrayList<>();
        EntityWrapper<MoocOrderT> moocOrderTEntityWrapper = new EntityWrapper<>();
        //这里暂时写上 1  等拿到 user信息了再改
        moocOrderTEntityWrapper.eq("order_user",userId);
        List<MoocOrderT> moocOrderTS = moocOrderTMapper.selectList(moocOrderTEntityWrapper);

        for (MoocOrderT moocOrderT : moocOrderTS) {
            OrderBuyTickets orderBuyTickets = new OrderBuyTickets();
            //中间是封装信息  cinemaName
            Integer cinemaId = moocOrderT.getCinemaId();
            EntityWrapper<MtimeCinemaT> mtimeCinemaTEntityWrapper = new EntityWrapper<>();
            mtimeCinemaTEntityWrapper.eq("UUID",cinemaId);
            List<MtimeCinemaT> mtimeCinemaTS = mtimeCinemaTMapper.selectList(mtimeCinemaTEntityWrapper);
            String cinemaName = mtimeCinemaTS.get(0).getCinemaName();
            orderBuyTickets.setCinemaName(cinemaName);
            //fieldTime
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format = simpleDateFormat.format(new Date());
            orderBuyTickets.setFieldTime(format);
            //filmName
            EntityWrapper<MtimeHallFilmInfoT> hallFilmInfoTEntityWrapper = new EntityWrapper<>();
            hallFilmInfoTEntityWrapper.eq("film_id",moocOrderT.getFilmId());
            List<MtimeHallFilmInfoT> mtimeHallFilmInfoTS = mtimeHallFilmInfoTMapper.selectList(hallFilmInfoTEntityWrapper);
            String filmName = mtimeHallFilmInfoTS.get(0).getFilmName();
            orderBuyTickets.setCinemaName(filmName);
            //orderId
            orderBuyTickets.setOrderId(moocOrderT.getUuid());
            //orderPrice
            String seatsIds = moocOrderT.getSeatsIds();
            String [] soldSeatArr= seatsIds.split(",");
            int length = soldSeatArr.length;
            Double filmPrice = moocOrderT.getFilmPrice();
            Double orderPrice = filmPrice*length;
            orderBuyTickets.setOrderPrice(String.valueOf(orderPrice));
            //orderStatus
            orderBuyTickets.setOrderStatus(String.valueOf(moocOrderT.getOrderStatus()));
            //orderTimestamp
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
            String format1 = simpleDateFormat1.format(new Date());
            orderBuyTickets.setOrderTimestamp(format1);
            //seatsName
            orderBuyTickets.setSeatsName(moocOrderT.getSeatsIds());

            ticketsArrayList.add(orderBuyTickets);
        }
        return ticketsArrayList;
    }



    /*-------------------------------生成二维码接口----------------------------------*/

    @Autowired
    private MoocOrderTMapper orderTMapper;


    @Reference(interfaceClass = CinemaService.class)
    private CinemaService cinemaService;



    @Override
    public OrderPayCinemaVo queryCinemaDetailByOrderId(String orderId) {
        MoocOrderT moocOrderT = orderTMapper.selectOrderByOrderId(orderId);
        OrderPayCinemaVo payCinemaVo = new OrderPayCinemaVo();

        if (moocOrderT!=null){
            payCinemaVo.setCinemaId(moocOrderT.getCinemaId());
            String cinemaName = cinemaService.queryCinemaNameByCinemaId(moocOrderT.getCinemaId());
            payCinemaVo.setCinemaName(cinemaName);
            payCinemaVo.setPrice(String.valueOf(moocOrderT.getOrderPrice()));
        }
        return payCinemaVo;
    }









}
