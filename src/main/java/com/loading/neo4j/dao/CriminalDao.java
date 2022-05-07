package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Crime;
import com.loading.neo4j.entity.Criminal;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriminalDao extends Neo4jRepository<Criminal, Long> {

    /**
     * 查询对应罪名的所有罪犯
     * @param crime
     * @return
     */
    @Query("match (:Crime { crime: {crime} })<--(n : Criminal) return n")
    List<Criminal> queryCriminalByCrime(@Param("crime") String crime);
}
