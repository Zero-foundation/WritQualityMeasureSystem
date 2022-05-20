package com.loading.neo4j.Controller;

import com.loading.neo4j.VO.ResponseVO;
import com.loading.neo4j.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired
    FileService fileService;
    @PostMapping("upload")
    public ResponseVO score(@RequestParam("file") MultipartFile file) throws IOException {

        try {
            System.out.println("successfully extract file");
            return ResponseVO.buildSuccess(fileService.uploadFile(file));
        } catch (Exception e) {
            return ResponseVO.buildFailure(e.getMessage());
        }
    }
}
