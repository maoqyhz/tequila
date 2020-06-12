package com.example.seed.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 测试实体
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "volume")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "userid")
    private Integer userId;
    private String capacity;
    private Integer status;
    private String name;
    private String path;
    private String ip;
    @Column(name = "volumepath")
    private String volumePath;
    @Column(name = "displayname")
    private String displayName;
    private Integer type;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createtime")
    private LocalDateTime createTime;
}