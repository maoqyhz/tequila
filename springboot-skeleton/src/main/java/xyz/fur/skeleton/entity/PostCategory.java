package xyz.fur.skeleton.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Fururur
 * @date 2020/3/25-11:13
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(value = "dmsPostCategory")
public class PostCategory {
    @Id
    private String id;
    private String name;
    private String parent;
    private Map<String, Object> project;
    private Map<String, Object> createdBy;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
}