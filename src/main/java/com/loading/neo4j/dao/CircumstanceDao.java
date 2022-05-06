package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Circumstance;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CircumstanceDao extends Neo4jRepository<Circumstance, Long> {
    @Query("MATCH (n:Criminal) -->(end : Circumstance) where id(n) = {id} RETURN end")
    Circumstance queryCircumstanceByCriminalId(@Param("id") Long id);
}
