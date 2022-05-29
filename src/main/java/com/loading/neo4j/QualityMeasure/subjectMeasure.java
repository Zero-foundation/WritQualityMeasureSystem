package com.loading.neo4j.QualityMeasure;

import com.loading.neo4j.dao.*;
import com.loading.neo4j.datainteract.Accused;
import com.loading.neo4j.datainteract.Writ;
import com.loading.neo4j.entity.Circumstance;
import com.loading.neo4j.entity.Criminal;
import com.loading.neo4j.entity.Judgment;
import com.loading.neo4j.entity.Law;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class subjectMeasure {
    Writ writ;
    List<String> laws;
    List<Accused> accuseds;
    @Autowired
    CriminalDao criminalDao;
    @Autowired
    LawDao lawDao;
    @Autowired
    CrimeDao crimeDao;
    @Autowired
    CircumstanceDao circumstanceDao;
    @Autowired
    JudgmentDao judgmentDao;
    subjectMeasure(){
    }
    public void subjectM(Writ writ){
        this.writ =  writ;
        laws = writ.getLaws();
        accuseds = writ.getAccused_list();
    }

    public double lawRight(){
        double scoreR = 0;
        double total = 0;
        double score = 0;
        for(Accused c : accuseds){
            String[] crimes = c.getCrimes();
            for(String crime : crimes){
                List<Criminal> criminals = criminalDao.queryCriminalByCrime(crime);
                for(Criminal criminal : criminals){
                    //criminal.getId();

                    long a  = criminal.getId();
                    List<Law> lawList = lawDao.queryLawByCriminalId(criminal.getId());
                    if(lawIsContain(lawList,laws)){
                        scoreR++;


                    }
                    total++;

                }
            }
        //int pause = 0;
        }
        if(total == 0) return 0;
        if(scoreR == 0) return 0;
        score = scoreR / total;
        System.out.println(score);
        return score;
    }
    private boolean lawIsContain(List<Law> lawList, List<String> lawSlist){
        if(lawList == null || lawSlist == null) return false;
        for(Law law : lawList){
            for(String strLaw : lawSlist){
                if(law.getLaw().equals(strLaw)){
                    return true;
                }
            }
        }
        return false;
    }

    public double JudgmentRight(){
        double scoreR = 0;
        double total = 0;
        double score = 0;
        for(Accused c : accuseds){
            String[] crimes = c.getCrimes();
            String[] circumstance = c.getCircumstance();
            String[] judge = c.getJudge_res();
            int numOfCrime = crimes.length;
            List<List<Criminal>> candidate = new ArrayList<>();
            for(String crime : crimes){
                List<Criminal> tem = criminalDao.queryCriminalByCrime(crime);
                candidate.add(tem);
            }
            List<Criminal> result = candidate.get(0);
            for(int i = 0;i<numOfCrime-1;i++){
                result.retainAll(candidate.get(i+1));
            }
            List<Criminal> re = new ArrayList<>();
            List<Integer> cirList = new ArrayList<>();
            List<Judgment> judgmentList = new ArrayList<>();
            for(Criminal criminal : result){
                int numOfCrime0 = crimeDao.queryNumOfCrimeByCriminalId(criminal.getId());
                if(numOfCrime0 == numOfCrime){
                    re.add(criminal);
                    cirList.add(cirWeight(circumstanceDao.
                            queryCircumstanceByCriminalId(criminal.getId())
                            .getCircumstanceList()));
                    //cirList.add(circumstanceDao.queryCircumstanceByCriminalId(criminal.getId()));
                    judgmentList.add(judgmentDao.queryJudgmentByCriminalId(criminal.getId()));
                }

            }
            int weight = cirWeight(Arrays.asList(circumstance));
            List<String> juList = Arrays.asList(judge);
            int L = 0;
            int R = 0;
            for(int i = 0;i<cirList.size();i++){
                if(i == 10){
                    int pause = 1;
                }
                if(cirList.get(i)<weight){
                    if(isHeavier(juList,judgmentList.get(i).getJudgment())){
                        scoreR++;
                    }else{
                        scoreR--;
                    }
                }else if(cirList.get(i)>weight){
                    if(isHeavier(juList,judgmentList.get(i).getJudgment())){
                        scoreR--;
                    }else{
                        scoreR++;
                    }
                }else{
                    if(isHeavier(juList,judgmentList.get(i).getJudgment())){
                        R++;
                    }else{
                        L++;
                    }
                }
                total++;
            }
            scoreR += Math.min(R,L) * 2;






        }
        if(scoreR == 0 || total == 0) return 0;
        score = scoreR / total;

        return score;
    }

    private int cirWeight(List<String> cirList){
        String standard1 = "从重";
        String standard2 = "从轻";
        String standard3 = "从宽";
        String standard4 = "减轻";
        String standard5 = "系初犯";

        int weight = 0;
        for(String circu : cirList){

            if(circu.contains(standard5)){
                weight --;
                continue;
            }
            if(circu.contains(standard1)){
                weight ++;
                continue;
            }
            if(circu.contains(standard2)){
                weight--;
                continue;
            }
            if(circu.contains(standard3)){
                weight--;
                continue;
            }
            if(circu.contains(standard4)){
                weight -= 2;
                continue;
            }


        }
        return weight;
    }
    private Map<String, Double> processJudge(List<String> judge){

        String type0  ="死刑";
        String type1 = "无期徒刑";
        String type2 = "有期徒刑";
        String type3 = "拘役";
        String type5 = "剥夺政治权利";
        String type4 = "缓刑";
        String type6 = "罚金";

        Map<String,Double> map = new HashMap<>();
        double zero = 0;
        map.put(type0,zero);
        map.put(type1,zero);
        map.put(type2,zero);
        map.put(type3,zero);
        map.put(type4,zero);
        map.put(type5,zero);
        map.put(type6,zero);
        for(String str : judge){
            if(str.contains(type0)){
                System.out.println(str);
                map.replace(type0,1.0);
                continue;
            }
            if(str.contains(type1)){
                System.out.println(str);
                map.replace(type1,1.0);
                continue;
            }
            if(str.contains(type2)){
                double num = 0;
                System.out.println(str);
                if(str.contains("年") && str.contains("月")){
                    String year = str.substring(str.indexOf("刑")+1,str.indexOf("年"));
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }

                    num = digitalTrans(year) + (double)(digitalTrans(month))/100;
                }else if(str.contains("月")){
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = (double)(digitalTrans(month))/100;
                }else if(str.contains("年")){
                    String year = str.substring(str.indexOf("刑")+1,str.indexOf("年"));

                    num = digitalTrans(year);
                }
                map.replace(type2,num);

                continue;
            }
            if(str.contains(type3)){
                double num = 0;
                System.out.println(str);
                if(str.contains("年") && str.contains("月")){
                    String year = str.substring(str.indexOf("役")+1,str.indexOf("年"));
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = digitalTrans(year) + (double)(digitalTrans(month))/100;
                }else if(str.contains("月")){
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = (double)(digitalTrans(month))/100;
                }else if(str.contains("年")){
                    String year = str.substring(str.indexOf("役")+1,str.indexOf("年"));

                    num = digitalTrans(year);
                }

                map.replace(type3,num);
                continue;
            }
            if(str.contains(type4)){
                System.out.println(str);
                double num = 0;
                if(str.contains("年") && str.contains("月")){
                    String year = str.substring(str.indexOf("刑")+1,str.indexOf("年"));
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = digitalTrans(year) + (double)(digitalTrans(month))/100;
                }else if(str.contains("月")){
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = (double)(digitalTrans(month))/100;
                }else if(str.contains("年")){
                    String year = str.substring(str.indexOf("刑")+1,str.indexOf("年"));

                    num = digitalTrans(year);
                }

                map.replace(type4,num);
                continue;
            }
            if(str.contains(type5)){
                System.out.println(str);
                double num = 0;
                if(str.contains("年") && str.contains("月")){
                    String year = str.substring(str.indexOf("利")+1,str.indexOf("年"));
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = digitalTrans(year) + (double)(digitalTrans(month))/100;
                }else if(str.contains("月")){
                    String month;
                    if(str.contains("个月")){
                        month = str.substring(str.indexOf("年")+1,str.indexOf("个月"));
                    }else{
                        month = str.substring(str.indexOf("年")+1,str.indexOf("月"));
                    }
                    num = (double)(digitalTrans(month))/100;
                }else if(str.contains("年")){
                    String year = str.substring(str.indexOf("利")+1,str.indexOf("年"));

                    num = digitalTrans(year);
                }

                map.replace(type5,num);
                continue;
            }

            if(str.contains(type6)){
                System.out.println(str);
                double num;

                String money = str.substring(str.indexOf("币")+1,str.indexOf("元"));
                num = digitalTrans(money);


                map.replace(type6,num);
                continue;
            }
        }
        return map;
    }
    private boolean isHeavier(List<String> judge1, List<String> judge2){
        if(judge1 == null || judge2 == null) return false;
        String type0  ="死刑";
        String type1 = "无期徒刑";
        String type2 = "有期徒刑";
        String type3 = "拘役";

        String type4 = "缓刑";
        String type5 = "剥夺政治权利";
        String type6 = "罚金";
        String[] typeList = {type0,type1,type2,type3,type4,type5,type6};
        Map<String,Double> map1 = processJudge(judge1);
        Map<String,Double> map2 = processJudge(judge2);

        for(int i = 0; i <map1.size();i++){
            double a = map1.get(typeList[i]);
            double b = map2.get(typeList[i]);
            if(Math.abs(a-b)<1E-10){
                continue;
            }else if(a>b){
                if(typeList[i].contains("缓刑")) return false;
                return true;
            }else{
                if(typeList[i].contains("缓刑")) return true;
                return false;
            }
        }






        return true;
    }

    private long digitalTrans(String str){
        String aval = "零一二三四五六七八九";
        String bval = "十百千万亿";
        int[] bnum = {10, 100, 1000, 10000, 100000000};


        long num = 0;

        char[] arr = str.toCharArray();
        int len = arr.length;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < len; i++) {
            char s = arr[i];
            //跳过零
            if(s == '零')continue;
            //用下标找到对应数字
            int index = bval.indexOf(s);
            //如果不在bval中，即当前字符为数字，直接入栈
            if(index == -1){
                stack.push(aval.indexOf(s));
            }else{ //当前字符为单位。
                int tempsum = 0;
                int val = bnum[index];
                //如果栈为空则直接入栈
                if(stack.isEmpty()){
                    stack.push(val);
                    continue;
                }
                //如果栈中有比val小的元素则出栈，累加，乘N，再入栈
                while(!stack.isEmpty() && stack.peek() < val){
                    tempsum += stack.pop();
                }
                //判断是否经过乘法处理
                if(tempsum == 0){
                    stack.push(val);
                }else{
                    stack.push(tempsum * val);
                }
            }
        }
        //计算最终的和
        while(!stack.isEmpty()){
            num += stack.pop();
        }
        //System.out.println(num);
        return num;
    }


}
