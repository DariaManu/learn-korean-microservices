package com.ubb.vocabularyservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.vocabularyservice.model.Vocabulary;
import com.ubb.vocabularyservice.service.VocabularyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(VocabularyController.class)
class VocabularyControllerTest {
    @MockBean
    private VocabularyService mockVocabularyService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void givenExistingWordReturnAllTranslations() throws Exception {
        //given
        Mockito.when(mockVocabularyService.findAllByWord("밥")).thenReturn(
                List.of(new Vocabulary(1L, "밥", "rice", null),
                        new Vocabulary(2L, "밥", "meal", null))
        );

        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/vocabulary/밥")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        List<Vocabulary> translations = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Vocabulary>>(){});
        assertEquals(translations.get(0).getTranslation(), "rice");
        assertEquals(translations.get(1).getTranslation(), "meal");
    }

    @Test
    public void givenWordThatDoesNotExistReturnEmptyList() throws Exception {
        //given
        Mockito.when(mockVocabularyService.findAllByWord("사랑해")).thenReturn(new ArrayList<>());

        //when
        MockHttpServletResponse response = mockMvc.perform(
                get("/vocabulary/사랑해")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //then
        assertEquals(response.getStatus(), HttpStatus.OK.value());
        List<Vocabulary> translations = objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Vocabulary>>(){});
        assertEquals(translations.size(), 0);
    }
}