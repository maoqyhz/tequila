package xyz.fur.skeleton.jobs;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import xyz.fur.skeleton.config.CustomProperties;
import xyz.fur.skeleton.utils.FileUtils;
import xyz.fur.skeleton.utils.SpringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

/**
 * 多线程文件上传线程类，并且通过Spring IOC管理
 *
 * @author Fururur
 * @Create 2019-07-30
 */
@Slf4j
@NoArgsConstructor
@Scope("prototype")
@Component(value = "uploadThread")
public class UploadThread implements Runnable {
    private List<ByteArrayInputStream> files;
    private List<String> fileNameList;
    private CustomProperties prop = SpringUtils.getBean(CustomProperties.class);

    // 如果直接传入MutiPartFile，文件会无法存入，因为对象传递后spring会将tmp文件缓存清除
    public UploadThread(List<ByteArrayInputStream> files, List<String> fileNameList) {
        this.files = files;
        this.fileNameList = fileNameList;
    }

    @Override
    public void run() {
        for (int i = 0; i < files.size(); ++i) {
            String fileName = fileNameList.get(i);
            String filePath = FileUtils.generatePath(prop.getImageSavePath(),fileName);
            log.info(filePath);
            FileUtils.save(new File(filePath), files.get(i));
        }
    }
}
