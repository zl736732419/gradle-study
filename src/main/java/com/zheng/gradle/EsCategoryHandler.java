package com.zheng.gradle;

import com.zheng.bean.SkuCategoryTreeNode;
import com.zheng.tool.FileHandler;
import com.zheng.util.CategoryUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2020年03月18日			zhenglian			    Initial.
 *
 * </pre>
 */
public class EsCategoryHandler extends FileHandler {
    private List<Map<String, Object>> categories = new ArrayList<>();
    
    @Override
    protected String getDir() {
        return "cat";
    }

    @Override
    protected void beforeFileHandle(File file) {
        
    }

    @Override
    protected void afterFileHandle(File file) {
        
    }

    @Override
    protected void handleLine(String line) {
        if (StringUtils.isEmpty(line)) {
            return;
        }
        line = line.trim();
        String[] arr = line.split("\\s+");
        String catId = arr[0];
        Integer parentId = Integer.parseInt(arr[arr.length - 1]);
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < arr.length-1; i++) {
            if (builder.length() != 0) {
                builder.append(" ");
            }
            builder.append(arr[i]);
        }
        String catName = builder.toString();
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("catName", catName);
        map.put("parentId", parentId);
        categories.add(map);
    }

    @Override
    protected void after() {
        System.out.println("size: " + categories.size());
    }
    
    public static void main(String[] args) throws Exception { 
        EsCategoryHandler handler = new EsCategoryHandler();
        handler.handle();
        SkuCategoryTreeNode tree = CategoryUtil.buildTree(handler.categories);
        System.out.println("done");
    }
}
