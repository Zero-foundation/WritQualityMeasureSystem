package com.loading.neo4j.dao;


import com.loading.neo4j.entity.Circumstance;
import com.loading.neo4j.entity.Judgment;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JudgmentDao extends Neo4jRepository<Judgment, Long> {
    @Query("MATCH (n:Criminal) -->(end : Judgment) where id(n) = {id} RETURN end")
    Judgment queryJudgmentByCriminalId(@Param("id") Long id);
}
