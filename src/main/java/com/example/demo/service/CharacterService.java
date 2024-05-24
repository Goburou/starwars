package com.example.demo.service;

import com.example.demo.entity.CharacterEntity;
import com.example.demo.exeption.CharacterNotFoundException;
import com.example.demo.repository.CharacterRepository;
import com.example.demo.repository.SwapiResponse;
import com.example.demo.rest.SwapiRestClient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final SwapiRestClient swapiRestClient;

    @Autowired
    public CharacterService(CharacterRepository characterRepository, SwapiRestClient swapiRestClient) {
        this.characterRepository = characterRepository;
        this.swapiRestClient = swapiRestClient;
    }

    @Transactional
    public void fetchAndSaveCharactersFromSwapi() {
        try {
            ResponseEntity<SwapiResponse<CharacterEntity>> response = swapiRestClient.fetchCharactersFromSwapi();

            if (response.getStatusCode() == HttpStatus.OK) {
                SwapiResponse<CharacterEntity> swapiResponse = response.getBody();
                if (swapiResponse != null && swapiResponse.getResults() != null) {
                    List<CharacterEntity> characters = swapiResponse.getResults();
                    characterRepository.saveAll(characters);
                }
            } else {
                throw new IllegalStateException("Failed to fetch characters from SWAPI: " + response.getStatusCode());
            }
        } catch (DataAccessException ex) {
            throw new IllegalStateException("An error occurred while accessing the database", ex);
        } catch (Exception ex) {
            throw new IllegalStateException("An unexpected error occurred", ex);
        }
    }

    public List<CharacterEntity> getAllCharacters() {
        return characterRepository.findAll();
    }

    public CharacterEntity getCharacterById(Long id) {
        Optional<CharacterEntity> characterOptional = characterRepository.findById(id);
        if (characterOptional.isPresent()) {
            CharacterEntity character = characterOptional.get();
            character.setQueryCount(character.getQueryCount() + 1);
            return characterRepository.save(character);
        } else {
            throw new CharacterNotFoundException(id);
        }
    }

    public CharacterEntity findById(Long id) {
        CharacterEntity character = characterRepository.findById(id).orElse(null);
        if (character != null) {
            characterRepository.incrementQueryCount(id);
        }
        return character;
    }

    public CharacterEntity addCharacter(CharacterEntity character) {
        Objects.requireNonNull(character, "Character cannot be null");
        return characterRepository.save(character);
    }

    public CharacterEntity updateCharacter(Long id, CharacterEntity updatedCharacter) {
        Objects.requireNonNull(updatedCharacter, "Updated character cannot be null");
        CharacterEntity character = getCharacterById(id);
        character.setName(updatedCharacter.getName());
        return characterRepository.save(character);
    }

    public void deleteCharacter(Long id) {
        characterRepository.deleteById(id);
    }
}
