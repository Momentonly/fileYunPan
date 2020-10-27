package com.szxy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * restore the mybatis generate sql to original whole sql
 * @author ob
 */
public class RestoreSqlUtil {

    public static final String PREPARING = "Preparing:";
    public static final String PARAMETERS = "Parameters:";
    public static final String SPLIT_LINE = "\n-- ---------------------------------------------------------------------------------------------------------------------\n";


    private static Set<String> needAssembledType = new HashSet<>();
    private static final String QUESTION_MARK = "?";
    private static final String REPLACE_MARK = "_o_?_b_";
    private static final String PARAM_TYPE_REGEX = "\\(\\D{3,30}?\\),{0,1}";

    private static String preparingLine = "";
    private static String parametersLine = "";
    private static boolean isEnd = false;
    private static int indexNum = 1;


    static {
        needAssembledType.add("(String)");
        needAssembledType.add("(Timestamp)");
        needAssembledType.add("(Date)");
        needAssembledType.add("(Time)");
    }

    public static String match(String p, String str) {
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static void main(String[] args) {
        String s = "  getWaitStorageCount - ==>  Preparing: select IFNULL(sum(tcio.plan_in_count - tcio.actual_in_count),0) from t_customer_in_order tcio LEFT JOIN t_customer_out_order tcoi ON tcoi.id = tcio.out_order_id and tcoi.valid = 1 where tcio.valid = 1 and tcio.state in (1,2) and ((tcio.type IN (1,3) AND tcoi.out_type IN (1,3)) OR tcio.type = 2)\n" +
                "2020-09-07 18:13:18.054 DEBUG [http-nio-8020-exec-2] c.l.o.m.TCustomerInOrderMapper.getWaitStorageCount - ==> Parameters:\n" +
                "2020-09-07 18:13:18.057 DEBUG [http-nio-8020-exec-2] c.l.o.m.TCustomerInOrderMapper.getWaitStorageCount - <==      Total: 1\n";
        System.out.println(start(s));
    }

    public static String start(String s){
        StringBuilder sb = new StringBuilder();
        indexNum = 1;
        BufferedReader reader = new BufferedReader(new StringReader(s));
        String currentLine;
        try {
            while ((currentLine = reader.readLine())!= null){
                String s1 = applyFilter(currentLine);
                if(null != s1){
                    sb.append(s1);
                    sb.append(SPLIT_LINE);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }




    public static String applyFilter(final String currentLine) {
        if(currentLine.contains(PREPARING)) {
            preparingLine = currentLine;
            return null;
        }
        if(isEmpty(preparingLine)) {
            return null;
        }
        parametersLine = currentLine.contains(PARAMETERS) ? currentLine : parametersLine + currentLine;
        if(!parametersLine.endsWith("Parameters: ") && !parametersLine.endsWith("null") && !parametersLine.endsWith(")")) {
            return null;
        } else {
            isEnd = true;
        }
        if(isNotEmpty(preparingLine) && isNotEmpty(parametersLine) && isEnd) {
            String preStr = "--  " + indexNum++ + "  " + parametersLine.split(PARAMETERS)[0].trim();//序号前缀字符串
            String restoreSql = RestoreSqlUtil.restoreSql(preparingLine, parametersLine);
            return preStr+"\n"+restoreSql;
        }
        return null;
    }

    public static String restoreSql(final String preparing, final String parameters) {
        String restoreSql = "";
        String preparingSql = "";
        String parametersSql = "";
        try {
            if(preparing.contains(PREPARING)) {
                preparingSql = preparing.split(PREPARING)[1].trim();
            } else {
                preparingSql = preparing;
            }
            boolean hasParam = false;
            if(parameters.contains(PARAMETERS)) {
                if(parameters.split(PARAMETERS).length > 1) {
                    parametersSql = parameters.split(PARAMETERS)[1];
                    if(isNotBlank(parametersSql)) {
                        hasParam = true;
                    }
                }
            } else {
                parametersSql = parameters;
            }
            if(hasParam) {
                preparingSql = replace(preparingSql, QUESTION_MARK, REPLACE_MARK);
                preparingSql = removeEnd(preparingSql, "\n");
                parametersSql = removeEnd(parametersSql, "\n");
                int questionMarkCount = countMatches(preparingSql, REPLACE_MARK);
                String[] paramArray = parametersSql.split(PARAM_TYPE_REGEX);
                for(int i=0; i<paramArray.length; ++i) {
                    if(questionMarkCount <= paramArray.length || parametersSql.indexOf("null") == -1) {
                        break;
                    } else {
                        parametersSql = parametersSql.replaceFirst("null", "null(Null)");
                    }
                    paramArray = parametersSql.split(PARAM_TYPE_REGEX);
                }
                for(int i=0; i<paramArray.length; ++i) {
                    paramArray[i] = removeStart(paramArray[i], " ");
                    parametersSql = replaceOnce(removeStart(parametersSql, " "), paramArray[i], "");
                    String paramType = match("(\\(\\D{3,25}?\\))", parametersSql);
                    preparingSql = replaceOnce(preparingSql, REPLACE_MARK, assembledParamValue(paramArray[i], paramType));
                    paramType = paramType.replace("(", "\\(").replace(")", "\\)") + ", ";
                    parametersSql = parametersSql.replaceFirst(paramType, "");
                }
            }
            restoreSql = simpleFormat(preparingSql);
            if(!restoreSql.endsWith(";")) {
                restoreSql += ";";
            }
        } catch (Exception e) {
            return "restore mybatis sql error!";
        }
        return restoreSql;
    }

    public static String assembledParamValue(String paramValue, String paramType) {
        if(needAssembledType.contains(paramType)) {
            paramValue = "'" + paramValue + "'";
        }
        return paramValue;
    }

    public static String simpleFormat(String sql) {
        if(isNotBlank(sql)) {
            return sql
                    .replaceAll("(?i)\\s+update\\s+", "\nUPDATE ")
                    .replaceAll("(?i)\\s+select\\s+", "\nSELECT ")
                    .replaceAll("(?i)\\s+from\\s+", "\nFROM ")
                    .replaceAll("(?i)\\s+where\\s+", "\nWHERE ")
                    .replaceAll("(?i)\\s+left join\\s+", "\n LEFT JOIN ")
                    .replaceAll("(?i)\\s+right join\\s+", "\n RIGHT JOIN ")
                    .replaceAll("(?i)\\s+inner join\\s+", "\n INNER JOIN ")
                    .replaceAll("(?i)\\s+limit\\s+", "\n LIMIT ")
                    .replaceAll("(?i)\\s+on\\s+", "\n ON ")
                    .replaceAll("(?i)\\s+union\\s+", "\n UNION ")
                    .replaceAll("(?i)\\s+set\\s+", "\nSET ")
                    .replaceAll("(?i)\\s+;\\s+", ";\n");
        }
        return "";
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    public static int countMatches(String str, String sub) {
        if (!isEmpty(str) && !isEmpty(sub)) {
            int count = 0;

            for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }
    public static String removeEnd(String str, String remove) {
        if (!isEmpty(str) && !isEmpty(remove)) {
            return str.endsWith(remove) ? str.substring(0, str.length() - remove.length()) : str;
        } else {
            return str;
        }
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if (end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0 ? 0 : increase;
                increase *= max < 0 ? 16 : (max > 64 ? 64 : max);

                StringBuilder buf;
                for(buf = new StringBuilder(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if (max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }
    public static String removeStart(String str, String remove) {
        if (!isEmpty(str) && !isEmpty(remove)) {
            return str.startsWith(remove) ? str.substring(remove.length()) : str;
        } else {
            return str;
        }
    }

}