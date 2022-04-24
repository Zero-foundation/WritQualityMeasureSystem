package com.loading.neo4j.dao;


import com.loading.neo4j.entity.Judgment;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface JudgmentDao extends Neo4jRepository<Judgment, Long> {

}
