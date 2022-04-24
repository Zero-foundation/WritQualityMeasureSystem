package com.loading.neo4j.entity;
import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Basic.BasicNodeInterface;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.*;


@NodeEntity
public class Crime extends BasicNode implements BasicNodeInterface{
    String crimeContent;
    public Crime(){

    }
    public Crime(String crimeContent){
        this.crimeContent = crimeContent;
    }
    Set<RelationShip> sets = new HashSet<>();
    public Set<RelationShip> getSets() {
        return sets;
    }
}
