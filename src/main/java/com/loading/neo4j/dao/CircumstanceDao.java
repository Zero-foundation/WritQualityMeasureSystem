package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Circumstance;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircumstanceDao extends Neo4jRepository<Circumstance, Long> {

}
