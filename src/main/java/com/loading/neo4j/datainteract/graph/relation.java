package com.loading.neo4j.datainteract.graph;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class relation{
    int source;
    int target;

    public relation(int source, int target) {
        this.source = source;
        this.target = target;
    }
}