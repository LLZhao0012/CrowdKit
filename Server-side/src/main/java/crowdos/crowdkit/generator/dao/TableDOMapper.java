package crowdos.crowdkit.generator.dao;

import crowdos.crowdkit.generator.dataobject.TableDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TableDOMapper {
    int deleteByPrimaryKey(Long tableId);

    int insert(TableDO record);

    int insertSelective(TableDO record);

    TableDO selectByPrimaryKey(Long tableId);

    TableDO selectByTableName(String tableName);

    int updateByPrimaryKeySelective(TableDO record);

    int updateByPrimaryKey(TableDO record);

    int createTable(@Param("sql") String sql);

    List<TableDO> selectDbTableListByNames(String[] tableNames);

    int insertGenTable(TableDO tableDO);

    List<TableDO> selectDbTableListByAuthor(String author);

    List<TableDO> selectAll();

    int deleteTable(@Param("tableName") String tableName);

}