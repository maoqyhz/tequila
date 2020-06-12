package xyz.fur.skeleton.entity.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fururur
 * @date 2020/4/2-9:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmdOutput {
    private String in;
    private String err;
}
