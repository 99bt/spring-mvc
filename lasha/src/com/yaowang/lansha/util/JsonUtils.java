package com.yaowang.lansha.util;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 16-2-19
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    /**
     * json字符串转变成map对象
     *
     * @param json
     *            json字符串
     * @return
     */
    public static <T> Map<String, T> toMap(String json) {
        if (StringUtils.isNotEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, Map.class);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyMap();
    }
    /**
     * 将对象转变为json字符串
     *
     * @param obj
     *            对象
     * @return
     */
    public static String toString(Object obj) {
        if (obj != null) {
            try {
                return OBJECT_MAPPER.writeValueAsString(obj);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    /**
     * json字符串转变为特定对象
     *
     * @param clazz
     *            对象
     * @param json
     *            字符串
     * @return
     */
    public static <T> T toObject(Class<T> clazz, String json) {
        if (clazz != null && StringUtils.isNotEmpty(json)) {
            try {
                return OBJECT_MAPPER.readValue(json, clazz);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static class CustomJsonDateDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = jp.getText();
            try {
                return format.parse(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}
