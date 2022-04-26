package com.loading.neo4j.GraphCreate;

import com.loading.neo4j.datainteract.criminalContent;
import com.loading.neo4j.entity.*;
import com.loading.neo4j.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class createGraphDaoImpl {
    @Autowired
    private GraphService graphService;
    List<criminalContent> criminalContentList;
    criminalContent c;
    Criminal criminalA;
    public createGraphDaoImpl(List<criminalContent> cl){
        this.criminalContentList = cl;
    }
    public void createGraph(){

        for(criminalContent cC : criminalContentList){
            this.c = cC;
            createCriminal();
            createCrime();
            createLaw();
            createCircumstance();
            createJudgement();
        }

    }
    private void createCriminal(){
        criminalA = new Criminal();
        String name = c.getCriminalName();
        criminalA.setNodeName(name);
        //criminalA.setAge(20);
        graphService.saveNode(criminalA);
    }
    private void createJudgement(){
        Judgment j = new Judgment(c.getJudgement());
        graphService.saveNode(j);
        RelationShip reA = new RelationShip(criminalA,j);
        reA.setRelationName("punish");
    }
    private void createCrime(){
        List<String> crimeList = c.getCrimeList();
        for(String s: crimeList){
            Crime crime = new Crime(s);
            graphService.saveNode(crime);
            RelationShip reA = new RelationShip(criminalA,crime);
            reA.setRelationName("convicted");
        }

    }
    private void createLaw(){
        List<String> lawList = c.getArticles_law();
        for(String s: lawList){
            Law law = new Law(s);
            graphService.saveNode(law);
            RelationShip reA = new RelationShip(criminalA,law);
            reA.setRelationName("apply");
        }

    }
    private void createCircumstance(){
        List<String> cList = c.getCircumstance();
        Circumstance circu = new Circumstance(cList);
        graphService.saveNode(circu);
        RelationShip reA = new RelationShip(criminalA,circu);
        reA.setRelationName("accuse");

    }

}
