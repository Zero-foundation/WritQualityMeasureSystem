package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Criminal;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface CriminalDao extends Neo4jRepository<Criminal, Long> {
}
