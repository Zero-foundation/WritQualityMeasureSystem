package com.loading.neo4j.service;

import com.loading.neo4j.Controller.GraphCreateController;
import com.loading.neo4j.VO.ResponseVO;
import com.loading.neo4j.datainteract.Graph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GraphTest {
    @Autowired
    private createGraphService createGraphService;
    @Autowired
    private  GraphCreateController graphCreateController;
    @Test
    public void getGraphTest() throws Exception {

        Graph graph=createGraphService.getGraph("D:\\\\Writ_temp\\\\16530645443101周某杨某甲非法拘禁罪一审刑事判决书.docx");
        System.out.println(graph.getNodeList().size());

    }

    @Test
    public void GraphControllerTest(){

        ResponseVO responseVO=graphCreateController.getGraph("D:\\\\Writ_temp\\\\16530645443101周某杨某甲非法拘禁罪一审刑事判决书.docx");
        responseVO.getContent();
        System.out.println("ok");
    }
}
