package com.zheng.gradle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 *
 *  File:
 *
 *  Copyright (c) 2016, globalegrow.com All Rights Reserved.
 *
 *  Description:
 *  解析cookie
 *  实例：
 *  [INFO][2019-12-17 04:00:02 -0600] com.globalegrow.esearch.search.v5.searcher.SkuSearcher.printBtsLog(SkuSearcher.java:153) - es request params: {"domain":"ZF_en","accessToken":"dc698883cbca47bbac191787969f9de4","version":5,"cache":false,"agent":"web","pageSize":120,"pageNo":1,"options":{"query.sort.solution":"keyword","query.rerank.enable":true},"filters":[{"field":"isLangShow","values":[1]},{"field":"isCategoryShow","values":[1]},{"field":"stockFlag","values":["*",0],"weight":-1000,"includeLow":true,"includeHigh":true,"type":"boost"},{"field":"shelfDownType","values":[100,100],"weight":-500,"includeLow":true,"includeHigh":true,"type":"boost"}],"collapse":{"field":"groupColorGoodsId"},"aggregations":[{"field":"displayPrice","type":"ranges","stat":"distinct","values":[{"key":"p0","from":0.01,"to":"5"},{"key":"p1","from":"5","to":"10"},{"key":"p2","from":"10","to":"20"},{"key":"p3","from":"20","to":"50"},{"key":"p4","from":"50","to":"100"},{"key":"p5","from":"100","to":"200"}]},{"field":"categories.catId","stat":"distinct","size":50}],"keyword":"276807704","countryCode":"US","identify":"be60bccd7029b8630fbcef1a88e34797","cookie":"10013157061263870988e34797604425"}, bts request log is invoke bts interface success. , request params are: BtsRequest(cookie=be60bccd7029b8630fbcef1a88e34797, appkey=ZF, plancode=categorypc, platform=PC, cookieNew=null), return response {"code":"200","desc":"成功","result":{"plancode":"categorypc","versionid":"3592","cookie":"be60bccd7029b8630fbcef1a88e34797","bucketid":"1","planid":"1293","policy":"JJ"}}, url is http://10.177.215.6:9086/gateway/shunt
 *
 *  Revision History
 *  Date,					Who,					What;
 *  2019年12月16日			zhenglian			    Initial.
 *
 * </pre>
 */
public class BtsHandler extends FileHandler {
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    private File dest = new File("C:\\Users\\Administrator\\Desktop\\bts_all_out.txt");
    private File uniqueCookieDest = new File("C:\\Users\\Administrator\\Desktop\\bts_unique_cookie.txt");
    
    private BufferedWriter destOuter;
    private BufferedWriter uniqueCookieDestOuter;
    
    // 总请求数
    private long count;
    // identify为空
    private long identifyNullCount;
    // identify cookie为空
    private long identifyCookieNullCount;
    
    
    // 传给算法去重后的identify
    private long aiIdentifyCollapseCount;
    // 传给算法去重后的cookie
    private long aiCookieCollapseCount;
    // bts生成的cookie
    private long btsCreatedCookieCount;

    // identify去重
    private Set<String> identifySet = new HashSet<>();
    // bts cookie去重
    private Set<String> aiCookieSet = new HashSet<>();

