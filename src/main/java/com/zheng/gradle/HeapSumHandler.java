package com.zheng.gradle;

import com.zheng.tool.FileHandler;
import com.zheng.util.StringUtils;

import java.io.File;

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
 *  Date,					Who,					What;
 *  2020年04月11日			zhenglian			    Initial.
 *
 * </pre>
 */
public class HeapSumHandler extends FileHandler {
    long bytes = 0L;
    
    @Override
    protected String getDir() {
        return "heap";
    }

    @Override
    protected void handleLine(String line) {
        if (StringUtils.isEmpty(line) || StringUtils.isEmpty(line.trim())) {
            return;
        }
        if (line.startsWith("num") || line.startsWith("-")) {
            return;
        }
        
        String[] arrs = line.split("\\s+");
        long byteNum = Long.parseLong(arrs[2]);
        bytes += byteNum;
    }

    @Override
    protected void afterFileHandle(File file) {
        System.out.println("total bytes: " + bytes + ", " + bytes / (1024 * 1024 * 1024) + "gb");
    }
    
    public static void main(String[] args) throws Exception {
        new HeapSumHandler().handle();
    }
}
