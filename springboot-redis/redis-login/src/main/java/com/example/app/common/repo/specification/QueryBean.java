package com.example.app.common.repo.specification;

import lombok.Data;

/**
 * 封装查询 Bean
 * @author Fururur
 * @create 2019-07-19-9:58
 */
@Data
public class QueryBean {
    /**
     * 操作符的key，如查询时的name,id之类
     */
    private String key;
    /**
     * 操作符的value，具体要查询的值
     */
    private Object value;
    /**
     * 操作符，自己定义的一组操作符，用来方便查询
     */
    private Ops operator;
    /**
     * 连接的方式：and或者or
     */
    private Join join;

    public enum Join {
        and, or
    }
}


