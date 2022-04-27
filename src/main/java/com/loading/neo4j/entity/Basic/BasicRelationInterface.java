package com.loading.neo4j.entity.Basic;


public interface BasicRelationInterface {

    BasicNode getSource();

    void setSource(BasicNode source);

    BasicNode getTarget();

    void setTarget(BasicNode target);

    Long getId();

    void setId(Long id);

    String getRelationName();

    Long getAdded();

    void setAdded(Long added);

}
