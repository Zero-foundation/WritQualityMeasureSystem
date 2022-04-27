package com.loading.neo4j.readUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loading.neo4j.entity.dto.Accused;
import com.loading.neo4j.entity.dto.wenshu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class readFromJson {


    public List<wenshu> readFromJson(String fileName) {
        List<wenshu> res=new ArrayList<>();
        String content="";
        String encoding = "UTF-8";
        File file = new File(fileName);
        long filelength = file.length();
        byte[] filecontent = new byte[(int) filelength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            content = new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
        }

        content = "["+content.substring(0,content.length()-1)+"]";
        JSONArray arr=JSON.parseArray(content);
        for(Object obj:arr){
            wenshu ws=new wenshu();
            JSONObject jsonObj=(JSONObject)obj;
            ws.setName((String)jsonObj.get("name"));
            ws.setProsecutionOrgan((String) jsonObj.get("prosecution_organ"));
            ws.setAccused((String) jsonObj.get("accused"));
            ws.setProcess((String) jsonObj.get("process"));
            ws.setAccusation((String)jsonObj.get("accusation"));
            ws.setFacts((String)jsonObj.get("facts"));
            ws.setReply((String) jsonObj.get("reply"));
            ws.setConclusion((String)jsonObj.get("conclusion"));
            ws.setDecision((String)jsonObj.get("decision"));
            ws.setRubufu((String)jsonObj.get("rubufu"));
            ws.setLuokuan((String) jsonObj.get("luokuan"));
            JSONArray law_arr=(JSONArray) jsonObj.get("law");
            for(Object law_obj:law_arr){
                ws.addLaw(law_obj.toString());
            }
            JSONArray accused_list=(JSONArray) jsonObj.get("accused_list");
            for(Object accused_obj:accused_list){
                String accused_text=((JSONObject)accused_obj).toString();
                Accused accused=JSON.parseObject(accused_text, Accused.class);
                if(accused.getCrimes().length==0){
                    accused.setCrimes(new String[]{ws.getCrime()});
                }
                ws.addAccused(accused);

            }
            res.add(ws);
        }

        return res;
    }
    public List<wenshu> readFromDir(String dirName){
        List<wenshu> res=new ArrayList<>();
        File dir = new File(dirName);
        if(dir.isDirectory()){
            String[] children=dir.list();
            assert children != null;
            for (String fileName:children){
                res.addAll(readFromJson(dirName+fileName));
            }
        }
        return res;
    }
    public static void main(String[] args){
        // replace the fileName below with your local address.
        String dirName="D:\\PythonProcect\\judgementSpider\\data\\alter\\";
        List<wenshu> list=new readFromJson().readFromDir(dirName);
        System.out.println(list.size());
    }
}
