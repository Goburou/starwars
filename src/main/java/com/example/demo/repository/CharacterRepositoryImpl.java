package com.example.demo.repository;

import com.example.demo.entity.CharacterEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CharacterRepositoryImpl implements CharacterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CharacterEntity> findById(Long id) {
        return Optional.ofNullable(entityManager.find(CharacterEntity.class, id));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public <S extends CharacterEntity> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public List<CharacterEntity> findAll() {
        TypedQuery<CharacterEntity> query = entityManager.createQuery("SELECT c FROM CharacterEntity c", CharacterEntity.class);
        return query.getResultList();
    }

    @Override
    public List<CharacterEntity> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public CharacterEntity save(CharacterEntity character) {
        return entityManager.merge(character);
    }

    @Override
    public void deleteById(Long id) {
        CharacterEntity character = entityManager.find(CharacterEntity.class, id);
        if (character != null) {
            entityManager.remove(character);
        }
    }

    @Override
    public void delete(CharacterEntity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends CharacterEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void incrementQueryCount(Long characterId) {
        CharacterEntity character = entityManager.find(CharacterEntity.class, characterId);
        if (character != null) {
            character.setQueryCount(character.getQueryCount() + 1);
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends CharacterEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends CharacterEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<CharacterEntity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CharacterEntity getOne(Long aLong) {
        return null;
    }

    @Override
    public CharacterEntity getById(Long aLong) {
        return null;
    }

    @Override
    public CharacterEntity getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends CharacterEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CharacterEntity> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends CharacterEntity> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends CharacterEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CharacterEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CharacterEntity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends CharacterEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<CharacterEntity> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<CharacterEntity> findAll(Pageable pageable) {
        return null;
    }
}
