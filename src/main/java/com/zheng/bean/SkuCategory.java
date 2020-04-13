package com.zheng.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * <pre>
 * 
 *  File: SkuCategory.java
 * 
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 * 
 *  Description:
 *  sku分类
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2016年6月6日				zhoujinhong				Initial.
 *
 * </pre>
 */
@JsonInclude(Include.NON_NULL)
public class SkuCategory implements Serializable
{
    public static final long serialVersionUID = 1L;

    public SkuCategory() {
    }

    public SkuCategory(Integer catId, String catName, Integer level) {
        this.catId = catId;
        this.catName = catName;
        this.level = level;
    }

    /**
     * 分类id
     */
    private Integer catId;

    /**
     * 分类名称
     */
    private String catName;
    
    /**
     * Category level
     */
    private Integer level;

    /**
     * @return the catId
     */

    public Integer getCatId()
    {
        return catId;
    }

    /**
     * @param catId the catId to set
     */
    public void setCatId(Integer catId)
    {
        this.catId = catId;
    }

    /**
     * @return the catName
     */

    public String getCatName()
    {
        return catName;
    }

    /**
     * @param catName the catName to set
     */
    public void setCatName(String catName)
    {
        this.catName = catName;
    }

    /**
     * @return the level
     */
    
    public Integer getLevel()
    {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(Integer level)
    {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SkuCategory{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                ", level=" + level +
                '}';
    }
}
