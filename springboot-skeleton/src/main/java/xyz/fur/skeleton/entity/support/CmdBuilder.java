package xyz.fur.skeleton.entity.support;

import com.google.common.base.Joiner;
import lombok.Builder;

/**
 * 命令常量
 *
 * @author Fururur
 * @date 2020/3/30-16:23
 */
@Builder
public class CmdBuilder {
    // smb.conf 配置文件路径
    private static final String SMB_CONFIG_PATH = "/etc/samba/smb.conf";

    /**
     * mv
     *
     * @param from
     * @param to
     * @return
     */
    public static String mv(String from, String to) {
        return Joiner.on(" ").join("mv", from, to);
    }

    /**
     * rm
     *
     * @param flag
     * @param path
     * @return
     */
    public static String rm(String flag, String path) {
        return Joiner.on(" ").join("rm", flag, path);
    }

    /**
     * 递归创建文件夹
     *
     * @param path
     * @return
     */
    public static String mkdir(String flag, String path) {
        return Joiner.on(" ").join("mkdir", flag, path);
    }

    /**
     * 配置 dir 权限
     *
     * @param flag       flag 可选 -R
     * @param permission 权限 777 755 750
     * @param path       路径
     * @return
     */
    public static String chmod(String flag, String permission, String path) {
        return Joiner.on(" ").join("chmod", flag, permission, path);
    }

    /**
     * systemctl
     *
     * @param flag       flag 可选 -R
     * @param permission 权限 777 755 750
     * @param path       路径
     * @return
     */
    public static String systemctl(String command, String service) {
        return Joiner.on(" ").join("systemctl", command, service);
    }

    /**
     * 查看当前路径下的磁盘空间
     *
     * @param path
     * @return
     */
    public static String df(String path) {
        return Joiner.on(" ").join("df -h", path);
    }


    /**
     * 创建软链接
     *
     * @param src
     * @param dest
     * @return
     */
    public static String ln(String src, String dest) {
        return Joiner.on(" ").join("ln -s", src, dest);
    }



    public static String host(String ip, Integer port) {
        return Joiner.on(":").join(ip, port);
    }


}
