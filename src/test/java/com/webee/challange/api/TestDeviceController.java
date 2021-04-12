package com.webee.challange.api;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
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
    public void getDeviceById() throws IOException {
        Long id = Long.valueOf(20);
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/device/id/" + id );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
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
    public void getDeviceByMacAddress() throws IOException {
        String macAddress = "AA:BB:CC:DD:EE:FF";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/device/macAddress/" + macAddress );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
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
    public void addDevice() throws IOException {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{");
        jsonBody.append("\"macAddress\":\"AA:BB:CC:DD:EE:SS\"");
        jsonBody.append("}");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( "http://localhost:8080/api/device" );
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        HttpEntity postParams = new StringEntity(jsonBody.toString());
        httpPost.setEntity(postParams);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertThat(
                response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_CREATED)
        );
    }

    @Test
    public void addDeviceThatAlreadyExist() throws IOException {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{");
        jsonBody.append("\"macAddress\":\"AA:BB:CC:DD:EE:SS\"");
        jsonBody.append("}");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost( "http://localhost:8080/api/device" );
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.ACCEPT, "application/json");
        HttpEntity postParams = new StringEntity(jsonBody.toString());
        httpPost.setEntity(postParams);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        assertThat(
                response.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_CONFLICT)
        );
    }

    @Test
    public void deleteDeviceById() throws IOException {
        Long id = Long.valueOf(19);
        HttpUriRequest request = new HttpDelete( "http://localhost:8080/api/device/" + id );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK)
        );
    }

    @Test
    public void deleteDeviceByIdThatNotExist() throws IOException {
        Long id = Long.valueOf(100);
        HttpUriRequest request = new HttpDelete( "http://localhost:8080/api/device" + id );
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND)
        );
    }
}