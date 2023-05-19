package com.ubb.usermanagementservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.usermanagementservice.service.NewUserProgressRequest;

public class TestMain {
    public static void main(String[] args) {
        ObjectMapper jsonMapper = new ObjectMapper();
        final Long learnerUserId = 2L;
        try {
            final NewUserProgressRequest newUserProgressRequest = new NewUserProgressRequest(learnerUserId);
            final String json = jsonMapper.writeValueAsString(newUserProgressRequest);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
