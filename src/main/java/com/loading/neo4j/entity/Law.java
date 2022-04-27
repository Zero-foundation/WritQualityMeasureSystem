package com.loading.neo4j.entity;

import com.loading.neo4j.entity.Basic.BasicNodeInterface;
import com.loading.neo4j.entity.Basic.endNode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;


@NodeEntity
public class Law extends endNode implements BasicNodeInterface{

    private String law;

    @Relationship(type = "relationShip", direction = Relationship.INCOMING)
    //private Set<RelationShip> partner;

    //@Relationship(type = "INVEST", direction = Relationship.OUTGOING)
    private Set<RelationShip> relationShips;

    public Law(){

    }
    public Law(String law){
        this.law = law;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }

    //public Set<RelationShip> getPartner() {
        //return partner;
    //}

    //public void setPartner(Set<RelationShip> partner) {
        //this.partner = partner;
    //}

    public Set<RelationShip> getRelationShips() {
        return relationShips;
    }

    public void setRelationShips(Set<RelationShip> relationShips) {
        this.relationShips = relationShips;
    }
}
