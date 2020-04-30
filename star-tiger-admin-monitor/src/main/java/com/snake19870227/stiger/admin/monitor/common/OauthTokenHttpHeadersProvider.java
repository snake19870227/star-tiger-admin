package com.snake19870227.stiger.admin.monitor.common;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.snake19870227.stiger.core.StarTigerConstant;
import com.snake19870227.stiger.core.utils.JsonUtil;

import static com.snake19870227.stiger.admin.StarTigerAdminMonitorContext.INSTANCE_ACCESS_TOKENS;
import static com.snake19870227.stiger.admin.StarTigerAdminMonitorContext.INSTANCE_ACCESS_TOKEN_EXPIRES;

/**
 * @author Bu HuaYang (buhuayang1987@foxmail.com)
 * 2020/04/30
 */
public class OauthTokenHttpHeadersProvider implements HttpHeadersProvider, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(OauthTokenHttpHeadersProvider.class);

    public static final String OAUTH_RESOURCE_SERVER = "oauth.resource.server";
    public static final String OAUTH_TOKEN_ENDPOINT = "oauth.token.endpoint";
    public static final String CLIENT_ID = "client.id";
    public static final String CLIENT_SECRET = "client.secret";

    private final RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    public OauthTokenHttpHeadersProvider(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public HttpHeaders getHeaders(Instance instance) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Map<String, String> metadata = instance.getRegistration().getMetadata();

        if (metadata.containsKey(OAUTH_RESOURCE_SERVER)) {

            String url = metadata.get(OAUTH_TOKEN_ENDPOINT);

            try {
                String accessToken = null;

                if (INSTANCE_ACCESS_TOKENS.containsKey(instance.getId().getValue())) {

                    accessToken = INSTANCE_ACCESS_TOKENS.get(instance.getId().getValue());

                    LocalDateTime expiresDateTime = INSTANCE_ACCESS_TOKEN_EXPIRES.get(instance.getId().getValue());
                    if (LocalDateTime.now().isAfter(expiresDateTime)) {
                        accessToken = createAccessToken(instance, url, metadata.get(CLIENT_ID), metadata.get(CLIENT_SECRET));
                    }

                } else if (metadata.containsKey(OAUTH_TOKEN_ENDPOINT)
                        && metadata.containsKey(CLIENT_ID)
                        && metadata.containsKey(CLIENT_SECRET)) {

                    accessToken = createAccessToken(instance, url, metadata.get(CLIENT_ID), metadata.get(CLIENT_SECRET));
                }

                httpHeaders.add("Authorization", StarTigerConstant.Oauth2Code.BEARER_TOKEN_PREFIX + accessToken);
            } catch (JsonProcessingException e) {
                logger.error("获取AccessToken失败", e);
            }
        }
        return httpHeaders;
    }

    private String createAccessToken(Instance instance, String oauthTokenEndpoint, String clientId, String clientSecret) throws JsonProcessingException {

        String body = "grant_type=client_credentials" + "&" +
                "scope=actuator" + "&" +
                "client_id=" + clientId + "&" +
                "client_secret=" + clientSecret;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(oauthTokenEndpoint, httpEntity, String.class);

        JsonNode jsonNode = JsonUtil.buildJacksonObjectMapper().readTree(responseEntity.getBody());
        JsonNode access_token = jsonNode.get("access_token");
        JsonNode expires_in = jsonNode.get("expires_in");

        String accessToken = access_token.textValue();
        int expiresIn = expires_in.intValue();

        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime expireDateTime = localDateTime.plusSeconds(expiresIn);

        INSTANCE_ACCESS_TOKENS.put(instance.getId().getValue(), accessToken);
        INSTANCE_ACCESS_TOKEN_EXPIRES.put(instance.getId().getValue(), expireDateTime);

        return accessToken;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restTemplate = restTemplateBuilder.requestFactory(OkHttp3ClientHttpRequestFactory::new).build();
    }
}
