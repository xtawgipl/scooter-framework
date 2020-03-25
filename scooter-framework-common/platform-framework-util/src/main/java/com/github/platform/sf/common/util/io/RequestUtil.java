package com.github.platform.sf.common.util.io;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhangjj
 * @create 2019-11-22 11:18
 **/
@Slf4j
public class RequestUtil {

    private static CloseableHttpClient HTTP_CLIENT = HttpClients.custom().build();
    // 配置超时时间
    private static RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000)
            .setSocketTimeout(3000).setRedirectsEnabled(true).build();

    public static String httpPostJson(String url, String jsonData, RequestConfig ... config) {
        RequestConfig requestConfig = config == null || config.length == 0 ? REQUEST_CONFIG : config[0];
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        post.setConfig(requestConfig);
        String responseContent = null;
        try (CloseableHttpResponse response = HTTP_CLIENT.execute(post)){
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
            log.debug("requestUrl : {}，上报状态 : {}, 返回 : {}", url, response.getStatusLine().getStatusCode(), responseContent);
        } catch (Exception e) {
            log.error(String.format("requestUrl : %s", url), e);
        }
        return responseContent;
    }


    public static <T> T httpPostJson(Class<T> respClass, String url, String jsonData, RequestConfig ... config) {
        String resp = httpPostJson(url, jsonData, config);
        return JSON.parseObject(resp, respClass);
    }



    public static String httpPost(String url, List<BasicNameValuePair> list, RequestConfig ... config) {
        RequestConfig requestConfig = config == null || config.length == 0 ? REQUEST_CONFIG : config[0];
        HttpPost httpPost = new HttpPost(url);
        // 设置超时时间
        httpPost.setConfig(requestConfig);

        String strResult = "";
        int StatusCode;
        try (CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost)){
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, StandardCharsets.UTF_8);
            // 设置post求情参数
            httpPost.setEntity(entity);

            if (httpResponse != null) {
                StatusCode=httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    log.info("{}, post/{}:{}", url, StatusCode, strResult);
                    return strResult;
                }  else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                    log.info("{}, post/{}:{}", url, StatusCode, strResult);
                    strResult=null;
                }
            }

        } catch (Exception e) {
            log.error(String.format("requestUrl = %s", url), e);
        }

        return strResult;
    }


    public static <T> T httpPost(Class<T> respClass, String url, List<BasicNameValuePair> list, RequestConfig ... config) {
        String resp = httpPost(url, list, config);
        return JSON.parseObject(resp, respClass);
    }

    public static String httpGet(String url, RequestConfig ... config) {
        RequestConfig requestConfig = config == null || config.length == 0 ? REQUEST_CONFIG : config[0];
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String strResult;
        int StatusCode;
        try (CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet)){
            StatusCode=httpResponse.getStatusLine().getStatusCode();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                log.info("{}, get/{}:{}", url, StatusCode, strResult);
                return strResult;
            } else {
                strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                log.info("{}, get/{}:{}", url, StatusCode, strResult);
                return null;
            }
        } catch (IOException e) {
            log.error(String.format("requestUrl = %s", url), e);
        }
        return null;
    }

    public static <T> T httpGet(Class<T> respClass, String url, RequestConfig ... config) {
        String resp = httpGet(url, config);
        return JSON.parseObject(resp, respClass);
    }

}
