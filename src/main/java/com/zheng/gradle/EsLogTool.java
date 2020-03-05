package com.zheng.gradle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
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
 *  Date,					Who,					What;
 *  2020年03月04日			zhenglian			    Initial.
 *
 * </pre>
 */
public class EsLogTool extends FileHandler {
    private PrintWriter writer;
    private Pattern tokePattern = Pattern.compile("^\\d+(\\.\\d+)?s$");
    
    @Override
    protected String getDir() {
        return "eslog";
    }

    @Override
    protected void beforeFileHandle(File file) {
        try {
            writer = new PrintWriter(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\eslog.log")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    @Override
    protected void afterFileHandle(File file) {
        writer.close();
    }

    @Override
    protected void handleLine(String line) {
        // [2020-03-04T00:00:11,319] ... xxx took[1s]
        String time = (line.split(",")[0]).split("T")[1];
        if (!time.startsWith("01:05")) {
            return;
        }
        String str = line.split(", took_millis")[0];

        String tokeStr = str.split("took\\[")[1];
        String tokeTime = tokeStr.substring(0, tokeStr.length()-1);
        if (!tokePattern.matcher(tokeTime).matches()) {
            return;
        }
        
        writer.println(str);
    }
    
    public static void main(String[] args) throws Exception {
        new EsLogTool().handle();
//        boolean result = new EsLogTool().tokePattern.matcher("123.25ms").matches();
//        System.out.println(result);
    }
}
