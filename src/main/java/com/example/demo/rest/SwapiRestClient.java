package com.example.demo.rest;

import com.example.demo.entity.CharacterEntity;
import com.example.demo.repository.SwapiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SwapiRestClient {

    private static final String SWAPI_BASE_URL = "https://swapi.dev/api/people/";

    private final RestTemplate restTemplate;

    @Autowired
    public SwapiRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<SwapiResponse<CharacterEntity>> fetchCharactersFromSwapi() {
        return restTemplate.exchange(
                SWAPI_BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<SwapiResponse<CharacterEntity>>() {}
        );
    }
}