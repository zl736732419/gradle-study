package com.zheng.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * <pre>
 * 
 *  File: FloatUtil.java
 * 
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2016年9月19日				chengmo				Initial.
 *
 * </pre>
 */
public class NumberUtil
{
    public static Float formatFloat(Object obj, int scale) {
        if (StringUtils.isEmpty(obj) || !NumberUtils.isNumber(obj.toString())) {
            return null;
        }
        scale = scale < 0 ? 0 : scale;
        Float number = Float.parseFloat(obj.toString());
        BigDecimal bigDecimal = new BigDecimal(number).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }

    public static Float formatFloat(BigDecimal obj, int scale) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        scale = scale < 0 ? 0 : scale;
        BigDecimal bigDecimal = obj.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.floatValue();
    }
    
    public static Double formatDouble(Object obj, int scale) {
        if (StringUtils.isEmpty(obj) || !NumberUtils.isNumber(obj.toString())) {
            return null;
        }
        scale = scale < 0 ? 0 : scale;
        Double number = Double.parseDouble(obj.toString());
        BigDecimal bigDecimal = new BigDecimal(number).setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static Double formatDouble(BigDecimal obj, int scale) {
        if (StringUtils.isEmpty(obj)) {
            return null;
        }
        scale = scale < 0 ? 0 : scale;
        BigDecimal bigDecimal = obj.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static Double formatDouble(Object obj, int scale, double defaultValue) {
        Double value = formatDouble(obj, scale);
        if (null == value) {
            value = defaultValue;
        }
        return value;
    }

    public static Double add(Double one, Double two) {
        return add(one, two, 4);
    }

    public static Double add(Double one, Double two, int scale) {
        one = StringUtils.isEmpty(one) ? 0.0d : one;
        two = StringUtils.isEmpty(two) ? 0.0d : two;
        BigDecimal result = new BigDecimal(one).add(new BigDecimal(two)).setScale(scale, RoundingMode.HALF_UP);
        return result.doubleValue();
    }
    
    public static Double subtract(Double one, Double two) {
        return subtract(one, two, 4);
    }

    public static Double subtract(Double one, Double two, int scale) {
        one = StringUtils.isEmpty(one) ? 0.0d : one;
        two = StringUtils.isEmpty(two) ? 0.0d : two;
        BigDecimal result = new BigDecimal(one).subtract(new BigDecimal(two)).setScale(scale, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static Double multiply(Double one, Double two) {
        return multiply(one, two, 4);
    }

    public static Double multiply(Double one, Double two, int scale) {
        if (StringUtils.isEmpty(one) || StringUtils.isEmpty(two)) {
            return 0.0d;
        }
        BigDecimal result = new BigDecimal(one).multiply(new BigDecimal(two)).setScale(scale, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static Double divide(Double one, Double two) {
        return divide(one, two, 4);
    }

    public static Double divide(Double one, Double two, int scale) {
        if (StringUtils.isEmpty(one)
                || StringUtils.isEmpty(two)
                || Objects.equals(two, 0.0d)) {
            return 0.0d;
        }
        BigDecimal result = new BigDecimal(one).divide(new BigDecimal(two), scale, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static Integer parseInt(Object obj) {
        if (StringUtils.isEmpty(obj) || !NumberUtils.isNumber(obj.toString())) {
            return null;
        }
        return Integer.parseInt(obj.toString());
    }

    public static Long parseLong(Object obj) {
        if (StringUtils.isEmpty(obj) || !NumberUtils.isNumber(obj.toString())) {
            return null;
        }
        return Long.parseLong(obj.toString());
    }

    public static float valueOf(Object obj, float def)
    {
        if (obj == null)
        {
            return def;
        }

        try
        {
            return Float.valueOf(obj.toString());
        }
        catch (NumberFormatException e)
        {
        }
        return def;
    }
    
    public static long valueOf(Object obj, long def)
    {
        if (obj == null)
        {
            return def;
        }

        try
        {
            return Long.valueOf(obj.toString().trim());
        }
        catch (NumberFormatException e)
        {
        }
        return def;
    }
    
    public static Long valueOf(String s, Long def)
    {
        if (s == null || s.equals(""))
        {
            return def;
        }
        
        try
        { 
            return Long.valueOf(s);
        }
        catch (NumberFormatException e)
        {
        }
        return def;
    }
    
    public static long valueOf(String s, long def)
    {
        if (s == null || s.equals(""))
        {
            return def;
        }
        
        try
        { 
            return Long.valueOf(s).longValue();
        }
        catch (NumberFormatException e)
        {
        }
        return def;
    }
    
    public static long valueOf(int value)
    {
        long def = -1;
        try
        { 
            def = Long.valueOf(String.valueOf(value)).longValue();
        }
        catch (NumberFormatException e)
        {
        }
        return def;
    }
}
