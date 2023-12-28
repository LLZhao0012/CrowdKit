package crowdos.crowdkit.generator.utils;


import crowdos.crowdkit.generator.config.GenConfig;
import crowdos.crowdkit.generator.constant.GenConstants;
import crowdos.crowdkit.generator.dataobject.ColumnDO;
import crowdos.crowdkit.generator.model.TableModel;
import org.apache.velocity.VelocityContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VelocityUtils {
    private static final String PROJECT_PATH = "src/main/java";

    private static final String MYBATIS_PATH = "src/main/resources/mapping";

    private static final String TEMPLATES_PATH = "src/main/resources/templates";

    private static final String DEFAULT_PARENT_MENU_ID = "3";

    public static VelocityContext prepareContext(TableModel genTable) {

        String packageName = genTable.getPackageName();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("functionName", genTable.getClassName());
        velocityContext.put("moduleName", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("author", genTable.getAuthor());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("columns", genTable.getColumns());
        velocityContext.put("parentClass", "");
        velocityContext.put("table", genTable);
        velocityContext.put("tableType", genTable.getTableType());
        return velocityContext;
    }


    public static List<String> getTemplateList() {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/mapper.java.vm");
        templates.add("vm/java/service.java.vm");
        templates.add("vm/java/serviceImpl.java.vm");
        templates.add("vm/java/controller.java.vm");
        templates.add("vm/xml/mapper.xml.vm");
        return templates;
    }

    public static String getFileName(String template, TableModel genTable) {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();

        // 大写类名
        String className = genTable.getClassName();


        String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");
        String mybatisPath = MYBATIS_PATH + "/";


        if (template.contains("domain.java.vm")) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, className);
        } else if (template.contains("mapper.java.vm")) {
            fileName = StringUtils.format("{}/dao/{}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StringUtils.format("{}/service/{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StringUtils.format("{}/{}Mapper.xml", mybatisPath, className);
        }
        return fileName;
    }

    public static String getProjectPath() {
        String packageName = GenConfig.getPackageName();
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(packageName.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }

    public static String getPackagePrefix(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StringUtils.substring(packageName, 0, lastIndex);
        return basePackage;
    }

    public static HashSet<String> getImportList(TableModel genTable) {
        List<ColumnDO> columns = genTable.getColumns();
        HashSet<String> importList = new HashSet<String>();
        for (ColumnDO column : columns) {
            if (!column.isSuperColumn() && GenConstants.TYPE_DATE.equals(column.getJavaType())) {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            } else if (!column.isSuperColumn() && GenConstants.TYPE_BIGDECIMAL.equals(column.getJavaType())) {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    public static String getPermissionPrefix(String moduleName, String businessName) {
        return StringUtils.format("{}:{}", moduleName, businessName);
    }

}