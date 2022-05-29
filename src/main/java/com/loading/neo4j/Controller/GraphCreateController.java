package com.loading.neo4j.Controller;

import com.loading.neo4j.VO.ResponseVO;
import com.loading.neo4j.datainteract.Graph;
import com.loading.neo4j.datainteract.criminalContent;
import com.loading.neo4j.readUtils.readFromJson;
import com.loading.neo4j.service.createGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/graph")
public class GraphCreateController implements CommandLineRunner {
    @Autowired
    createGraphService createGS;

    @Override
    public void run(String... strings) throws Exception {

    }
    //@Override
    /*public void run(String... strings) throws Exception {
        String dirName="data2\\";
        readFromJson readU = new readFromJson();
        //List<Writ> list=readU.readFromDir(dirName);
        List<criminalContent> cCList = readU.getcCList(dirName);
        //createGDI = new createGraphService();
        createGS.createGraphList(cCList);
        createGS.createGraph();
    }

     */
    @GetMapping("getGraph")
    public ResponseVO getGraph(@RequestParam("fileName") String fileName){
        try{
            Graph graph=createGS.getGraph(fileName);
            System.out.println(graph.getNodeList().size());
            return ResponseVO.buildSuccess(graph);
        }catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }
}
