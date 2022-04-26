package com.loading.neo4j.dao;

import com.loading.neo4j.entity.law;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface LawDao extends Neo4jRepository<law, Long> {
}
