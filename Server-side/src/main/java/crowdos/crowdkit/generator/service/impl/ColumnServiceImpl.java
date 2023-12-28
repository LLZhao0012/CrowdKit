package crowdos.crowdkit.generator.service.impl;

import crowdos.crowdkit.generator.dao.ColumnDOMapper;
import crowdos.crowdkit.generator.dataobject.ColumnDO;
import crowdos.crowdkit.generator.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {
    @Autowired
    private ColumnDOMapper columnDOMapper;

    @Override
    public List<ColumnDO> getColumnsByTableName(String tableName) {
        return columnDOMapper.getColumnsByTableName(tableName);
    }

    @Override
    public int insertGenTableColumn(ColumnDO columnDO) {
        return columnDOMapper.insertGenTableColumn(columnDO);
    }

    @Override
    public List<ColumnDO> selectByTableId(Long tableId) {
        return columnDOMapper.selectByTableId(tableId);
    }
}
