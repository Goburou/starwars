package com.example.demo;

import com.example.demo.service.CharacterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	private final CharacterService characterService;

	public DemoApplication(CharacterService characterService) {
		this.characterService = characterService;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
