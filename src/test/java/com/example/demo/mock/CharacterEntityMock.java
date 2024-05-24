package com.example.demo.mock;

import com.example.demo.entity.CharacterEntity;

public class CharacterEntityMock extends CharacterEntity {

    public CharacterEntityMock() {
        this.setId(1L);
        this.setName("Mock Character");
        this.setQueryCount(0L);
    }
}