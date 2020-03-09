package com.zheng.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2019年09月22日			zhenglian			    Initial.
 *
 * </pre>
 */
public abstract class FileHandler {
    
    public void handle() throws Exception {
        init();
        URI uri = this.getClass().getClassLoader().getResource(getDir()).toURI();
        File dir = new File(uri);
        System.out.println("handle dir: " + dir.getName());
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("dir error!");
            return;
        }
        before();
        recursiveHandleFile(dir);
        after();
    }

    private void recursiveHandleFile(File file) throws Exception {
        if (file.isFile()) {
            beforeFileHandle(file);
            handleFile(file);
            afterFileHandle(file);
        } else {
            for (File subFile : file.listFiles()) {
                recursiveHandleFile(subFile);
            }
        }
    }

    protected void init() {};

    protected void before() {}
    protected void after() {}


    private void handleFile(File file) throws Exception {
        if (null == file || !file.isFile()) {
            return;
        }
        InputStream input = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            if (null == line || Objects.equals(0, line.length())) {
                continue;
            }
            handleLine(line.trim());
        }
        
        reader.close();
    }

    protected abstract String getDir();
    protected abstract void beforeFileHandle(File file);
    protected abstract void afterFileHandle(File file);
    protected abstract void handleLine(String line);
}
