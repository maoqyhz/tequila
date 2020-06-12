package xyz.fur.skeleton.utils;

/**
 * 图像工具类
 * @author Fururur
 * @create 2019-07-24-15:55
 */
public class ImageUtils {
    public static boolean isJpegImage(byte[] b) {
        if (b.length > 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            System.out.println(Integer.toHexString(b0));
            System.out.println(Integer.toHexString(b1));
            if (b0 == 0xffffffff && b1 == 0xffffffd8 && b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                return true;
            }
        }
        return false;
    }
}
