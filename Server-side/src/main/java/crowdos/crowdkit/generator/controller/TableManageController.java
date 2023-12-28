package crowdos.crowdkit.generator.controller;

import com.github.pagehelper.PageInfo;
import crowdos.crowdkit.generator.dataobject.TableDO;
import crowdos.crowdkit.generator.response.CommonReturnType;
import crowdos.crowdkit.generator.service.TableService;
import crowdos.crowdkit.generator.viewobject.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TableManageController {

    @Autowired
    TableService tableService;

    @RequestMapping("/apis/getTable")
    @ResponseBody
    public CommonReturnType getTablesByAuthor(){
        List<TableDO> list = tableService.getTablesByAuthor("syb");
        List<TableVO> result = new ArrayList<TableVO>();
        for(TableDO table: list){
            TableVO tableVO = new TableVO(table.getTableId(), table.getTableName(), table.getTableComment());
            result.add(tableVO);
        }
        return CommonReturnType.create(result);
    }

    @RequestMapping("/apis/getTableByPage")
    @ResponseBody
    public CommonReturnType getTablesByPage(@RequestParam(name="currentPage")int currentPage,
                                            @RequestParam(name="pageSize") int pageSize){
        System.out.println(currentPage + "    " + pageSize);
        PageInfo<TableDO> pageInfo = null;
        Map<String, Object> map = tableService.getTablesByPage(currentPage,pageSize,"syb");
        return CommonReturnType.create(map);
    }


    @RequestMapping("/apis/deleteTable")
    @ResponseBody
    public CommonReturnType deleteTable(@RequestBody List<TableVO> tableVOList){
        for(TableVO tableVO : tableVOList){
            System.out.println(tableVO);
        }
        tableService.deleteTables(tableVOList);
        return CommonReturnType.create("删除成功");
    }
}
