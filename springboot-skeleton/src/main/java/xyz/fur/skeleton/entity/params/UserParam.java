package xyz.fur.skeleton.entity.params;

import lombok.Data;
import xyz.fur.skeleton.entity.validate.NewEntity;
import xyz.fur.skeleton.entity.validate.UpdateEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author Fururur
 * @create 2019-07-16-17:14
 */
@Data
public class UserParam implements Serializable {
    @NotNull(message = "ID不能为空", groups = {UpdateEntity.class})
    private Long id;
    @NotBlank(message = "姓名不能为空", groups = {NewEntity.class})
    private String name;
    private Integer age;
    @Pattern(regexp = "[a-zA-z]+://[^\\s]*", message = "项目地址格式不正确,请输入合法的格式")
    private String address;
}
