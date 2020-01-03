package com.cskaoyan.controller.wx;

import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.mapper.AddressMapper;
import com.cskaoyan.service.wx.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WxAddressController {


    @Autowired
    AddressService addressService;

    @RequestMapping("wx/address/list")
    public String addressList(){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        String addresses = addressService.showAddressList();
        baseRespVo.setData(addresses);
        return addresses;
    }
}
