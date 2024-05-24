package com.example.demo.testecontroller;

import com.example.demo.controller.CharacterController;
import com.example.demo.entity.CharacterEntity;
import com.example.demo.exeption.CharacterNotFoundException;
import com.example.demo.mock.CharacterEntityMock;
import com.example.demo.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CharacterControllerTest {

    @Mock
    private CharacterService characterService;

    @InjectMocks
    private CharacterController characterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCharacters() {
        List<CharacterEntity> characters = Collections.singletonList(new CharacterEntityMock());
        when(characterService.getAllCharacters()).thenReturn(characters);

        ResponseEntity<List<CharacterEntity>> response = characterController.getAllCharacters();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(characters, response.getBody());
    }

    @Test
    void testGetCharacterById() {
        CharacterEntity character = new CharacterEntityMock();
        when(characterService.getCharacterById(1L)).thenReturn(character);

        ResponseEntity<CharacterEntity> response = characterController.getCharacterById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(character, response.getBody());
    }

    @Test
    void testGetCharacterByIdNotFound() {
        when(characterService.getCharacterById(1L)).thenThrow(new CharacterNotFoundException(1L));

        ResponseEntity<CharacterEntity> response = characterController.getCharacterById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddCharacter() {
        CharacterEntity character = new CharacterEntityMock();
        CharacterEntityMock characterDTO = new CharacterEntityMock();

        when(characterService.addCharacter(any(CharacterEntity.class))).thenReturn(character);

        ResponseEntity<CharacterEntity> response = characterController.addCharacter(characterDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(character, response.getBody());
    }

    @Test
    void testUpdateCharacter() {
        CharacterEntity updatedCharacter = new CharacterEntityMock();
        CharacterEntityMock characterDTO = new CharacterEntityMock();

        when(characterService.updateCharacter(any(Long.class), any(CharacterEntity.class))).thenReturn(updatedCharacter);

        ResponseEntity<CharacterEntity> response = characterController.updateCharacter(1L, characterDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCharacter, response.getBody());
    }

    @Test
    void testDeleteCharacter() {
        ResponseEntity<Void> response = characterController.deleteCharacter(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(characterService, times(1)).deleteCharacter(1L);
    }
}
