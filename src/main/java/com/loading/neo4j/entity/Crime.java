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
    Criminal criminal;
    public Crime(){
    }

    public void setCriminal(Criminal criminal) {
        this.criminal = criminal;
    }
    public Crime(String crime){

        this.crime = crime;
    }
    public Crime(String crime, Criminal criminal){
        this.criminal = criminal;
        this.crime = crime;
    }

    public Criminal getCriminal() {
        return criminal;
    }

    public String getCrimeContent() {
        return crime;
    }

    Set<RelationShip> sets = new HashSet<>();
    Set<Criminal> sets1 = new HashSet<>();

    public void setSets1(Set<Criminal> sets1) {
        this.sets1 = sets1;
    }

    public Set<Criminal> getSets1() {
        return sets1;
    }
}
