package com.loading.neo4j.datainteract;

import com.loading.neo4j.datainteract.graph.node;
import com.loading.neo4j.datainteract.graph.relation;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Graph {
    public Graph(){
        this.nodeList=new ArrayList<>();
        this.relationList=new ArrayList<>();
    }
    List<node> nodeList;
    List<relation> relationList;
    public void addNode(node n){
        this.nodeList.add(n);
    }
    public void addRelation(relation r){
        this.relationList.add(r);
    }

}
