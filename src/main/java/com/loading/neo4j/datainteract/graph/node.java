package com.loading.neo4j.datainteract.graph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class node{
    int id;
    String name;
    String type;

    public node(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}