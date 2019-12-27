package com.cskaoyan.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String[].class)
public class String2StringArrayTypeHandler implements TypeHandler<String[]> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, String[] strings, JdbcType jdbcType) throws SQLException {
        //存入的过程
        //使用json 是方便转换过程
        ObjectMapper objectMapper = new ObjectMapper();
        //这是存入过程
        String s = null;
        try {
            s = objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        preparedStatement.setString(index,s);
    }
    /*
    * 取出字符串数据，并且封装成String[]格式的过程
    * */
    @Override
    public String[] getResult(ResultSet resultSet, String column) throws SQLException {
        String result = resultSet.getString(column);
        return transfer(result);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int index) throws SQLException {
        String result = resultSet.getString(index);
        return transfer(result);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int index) throws SQLException {
        String result = callableStatement.getString(index);
        return transfer(result);
    }

    private String[] transfer(String result){
        ObjectMapper objectMapper = new ObjectMapper();
        String[] strings = new String[0];
        try {
            strings = objectMapper.readValue(result, String[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
