package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Basic.BasicRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRelationDao extends Neo4jRepository<BasicRelation, Long>{
}
