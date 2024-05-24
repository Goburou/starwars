package com.example.demo.exeption;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException(Long id) {
        super("Character not found with ID: " + id);
    }
}
