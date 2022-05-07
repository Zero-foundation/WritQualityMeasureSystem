package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Crime;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CrimeDao extends Neo4jRepository<Crime, Long> {

}
