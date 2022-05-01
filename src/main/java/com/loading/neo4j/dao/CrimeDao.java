package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Crime;

import com.loading.neo4j.entity.Criminal;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrimeDao extends Neo4jRepository<Crime, Long> {


    /**
     * 查询对应内容的罪名是否已存在
     * @param crime
     * @return
     */
    @Query("match(n:Crime) where n.crime = {crime} return n")
    List<Crime> queryCrimeByContent(@Param("crime") String crime);


    @Query("match(n:Crime) where n.crime = {crime} detach delete n")
    void deleteCrimeByContent(@Param("crime") String crime);

    @Query("match (:Crime { crime: {crime} })<--(n : Criminal) return n")
    List<Criminal> queryCriminalByCrime(@Param("crime") String crime);
}
