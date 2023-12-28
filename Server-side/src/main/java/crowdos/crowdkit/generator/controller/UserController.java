package crowdos.crowdkit.generator.controller;

import crowdos.crowdkit.generator.exception.BusinessException;
import crowdos.crowdkit.generator.exception.EmBusinessError;
import crowdos.crowdkit.generator.model.UserModel;
import crowdos.crowdkit.generator.response.CommonReturnType;
import crowdos.crowdkit.generator.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/apis/login")
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="name")String name,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 参数校验
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.validateLogin(name,EncodeByMd5(password));

        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/apis/register")
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="name")String name,
                                     @RequestParam(name="password")String password,
                                     @RequestParam(name="phone")String phone) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setPhone(phone);
        userModel.setEncrptPassword(EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}
