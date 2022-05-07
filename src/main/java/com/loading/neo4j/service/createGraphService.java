package com.loading.neo4j.service;

import com.loading.neo4j.dao.BasicNodeDao;
import com.loading.neo4j.dao.CrimeDao;
import com.loading.neo4j.dao.LawDao;
import com.loading.neo4j.datainteract.criminalContent;
import com.loading.neo4j.entity.*;
import com.loading.neo4j.entity.Basic.BasicNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class createGraphService {

    @Autowired
    private BasicNodeDao<BasicNode> basicNodeDao;
    @Autowired
    private CrimeDao crimeDao;
    @Autowired
    private LawDao lawDao;

    @Autowired
    private GraphService graphService;

    List<criminalContent> criminalContentList;
    criminalContent c;
    Criminal criminalA;
    List<String> containsCrime = new ArrayList<>();
    List<String> containsLaw = new ArrayList<>();
    //Set<String> sets = new HashSet<>();
    public void createGraphList(List<criminalContent> cl){
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
        removeRepeatCrimes(containsCrime);
        removeRepeatLaw(containsLaw);

    }
    public void removeRepeatCrimes(List<String> containsCrime){

        for(String str : containsCrime){
            List<Criminal> criminals =  crimeDao.queryCriminalByCrime(str);
            crimeDao.deleteCrimeByContent(str);
            Crime newCrime = new Crime(str);
            newCrime.setNodeName("罪名");
            graphService.saveNode(newCrime);
            for(Criminal criminal : criminals){
                RelationShip reA = new RelationShip(criminal,newCrime);
                reA.setRelationName("convicted");
                graphService.saveRelation(reA);
            }
        }
    }
    public void removeRepeatLaw(List<String> containsLaw){

        for(String str : containsLaw){
            List<Criminal> criminals =  lawDao.queryCriminalByLaw(str);
            lawDao.deleteLawByContent(str);
            Law newLaw = new Law(str);
            newLaw.setNodeName("法律法条");

            graphService.saveNode(newLaw);
            for(Criminal criminal : criminals){
                RelationShip reA = new RelationShip(criminal,newLaw);
                reA.setRelationName("apply");
                graphService.saveRelation(reA);
            }
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
        j.setNodeName("判罚");
        graphService.saveNode(j);
        RelationShip reA = new RelationShip(criminalA,j);
        reA.setRelationName("punish");
        graphService.saveRelation(reA);
    }
    private void createCrime(){
        List<String> crimeList = c.getCrimeList();

        for(String s: crimeList){
            if(s==null) continue;
            if(s.equals("")) continue;
            Crime crime;
            crime = new Crime(s);
            crime.setNodeName("罪名");

            graphService.saveNode(crime);
            if(!containsCrime.contains(s)){
                containsCrime.add(s);
            }

            RelationShip reA = new RelationShip(criminalA,crime);
            reA.setRelationName("convicted");
            graphService.saveRelation(reA);
            /*
            if(!sets.contains(s)){
                sets.add(s);
                crime = new Crime(s);
                crime.setNodeName("罪名");
                graphService.saveNode(crime);
                RelationShip reA = new RelationShip(criminalA,crime);
                reA.setRelationName("convicted");
                graphService.saveRelation(reA);
            }else{
                containsCrime.add(s);
                //crime = crimeDao.queryCrimeByContent(s);
            }

             */


            //Crime crime = new Crime(s);



        }

    }
    private void createLaw(){
        List<String> lawList = c.getArticles_law();
        for(String s: lawList){


            if(s==null) continue;
            if(s.equals("")) continue;
            Law law = new Law(s);
            law.setNodeName("法律条文");
            graphService.saveNode(law);
            if(!containsLaw.contains(s)){
                containsLaw.add(s);
            }
            RelationShip reA = new RelationShip(criminalA,law);
            reA.setRelationName("apply");
            graphService.saveRelation(reA);
        }

    }
    private void createCircumstance(){
        List<String> cList = c.getCircumstance();
        Circumstance circu = new Circumstance(cList);
        circu.setNodeName("情节");
        graphService.saveNode(circu);
        RelationShip reA = new RelationShip(criminalA,circu);
        reA.setRelationName("accuse");
        graphService.saveRelation(reA);
    }

}
