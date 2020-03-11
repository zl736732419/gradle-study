package com.zheng.gradle;

import com.zheng.tool.FileHandler;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  索引占用内存分析
 *index                   shard prirep ip           segment generation docs.count docs.deleted    size size.memory committed searchable version compound
 * dresslily20200309034553 0     p      10.93.157.12 _3t0          4932     109691        24696  40.5mb           0 true      false      6.6.1   false
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2020年03月11日			zhenglian			    Initial.
 *
 * </pre>
 */
public class EsIndexMemHandler extends FileHandler {
    private Map<String, MemData> dataMap = new HashMap<>();
    private Map<String, Double> unitMap = new HashMap<>();
    private String currentDomain;
    
    public EsIndexMemHandler() {
        unitMap.put("tb", 1024*1024*1024*1024d);
        unitMap.put("gb", 1024*1024*1024d);
        unitMap.put("mb", 1024*1024d);
        unitMap.put("kb", 1024d);
        unitMap.put("b", 1d);
    }
    
    @Override
    protected String getDir() {
        return "index.mem";
    }

    @Override
    protected void beforeFileHandle(File file) {
        currentDomain = file.getName().substring(0, file.getName().lastIndexOf("."));
        MemData memData = dataMap.get(currentDomain);
        if (null == memData) {
            memData = new MemData(0.0d, 0.0d);
            dataMap.put(currentDomain, memData);
        }
    }

    @Override
    protected void afterFileHandle(File file) {
    }

    @Override
    protected void handleLine(String line) {
        if (line.startsWith("index")) {
            return;
        }
        String[] arr = line.split("\\s+");
        String diskCountStr = arr[8];
        String memSizeStr = arr[9];
        
        Double diskSize = parseCount(diskCountStr.toLowerCase());
        Double memSize = parseCount(memSizeStr.toLowerCase());
        dataMap.get(currentDomain).incrementDiskSize(diskSize);
        dataMap.get(currentDomain).incrementMemSize(memSize);
    }

    private Double parseCount(String string) {
        if (StringUtils.isEmpty(string)) {
            return 0.0d;
        }
        
        String numStr = null;
        String unitStr;
        Double unitNum = 1d;
        for (String unit : unitMap.keySet()) {
            if (string.contains(unit)) {
                unitStr = unit;
                numStr = string.substring(0, string.indexOf(unitStr));
                unitNum = unitMap.get(unit);
            }
        }
        if (null == numStr) {
            numStr = string;
            unitNum = 1d;
        }
        
        Double num = Double.parseDouble(numStr);
        BigDecimal bigDecimal = new BigDecimal(num*unitNum).setScale(4, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    @Override
    protected void before() {
        System.out.println("index, disk, mem");
    }

    @Override
    protected void after() {
        dataMap.forEach((domain, data) -> System.out.println(new StringBuilder().append(domain).append(",").append(data)));
    }

    private class MemData {
        /**
         * 磁盘大小
         */
        private Double diskSize;
        /**
         * 内存大小
         */
        private Double memSize;

        public MemData(Double diskSize, Double memSize) {
            this.diskSize = diskSize;
            this.memSize = memSize;
        }

        public void incrementDiskSize(Double size) {
            if (size == null) {
                return;
            }
            this.diskSize += size;
        }

        public void incrementMemSize(Double size) {
            if (size == null) {
                return;
            }
            this.memSize += size;
        }
        
        public Double getDiskSize() {
            return diskSize;
        }

        public void setDiskSize(Double diskSize) {
            this.diskSize = diskSize;
        }

        public Double getMemSize() {
            return memSize;
        }

        public void setMemSize(Double memSize) {
            this.memSize = memSize;
        }

        @Override
        public String toString() {
            Double disk = diskSize / unitMap.get("gb");
            Double mem = memSize / unitMap.get("mb");
            return new StringBuilder().append(new BigDecimal(disk).setScale(4, BigDecimal.ROUND_HALF_UP)).append("gb")
                    .append(",").append(new BigDecimal(mem).setScale(4, BigDecimal.ROUND_HALF_UP)).append("mb").toString();
        }
    }
    
    public static void main(String[] args) throws Exception {
        new EsIndexMemHandler().handle();
    }
}
