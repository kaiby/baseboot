package com.peazone.core.base.rest.RPCClient;

import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestGetClient extends RestClientBase {
    private static final Logger logger = LoggerFactory.getLogger(RestGetClient.class);

    public RestGetClient() {
    }

    public String callRestRPC(String requestURI, Map<String, String> headerParam) {
        try {
            HttpGet e = new HttpGet();
            this.setURI(e, requestURI);
            this.initHttpHeader(e, headerParam);
            return this.getResponse(e);
        } catch (URISyntaxException var4) {
            var4.printStackTrace();
            logger.error("Error request uri: {}", requestURI);
            return null;
        }
    }
}
