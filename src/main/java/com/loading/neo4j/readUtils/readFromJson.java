package com.loading.neo4j.readUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loading.neo4j.datainteract.criminalContent;
import com.loading.neo4j.datainteract.Accused;
import com.loading.neo4j.datainteract.Writ;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class readFromJson {

    List<Writ> res=new ArrayList<>();
    List<criminalContent> cCList = new ArrayList<>();
    public readFromJson(){
    }
    public List<Writ> readFromData(String fileName) {
        List<Writ> re=new ArrayList<>();
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
            Writ ws=new Writ();
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
            re.add(ws);
        }

        return re;
    }
    public List<Writ> readFromDir(String dirName){
        //List<Writ> res=new ArrayList<>();
        File dir = new File(dirName);
        if(dir.isDirectory()){
            String[] children=dir.list();
            assert children != null;
            for (String fileName:children){
                res.addAll(readFromData(dirName+fileName));
            }
        }
        return res;
    }
    public List<criminalContent> getcCList(String dirName) {
        readFromDir(dirName);
        for(Writ writ : res){
            List<Accused> accuseds= writ.getAccused_list();
            List<String> laws = writ.getLaws();
            for(Accused accused : accuseds){




                criminalContent cC = new criminalContent(accused,laws);
                cCList.add(cC);
            }



        }
        return cCList;
    }


    public List<criminalContent>  run(){
        // replace the fileName below with your local address.
        String dirName="data2\\";
        readFromJson readU = new readFromJson();
        //List<Writ> list=readU.readFromDir(dirName);
        List<criminalContent> cCList = readU.getcCList(dirName);
        System.out.println(cCList.size());
        return cCList;
    }
}
