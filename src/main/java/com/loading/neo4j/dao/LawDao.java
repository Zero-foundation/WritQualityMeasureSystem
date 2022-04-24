package com.loading.neo4j.dao;

import com.loading.neo4j.entity.law;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * desc:
 * Created on 2017/10/13.
 *
 * @author Lo_ading
 * @version 1.0.0
 * @since 1.0.0
 */
public interface LawDao extends Neo4jRepository<law, Long> {
}
