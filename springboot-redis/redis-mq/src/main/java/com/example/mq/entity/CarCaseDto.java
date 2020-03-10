package com.example.mq.entity;

import lombok.Data;

import java.util.List;

/**
 * 业务类
 * @author Fururur
 * @create 2020-02-24-17:24
 */
@Data
public class CarCaseDto {
    private String caseNum;
    private List<String> imageUrls;
}
