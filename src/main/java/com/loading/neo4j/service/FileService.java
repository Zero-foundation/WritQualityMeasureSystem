package com.loading.neo4j.service;

import com.loading.neo4j.datainteract.Writ;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@Service
public class FileService {
    public String uploadFile(MultipartFile file) throws Exception {
        System.out.println(file);
        Writ ws;
        if (file == null) {
            System.out.println("-----------文件------------");
            return null;
        }
        if (!file.getOriginalFilename().endsWith(".docx")) {
            throw new Exception("文件不是docx类型，不符合规范。");
        }
        InputStream in = null;
        String fileName = "";
        try {
            //将file转InputStream
            in = file.getInputStream();
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            fileName = "D:\\Writ_temp\\" + time + file.getOriginalFilename();
            OutputStream outputStream = new FileOutputStream(fileName);
            IOUtils.copy(in, outputStream);
            outputStream.flush();
            in.close();
            outputStream.close();
            return fileName;
        } catch (IOException e) {
            throw new Exception("文书解析失败，请检查内容");
        }
    }
}
