package com.loading.neo4j.readUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loading.neo4j.datainteract.Accused;
import com.loading.neo4j.datainteract.Writ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class readFromDocx {

    @Value("${python.location}")
    private String python_location;
    @Value("${extract.location}")
    private String pythonPath;
    /**
     *
     * @param fileName
     * @return wenshu
     * @description 通过这个方法可以获得文书对象；fileName是docx的路径。
     * arguments里的第一项是本地的python路径，第二项是python代码extract.py的路径，第三项是文书的路径
     * 本地的python需要安装一些库，比如docx
     * TODO：这个方法需要修改，入参应该是一个从前端获取的file对象？或者String；目前是从本地文件中读取
     */
    public Writ extract(String fileName) throws Exception {

//        String pythonPath = "D:\\PythonProcect\\judgementSpider\\src\\extract.py";
        String[] arguments = new String[] {python_location,pythonPath,fileName};//指定命令、路径、传递的参数
        StringBuilder sbrs = null;
        StringBuilder sberror = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(arguments);
            Process process = builder.start();
            //Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));//获取字符输入流对象
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));//获取错误信息的字符输入流对象

            String line = null;
            sbrs = new StringBuilder();
            sberror = new StringBuilder();

            //记录输出结果
            while ((line = in.readLine()) != null) {
                sbrs.append(line);
            }
            //记录错误信息
            while ((line = error.readLine()) != null) {
                sberror.append(line);
            }
            in.close();
            process.waitFor();
        } catch (Exception e) {
            throw new Exception("文书解析失败，请检查内容");
        }

        System.out.println(sberror);
        assert sbrs != null;
        try{
        return readFromJson(sbrs.toString());
        }catch (Exception e){
            throw new Exception("文书解析失败，请检查内容");
        }
    }
    public Writ readFromJson(String obj){
        Writ ws=new Writ();
        JSONObject jsonObj=JSON.parseObject(obj);
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
        ws.setCrime((String) jsonObj.get("crime"));
        JSONArray law_arr=(JSONArray) jsonObj.get("law");
        for(Object law_obj:law_arr){
            ws.addLaw(law_obj.toString());
        }
        JSONArray accused_list=(JSONArray) jsonObj.get("accused_list");
        for(Object accused_obj:accused_list){
            String accused_text=((JSONObject)accused_obj).toString();
            Accused accused= JSON.parseObject(accused_text, Accused.class);
            if(accused.getCrimes().length==0){
                accused.setCrimes(new String[]{ws.getCrime()});
            }
            ws.addAccused(accused);
            if(accused.getCrimes().length==0){
                System.out.println("hit");
            }
        }
        return ws;
    }
}
