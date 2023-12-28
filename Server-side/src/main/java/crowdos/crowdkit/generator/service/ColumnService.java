package crowdos.crowdkit.generator.service;

import crowdos.crowdkit.generator.dataobject.ColumnDO;

import java.util.List;

public interface ColumnService {
    public List<ColumnDO>  getColumnsByTableName(String tableName);

    public int insertGenTableColumn(ColumnDO columnDO);

    public List<ColumnDO> selectByTableId(Long tableId);
}
