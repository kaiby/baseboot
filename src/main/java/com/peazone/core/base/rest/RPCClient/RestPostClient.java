package com.peazone.core.base.rest.RPCClient;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestPostClient extends RestClientBase {
    private static final Logger logger = LoggerFactory.getLogger(RestPostClient.class);

    public RestPostClient() {
    }

    public String callRestRPC(String requestURI, Map<String, String> headerParam, Map<String, String> postParam) {
        try {
            HttpPost e = new HttpPost();
            this.setURI(e, requestURI);
            this.initHttpHeader(e, headerParam);
            if (postParam != null) {
                ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                Iterator<Entry<String, String>> var7 = postParam.entrySet().iterator();

                while (true) {
                    if (!var7.hasNext()) {
                        UrlEncodedFormEntity entity1 = new UrlEncodedFormEntity(params, "utf-8");
                        e.setEntity(entity1);
                        break;
                    }

                    Entry<String, String> entity = var7.next();
                    params.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
                }
            }

            return this.getResponse(e);
        } catch (URISyntaxException var8) {
            logger.error("Error request uri: {}", requestURI);
        } catch (UnsupportedEncodingException var9) {
            logger.error("Error request param on : {}", requestURI);
        }

        return null;
    }

    public String callRestRPC(String requestURI, Map<String, String> headerParam, String postParam) {
        try {
            HttpPost e = new HttpPost();
            this.setURI(e, requestURI);
            this.initHttpHeader(e, headerParam);
            if (postParam != null) {
                StringEntity entity = new StringEntity(postParam, "utf-8");
                ((AbstractHttpEntity) entity).setContentEncoding(new BasicHeader("Content-Type", "application/json"));
                e.setEntity(entity);
            }

            return this.getResponse(e);
        } catch (URISyntaxException var6) {
            logger.error("Error request uri: {}", requestURI);
        }

        return null;
    }

    /**
     * 
     * @param requestURI
     *            请求URI
     * @param headerParam
     *            头参数
     * @param postParam
     *            请求体参数
     * @param contentType
     *            Content-Type 例：application/json、text/xml 等
     * @return
     */
    public String callRestRPC(String requestURI, Map<String, String> headerParam, String postParam, String contentType) {
        try {
            HttpPost e = new HttpPost();
            this.setURI(e, requestURI);
            this.initHttpHeader(e, headerParam);
            if (postParam != null) {
                StringEntity entity = new StringEntity(postParam, "UTF-8");
                ((AbstractHttpEntity) entity).setContentEncoding(new BasicHeader("Content-Type", contentType));
                e.setEntity(entity);
            }
            return this.getResponse(e);
        } catch (URISyntaxException var6) {
            logger.error("Error request uri: {}", requestURI);
        }

        return null;
    }
}
