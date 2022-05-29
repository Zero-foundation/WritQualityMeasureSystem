package com.loading.neo4j.Controller;

import com.loading.neo4j.VO.ResponseVO;
import com.loading.neo4j.datainteract.Writ;
import com.loading.neo4j.readUtils.readFromDocx;
import com.loading.neo4j.service.ScoreService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/score")
public class scoreController {
    @Autowired
    ScoreService scoreService;

    @PostMapping("score")
    public ResponseVO score(@RequestParam("fileName") String fileName) throws IOException {

        try {
            System.out.println("successfully extract file");
            return ResponseVO.buildSuccess(scoreService.score(fileName));
        } catch (Exception e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

}
