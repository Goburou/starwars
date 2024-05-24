package com.example.demo.testeservice;

import com.example.demo.entity.CharacterEntity;
import com.example.demo.exeption.CharacterNotFoundException;
import com.example.demo.mock.CharacterEntityMock;
import com.example.demo.repository.CharacterRepository;
import com.example.demo.repository.SwapiResponse;
import com.example.demo.rest.SwapiRestClient;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @Mock
    private SwapiRestClient swapiRestClient;

    @InjectMocks
    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchAndSaveCharactersFromSwapi() {
        SwapiResponse<CharacterEntity> swapiResponse = new SwapiResponse<>();
        swapiResponse.setResults(Collections.singletonList(new CharacterEntityMock()));
        ResponseEntity<SwapiResponse<CharacterEntity>> responseEntity = new ResponseEntity<>(swapiResponse, HttpStatus.OK);
        when(swapiRestClient.fetchCharactersFromSwapi()).thenReturn(responseEntity);

        assertDoesNotThrow(() -> characterService.fetchAndSaveCharactersFromSwapi());

        verify(characterRepository, times(1)).saveAll(swapiResponse.getResults());
    }

    @Test
    void testFetchAndSaveCharactersFromSwapiWithFailedResponse() {
        ResponseEntity<SwapiResponse<CharacterEntity>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(swapiRestClient.fetchCharactersFromSwapi()).thenReturn(responseEntity);

        assertThrows(IllegalStateException.class, () -> characterService.fetchAndSaveCharactersFromSwapi());

        verify(characterRepository, never()).saveAll(any());
    }

    @Test
    void testGetAllCharacters() {
        List<CharacterEntity> characters = Collections.singletonList(new CharacterEntityMock());
        when(characterRepository.findAll()).thenReturn(characters);

        List<CharacterEntity> result = characterService.getAllCharacters();

        assertEquals(characters, result);
    }

    @Test
    void testGetCharacterById() {
        CharacterEntity character = new CharacterEntityMock();
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));
        when(characterRepository.save(any(CharacterEntity.class))).thenReturn(character); // Ensure the save mock

        CharacterEntity result = characterService.getCharacterById(1L);

        assertNotNull(result);
        assertEquals(character, result);
    }

    @Test
    void testGetCharacterByIdNotFound() {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CharacterNotFoundException.class, () -> characterService.getCharacterById(1L));
    }

    @Test
    void testFindById() {
        CharacterEntity character = new CharacterEntityMock();
        when(characterRepository.findById(1L)).thenReturn(Optional.of(character));

        CharacterEntity result = characterService.findById(1L);

        assertNotNull(result);
        assertEquals(character, result);
    }

    @Test
    void testFindByIdNotFound() {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        CharacterEntity result = characterService.findById(1L);

        assertNull(result);
    }

    @Test
    void testAddCharacter() {
        CharacterEntity character = new CharacterEntityMock();
        when(characterRepository.save(any())).thenReturn(character);

        CharacterEntity result = characterService.addCharacter(character);

        assertNotNull(result);
        assertEquals(character, result);
    }

    @Test
    void testUpdateCharacter() {
        CharacterEntity existingCharacter = new CharacterEntityMock();
        CharacterEntity updatedCharacter = new CharacterEntityMock();
        updatedCharacter.setName("Updated Character");

        when(characterRepository.findById(1L)).thenReturn(Optional.of(existingCharacter));
        when(characterRepository.save(any())).thenReturn(updatedCharacter);

        CharacterEntity result = characterService.updateCharacter(1L, updatedCharacter);

        assertNotNull(result);
        assertEquals(updatedCharacter.getName(), result.getName());
    }

    @Test
    void testUpdateCharacterNotFound() {
        when(characterRepository.findById(1L)).thenReturn(Optional.empty());

        CharacterEntity updatedCharacter = new CharacterEntityMock();
        assertThrows(CharacterNotFoundException.class, () -> characterService.updateCharacter(1L, updatedCharacter));
    }

    @Test
    void testDeleteCharacter() {
        doNothing().when(characterRepository).deleteById(1L);

        assertDoesNotThrow(() -> characterService.deleteCharacter(1L));
    }
}
