package crowdos.crowdkit.generator.utils;

import crowdos.crowdkit.generator.exception.UtilException;

public class SqlUtil {
    public static String SQL_REGEX = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare ";
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new UtilException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    public static void filterKeyword(String value) {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (int i = 0; i < sqlKeywords.length; i++) {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeywords[i]) > -1) {
                throw new UtilException("参数存在SQL注入风险");
            }
        }
    }
}
