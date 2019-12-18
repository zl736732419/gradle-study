package com.zheng.gradle;

import java.io.File;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  rerank耗时统计文件处理工具
 *  文件内容格式：
 *  total:226, error:0, totalTime:5,309ms, qps:1, avgTime:23, maxElapseTime:74
 *  total:313, error:0, totalTime:2,029ms, qps:1, avgTime:6, maxElapseTime:70
 *  
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2019年09月22日			zhenglian			    Initial.
 *
 * </pre>
 */
public class RerankInvokeTool extends FileHandler {

    private Integer total;
    private Integer error;
    private Integer totalTime;
    private Integer maxElapseTime;
    
    @Override
    protected String getDir() {
        return "rerank_invoke";
    }

    @Override
    protected void before() {
        System.out.println("name,website,platform,total,error,totalTime,maxElapseTime,qps,avgTime");
    }

    @Override
    protected void beforeFileHandle(File file) {
        total = error = totalTime = maxElapseTime = 0;
    }

    @Override
    protected void afterFileHandle(File file) {
        String filename = file.getName();
        String simpleName = filename.substring(0, filename.lastIndexOf("."));
        String[] arr = simpleName.split("\\s");
        String msg = new StringBuilder()
                .append(filename)
                .append(",").append(arr[0])
                .append(",").append(arr[1])
                .append(",").append(total)
                .append(",").append(error)
                .append(",").append(totalTime)
                .append(",").append(maxElapseTime)
                .append(",").append(total / (24*60*60) + 1)
                .append(",").append(totalTime / (total == 0 ? 1 : total))
                .toString();
        System.out.println(msg);
    }

    @Override
    protected void handleLine(String line) {
        try {
            String[] arr = line.split(" ");
            for (String item : arr) {
                if (!handleItem(item)) {
                    System.err.println("handle line " + line + " error !");
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.err.println("error: " + line);
        }
    }

    private boolean handleItem(String item) {
        if (item.charAt(item.length() - 1) == ',') {
            item = item.substring(0, item.length() - 1);
        }
        String[] arr = item.split(":");
        if (arr.length != 2) {
            return false;
        }
        String key = arr[0];
        Integer value = parseValue(arr[1]);
        switch (key) {
            case "total": 
                total += value;
                break;
            case "error":
                error += value;
                break;
            case "totalTime": 
                totalTime += value;
                break;
            case "maxElapseTime":
                if (value > maxElapseTime) {
                    maxElapseTime = value;
                }
                break;
        }
        return true;
    }

    private Integer parseValue(String str) {
        if (str.contains(",")) {
            str = str.replaceAll(",", "");
        }
        if (str.contains("ms")) {
            str = str.substring(0, str.length() - 2);
        }
        return Integer.valueOf(str);
    }
    
    public static void main(String[] args) throws Exception {
        new RerankInvokeTool().handle();
    }
}
