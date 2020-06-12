package xyz.fur.skeleton.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import xyz.fur.skeleton.config.CustomProperties;
import xyz.fur.skeleton.config.ParamAssert;
import xyz.fur.skeleton.entity.User;
import xyz.fur.skeleton.entity.base.Pagination;
import xyz.fur.skeleton.entity.base.RestResult;
import xyz.fur.skeleton.entity.validate.NewEntity;
import xyz.fur.skeleton.services.IUserService;
import xyz.fur.skeleton.utils.FileUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * crud示例
 *
 * @author Fururur
 * @create 2019-07-16-17:17
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UsersController extends BaseController {
    private final IUserService userService;
    private final CustomProperties prop;

    public UsersController(IUserService userService, CustomProperties prop) {
        this.userService = userService;
        this.prop = prop;
    }

    @GetMapping("test")
    public Object test() {
        return prop.getImageSavePath();
    }

    @GetMapping("count")
    public Object count() {
        return userService.countBy();
    }

    @GetMapping("/getById")
    public Object getById(@RequestParam Long id) {
        ParamAssert.notNull(id, "id不能为空");
        return userService.findById(id);
    }

    @PostMapping("/add")
    public Object add(@RequestBody @Validated({NewEntity.class}) User u, BindingResult bindingResult) {
        ParamAssert.checkBindingResult(bindingResult);
        userService.save(u);
        return u;
    }

    @GetMapping("/findUserByConditions")
    public RestResult findUserByConditions(int age, int pageNo, int pageSize) {
        Page<User> userPage = userService.findUserByConditions(age, pageNo, pageSize);
        Pagination<User> p = new Pagination<>(pageNo, pageSize);
        if (userPage.hasContent()) {
            p.setItems(userPage.getContent());
            p.setTotal(userPage.getTotalElements());
            p.setTotalPages(userPage.getTotalPages());
        }
        return RestResult.ok(p);
    }

    @Resource(name = "uploadThreadPool")
    private ExecutorService uploadThreadPool;

    /**
     * 图片异步上传
     * MultipartFile的TransferTo的是同步的方法，需要等待
     *
     * @param request
     * @return
     */
    @PostMapping("/upload")
    @RequestMapping
    public RestResult upload(HttpServletRequest request) {
        long start = System.currentTimeMillis();
        String taskId = request.getParameter("taskId");
        log.info("taskID:" + taskId);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        if (files.isEmpty()) {
            return RestResult.invalidParams();
        }
        RestResult isValidFiles = FileUtils.checkFiles(files, prop.getMaxImageSize());

        //仅支持JPG格式图片
        if (isValidFiles != null) {
            return isValidFiles;
        }

        // MultipartFile传出后，会自动清理。异步任务前先copy出一份
        List<ByteArrayInputStream> fileCopyList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        for (MultipartFile f : files) {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(IOUtils.toByteArray(f.getInputStream()));
                fileCopyList.add(byteArrayInputStream);
                fileNameList.add(f.getOriginalFilename());
            } catch (Exception e) {
                log.error("Failed to convert image stream to byte array: " + e);
            }
        }
        return RestResult.ok();
    }
}
