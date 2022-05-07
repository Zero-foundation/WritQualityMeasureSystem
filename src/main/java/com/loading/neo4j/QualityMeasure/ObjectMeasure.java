package com.loading.neo4j.QualityMeasure;

import com.loading.neo4j.entity.dto.Accused;
import com.loading.neo4j.entity.dto.wenshu;
import com.loading.neo4j.readUtils.readFromDocx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectMeasure {
    wenshu ws;
//    int object_score;
//    Map<String,String> object_score_list;
//    Map<String,String> map_content;
//    ObjectMeasure(Map<String,String> map){
//        object_score = 0;
//        object_score_list = new HashMap<String, String>();
//        map_content = map;
//    }
    ObjectMeasure(){

    }
    public void setWenshu(wenshu ws){
        this.ws=ws;
    }

    /**
     *
     * @param   regex 正则
     * @param   text  需要匹配的文本
     * @return  groups ArrayList<String>
     */
    public List<List<String>> regexFind(String regex,String text){
        List<List<String>> res=new ArrayList<>();
        Pattern r=Pattern.compile(regex);
        Matcher m=r.matcher(text);
        while(m.find()){
            List<String> one_match=new ArrayList<>();
            for(int i=0;i<=m.groupCount();i++){
                one_match.add(m.group(i));
            }
            res.add(one_match);
        }
        return res;
    }

    /**
     * 评价文书完整性。完整的文书应该包含：
     * 1. 公诉机关
     * 2. 被告人信息、辩护人信息
     * 3. 过程
     * ————————————以上是首部————————————
     * 4. 事实部分（人民检察院指控被告人犯罪的事实和证据;被告人的供述、辩护和辩护人的辩护意见;经法庭审理查明的事实和据以定案的证据。）
     * 5. 被告人辩称
     * 6. 理由（法院认为）（针对具体案件的特点，运用法律规定、犯罪构成和刑事诉讼理论，阐明控方的指控是否成立，被告人的行为是否构成犯罪，犯什么罪，情节轻重与否，依法应当如何处理。）
     * 7. 审判结果
     * ————————————以下尾部——————————————
     * 8. 如不服判决……（上诉权利）
     * 9. 落款
     *
     */
    public void cal_integrality(){
        //完整性
        //TODO: 计算被告人信息完整性
        System.out.println(cal_criminal());
        //TODO: 计算程序完整性
        System.out.println(cal_trial());
        //TODO: 事实完整性
        //最后根据各部分做个加权


    }

    /**
     * 计算被告人信息完整性
     * @return
     */
    public double cal_criminal(){
        String text=ws.getAccused();
        List<List<String>> criminalReses=regexFind("被告人.*\n?",text);
        List<Double> scoreList=new ArrayList<>();
        for(List<String> res:criminalReses){
            String accused_name="";
            List<String> missingList=new ArrayList<>();
            for(Accused accused:ws.getAccused_list()){
                if(res.get(0).contains(accused.getName())){
                    accused_name=accused.getName();
                }
            }
            double score=20.0;
            String criminalText=res.get(0);

            List<List<String>> genderRes=regexFind("[男女]",criminalText);
            if(genderRes.size()==0){
                missingList.add("性别");
            }else{
                score+=10.0;
            }

            List<List<String>> birthdayRes=regexFind("\\d{4}年\\d{1,2}月\\d{1,2}日出?生",criminalText);
            if(birthdayRes.size()==0){
                missingList.add("生日");
            }else{
                score+=10.0;
            }

            List<List<String>> raceRes=regexFind("，([^，。]+族)",criminalText);
            if(raceRes.size()==0){
                missingList.add("民族");
            }else{
                score+=10.0;
            }


            List<List<String>> educationRes=regexFind("(文化)|(文盲)|(小学)|(初中)|(高中)|(肄业)|(中专)|(大专)|(本科)|(大学)|(硕士)|(博士)",criminalText);
            if(educationRes.size()==0){
                missingList.add("文化程度");
            }else{
                score+=10.0;
            }

            List<List<String>> jobRes=regexFind("(无业)|(工作)|(农民)|(务工)|(职员)|(公司)|(员工)|(个体)|(企业)|(工)|(公务员)|(咨询师)|(平台)",criminalText);
            if(jobRes.size()==0){
                missingList.add("工作");
            }else{
                score+=10.0;
            }


            List<List<String>> censusRegisterRes=regexFind("户籍",criminalText);
            if(censusRegisterRes.size()==0){
                missingList.add("户籍信息");
            }else{
                score+=10.0;
            }

            List<List<String>> residenceRes=regexFind("住",criminalText);
            if(residenceRes.size()==0){
                missingList.add("所居地");
            }else{
                score+=10.0;
            }

            List<List<String>> detentionRes=regexFind("(取保候审)|(逮捕)|(羁押)|(在家候审)",criminalText);
            if(residenceRes.size()==0){
                missingList.add("候审情况");
            }else{
                score+=10.0;
            }
            StringBuilder sb=new StringBuilder();
            sb.append("被告人").append(accused_name).append("的");
            for(String element:missingList){
                sb.append(element).append("、");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("有待补充。");
            //TODO: 加入到建议里
            System.out.println(sb.toString());
            scoreList.add(score);
        }
        double score_all=0;
        for(double score:scoreList){
            score_all+=score;
        }

        return score_all/scoreList.size();
    }


    public void cal_acc(){
        //准确性
        cal_trial();
        cal_constituent();
    }
    public void cal_consistency(){
        //一致性
    }
    private double cal_trial(){
        //审理经过
        List<String> missingList=new ArrayList<>();
        double score=0.0;
        String text=ws.getProcess();

        List<List<String>> accusationRes=regexFind("起诉书",text);
        if(accusationRes.size()==0){
            missingList.add("起诉书指控");
        }else{
            score+=10.0;
        }

        List<List<String>> processRes=regexFind("(适用简易程序)|(普通程序)|(组成合议庭)|(速裁程序)",text);
        if(processRes.size()==0){
            missingList.add("适用程序");
        }else{
            score+=10.0;
        }

        List<List<String>> hearRes=regexFind("(不?公开开庭审理)|(进行了审理)",text);
        if(hearRes.size()==0){
            missingList.add("审理");
        }else{
            score+=10.0;
        }

        List<List<String>> inspectorRes=regexFind("检察员",text);
        if(inspectorRes.size()==0){
            missingList.add("检察员到庭情况");
        }else{
            score+=10.0;
        }

        List<List<String>> defendantRes=regexFind("被告人[^。，；]*到庭",text);
        if(defendantRes.size()==0){
            missingList.add("被告人到庭情况");
        }else{
            score+=10.0;
        }

        List<List<String>> completionRes=regexFind("审理终结",text);
        if(completionRes.size()==0){
            missingList.add("审理终结与否");
        }else{
            score+=10.0;
        }


        StringBuilder sb=new StringBuilder();
        if(missingList.size()!=0){
            for(String element:missingList){
                sb.append(element).append("、");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("有待补充。");
        }
        //TODO: 加入到建议里
        System.out.println(sb.toString());
        return score;
    }
    private void cal_constituent(){
        //构成事项

    }


    public static void main(String[] args){
        ObjectMeasure objectMeasure=new ObjectMeasure();
        wenshu ws=new readFromDocx().extract("D:\\PythonProcect\\xingchu\\于志祥危险驾驶罪一审刑事判决书.docx");
//        ws.setAccused("被告人xxx，高中文化\n被告人xxxxx,高中文化，无业");
        objectMeasure.setWenshu(ws);
        objectMeasure.cal_integrality();
    }






}
