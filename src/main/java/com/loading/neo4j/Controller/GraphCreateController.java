package com.loading.neo4j.Controller;

import com.loading.neo4j.datainteract.criminalContent;
import com.loading.neo4j.readUtils.readFromJson;
import com.loading.neo4j.service.createGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
}
