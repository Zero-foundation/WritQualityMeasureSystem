package com.loading.neo4j;

import com.loading.neo4j.readUtils.readFromJson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"com.loading.neo4j.Controller",
		"com.loading.neo4j.dao",
		"com.loading.neo4j.service",
})

public class Neo4jDemoApplication {


	public static void main(String[] args) {

		SpringApplication.run(Neo4jDemoApplication.class, args);

		/*
		String dirName="data2\\";
		readFromJson readU = new readFromJson();
		//List<Writ> list=readU.readFromDir(dirName);
		List<criminalContent> cCList = readU.getcCList(dirName);

		createGDI.createGraphList(cCList);
		createGDI.createGraph();

		 */
	}
}
