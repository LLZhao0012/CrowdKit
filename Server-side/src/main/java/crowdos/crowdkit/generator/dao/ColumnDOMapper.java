package crowdos.crowdkit.generator.dao;

import crowdos.crowdkit.generator.dataobject.ColumnDO;

import java.util.List;

public interface ColumnDOMapper {
    int deleteByPrimaryKey(Long columnId);

    int insert(ColumnDO record);

    int insertSelective(ColumnDO record);

    ColumnDO selectByPrimaryKey(Long columnId);

    int updateByPrimaryKeySelective(ColumnDO record);

    int updateByPrimaryKey(ColumnDO record);

    List<ColumnDO> getColumnsByTableName(String tableName);

    List<ColumnDO> selectByTableId(Long tableId);

    int insertGenTableColumn(ColumnDO columnDO);

    int deleteByTableId(Long tableId);
}