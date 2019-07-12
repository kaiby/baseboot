package com.peazone.core.base.rest.RPCClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RestClientBase {
    private static final Logger logger = LoggerFactory.getLogger(RestClientBase.class);
    protected String baseHost = null;

    public RestClientBase() {
    }

    public String getBaseHost() {
        return this.baseHost;
    }

    public void setBaseHost(String baseHost) {
        this.baseHost = baseHost;
    }

    protected void setURI(HttpRequestBase requestBase, String requestURI) throws URISyntaxException {
        String uri;
        if (this.getBaseHost() != null) {
            uri = this.getBaseHost() + requestURI;
        } else {
            uri = requestURI;
        }

        requestBase.setURI(new URI(uri));
    }

    protected void initHttpHeader(HttpRequestBase requestBase, Map<String, String> headerParam) {
        if (headerParam != null) {
            Iterator<Entry<String, String>> var4 = headerParam.entrySet().iterator();

            while (var4.hasNext()) {
                Entry<String, String> entry = var4.next();
                requestBase.addHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }

    }

    protected String getResponse(HttpRequestBase requestBase) {
        try {
            HttpClientBuilder builder = HttpClientBuilder.create();
            CloseableHttpClient client = builder.build();
            CloseableHttpResponse response = client.execute(requestBase);

            // DefaultHttpClient e = new DefaultHttpClient();
            // HttpResponse response = e.execute(requestBase);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException var5) {
            var5.printStackTrace();
            logger.error("Http request failed on {}", requestBase.getURI().toString());
        }

        return null;
    }
}
