package com.github.platform.sf.common.util.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
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

    private static CloseableHttpClient httpClient = HttpClients.custom().build();


    public static String httpPostJson(String url, String jsonData) {

        // 配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
        post.setEntity(entity);

        post.setConfig(requestConfig);
        String responseContent = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                responseContent = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
            log.debug("上报状态 : {}, 返回 : {}", response.getStatusLine().getStatusCode(), responseContent);
        } catch (Exception e) {
            log.error("", e);
        } finally {

        }
        return responseContent;
    }



    public static String httpPost(String url, List<BasicNameValuePair> list) {

        // 配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();

        HttpPost httpPost = new HttpPost(url);
        // 设置超时时间
        httpPost.setConfig(requestConfig);

        String strResult = "";
        int StatusCode;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, StandardCharsets.UTF_8);
            // 设置post求情参数
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null) {
                StatusCode=httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    log.info("post/"+StatusCode+":"+strResult);
                    return strResult;
                }  else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                    log.info("post/"+StatusCode+":"+strResult);
                    strResult=null;
                }
            } else {

            }

        } catch (Exception e) {
            log.error("", e);
        } finally {

        }

        return strResult;
    }

    public static String httpGet(String url) {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000).setRedirectsEnabled(true)// 默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet(url);
        httpGet2.setConfig(requestConfig);
        String srtResult =null;
        int StatusCode=404;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet2);
            StatusCode=httpResponse.getStatusLine().getStatusCode();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                log.info("get/"+StatusCode+":"+srtResult);
                return srtResult;
            } else {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                log.info("get/"+StatusCode+":"+srtResult);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}
