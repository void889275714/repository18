package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.bean.AdminExample;
import com.cskaoyan.bean.ListAdminCondition;
import com.cskaoyan.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemAdminServiceImpl implements SystemAdminService {


    @Autowired
    AdminMapper adminMapper;

    /**
     * 管理员的显示及查询
     * @param listAdminCondition
     * @return
     */
    @Override
    public Map<String, Object> showAdminList(ListAdminCondition listAdminCondition) {
        //处理分页参数
        int page = listAdminCondition.getPage();
        int limit = listAdminCondition.getLimit();
        PageHelper.startPage(page,limit);
        String order = listAdminCondition.getOrder();
        String sort = listAdminCondition.getSort();
        String username = listAdminCondition.getUsername();
        String s = sort + " " + order;


        AdminExample adminExample = new AdminExample();
        adminExample.setOrderByClause(s);
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andDeletedEqualTo(false);



        if (username != null ){
            criteria.andUsernameLike("%" + username + "%");
        }
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        HashMap<String, Object> map = new HashMap<>();
        long size = adminMapper.countByExample(adminExample);
        map.put("total",(int)size);
        map.put("items",admins);
        return map;
    }


    /**
     * 系统管理 --> 管理员 --> 新增
     * @param admin
     * @return
     */
    @Override
    public Admin createAdmin(Admin admin) {
        adminMapper.insertAndGenerateId(admin);
        return admin;
    }

    /**
     * 系统管理 --> 管理员 --> 修改
     * 修改的时候输入的密码什么的没有判断，也就是输入错误的没返回信息，或者跳转之类的
     * @param admin
     * @return
     */
    @Override
    public Admin updateAdmin(Admin admin) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        admin.setUpdateTime(parse);
        adminMapper.updateByPrimaryKey(admin);
        Integer id = admin.getId();
        Admin admin1 = adminMapper.selectByPrimaryKey(id);
        return admin1;
    }


    /**
     * 管理员 --> 假删
     * @param admin
     * @return
     */
    @Override
    public boolean deleteAdmin(Admin admin) {
        admin.setDeleted(true);
        int update = adminMapper.updateByPrimaryKey(admin);
        if (update > 0) {
            return true;
        }
        return false;
    }


}
