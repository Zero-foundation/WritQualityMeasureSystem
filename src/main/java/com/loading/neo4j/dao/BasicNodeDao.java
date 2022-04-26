package com.loading.neo4j.dao;

import com.loading.neo4j.entity.Basic.BasicNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BasicNodeDao<T extends BasicNode> extends Neo4jRepository<T, Long> {


    @Override
    @Query("start n=node({0}) detach delete n")
    void delete(Long id);

}