    @Override
    protected void init() {
        try {
            destOuter = new BufferedWriter(new FileWriter(dest));
            uniqueCookieDestOuter = new BufferedWriter(new FileWriter(uniqueCookieDest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getDir() {
        return "bts";
    }

    @Override
    protected void before() {
        String header = "domain,agent,identify,cookie,btsRequestCookie,btsResponseCookie";
        printLine(header, destOuter);
        printLine(header, uniqueCookieDestOuter);
    }

    private void printLine(String string, BufferedWriter outer) {
        try {
            outer.write(string + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void after() {
        
        System.out.println("总请求结果数: " + count);
        System.out.println("请求参数identify为空结果数: " + identifyNullCount);
        System.out.println("bts生成的cookie结果数: " + btsCreatedCookieCount);
        System.out.println("请求参数identify,cookie都为空结果数: " + identifyCookieNullCount);
        
        System.out.println("搜索传给算法去重后的identify结果数: " + aiIdentifyCollapseCount);
        System.out.println("搜索传给算法去重后的cookie结果数: " + aiCookieCollapseCount);
        
        try {
            destOuter.close();
            uniqueCookieDestOuter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void beforeFileHandle(File file) {
    }
    
    @Override
    protected void afterFileHandle(File file) {
        
    }
    
    @Override
    protected void handleLine(String line) {
//        System.out.println(line);
        String[] arr = line.split("es request params: ");
        arr = arr[1].split(", bts");
        String esParams = arr[0];
//        System.out.println("es params: " + esParams);

        String domain = null;
        String agent = null;
        String identify = null;
        String cookie = null;
        try {
            JsonNode node = objectMapper.readTree(esParams);
            domain = parseJsonField("domain", node);
            agent = parseJsonField("agent", node);
            identify = parseJsonField("identify", node);
            cookie = parseJsonField("cookie", node);
        } catch (IOException e) {
            e.printStackTrace();
        }

        arr = arr[1].split("BtsRequest\\(cookie=");
        int btsCookieEndIndex = arr[1].indexOf(",");
        String btsRequestCookie = arr[1].substring(0, btsCookieEndIndex);
        
        arr = arr[1].split("return response ");
        arr = arr[1].split(", url is");
        String btsResponse = arr[0];
        String btsResponseCookie = null;
        try {
            JsonNode node = objectMapper.readTree(btsResponse);
            if (node.has("result")) {
                JsonNode resultNode = node.get("result");
                btsResponseCookie = parseJsonField("cookie", resultNode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        StringBuilder builder = new StringBuilder();
        appendStr(builder, domain);
        appendStr(builder, agent);
        appendStr(builder, identify);
        appendStr(builder, cookie);
        appendStr(builder, btsRequestCookie);
        appendStr(builder, btsResponseCookie);
        
        printLine(builder.toString(), destOuter);
        
        count++;
        if (StringUtils.isEmpty(identify)) {
            identifyNullCount++;
            if (StringUtils.isEmpty(cookie)) {
                identifyCookieNullCount++;
            }
            
            if (StringUtils.isNotEmpty(btsResponseCookie)) {
                btsCreatedCookieCount++;
                if (identifySet.add(btsResponseCookie)) {
                    aiIdentifyCollapseCount++;
                    printLine(builder.toString(), uniqueCookieDestOuter);
                }
                if (StringUtils.isEmpty(cookie)) {
                    if (aiCookieSet.add(btsResponseCookie)) {
                        aiCookieCollapseCount++;
                    }
                }
                
            }
        } else {
            if (identifySet.add(identify)) {
                aiIdentifyCollapseCount++;
                printLine(builder.toString(), uniqueCookieDestOuter);
            }
            if (StringUtils.isEmpty(cookie)) {
                if (aiCookieSet.add(btsResponseCookie)) {
                    aiCookieCollapseCount++;
                }
            }
        }
        
        if (StringUtils.isNotEmpty(cookie)) {
            if (aiCookieSet.add(btsResponseCookie)) {
                aiCookieCollapseCount++;
            }
        }
    }

    private void appendStr(StringBuilder builder, String str) {
        if (str == null || str.length() == 0) {
            str = "null";
        }
        if (null != builder && builder.length() != 0) {
            builder.append(",");
        }
        builder.append(str);
    }

    private String parseJsonField(String field, JsonNode node) {
        if (!node.has(field)) {
            return null;
        }
        return node.get(field).asText();
    }

    public static void main(String[] args) throws Exception {
        new BtsHandler().handle();
    }
}
