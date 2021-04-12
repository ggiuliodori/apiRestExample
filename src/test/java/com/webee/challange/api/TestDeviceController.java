package com.webee.challange.api;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestDeviceController {

    @Test
    public void getDeviceByIdThatNotExist() throws IOException {
        Long id = Long.valueOf(100);
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/device/id/" + id );

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND)
        );
    }

    @Test
    public void getDeviceByMacAddressThatNotExist() throws IOException {
        String macAddress = "AA:AA:AA:AA:AA:AA";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/device/macAddress/" + macAddress );

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND)
        );
    }

    @Test
    public void getAllDevices() throws IOException {
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/device" );

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
        );
    }

    @Test
    public void addDeviceThatAlreadyExist() throws IOException {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("macAddress", "AA:BB:CC:DD:EE:FF"));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( "http://localhost:8080/api/device" );
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        httpPost.setHeader(HttpHeaders.ACCEPT_ENCODING, "Utf8Encoder");
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = httpClient.execute(httpPost);

        assertThat(
                response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_CONFLICT)
        );
    }
}