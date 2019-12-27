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
@MappedTypes(Integer[].class)
public class String2IntegerArrayTypeHandler implements TypeHandler<Integer[]> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, Integer[] integers, JdbcType jdbcType) throws SQLException {
        //存入
        ObjectMapper objectMapper = new ObjectMapper();
        String s = null;
        try {
            s = objectMapper.writeValueAsString(integers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        preparedStatement.setString(index,s);
    }

    /**
     * 取出字符串数据，并且封装成Integer[]格式的过程
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException
     */
    @Override
    public Integer[] getResult(ResultSet resultSet, String column) throws SQLException {
        String result = resultSet.getString(column);
        return transfer(result);
    }

    @Override
    public Integer[] getResult(ResultSet resultSet, int index) throws SQLException {
        String result = resultSet.getString(index);
        return transfer(result);
    }

    @Override
    public Integer[] getResult(CallableStatement callableStatement, int index) throws SQLException {
        String result = callableStatement.getString(index);
        return transfer(result);
    }

    /**
     * 将string转为int数组
     * @param result
     * @return
     */
    private Integer[] transfer(String result){
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] integers = new Integer[0];
        try {
            integers = objectMapper.readValue(result, Integer[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return integers;
    }
}
