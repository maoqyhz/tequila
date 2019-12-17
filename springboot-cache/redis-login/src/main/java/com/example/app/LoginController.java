package com.example.app;

import com.alibaba.fastjson.JSONObject;
import com.example.app.common.entity.RestResult;
import com.example.app.common.entity.ResultCode;
import com.example.app.common.exception.ServiceException;
import com.example.app.utils.JsonUtils;
import com.google.common.base.Strings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Fururur
 * @create 2019-12-13-16:36
 */
@RestController
@RequestMapping("auth")
public class LoginController {
    private final IUserService userService;

    public LoginController(IUserService userService) {
        this.userService = userService;
    }


    @PostMapping("login")
    public RestResult login(HttpServletRequest request) {
        JSONObject params = JsonUtils.parserJson(request);
        String username = params.getString("username");
        String password = params.getString("password");
        try {
            String token = userService.checkLogin(username, password);
            if (!Strings.isNullOrEmpty(token)) {
                return new RestResult(ResultCode.LOGIN_SUCCESS.getCode(), new HashMap<String, String>() {{
                    put("token", token);
                }});
            }
        } catch (ServiceException e) {
            return new RestResult(e.getCode(), null);
        }
        return null;
    }
}
