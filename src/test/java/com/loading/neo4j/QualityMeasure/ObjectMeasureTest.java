package com.loading.neo4j.QualityMeasure;

import com.loading.neo4j.datainteract.Writ;
import com.loading.neo4j.readUtils.readFromDocx;
import com.loading.neo4j.readUtils.readFromJson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ObjectMeasureTest {
    @Autowired
    readFromDocx rfd;

//    @Test
//    public void calAllTest() throws Exception {
//        String fileName="D:\\PythonProcect\\xingchu\\于昂昂危险驾驶罪一审刑事判决书.docx";
//        Writ writ=rfd.extract(fileName);
//        ObjectMeasure objectMeasure=new ObjectMeasure(writ);
//        objectMeasure.cal_all();
//        System.out.println("ok");
//    }

//    @Test
//    public void getLevelTest() throws IOException, InterruptedException {
//        BufferedWriter bw=new BufferedWriter(new FileWriter("D:\\JAVAWeb\\WritQualityMeasureSystem\\KnowledgeGraphJson\\level.csv",true));
//        readFromJson rfj=new readFromJson();
//        for(int i=0;i<=12;i++){
//            List<Writ> lists=rfj.readFromData("D:\\PythonProcect\\judgementSpider\\data\\alter\\wenshu"+i+".json");
//            int cnt=0;
//            for(Writ writ:lists){
//                ObjectMeasure objectMeasure=new ObjectMeasure(writ);
//                objectMeasure.cal_all();
//                cnt++;
//                bw.write(objectMeasure.getCsv());
//                bw.newLine();
//                bw.flush();
//                Thread.sleep(1);
//            }
//        }
//
//    }

    @Test
    public void sortLevel() throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("src/main/resources/csv/sortedScore.csv",true));

        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("D:\\JAVAWeb\\WritQualityMeasureSystem\\KnowledgeGraphJson\\level.csv")));
        List<List<Double>> d_Matrix=new ArrayList<>();
        double[] scores=new double[59945];
        String str;
        while((str=br.readLine())!=null){
            List<Double> row=new ArrayList<>();
            for(String d_str:str.split(",")){
                double num=Double.parseDouble(d_str);
                row.add(num);
            }
            d_Matrix.add(row);
        }
        for(int r=0;r<7;r++){
            for(int i=0;i<59945;i++){
                scores[i]=d_Matrix.get(i).get(r);
            }
            Arrays.sort(scores);
            for(double score:scores){
                bw.write(String.valueOf(score));
                bw.write(",");
            }
            bw.newLine();
            bw.flush();
        }

    }



}
