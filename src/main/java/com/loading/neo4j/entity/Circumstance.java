package com.loading.neo4j.entity;
import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Basic.BasicNodeInterface;
import com.loading.neo4j.entity.Basic.endNode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;


@NodeEntity
public class Circumstance extends BasicNode implements BasicNodeInterface {
    private List<String> circumstanceList;
    public Circumstance(){

    }
    public Circumstance(List<String> circumstanceList){
        this.circumstanceList = circumstanceList;
    }
    //@Override
    Set<RelationShip> sets = new HashSet<>();
    public Set<RelationShip> getSets() {
        return sets;
    }
}
