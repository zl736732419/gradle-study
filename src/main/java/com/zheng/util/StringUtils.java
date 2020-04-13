package com.zheng.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 
 *  File: StringUtils.java
 * 
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 * 
 *  String 工具类
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2016年8月26日				lizhaohui				Initial.
 *
 * </pre>
 */
public class StringUtils
{

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj)
    {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[])
        {
            Object[] object = (Object[]) obj;
            if (object.length == 0)
            {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++)
            {
                if (!isEmpty(object[i]))
                {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj)
    {
        return !isEmpty(obj);
    }
    
    /**
     * Remove special char only save letter number and space
     *
     * @param str
     * @return
     */
    public static String removeSpecialChar(String str)
    {
    	if(str == null)
    	{
    		return null;
    	}
    	//String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】\\[\\]‘；：”“’。，、？]";   
    	String regEx = "[^a-zA-Z0-9 ]"; 
    	Pattern p = Pattern.compile(regEx);      
    	Matcher m = p.matcher(str);      
    	return m.replaceAll("").trim();
    }

    /**
     * replace special char with replaceStr
     * @param str
     * @param replaceStr
     * @return
     */
    public static String removeSpecialChar(String str,String replaceStr)
    {
        if(str == null)
        {
            return str;
        }
        String regEx="[`!@#$%^&*()~+=|{}:;,//[//]<>/?！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replaceStr).trim();
    }

    public static String connectStr(String separator, String... strs)
    {
        if (null == separator) {
            separator = ".";
        }

        if(strs.length == 1)
        {
            return strs[0];
        }

        StringBuffer sb = new StringBuffer();
        for (String str : strs) {
            if (isEmpty(str)) {
                continue;
            }

            if (sb.length() != 0 && isNotEmpty(separator)) {
                sb.append(separator);
            }
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * 获得一个单词的首位两个字母
     * @param str 
     * @return
     */
    public static String getHeadFoot(String str) 
    {
    	StringBuilder builder=new StringBuilder();
    	if(isNotEmpty(str)) 
    	{
    		String first=str.substring(0, 1);
    		String last=str.substring(str.length()-1, str.length());
    		builder.append(first).append(last);
    	}
		return builder.toString();
    }

    public static boolean isNulStr(String str) {
        if (isEmpty(str)) {
            return true;
        }
        if ("null".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 将null替换成给定值，默认替换成空字符串
     * @param strs
     * @return
     */
    public static String replaceNull(String... strs) {
        if (null == strs) {
            return null;
        }
        String str = strs[0];
        String replace = "";
        if (strs.length > 1) {
            replace = strs[1];
        }
        return StringUtils.isEmpty(str) ? replace : str;
    }
    
    public static boolean isBoolStr(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return Objects.equals(str, "true") || Objects.equals(str, "false");
    }
    
    public static void main(String[] args) {
    	StringBuilder builder=new StringBuilder();
		String str="mr";
		String first=str.substring(0, 1);
		String last=str.substring(str.length()-1, str.length());
		System.out.println(builder.append(first).append(last));
	}

}
