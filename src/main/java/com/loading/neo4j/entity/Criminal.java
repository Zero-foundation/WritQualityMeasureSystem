package com.loading.neo4j.entity;

import com.loading.neo4j.entity.Basic.BasicNodeInterface;
import com.loading.neo4j.entity.Basic.BasicNode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;


@NodeEntity
public class Criminal extends BasicNode implements BasicNodeInterface{

    private int age;
    private String name;

    @Relationship(type = "relationShip", direction = Relationship.OUTGOING)
    private Set<RelationShip> relationShips;

    public Criminal(){

    }
    public Criminal(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<RelationShip> getRelationShips() {
        return relationShips;
    }

    public void setRelationShips(Set<RelationShip> relationShips) {
        this.relationShips = relationShips;
    }
}
