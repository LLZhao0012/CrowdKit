package crowdos.crowdkit.generator.service.impl;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import crowdos.crowdkit.generator.constant.Constants;
import crowdos.crowdkit.generator.dao.ColumnDOMapper;
import crowdos.crowdkit.generator.dao.TableDOMapper;
import crowdos.crowdkit.generator.dataobject.ColumnDO;
import crowdos.crowdkit.generator.dataobject.TableDO;
import crowdos.crowdkit.generator.exception.BusinessException;
import crowdos.crowdkit.generator.exception.EmBusinessError;
import crowdos.crowdkit.generator.model.TableModel;
import crowdos.crowdkit.generator.service.ColumnService;
import crowdos.crowdkit.generator.service.TableService;
import crowdos.crowdkit.generator.utils.*;
import crowdos.crowdkit.generator.viewobject.TableVO;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class TableServiceImpl implements TableService {
    @Autowired
    private TableDOMapper tableDOMapper;
    @Autowired
    private ColumnDOMapper columnDOMapper;
    @Autowired
    private ColumnService columnService;
    @Override
    public void createTable(String sql, String author, String tableType) throws BusinessException{
        try{
            SqlUtil.filterKeyword(sql);
            List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, DbType.mysql);
            List<String> tableNames = new ArrayList<>();
            for(SQLStatement sqlStatement: sqlStatements){
                if(sqlStatement instanceof MySqlCreateTableStatement){
                    MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
                    System.out.println(createTableStatement.toString());
                    //利用sql语句真正的创建表
                    if(tableDOMapper.createTable(createTableStatement.toString()) == 0){
                        String tableName = createTableStatement.getTableName().replaceAll("`", "");
                        tableNames.add(tableName);
                    }
                }
            }
            //获取创建表的信息
            List<TableDO> tableDOList = tableDOMapper.selectDbTableListByNames(tableNames.toArray(new String[tableNames.size()]));
            String userName = author;
            for(TableDO tableDO: tableDOList){
                //初始化表信息
                GenUtils.initTable(tableDO, userName);
                tableDO.setTableType(tableType);
                //在数据表信息表和数据列信息表中添加数据
                importTable(tableDO);
            }
        }catch (Exception e){
            throw new BusinessException(EmBusinessError.TABLE_CREATE_FAIL, e.getMessage());
        }

    }

    @Override
    public void importTable(TableDO tableDO) {
        int row = tableDOMapper.insertGenTable(tableDO);
        if(row > 0){
            System.out.println(tableDO.getTableName());
            List<ColumnDO> columnDOList = columnService.getColumnsByTableName(tableDO.getTableName());
            for(ColumnDO columnDO: columnDOList){
                GenUtils.initColumnField(columnDO, tableDO);
                columnService.insertGenTableColumn(columnDO);
            }
        }
    }

    @Override
    public byte[] genCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 根据表名获取表模型
        TableDO tableDO = tableDOMapper.selectByTableName(tableName);
        List<ColumnDO> columnDOList = columnService.selectByTableId(tableDO.getTableId());
        TableModel tableModel = convertFromDO(tableDO, columnDOList);
        // 初始化模板引擎
        VelocityInitializer.initVelocity();
        // 加载表信息
        VelocityContext context = VelocityUtils.prepareContext(tableModel);
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList();
        // 加载模板 组装数据
        for(String template : templates){
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, tableModel)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                System.out.println("渲染模板失败，表名：" + tableModel.getTableName());
                e.printStackTrace();
            }
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public List<TableDO> getTablesByAuthor(String author) {
        return tableDOMapper.selectDbTableListByAuthor(author);
    }

    @Override
    public Map<String, Object> getTablesByPage(int currentPage, int pageSize, String author) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        List<TableDO> list= tableDOMapper.selectDbTableListByAuthor(author);
        int total = tableDOMapper.selectDbTableListByAuthor(author).size();
        PageInfo<TableDO> pageInfo = new PageInfo<TableDO>(list);
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo);
        map.put("totalSize", total);
        return map;
    }

    @Override
    @Transactional
    public void deleteTables(List<TableVO> tableVOList) {
        for(TableVO tableVO: tableVOList){
            //删除gen_table中的表信息
            tableDOMapper.deleteByPrimaryKey(tableVO.getTableId());
            //删除gen_table_column中的列信息
            columnDOMapper.deleteByTableId(tableVO.getTableId());
            //删除系统数据库中的表
            tableDOMapper.deleteTable(tableVO.getTableName());
        }

    }

    private TableModel convertFromDO(TableDO tableDO, List<ColumnDO> columnDOList){
        if(tableDO == null){
            return null;
        }
        TableModel tableModel = new TableModel();
        BeanUtils.copyProperties(tableDO, tableModel);
        tableModel.setColumns(columnDOList);
        //设置主键列信息
        for(ColumnDO columnDO: columnDOList){
            if(columnDO.isPk()){
                tableModel.setPkColumn(columnDO);
            }
        }
        if(StringUtils.isNull(tableModel.getPkColumn())){
            tableModel.setPkColumn(columnDOList.get(0));
        }
        return tableModel;
    }
}
