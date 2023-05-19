package com.ubb.usermanagementservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestsHandler {
    private final HttpClient httpClient;
    private final ObjectMapper jsonMapper;

    public HttpRequestsHandler() {
        this.httpClient = HttpClient.newHttpClient();
        this.jsonMapper = new ObjectMapper();
    }

    public String sendRequestForNewUserProgress(final Long learnerUserId) {
        try {
            final NewUserProgressRequest newUserProgressRequest = new NewUserProgressRequest(learnerUserId);
            final String json = jsonMapper.writeValueAsString(newUserProgressRequest);
            final HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(new URI("http://localhost:8083/progress"))
                    .header("Content-type", "application/json")
                    .header("Accept", "application/json")
                    .build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return jsonMapper.readValue(response.body(), LearnerUserProgressLevelResponse.class).getLearnerUserProgressLevel();
        } catch (URISyntaxException | InterruptedException | IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String sendRequestToGetLearnerUserProgressLevel(final Long learnerUserId) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI("http://localhost:8083/progress/" + learnerUserId))
                    .build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return jsonMapper.readValue(response.body(), LearnerUserProgressLevelResponse.class).getLearnerUserProgressLevel();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
