package com.zheng.gradle;

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
 *  TODO
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
    private Integer qpsSum; // qps统计的总和
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
        System.out.println("完成统计....");
    }

    @Override
    protected void handleLine(String line) {
        if (line.startsWith("_") || line.startsWith("/")) {
            if (key != null && !Objects.equals(key, line)) {
                qpsMap.put(key, qpsSum/qpsCount);
            }
            key = line;
            qpsCount = 0;
            qpsSum = 0;
        } else {
            String[] arr = line.split("\\s");
            String timeStr = arr[0];
            if (pattern.matcher(timeStr).matches()) {
                qpsCount++;
                qpsSum += Integer.parseInt(arr[1]);
            }
        }
    }

}
