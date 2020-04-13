package com.zheng.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *  File: SkuCategoryTreeNode.java
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  TODO
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2018年08月01日			zhenglian			    Initial.
 *
 * </pre>
 */
public class SkuCategoryTreeNode extends SkuCategory {
    /**
     * the parent of current category
     */
    private SkuCategoryTreeNode parent;
    /**
     * the children of current category
     */
    private List<SkuCategoryTreeNode> children = new ArrayList<>();

    public SkuCategoryTreeNode() {}

    public SkuCategoryTreeNode(Integer catId, String catName, Integer level) {
        super(catId, catName, level);
    }

    public SkuCategoryTreeNode getParent() {
        return parent;
    }

    public void setParent(SkuCategoryTreeNode parent) {
        this.parent = parent;
    }

    public List<SkuCategoryTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<SkuCategoryTreeNode> children) {
        this.children = children;
    }

    public void addChild(SkuCategoryTreeNode child) {
        if (null == children) {
            children = new ArrayList<>();
        }
        children.add(child);
    }

    public static class Builder {
        private SkuCategoryTreeNode categoryTreeNode;
        public Builder() {
            categoryTreeNode = new SkuCategoryTreeNode();
        }

        public Builder catId(Integer catId) {
            categoryTreeNode.setCatId(catId);
            return this;
        }

        public Builder catName(String catName) {
            categoryTreeNode.setCatName(catName);
            return this;
        }

        public Builder level(Integer level) {
            categoryTreeNode.setLevel(level);
            return this;
        }

        public Builder parentId(SkuCategoryTreeNode parent) {
            categoryTreeNode.setParent(parent);
            return this;
        }

        public Builder children(List<SkuCategoryTreeNode> children) {
            categoryTreeNode.setChildren(children);
            return this;
        }

        public SkuCategoryTreeNode build() {
            return categoryTreeNode;
        }
    }
}
