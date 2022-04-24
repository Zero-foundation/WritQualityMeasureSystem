package com.loading.neo4j.service;

import com.alibaba.fastjson.JSON;
import com.loading.neo4j.dao.BasicNodeDao;
import com.loading.neo4j.dao.LawDao;
import com.loading.neo4j.dao.CriminalDao;
import com.loading.neo4j.entity.Basic.BasicNode;
import com.loading.neo4j.entity.Crime;
import com.loading.neo4j.entity.law;
import com.loading.neo4j.entity.Criminal;
import com.loading.neo4j.entity.RelationShip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * desc:
 * Created on 2017/10/13.
 *
 * @author Lo_ading
 * @version 1.0.0
 * @since 1.0.0
 */
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
    private BasicNodeDao<BasicNode> basicNodeDao;

    @Test
    public void save() throws Exception {

        Criminal criminalA = new Criminal();
        criminalA.setNodeName("A Person");
        criminalA.setAge(20);
        graphService.saveNode(criminalA);
        //System.out.println(JSON.toJSONString(criminalA));

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

        law lawA = new law();
        lawA.setNodeName("law");
        lawA.setLaw("a law");
        graphService.saveNode(lawA);
        //System.out.println(JSON.toJSONString(lawA));

        law lawB = new law();
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
    public void delete() throws Exception {
        graphService.delete(28L);
    }

    @Test
    public void findLaw() throws Exception {
        law law = lawDao.findOne(34L,1);
        System.out.println(JSON.toJSONString(law));
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