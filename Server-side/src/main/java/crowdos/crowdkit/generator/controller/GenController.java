package crowdos.crowdkit.generator.controller;

import crowdos.crowdkit.generator.exception.BusinessException;
import crowdos.crowdkit.generator.exception.EmBusinessError;
import crowdos.crowdkit.generator.model.UserModel;
import crowdos.crowdkit.generator.response.CommonReturnType;
import crowdos.crowdkit.generator.service.TableService;
import crowdos.crowdkit.generator.service.TestService;
import crowdos.crowdkit.generator.viewobject.TableVO;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GenController {
    @Autowired
    TableService tableService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/")
    public String test(){
        return "index";
    }

    @RequestMapping("/apis/createTable")
    @ResponseBody
    public CommonReturnType create(@RequestParam(name="sql") String sql,
                                   @RequestParam(name = "type")String tableType) throws BusinessException {
        System.out.println(sql);
        //用户校验
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登录，不能创建");
        }

        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");

        tableService.createTable(sql,userModel.getName(),tableType);
        return CommonReturnType.create("创建成功");
    }
    @RequestMapping("/apis/genCode")
    public void genCode(HttpServletResponse response, @RequestParam(name="name") String tableName) throws IOException {
        System.out.println("开始生成代码" + tableName);
        byte[] data =  tableService.genCode(tableName);
        genCode(response, data);
        System.out.println("代码生成完毕");
    }

    @RequestMapping("/apis/multiGenCode")
    public void vuetest(HttpServletResponse response, @RequestBody List<TableVO> tableVOList) throws IOException {
        System.out.println("批量生成代码开始");
        List<byte[]> dataList = new ArrayList<byte[]>();
        for(TableVO tableVO: tableVOList){
            dataList.add(tableService.genCode(tableVO.getTableName()));
        }
        genCode(response, dataList);
        System.out.println("批量生成代码完毕");
    }


    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        response.reset();
        response.setHeader("Access-Control-Expose-Headers", "content-disposition");
        response.setHeader("Content-Disposition", "attachment; filename=crowdDemo.zip");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }

    private void genCode(HttpServletResponse response, List<byte[]> data) throws IOException
    {
        response.reset();
        response.setHeader("Access-Control-Expose-Headers", "content-disposition");
        response.setHeader("Access-Control-Expose-Headers", "content-disposition");
        response.setHeader("Content-Disposition", "attachment; filename=crowdDemo.zip");
//        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        for(int i = 0; i < data.size(); i++){
            IOUtils.write(data.get(i), response.getOutputStream());
        }

    }
}
