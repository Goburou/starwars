package com.example.demo.controller;

import com.example.demo.entity.CharacterEntity;
import com.example.demo.exeption.CharacterNotFoundException;
import com.example.demo.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/characters")
@Tag(name = "Character API", description = "API for managing Star Wars characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    @Operation(summary = "Get all characters", description = "Retrieves all characters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved characters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CharacterEntity>> getAllCharacters() {
        List<CharacterEntity> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get character by ID", description = "Retrieves a character by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the character"),
            @ApiResponse(responseCode = "404", description = "Character not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CharacterEntity> getCharacterById(@PathVariable Long id) {
        try {
            System.out.println("Searching for character with ID: " + id);
            CharacterEntity character = characterService.getCharacterById(id);
            System.out.println("Character found: " + character.getName());
            return ResponseEntity.ok(character);
        } catch (CharacterNotFoundException ex) {
            System.out.println("Character not found with ID: " + id);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            System.out.println("Error occurred while searching for character with ID: " + id);
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Add a new character", description = "Creates a new character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Character created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CharacterEntity> addCharacter(@RequestBody CharacterEntity character) {
        CharacterEntity newCharacter = characterService.addCharacter(character);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCharacter);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a character", description = "Updates an existing character")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Character updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CharacterEntity> updateCharacter(@PathVariable Long id, @RequestBody CharacterEntity updatedCharacter) {
        CharacterEntity updated = characterService.updateCharacter(id, updatedCharacter);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a character", description = "Deletes a character by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Character deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return ResponseEntity.noContent().build();
    }
}
