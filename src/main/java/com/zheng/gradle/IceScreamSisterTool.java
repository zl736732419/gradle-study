package com.zheng.gradle;

import com.zheng.tool.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * <pre>
 *
 *  File:
 *  
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  示例
 *  210392101	Ear	Jackets	Zircon	Women	Earrings	Earrings	That	Dangle	In	Front	And	Back	Earrings	With	Hanging	Backs
 * 
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2019年05月06日			zhenglian			    Initial.
 *
 * </pre>
 */
public class IceScreamSisterTool extends FileHandler {
    private PrintWriter writer;
    private String separation = " ";

    @Override
    protected String getDir() {
        return "ice_scream";
    }

    @Override
    protected void beforeFileHandle(File file) {
        String filename = file.getName();
        int separatorIndex = filename.lastIndexOf(".");
        String name = filename.substring(0, separatorIndex);
        String suffix = filename.substring(separatorIndex);
        
        String dirname = new StringBuilder().append(getDir()).append("_").append("out").toString();
        File  outDir = new File(dirname);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        String outname = new StringBuilder()
                .append(dirname).append(File.separator) // dir
                .append(name).append("_").append("out").append(suffix) // file
                .toString();
        try {
            writer = new PrintWriter(new FileOutputStream(new File(outname)));
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
        line = line.replaceAll("\\s", " ");
        int spaceIndex = line.indexOf(" ");
        String goodsSn = line.substring(0, spaceIndex);

        String modelStrs = line.substring(spaceIndex+1);
        String[] models = modelStrs.split(separation);

        for (String model : models) {
            String resultLine = new StringBuilder().append(goodsSn).append(" ").append(model).toString();
            writer.println(resultLine);
        }
    }

    public static void main(String[] args) throws Exception {
        new IceScreamSisterTool().handle();
    }
}
