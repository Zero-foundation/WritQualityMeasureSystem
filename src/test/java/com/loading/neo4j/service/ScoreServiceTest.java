package com.loading.neo4j.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreServiceTest {
    @Autowired
    ScoreService scoreService;

    @Test
    public void scoreTest() throws Exception {
        scoreService.score("D:\\Writ_temp\\1边海亮盗窃罪一审刑事判决书.docx");
    }
}
