package com.loading.neo4j.entity;
import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Basic.BasicNodeInterface;
import com.loading.neo4j.entity.Basic.endNode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;


@NodeEntity
public class Judgment extends endNode implements BasicNodeInterface{
    @Relationship(type = "relationShip", direction = Relationship.INCOMING)
    List<String> judgment;
    public Judgment(){

    }
    public Judgment(List<String> judgment){
        this.judgment = judgment;
    }

    public List<String> getJudgment() {
        return judgment;
    }

    Set<RelationShip> sets = new HashSet<>();
    public Set<RelationShip> getSets() {
        return sets;
    }
}
