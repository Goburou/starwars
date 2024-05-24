package com.example.demo.mock;

public class CharacterDTOMock {

    private Long id;
    private String name;

    public CharacterDTOMock() {
        this.id = 1L;
        this.name = "Mock Character";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
