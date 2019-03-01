package com.testingbot;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        final String testingBotKey = System.getenv("TESTINGBOT_KEY");
        final String testingBotSecret = System.getenv("TESTINGBOT_SECRET");

        String urlApi = "https://api.testingbot.com/v1/storage";

        HttpClient httpClient = HttpClientBuilder.create()
                .build();

        URI url = null;
        try {
            url = new URI(urlApi);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpPost post = new HttpPost(url);

        HttpEntity entity = MultipartEntityBuilder.create().addPart("file", new FileBody(new File(args[0]))).build();

        post.setEntity(entity);

        HttpResponse response;
        try {
            HttpHost httpHost = URIUtils.extractHost(new URI(urlApi));
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(testingBotKey, testingBotSecret));
            AuthCache authCache = new BasicAuthCache();
            authCache.put(httpHost, new BasicScheme());
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);

            response = httpClient.execute(post, context);
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity,"UTF-8");
            System.out.println(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
