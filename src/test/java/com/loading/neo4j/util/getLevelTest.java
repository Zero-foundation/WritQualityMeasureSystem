package com.loading.neo4j.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@SpringBootTest
@RunWith(SpringRunner.class)
public class getLevelTest {
    @Autowired
    GetLevelUtil getLevelUtil;

    @Test
    public void getLevelUtil(){
        System.out.println(getLevelUtil.getLevel(-100.0,1));
        System.out.println(getLevelUtil.getLevel(50,1));
        System.out.println(getLevelUtil.getLevel(60,1));
        System.out.println(getLevelUtil.getLevel(70,1));
        System.out.println(getLevelUtil.getLevel(80,1));
        System.out.println(getLevelUtil.getLevel(90,1));
        System.out.println(getLevelUtil.getLevel(95,1));
        System.out.println(getLevelUtil.getLevel(99,1));
        System.out.println(getLevelUtil.getLevel(100.0,1));
        System.out.println(getLevelUtil.getLevel(100.0000,1));

    }
}
