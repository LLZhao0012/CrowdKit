package crowdos.crowdkit.generator.viewobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TableVO {
    private Long tableId;
    private String tableName;
    private String tableComment;

    public TableVO(Long tableId, String tableName, String tableComment) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.tableComment = tableComment;
    }

    public TableVO() {
    }

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

    @Override
    public String toString() {
        return "TableVO{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                '}';
    }
}
