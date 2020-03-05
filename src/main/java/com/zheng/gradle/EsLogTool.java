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
 *  [2020-03-04T00:00:00,825][WARN ][index.search.slowlog.query] [esearch-one-1] [gearbest20200303191536][4] took[464.5ms], took_millis[464], types[sku], stats[], search_type[QUERY_THEN_FETCH], total_shards[5], source[{"from":0,"size":36,"query":{"function_score":{"query":{"bool":{"must":[{"bool":{"should":[{"nested":{"query":{"match":{"categories.catName":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"categories","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"goodsModelWord":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":2.0}}},{"nested":{"query":{"match":{"skuAttrs.attrValue":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"bool":{"must":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"silicone","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.0}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"bool":{"must":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"silicone","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.0}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"bool":{"must":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"keyboard","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"silicone cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.0}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"bool":{"must":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"silicone keyboard","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.0}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"match":{"goodsTitle":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":10.0}}},{"match":{"searchWords":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.1}}},{"match":{"subTitle":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":2.0}}},{"match":{"brandName":{"query":"silicone keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":3.0}}},{"bool":{"must":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"silicone","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"keyboard cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"keyboard","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"bool":{"must":[{"bool":{"should":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"cover","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.1}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"bool":{"should":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"silicone","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"silicone","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.1}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},{"bool":{"should":[{"nested":{"query":{"match":{"skuDescAttrs.attrValue":{"query":"keyboard","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":1.0}}},"path":"skuDescAttrs","ignore_unmapped":false,"score_mode":"sum","boost":1.0}},{"match":{"searchWords":{"query":"keyboard","operator":"OR","prefix_length":0,"max_expansions":50,"minimum_should_match":"100%","fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","boost":0.1}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}}],"filter":[{"bool":{"must":[{"terms":{"webStatus":[2,4,5],"boost":1.0}},{"terms":{"whCode":["1273483","1502525","1214279","1451296","1895967","1130971","1651131","1283496","1000766","1629072","1826262","1591127","1839282","1623105","1527929","1349303","2000001","1255288","1202261","1716105","1103083","1817324","1363221","1812487","1320867","1270658","1245851","1265533","1859758","1411263","1919032","1283499","1164304","1525958","1433363","1112942","2000001"],"boost":1.0}},{"terms":{"isTort":[0],"boost":1.0}}],"must_not":[{"terms":{"recommendedLevel":[14],"boost":1.0}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}}],"should":[{"term":{"centerWord":{"value":"cover","boost":8.0}}},{"bool":{"should":[{"range":{"stockFlag":{"from":null,"to":0,"include_lower":true,"include_upper":true,"boost":-1000.0}}},{"range":{"webStatus":{"from":4,"to":5,"include_lower":true,"include_upper":true,"boost":-500.0}}},{"terms":{"tags":["black"],"boost":-800.0}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1.0}},"functions":[{"filter":{"match_all":{"boost":1.0}},"weight":1.0,"field_value_factor":{"field":"baseScore1","factor":1.0,"missing":0.0,"modifier":"none"}}],"score_mode":"sum","boost_mode":"sum","max_boost":3.4028235E38,"boost":1.0}},"_source":{"includes":["goodsWebSku","goodsSn","discount","mailPrice","payStartTime","brandCode","stockFlag","appStatus","tags","catId","shopPrice","deposit","appPriceType","goodsId","skuInstallmentId","appExpiredTime","appDiscount","shopCode","swellAmount","priceType","payEndTime","labelFlags.type","mailPriceCipherText","goodsTitle","activityIds","activities.activityType","labelFlags.labelId","mStatus","subTitle","mailPriceActive","labelFlags.platform","webStatus","passTotalNum","brandName","urlTitle","goodsWebSpu","catInstallmentId","whCode","totalFavoriteCount","mailPriceDiscount","youtube","priceRates","imgExtendUrl","isPlatform","coupons.code","appDisplayPrice","displayPrice","activities.activityId","originalUrl","expiredTime","thumbExtendUrl","imgUrl","passAvgSco
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
