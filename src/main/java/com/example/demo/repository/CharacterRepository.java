package com.example.demo.repository;

import com.example.demo.entity.CharacterEntity;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE CharacterEntity c SET c.queryCount = c.queryCount + 1 WHERE c.id = :characterId")
    void incrementQueryCount(@Param("characterId") Long characterId);
}
