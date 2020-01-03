package com.cskaoyan.service.wx;

import com.cskaoyan.bean.Address;
import com.cskaoyan.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{


    @Autowired
    AddressMapper addressMapper;

    /**
     * 显示收获地址
     * @return
     */
    @Override
    public String showAddressList() {
        List<String> strings = addressMapper.queryAddress(1);
        String result = "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"data\": [{\n" +
                "\t\t\"isDefault\": false,\n" +
                "\t\t\"detailedAddress\": \"湖北省武汉市洪山区 软件新城2期C13\",\n" +
                "\t\t\"name\": \"充满鲜花\",\n" +
                "\t\t\"mobile\": \"13313331331\",\n" +
                "\t\t\"id\": 34\n" +
                "\t}, {\n" +
                "\t\t\"isDefault\": false,\n" +
                "\t\t\"detailedAddress\": \"湖北省武汉市洪山区 软件新城2期C13\",\n" +
                "\t\t\"name\": \"的世界\",\n" +
                "\t\t\"mobile\": \"18818881888\",\n" +
                "\t\t\"id\": 35\n" +
                "\t}, {\n" +
                "\t\t\"isDefault\": true,\n" +
                "\t\t\"detailedAddress\": \"湖北省武汉市洪山区 软件新城2期C13\",\n" +
                "\t\t\"name\": \"蓝先生\",\n" +
                "\t\t\"mobile\": \"13838384381\",\n" +
                "\t\t\"id\": 36\n" +
                "\t}, {\n" +
                "\t\t\"isDefault\": false,\n" +
                "\t\t\"detailedAddress\": \"湖北省武汉市洪山区 软件新城2期C13\",\n" +
                "\t\t\"name\": \"如果他\",\n" +
                "\t\t\"mobile\": \"18050959159\",\n" +
                "\t\t\"id\": 37\n" +
                "\t}, {\n" +
                "\t\t\"isDefault\": false,\n" +
                "\t\t\"detailedAddress\": \"湖北省武汉市洪山区 软件新城2期C13\",\n" +
                "\t\t\"name\": \"真的存在\",\n" +
                "\t\t\"mobile\": \"18050959159\",\n" +
                "\t\t\"id\": 38\n" +
                "\t}],\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        return result;
    }
}
