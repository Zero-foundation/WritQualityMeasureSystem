package com.loading.neo4j.entity;

import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Basic.BasicRelation;
import com.loading.neo4j.entity.Basic.BasicRelationInterface;
import com.loading.neo4j.entity.Basic.endNode;
import org.neo4j.ogm.annotation.*;


@RelationshipEntity(type = "relationShip")
public class RelationShip extends BasicRelation implements BasicRelationInterface{

    @Property
    private String relationName;

    public RelationShip(){

    }

    public  RelationShip(BasicNode source, endNode target) {
        super(source, target);
    }

    public void setRelationName(String relationName){
        this.relationName = relationName;
    }

    @Override
    public String getRelationName() {
        return relationName;
    }

}
