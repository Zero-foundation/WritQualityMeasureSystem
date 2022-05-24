package com.loading.neo4j.service;

import com.alibaba.fastjson.JSON;
import com.loading.neo4j.QualityMeasure.subjectMeasure;
import com.loading.neo4j.dao.BasicNodeDao;
import com.loading.neo4j.dao.CrimeDao;
import com.loading.neo4j.dao.LawDao;
import com.loading.neo4j.dao.CriminalDao;
import com.loading.neo4j.datainteract.Writ;
import com.loading.neo4j.datainteract.criminalContent;
import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Crime;
import com.loading.neo4j.entity.Law;
import com.loading.neo4j.entity.Criminal;
import com.loading.neo4j.entity.RelationShip;
import com.loading.neo4j.readUtils.readFromJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
//@Rollback(true)
public class GraphServiceTest {

    @Autowired
    private GraphService graphService;

    @Autowired
    private LawDao lawDao;

    @Autowired
    private CriminalDao criminalDao;

    @Autowired
    private CrimeDao crimeDao;

    @Autowired
    private BasicNodeDao<BasicNode> basicNodeDao;

    @Autowired
    createGraphService createGDI;

    @Autowired
    subjectMeasure subjectM;

    @Test
    public void createGraph(){
        String dirName="dataTest\\";
        readFromJson readU = new readFromJson();
        //List<Writ> list=readU.readFromDir(dirName);
        List<criminalContent> cCList = readU.getcCList(dirName);
        //createGDI = new createGraphService();
        createGDI.createGraphList(cCList);
        createGDI.createGraph();
    }
    @Test
    public void lawMeasure(){
        String dirName="dataMeasureTest\\";
        readFromJson readU = new readFromJson();
        readU.readFromDir(dirName);
        //Writ writ = readU.readFromDir(dirName).get(0);
        int i = 0;
        for(Writ writ : readU.readFromDir(dirName)){
            if(i==1) break;
            i++;
            subjectM.subjectM(writ);
            subjectM.lawRight();
        }


    }
    @Test
    public void JudgeMeasure(){
        String dirName="dataMeasureTest\\";
        readFromJson readU = new readFromJson();
        readU.readFromDir(dirName);
        //Writ writ = readU.readFromDir(dirName).get(0);
        int i = 0;
        for(Writ writ : readU.readFromDir(dirName)) {
            if (i == 100) break;
            i++;
            subjectM.subjectM(writ);
            subjectM.JudgmentRight();
        }

    }
    @Test
    public void save() throws Exception {

        Criminal criminalA = new Criminal();
        criminalA.setNodeName("A Person");
        criminalA.setAge(20);
        graphService.saveNode(criminalA);
        //System.out.println(JSON.toJSONString(criminalA));

        Crime crime = new Crime("死刑");
        graphService.saveNode(crime);
        Criminal criminalB = new Criminal();
        criminalB.setNodeName("B Person");
        criminalB.setAge(23);
        graphService.saveNode(criminalB);
        //System.out.println(JSON.toJSONString(criminalB));

        Criminal criminalC = new Criminal();
        criminalC.setNodeName("C Person");
        criminalC.setAge(40);
        graphService.saveNode(criminalC);
        //System.out.println(JSON.toJSONString(criminalC));

        Law lawA = new Law();
        lawA.setNodeName("law");
        lawA.setLaw("a law");
        graphService.saveNode(lawA);
        //System.out.println(JSON.toJSONString(lawA));

        Law lawB = new Law();
        lawB.setNodeName("law");
        lawB.setLaw("b law");
        graphService.saveNode(lawB);
        //System.out.println(JSON.toJSONString(lawB));

        Crime crimeA = new Crime();
        crimeA.setNodeName("crime");

        RelationShip relationShipA = new RelationShip(criminalA, lawA);
        relationShipA.setRelationName("shiyong");
        graphService.saveRelation(relationShipA);
        //System.out.println(JSON.toJSONString(relationShipA));



        RelationShip relationShipB = new RelationShip(criminalB, lawB);
        relationShipB.setRelationName("shiyong");
        graphService.saveRelation(relationShipB);
        //System.out.println(JSON.toJSONString(relationShipB));

        RelationShip relationShipC = new RelationShip(criminalC, lawA);
        relationShipC.setRelationName("shiyong");
        graphService.saveRelation(relationShipC);
        //System.out.println(JSON.toJSONString(relationShipC));

        RelationShip relationShipD = new RelationShip(criminalC, lawB);
        relationShipD.setRelationName("shiyong");
        graphService.saveRelation(relationShipD);
        //System.out.println(JSON.toJSONString(relationShipD));
    }
    @Test
    public void save1() throws Exception {


        Criminal criminalC = new Criminal();
        criminalC.setNodeName("C Person");
        criminalC.setAge(40);
        graphService.saveNode(criminalC);

        Criminal criminalA = new Criminal();
        criminalA.setNodeName("A Person");
        criminalA.setAge(40);
        graphService.saveNode(criminalA);

        Crime crime1 = new Crime("死刑");
        crime1.setNodeName("罪名1");
        //Set<Criminal> t = new HashSet<Criminal>();
        //t.add(criminalA);
        //crime1.setSets1(t);
        graphService.saveNode(crime1);

        Crime crime2 = new Crime("死刑");
        crime2.setNodeName("罪名2");
        //crime2.setCriminal(criminalC);
        graphService.saveNode(crime2);

        RelationShip relationShipA = new RelationShip(criminalA, crime1);
        relationShipA.setRelationName("shiyong");
        graphService.saveRelation(relationShipA);

        RelationShip relationShipB = new RelationShip(criminalC, crime2);
        relationShipB.setRelationName("shiyong");
        graphService.saveRelation(relationShipB);

    }
    @Test
    public void delete() throws Exception {
        graphService.delete(28L);
    }

    @Test
    public void findCrime() throws Exception {
        List<String> test = new ArrayList<>();
        test.add("死刑");
        //List<Crime> crimes = crimeDao.queryCrimeByContent("死刑");
        createGDI.removeRepeatCrimes(test);
        //Crime crime1 = crimeDao.queryCrimeByContent("死刑1");


        //System.out.println(crime1.getCrimeContent());
    }

    @Test
    public void findCriminal() throws Exception {
        Criminal criminal = criminalDao.findOne(32L,2);
        System.out.println(JSON.toJSONString(criminal));
    }

    @Test
    public void findNode() throws Exception {
        BasicNode node = basicNodeDao.findOne(34L, 1);
        System.out.println(JSON.toJSONString(node));
    }

}