package crowdos.crowdkit.generator.viewobject;

import crowdos.crowdkit.generator.dataobject.ColumnDO;

import java.util.List;

public class TableInfoVO {
    private Long tableId;
    private String tableName;
    private String tableComment;
    private String className;
    private String author;
    private List<ColumnDO> columnDOList;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<ColumnDO> getColumnDOList() {
        return columnDOList;
    }

    public void setColumnDOList(List<ColumnDO> columnDOList) {
        this.columnDOList = columnDOList;
    }

    @Override
    public String toString() {
        return "TableInfoVO{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", className='" + className + '\'' +
                ", author='" + author + '\'' +
                ", columnDOList=" + columnDOList +
                '}';
    }
}
