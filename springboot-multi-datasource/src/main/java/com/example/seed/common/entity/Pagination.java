package com.example.seed.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页数据包装类
 * @author Fururur
 * @create 2019-07-23-14:53
 */
@Data
@NoArgsConstructor
public class Pagination<T> {
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalPages;
    private Long total;
    private List<T> items;

    public Pagination(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
