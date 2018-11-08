package com.example.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author yanli.zhang
 * @dateTime 2018/11/8 11:25
 */
public interface MyMapper<T>  extends Mapper<T>, MySqlMapper<T> {

}