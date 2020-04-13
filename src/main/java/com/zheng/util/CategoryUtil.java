package com.zheng.util;

import com.zheng.bean.SkuCategory;
import com.zheng.bean.SkuCategoryTreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2020年03月18日			zhenglian			    Initial.
 *
 * </pre>
 */
public class CategoryUtil {


    public static SkuCategoryTreeNode buildTree(List<Map<String, Object>> categories) {
        if (StringUtils.isEmpty(categories)) {
            return null;
        }

        SkuCategoryTreeNode root = buildCategoryTree(categories, 0);
        if (StringUtils.isEmpty(root) || StringUtils.isEmpty(root.getChildren())) {
            return null;
        }
        return root;
    }


    private static SkuCategoryTreeNode buildCategoryTree(List<Map<String,Object>> categories, Integer currentCatId) {
        if (StringUtils.isEmpty(categories)) {
            return null;
        }

        // record all categories
        Map<Integer, SkuCategoryTreeNode> allCategoryTreeNodes = new HashMap<>();
        Map<Integer, List<SkuCategoryTreeNode>> parentTreeNodes = new HashMap<>();
        // init root category
        SkuCategoryTreeNode rootCategory = new SkuCategoryTreeNode();
        rootCategory.setCatId(0);
        allCategoryTreeNodes.put(0, rootCategory);

        // 汇总每个分类节点对应的子分类
        categories.stream()
                .filter(category -> StringUtils.isNotEmpty(category))
                .forEach(category -> {
                    Integer parentId = (Integer) category.get("parentId");
                    List<SkuCategoryTreeNode> children = parentTreeNodes.get(parentId);
                    if (StringUtils.isEmpty(children)) {
                        children = new ArrayList<>();
                        parentTreeNodes.put(parentId, children);
                    }

                    SkuCategoryTreeNode categoryTreeNode = buildCategoryTreeNodeFromMap(category, allCategoryTreeNodes);
                    children.add(categoryTreeNode);
                });

        // combine parent and children category
        allCategoryTreeNodes.values().stream()
                .filter(category -> StringUtils.isNotEmpty(category))
                .forEach(category -> {
                    Integer catId = category.getCatId();
                    List<SkuCategoryTreeNode> children = parentTreeNodes.get(catId);
                    if (StringUtils.isEmpty(children)) {
                        children = new ArrayList<>();
                    } else {
                        sortCategory(children);
                    }
                    category.setChildren(children);
                    fillParent(children, category);
                });
        // get top categories which build tree above
        SkuCategoryTreeNode categoryTreeNode = allCategoryTreeNodes.get(currentCatId);
        if (StringUtils.isEmpty(categoryTreeNode)) {
            return null;
        }
        // 填充level
        fillCategoryLevelInfo(categoryTreeNode, 0);

        categories.clear();
        allCategoryTreeNodes.clear();
        return categoryTreeNode;
    }

    private static void sortCategory(List<? extends SkuCategory> categories) {
        if (StringUtils.isEmpty(categories)) {
            return;
        }
        Collections.sort(categories, Comparator.comparing(SkuCategory::getCatName));
    }

    private static void fillParent(List<SkuCategoryTreeNode> children, SkuCategoryTreeNode parent) {
        if (StringUtils.isEmpty(children) || StringUtils.isEmpty(parent)) {
            return;
        }

        SkuCategoryTreeNode newParent = new SkuCategoryTreeNode(parent.getCatId(), parent.getCatName(), parent.getLevel());

        children.stream()
                .filter(category -> StringUtils.isNotEmpty(category))
                .forEach(category -> category.setParent(newParent));
    }

    private static void fillCategoryLevelInfo(SkuCategoryTreeNode categoryTreeNode, int level) {
        if (StringUtils.isEmpty(categoryTreeNode)) {
            return;
        }

        categoryTreeNode.setLevel(level);

        List<SkuCategoryTreeNode> children = categoryTreeNode.getChildren();
        if (StringUtils.isEmpty(children)) {
            return;
        }

        children.stream()
                .filter(category -> StringUtils.isNotEmpty(category))
                .forEach(category -> {
                    int nextLevel = level + 1;
                    fillCategoryLevelInfo(category, nextLevel);
                });
    }
    
    private static SkuCategoryTreeNode buildCategoryTreeNodeFromMap(Map<String,Object> category, Map<Integer,SkuCategoryTreeNode> allCategoryTreeNodes) {
        Integer catId = NumberUtil.parseInt(category.get("catId"));
        String catName = (String) category.get("catName");

        SkuCategoryTreeNode categoryTreeNode = allCategoryTreeNodes.get(catId);
        if (StringUtils.isNotEmpty(categoryTreeNode)) {
            return categoryTreeNode;
        }

        categoryTreeNode = new SkuCategoryTreeNode.Builder()
                .catId(catId)
                .catName(catName)
                .level(0)
                .build();
        allCategoryTreeNodes.put(catId, categoryTreeNode);
        return categoryTreeNode;
    }
}
