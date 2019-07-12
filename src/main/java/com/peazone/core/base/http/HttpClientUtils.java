package com.peazone.core.base.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * HTTP 请求工具类
 * 
 * @author kaibyx
 * @date 2019年7月12日 上午10:41:42
 */
public class HttpClientUtils {
    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(50);// 整个连接池最大连接数
            cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * GET 请求
     * 
     * @param url
     *            请求地址
     * @return
     */
    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    /**
     * GET 请求
     * 
     * @param url
     *            请求地址
     * @param params
     *            Body 参数
     * @return
     * @throws URISyntaxException
     */
    public static String get(String url, Map<String, String> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        if (params != null) {
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);
        }

        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    /**
     * GET 请求
     * 
     * @param url
     *            请求地址
     * @param header
     *            Header 参数
     * @param params
     *            Body参数
     * @return
     * @throws URISyntaxException
     */
    public static String get(String url, Map<String, Object> header, Map<String, String> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        if (params != null) {
            ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
            ub.setParameters(pairs);
        }

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : header.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    /**
     * POST 请求
     * 
     * @param url
     *            请求地址
     * @return
     */
    public static String post(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    /**
     * POST 请求
     * 
     * @param url
     *            请求地址
     * @param params
     *            Body参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params), "utf-8"));// 设置表单提交编码

        // httpPost.setEntity(new StringEntity(JSON.toJSONString(params),
        // ContentType.APPLICATION_FORM_URLENCODED));
        return getResult(httpPost);
    }

    /**
     * POST 请求
     * 
     * @param url
     *            请求地址
     * @param headers
     *            Header参数
     * @param params
     *            Body 参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, Object> headers, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);

        if (params != null) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        httpPost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.APPLICATION_JSON));

        return getResult(httpPost);
    }

    /**
     * POST 请求
     * 
     * @param url
     *            请求地址
     * @param headers
     *            Header参数
     * @param bodyJson
     *            Body json 参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String post(String url, Map<String, Object> headers, String bodyJson) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);

        if (headers != null) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        httpPost.setEntity(new StringEntity(bodyJson, ContentType.APPLICATION_JSON));

        return getResult(httpPost);
    }

    /**
     * 自定义请求
     * 
     * @param method
     *            请求方式
     * @param url
     *            请求地址
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String request(String method, String url) throws UnsupportedEncodingException {

        RequestBuilder requestBuilder = RequestBuilder.create(method);
        requestBuilder.setUri(url);
        return getResult(requestBuilder);
    }

    /**
     * 自定义请求
     * 
     * @param method
     *            请求方式
     * @param url
     *            请求地址
     * @param params
     *            Body 参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String request(String method, String url, Map<String, Object> params) throws UnsupportedEncodingException {

        RequestBuilder requestBuilder = RequestBuilder.create(method);
        requestBuilder.setUri(url);

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentEncoding(UTF_8);
        entityBuilder.setText(JSON.toJSONString(params));
        entityBuilder.setContentType(ContentType.APPLICATION_FORM_URLENCODED);

        requestBuilder.setEntity(entityBuilder.build());

        return getResult(requestBuilder);
    }

    /**
     * 自定义请求
     * 
     * @param method
     *            请求方式
     * @param url
     *            请求地址
     * @param headers
     *            Header 参数
     * @param params
     *            Body 参数
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String request(String method, String url, Map<String, Object> headers, Map<String, String> params) throws UnsupportedEncodingException {

        RequestBuilder requestBuilder = RequestBuilder.create(method);
        requestBuilder.setUri(url);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            requestBuilder.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentEncoding(UTF_8);
        entityBuilder.setText(JSON.toJSONString(params));
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);

        requestBuilder.setEntity(entityBuilder.build());

        return getResult(requestBuilder);
    }

    /**
     * 文件上传
     * 
     * @param url
     *            请求地址
     * @param file
     *            文件字节数组
     * @param fileName
     *            文件名
     * @return
     */
    public static String uploadFile(String url, byte[] file, String fileName) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept-Encoding", "gzip");
        httpPost.setHeader("charset", "utf-8");

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(Charset.forName(UTF_8));
        multipartEntityBuilder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, fileName);

        httpPost.setEntity(multipartEntityBuilder.build());

        return getResult(httpPost);
    }
    
    /**
     * 文件下载
     * 
     * @param url
     *            下载地址
     * @param fileName
     *            下载后保存文件名
     * @param savePath
     *            下载后保存路径
     * @throws IOException
     */
    public static void downLoad(String url, String fileName, String savePath) throws IOException {
        // 获取字节数组
        byte[] getData = HttpClientUtils.downLoad(url);
        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
    }

    /**
     * 文件下载
     * 
     * @param urlPath
     *            下载地址
     * @return
     * @throws IOException
     */
    public static byte[] downLoad(String urlPath) throws IOException {
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为30秒
        conn.setConnectTimeout(60 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取字节数组
        byte[] getData = readInputStream(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
        return getData;
    }

    /**
     * 从输入流中获取字节数组
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (param.getValue() != null) {
                pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }

        return pairs;
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity, UTF_8);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return EMPTY_STR;
    }

    /**
     * 处理Http请求
     *
     * @param requestBuilder
     * @return
     */
    private static String getResult(RequestBuilder requestBuilder) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try {
            CloseableHttpResponse response = httpClient.execute(requestBuilder.build());
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity, UTF_8);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return EMPTY_STR;
    }

    public static void main(String[] args) {
        try {
            HttpClientUtils.get("https://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /*String urlPath = "https://www.ctyun.cn/product/getoosfile?f=/ctyunfile/card/2018-11-15/accountId_card_pic2_1542280887395.png";
        String fileName = "1542280887395.png";
        String savePath = "D:\\";
        try {
            HttpTools.downLoad(urlPath, fileName, savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

}
