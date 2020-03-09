package com.zheng.gradle;

import com.zheng.tool.FileHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  es qps统计，从sentinel后台获取元数据
 *  格式如下
 * /GBRU/web/search
 * 时间  通过 QPS  拒绝QPS   响应时间（ms）
 * 18:42:58    6.0 0.0 61.0
 * 18:42:57    3.0 0.0 34.5
 * 18:42:56    1.0 0.0 291.0
 * 18:42:55    4.0 0.0 126.5
 * 18:42:54    9.0 0.0 86.9
 * 18:42:53    4.0 0.0 93.0
 *
 *  Revision History
 *  Date,                   Who,                    What;
 *  2020年03月09日            xiaolian             Initial.
 *
 * </pre>
 */
public class EsQpsHandler extends FileHandler {
    private Map<String, Integer> qpsMap = new HashMap<>();
    private String key;
    private Double qpsSum; // qps统计的总和
    private Integer qpsCount; // qps统计的次数
    private Pattern pattern = Pattern.compile("(\\d+){2}:(\\d+){2}:(\\d){2}");
    

    @Override
    protected String getDir() {
        return "qps";
    }

    @Override
    protected void beforeFileHandle(File file) {
        System.out.println("搜索各接口qps情况：");
    }

    @Override
    protected void afterFileHandle(File file) {
        System.out.println("统计结果: ");
        qpsMap.forEach((key, qps)-> System.out.println(key + ":" + qps));
    }

    @Override
    protected void handleLine(String line) {
        if (line.startsWith("时间")) {
            return;
        }
        if (line.startsWith("_") || line.startsWith("/")) {
            if (key != null && !Objects.equals(key, line)) {
                qpsMap.put(key, (int)(qpsSum/qpsCount));
            }
            key = line;
            qpsCount = 0;
            qpsSum = 0d;
        } else {
            String[] arr = line.split("\\s+");
            String timeStr = arr[0];
            if (pattern.matcher(timeStr).matches()) {
                qpsCount++;
                qpsSum += Double.parseDouble(arr[1]);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new EsQpsHandler().handle();
    }

}
