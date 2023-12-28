package crowdos.crowdkit.generator.service;

import crowdos.crowdkit.generator.dataobject.TableDO;
import crowdos.crowdkit.generator.exception.BusinessException;
import crowdos.crowdkit.generator.viewobject.TableVO;

import java.util.List;
import java.util.Map;

public interface TableService {
    public void createTable(String sql, String author, String tableType) throws BusinessException;

    public void importTable(TableDO tableDO);

    public byte[] genCode(String tableName);

    public List<TableDO> getTablesByAuthor(String author);

    public Map<String, Object> getTablesByPage(int currentPage, int pageSize, String author);

    public void deleteTables(List<TableVO> tableVOList);
}
