package xyz.fur.skeleton.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import xyz.fur.skeleton.config.CustomProperties;
import xyz.fur.skeleton.entity.base.RestResult;
import xyz.fur.skeleton.entity.base.ResultCode;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 文件上传的工具类
 *
 * @author Fururur
 * @create 2019-07-26-9:01
 */
@Slf4j
public class FileUtils {

    public static String foo() {
        CustomProperties prop = SpringUtils.getBean(CustomProperties.class);
        return prop.getImageSavePath();
    }

    /**
     * 根据日期创建保存目录
     *
     * @param imageSavePath 根存储目录
     * @return 返回拼接后的路径
     */
    public static String generatePath(String imageSavePath, String fileName) {
        LocalDate now = LocalDate.now();
        String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        StringBuilder sb = new StringBuilder();
        sb.append(imageSavePath).append(nowStr).append("/").append(fileName);
        return sb.toString();
    }

    /**
     * 文件有效性检查
     *
     * @param files
     * @param maxImageSize
     * @return
     */
    public static RestResult checkFiles(List<MultipartFile> files, Integer maxImageSize) {
        for (int i = 0; i < files.size(); ++i) {
            MultipartFile file = files.get(i);
            String fileName = file.getOriginalFilename();
            String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));

            if (!suffixName.equalsIgnoreCase(".jpg")) {
                log.error("文件名不是以jpg结尾，filename：" + fileName);
                return new RestResult(ResultCode.INVALID_IMAGE_FORMAT_ERROR, null);
            }
            if ((1.0 * file.getSize() / 1024) > maxImageSize) {
                log.error("文件大小超过" + maxImageSize + "K，filename：" + fileName);
                return new RestResult(ResultCode.IMAGE_SIZE_EXCEED_ERROR, null);
            }
        }
        return null;
    }

    /**
     * 保存到文件
     *
     * @param file
     * @param in
     */
    public static void save(File file, InputStream in) {
        try {
            File parent = file.getParentFile();
            if (!parent.exists() || parent.isFile()) {
                parent.mkdirs();
            }
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            BufferedInputStream inputStream = new BufferedInputStream(in);
            byte[] buff = new byte[1024];
            int len;
            while ((len = inputStream.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayBlockingQueue<Integer> list = new ArrayBlockingQueue<Integer>(10);


    }
}
