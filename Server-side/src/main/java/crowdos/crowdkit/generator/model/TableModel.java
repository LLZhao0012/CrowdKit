package crowdos.crowdkit.generator.model;

import crowdos.crowdkit.generator.dataobject.ColumnDO;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class TableModel {
    private Long tableId;
    @NotBlank(message = "表名称不能为空")
    private String tableName;
    @NotBlank(message = "表描述不能为空")
    private String tableComment;
    @NotBlank(message = "实体类名称不能为空")
    private String className;
    @NotBlank(message = "生成包路径不能为空")
    private String packageName;
    @NotBlank(message = "作者不能为空")
    private String author;
    @NotBlank(message = "表类型不能为空")
    private String tableType;
    private ColumnDO pkColumn;
    private String parentClassName;
    private List<ColumnDO> columns;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ColumnDO getPkColumn() {
        return pkColumn;
    }

    public void setPkColumn(ColumnDO pkColumn) {
        this.pkColumn = pkColumn;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public List<ColumnDO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDO> columns) {
        this.columns = columns;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }
}
