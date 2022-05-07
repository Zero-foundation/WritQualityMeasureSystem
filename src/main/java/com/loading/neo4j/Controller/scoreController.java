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
    @Autowired
    readFromDocx rfd;
    @PostMapping("score")
    public ResponseVO score(@RequestParam("file") MultipartFile file) throws IOException {
        //将文件保存在本地的D:\\Writ_temp文件夹下
        System.out.println(file);
        if (file == null) {
            System.out.println("-----------文件------------");
            return null;
        }
        InputStream in = null;
        try {
            //将file转InputStream
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = "D:\\Writ_temp\\" + file.getOriginalFilename();
        OutputStream outputStream = new FileOutputStream(fileName);
        IOUtils.copy(in, outputStream);
        outputStream.flush();
        in.close();
        outputStream.close();

        //调用python方法获取Writ对象
        Writ ws = rfd.extract(fileName);
        try {
            System.out.println("successfully extract file");
            return ResponseVO.buildSuccess(scoreService.score(ws));
        } catch (Exception e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
    }


}
