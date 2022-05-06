package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Crime;
import com.loading.neo4j.entity.Criminal;
import com.loading.neo4j.entity.Law;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawDao extends Neo4jRepository<Law, Long> {
    /**
     * 查询对应内容的法条是否已存在
     * @param law
     * @return
     */
    @Query("match(n:Law) where n.law = {law} return n")
    Law queryLawByContent(@Param("law") String law);

    @Query("match(n:Law) where n.law = {law} detach delete n")
    void deleteLawByContent(@Param("law") String law);

    @Query("match (:Law { law: {law} })<--(n : Criminal) return n")
    List<Criminal> queryCriminalByLaw(@Param("law") String law);

    @Query("MATCH (n:Criminal) -->(end : Law) where id(n) = {id} RETURN end")
    List<Law> queryLawByCriminalId(@Param("id") Long id);
}
