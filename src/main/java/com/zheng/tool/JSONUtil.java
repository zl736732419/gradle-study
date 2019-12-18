package com.zheng.tool;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class JSONUtil {
    private static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            initObjectMapper();
        }
        return objectMapper;
    }

    /**
     * Using a generic method, convert json string to the corresponding JavaBean
     * object.
     *
     * @param content   json string
     * @param valueType object type
     */
    public static <T> T readValue(String content, Class<T> valueType) {
        if (objectMapper == null) {
            initObjectMapper();
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Escape the string,then convert it to the corresponding JavaBean
     *
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T readEscapedValue(String content, Class<T> valueType) {
        if (objectMapper == null) {
            initObjectMapper();
        }
        try {
            String escpe = JsonEscaper.escape(content);
            return objectMapper.readValue(escpe, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Convert JavaBean to json string
     *
     * @param object
     * @return json
     */
    public static String toJson(Object object) {
        if (objectMapper == null) {
            initObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Using a generic method, convert json string to the List of the generic
     * type List<T>.
     *
     * @param content
     * @param collectionClass
     * @param elementClasses
     * @return
     */
    public static <T> List<T> readListValue(String content, Class<?> collectionClass, Class<?> elementClasses) {
        if (objectMapper == null) {
            initObjectMapper();
        }
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return objectMapper.readValue(content, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> List<T> readEscapedListValue(String content, Class<?> collectionClass, Class<?> elementClasses) {
        if (null == content || content.length() == 0) {
            return null;
        }
        String escpe = JsonEscaper.escape(content);

        return readListValue(escpe, collectionClass, elementClasses);
    }

    /**
     * Json string format, for indent Blank.
     *
     * @param source json string
     * @return
     */
    public static String formatJson(String source) {
        if (null == source || "".equals(source)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < source.length(); i++) {
            last = current;
            current = source.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
    }

    private static void initObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }


    private static class JsonEscaper {
        //该map用于纠正错误的json数据
        private static final Map<String, String> jsonErrorMap ;
        static{
            //错误数据map 为方便扩展
            jsonErrorMap = new HashMap<String, String>();
            jsonErrorMap.put("\\", "");	  //当遇到  \  时清除 \
        }

        /**
         * 该方法主要用于纠正错误的json数据
         * @Title: redressJson
         * @Description: TODO
         * @param jsonStr
         * @return
         * @throws
         */
        //如果json中包含错误的数据 则进行强制替换
        public static String redressJson(String jsonStr){
            if (null != jsonStr && jsonStr.length() != 0) {
                Set<Map.Entry<String, String>> entrySet = jsonErrorMap.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    if (jsonStr.contains(entry.getKey())) {
                        jsonStr = jsonStr.replace(entry.getKey(), entry.getValue());
                    }
                }
            }
            return jsonStr;
        }

        public static String escape(String str) {
            if (null == str || str.length() == 0) {
                return "";
            }
            str = redressJson(str);
            str = Pattern.compile("\t|\r|\n").matcher(str).replaceAll("");
            // " to \"
            char[] temp = str.toCharArray();
            int n = temp.length;
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    continue;
                }
                if (temp[i] == ':' && temp[i + 1] == '"' && temp[i - 1] == '"') {
                    for (int j = i + 2; j < n; j++) {
                        if (temp[j] == '"') {
                            if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                                temp[j] = '\001';
                            } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                                break;
                            }
                        }
                    }
                }
            }
            return new String(temp).replace("\001", "");
        }
    }

}
