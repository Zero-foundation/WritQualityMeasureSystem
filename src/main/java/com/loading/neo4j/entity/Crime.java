package com.loading.neo4j.entity;
import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Basic.BasicNodeInterface;
import com.loading.neo4j.entity.Basic.endNode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;


@NodeEntity
public class Crime extends endNode implements BasicNodeInterface{
    @Relationship(type = "relationShip", direction = Relationship.INCOMING)
    String crime;
    public Crime(){
    }
    public Crime(String crime){
        this.crime = crime;
    }
    Set<RelationShip> sets = new HashSet<>();
    public Set<RelationShip> getSets() {
        return sets;
    }
}
