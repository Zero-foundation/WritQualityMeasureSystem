package com.loading.neo4j.QualityMeasure;

import com.loading.neo4j.datainteract.Accused;
import com.loading.neo4j.datainteract.Writ;
import com.loading.neo4j.readUtils.readFromDocx;
import com.loading.neo4j.util.GetLevelUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Getter
public class ObjectMeasure {
    @Autowired
    GetLevelUtil getLevelUtil;

    Writ ws;
    private List<String> advice;
    private List<String> wrongUsedList;
    private List<Integer> levels;
    //被告人信息
    double criminal_score;
    //审判流程
    double trial_score;
    //完整性
    double integrality_score;
    //一致性评分
    double consistency_score;
    double sign_consistency;
    double num_consistency;
    double acc_score;
    String getCsv(){

        String sb = String.valueOf(integrality_score) + ',' +
                consistency_score + ',' +
                sign_consistency + ',' +
                num_consistency + ',' +
                acc_score + ',' +
                criminal_score + ',' +
                trial_score;
        return sb;
    }
    public ObjectMeasure() {
        advice = new ArrayList<>();
        criminal_score = 0.0;
        trial_score = 0.0;
        integrality_score = 0.0;
        consistency_score = 0.0;
        acc_score = 0.0;
        num_consistency=0.0;
        sign_consistency=0.0;
        wrongUsedList=new ArrayList<>();
        levels=new ArrayList<>();

    }

    public void setWenshu(Writ ws) {
        this.ws = ws;
    }

    //计算所有属性
    public void cal_all() {
        advice=new ArrayList<>();
        wrongUsedList=new ArrayList<>();
        levels=new ArrayList<>();
        cal_integrality();
        cal_criminal();
        cal_trial();
        cal_consistency();
        cal_acc();
        cal_punctuation_consistency();
        cal_num_consistency();
        cal_consistency();
        getLevel();
    }

    public void getLevel(){
        levels.add(getLevelUtil.getLevel(integrality_score,1));
        levels.add(getLevelUtil.getLevel(consistency_score,2));
        levels.add(getLevelUtil.getLevel(sign_consistency,3));
        levels.add(getLevelUtil.getLevel(num_consistency,4));
        levels.add(getLevelUtil.getLevel(acc_score,5));
        levels.add(getLevelUtil.getLevel(criminal_score,6));
        levels.add(getLevelUtil.getLevel(trial_score,7));
    }

    /**
     * @param regex 正则
     * @param text  需要匹配的文本
     * @return groups ArrayList<String>
     */
    public List<List<String>> regexFind(String regex, String text) {
        List<List<String>> res = new ArrayList<>();
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(text);
        while (m.find()) {
            List<String> one_match = new ArrayList<>();
            for (int i = 0; i <= m.groupCount(); i++) {
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
     */
    public void cal_integrality() {
        double score = 0;
        double total = 0;
        List<String> missingList = new ArrayList<>();
        //首部
        total++;
        if (ws.getProsecutionOrgan().length() > 0) {
            score += 1;
        } else {
            missingList.add("公诉机关");
        }
        //被告人部分
        total+=2;
        if (ws.getAccused().length() > 0) {
            if (ws.getProcess().contains("辩护人") || ws.getFacts().contains("辩护人") || ws.getConclusion().contains(("辩护人"))) {
                total+=1;
                if (ws.getAccused().contains("辩护人")) {
                    score+=1;
                } else {
                    advice.add("文中出现辩护人，但文章首部未记录辩护人信息。");
                }
            }
            score+=2;
        } else {
            missingList.add("被告人信息");
        }

        //事实部分
        String facts = ws.getFacts();
        //公诉机关指控内容
        total += 2;
        boolean accusation = find(facts, "(公诉机关指控)|(检察院指控)|(公诉机关起诉指控)|(检察院起诉指控)");
        if (accusation) {
            score += 2;
        } else if(find(facts,"审理查明")){
            score +=1;
            advice.add("缺乏检方指控部分，可能与法院审理查明部分内容重复而省略");
        }
        else{
            missingList.add("公诉机关指控内容");
        }
        //证据部分
        total += 2;
        if (find(facts, "(等?证据(予以)?(证实)|(佐证)|(证明))")) {
            score += 2;
        } else if (find(facts, "证据")) {
            score += 1;
        } else {
            missingList.add("证据部分");
        }

        //法院审理
        total += 2;
        if (find(facts, "审理查明")) {
            score += 2;
        } else if (find(ws.getProcess(), "(简易)|(速裁)")) {
            score += 1;
            if (accusation) {
                score += 0.5;
                advice.add("缺少法院审理查明部分，可能因为与检方控诉内容重复而省略。");
            }
            advice.add("缺少法院审理查明部分，可能是因为是简易程序或速裁程序。");
        } else {
            missingList.add("法院审理查明部分");
        }

        //被告人供述、辩护、辩护人辩护意见
        total+=2;
        if(find(facts,"(被告人[^，。]*辩)|(异议)")){
            score+=2;
        }else {
            missingList.add("被告人供述、辩护部分");
        }

        if(find(ws.getAccused(),"辩护人")){
            total++;
            if(find(facts,"辩护意见")){
                score+=1;
            }else{
                missingList.add("辩护人的辩护意见");
            }
        }

        //理由部分
        String reason = ws.getConclusion();

        total+=1;
        if(find(reason,"(检察院)|(公诉机关)(起诉)|(指控)")){
            score+=1;
        }else{
            missingList.add("理由部分中控方指控是否成立");
        }

        total+=1;
        if(find(reason,"(构成[^，。]*罪)|(罪名成立)")){
            score+=1;
        }else{
            missingList.add("理由部分中是否构成犯罪");
        }

        total+=1;
        if(ws.getCrime()!=null&&ws.getCrime().length()>0){
            score+=1;
        }else{
            missingList.add("理由部分中罪名");
        }

        total+=2;
        if(ws.getLaws().size()>0){
            score+=2;
        }else{
            missingList.add("法条");
        }

        //结果部分不评估

        //
        total+=2;
        if(ws.getRubufu().length()>0){
            score+=2;
        }else{
            missingList.add("上诉权利部分");
        }

        total+=1;
        if(ws.getLuokuan().length()>0){
            score+=1;
        }else{
            missingList.add("尾部落款部分");
        }

        StringBuilder sb = new StringBuilder();
        if (missingList.size() != 0) {
            for (String element : missingList) {
                sb.append(element).append("、");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("有待补充。");
        }
        if (sb.length() > 0) {
            this.advice.add(sb.toString());
        }

        this.integrality_score=(score/total)*100.0;

    }

    /**
     * 计算被告人信息完整性
     */
    public void cal_criminal() {

        String text = ws.getAccused();
        List<List<String>> criminalReses = regexFind("被告人.*\n?", text);
        List<Double> scoreList = new ArrayList<>();
        for (List<String> res : criminalReses) {
            String accused_name = "";
            List<String> missingList = new ArrayList<>();
            for (Accused accused : ws.getAccused_list()) {
                if (res.get(0).contains(accused.getName())) {
                    accused_name = accused.getName();
                }
            }
            double score = 20.0;
            String criminalText = res.get(0);

            List<List<String>> genderRes = regexFind("[男女]", criminalText);
            if (genderRes.size() == 0) {
                missingList.add("性别");
            } else {
                score += 10.0;
            }

            List<List<String>> birthdayRes = regexFind("\\d{4}年\\d{1,2}月\\d{1,2}日出?生", criminalText);
            if (birthdayRes.size() == 0) {
                missingList.add("生日");
            } else {
                score += 10.0;
            }

            List<List<String>> raceRes = regexFind("，([^，。]+族)", criminalText);
            if (raceRes.size() == 0) {
                missingList.add("民族");
            } else {
                score += 10.0;
            }


            List<List<String>> educationRes = regexFind("(文化)|(文盲)|(小学)|(初中)|(高中)|(肄业)|(中专)|(大专)|(本科)|(大学)|(硕士)|(博士)", criminalText);
            if (educationRes.size() == 0) {
                missingList.add("文化程度");
            } else {
                score += 10.0;
            }

            List<List<String>> jobRes = regexFind("(无业)|(工作)|(农民)|(务工)|(匠)|(职员)|(公司)|(员工)|(个体)|(企业)|(工)|(公务员)|(咨询师)|(平台)", criminalText);
            if (jobRes.size() == 0) {
                missingList.add("工作");
            } else {
                score += 10.0;
            }


            List<List<String>> censusRegisterRes = regexFind("户籍", criminalText);
            if (censusRegisterRes.size() == 0) {
                missingList.add("户籍信息");
            } else {
                score += 10.0;
            }

            List<List<String>> residenceRes = regexFind("住", criminalText);
            if (residenceRes.size() == 0) {
                missingList.add("所居地");
            } else {
                score += 10.0;
            }

            List<List<String>> detentionRes = regexFind("(取保候审)|(逮捕)|(羁押)|(在家候审)", criminalText);
            if (residenceRes.size() == 0) {
                missingList.add("候审情况");
            } else {
                score += 10.0;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("被告人").append(accused_name).append("的");
            for (String element : missingList) {
                sb.append(element).append("、");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("有待补充。");
            //TODO: 加入到建议里
            if (sb.length() > 0) {
                this.advice.add(sb.toString());
            }
            scoreList.add(score);
        }
        double score_all = 0;
        for (double score : scoreList) {
            score_all += score;
        }
        this.criminal_score = score_all / scoreList.size();
    }


    public void cal_acc() {

        //准确性
        this.acc_score = (this.trial_score + this.criminal_score) / 2.0;
    }

    public void cal_consistency() {
        this.consistency_score=(this.num_consistency+this.sign_consistency)/2;
        //一致性
    }

    public void cal_trial() {

        //审理经过
        List<String> missingList = new ArrayList<>();
        double score = 0.0;
        String text = ws.getProcess();

        List<List<String>> accusationRes = regexFind("起诉书", text);
        if (accusationRes.size() == 0) {
            missingList.add("起诉书指控");
        } else {
            score += 100 / 6.0;
        }

        List<List<String>> processRes = regexFind("(适用简易程序)|(普通程序)|(组成合议庭)|(速裁程序)", text);
        if (processRes.size() == 0) {
            missingList.add("适用程序");
        } else {
            score += 100 / 6.0;
        }

        List<List<String>> hearRes = regexFind("(不?公开开庭审理)|(进行了审理)", text);
        if (hearRes.size() == 0) {
            missingList.add("审理");
        } else {
            score += 100 / 6.0;
        }

        List<List<String>> inspectorRes = regexFind("检察员", text);
        if (inspectorRes.size() == 0) {
            missingList.add("检察员到庭情况");
        } else {
            score += 100 / 6.0;
        }

        List<List<String>> defendantRes = regexFind("被告人[^。，；]*到庭", text);
        if (defendantRes.size() == 0) {
            missingList.add("被告人到庭情况");
        } else {
            score += 100 / 6.0;
        }

        List<List<String>> completionRes = regexFind("审理终结", text);
        if (completionRes.size() == 0) {
            missingList.add("审理终结与否");
        } else {
            score += 100 / 6.0;
        }


        StringBuilder sb = new StringBuilder();
        if (missingList.size() != 0) {
            for (String element : missingList) {
                sb.append(element).append("、");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("有待补充。");
        }
        if (sb.length() > 0) {
            this.advice.add(sb.toString());
        }
        this.trial_score = Math.round(score);
    }

    public boolean find(String text, String pattern) {
        List<List<String>> res = regexFind(pattern, text);
        if (res.size() > 0) {
            return true;
        }
        return false;
    }

    public void cal_punctuation_consistency(){
//        Set<Character> signSet=new HashSet<>();
//        signSet.add(',');signSet.add('，');signSet.add('。');signSet.add('.');signSet.add(':');signSet.add('：');
        double total=0.0;
        double score=0.0;
        //comma ，
        String reason=ws.getConclusion();
        List<List<String>> res=regexFind("本院认为(.)",reason);
        if(res.size()>0){
            for(List<String> res_:res){
                total++;
                String sign = res.get(0).get(1);
                if(sign.equals("，")){
                    score++;
                }else{
                    wrongUsedList.add(String.format("\"本院认为\"后面应该是逗号，而不是\"%s\"", sign));
                }
            }
        }

        res=regexFind("指控称?([，。：:.,])",ws.getFacts().substring(0, Math.min(ws.getFacts().length(), 50)));

        if(res.size()>0){
            for(List<String> res_:res){
                total++;
                String sign=res.get(0).get(1);
                if(sign.equals("：")||sign.equals("，")){
                    score++;
                    
                    
                }else{
                    wrongUsedList.add(String.format("\"检察院指控\"后的标点应该是冒号或逗号，而不是\"%s\"",sign ));
                }
            }
        }

        res=regexFind("审理查明([，。：:.,])",ws.getFacts());

        if(res.size()>0){
            for(List<String> res_:res){
                total++;
                String sign=res_.get(1);
                if(sign.equals("：")){
                    score++;
                }else{
                    wrongUsedList.add(String.format("\"经审理查明\"后的标点应该是冒号，而不是\"%s\"", sign));
                }
            }
        }

        res=regexFind("判决如下(.)",ws.getConclusion());

        if(res.size()>0){
            for(List<String> res_:res){
                total++;
                String sign=res_.get(1);
                if(sign.equals("：")){
                    score++;

                }
                else{
                    wrongUsedList.add(String.format("\"判决如下\"后的标点应该是冒号，而不是\"%s\"", sign));
                }
            }
        }

        res=regexFind("辩称([.。，,：:])",ws.getFacts());

        if(res.size()>0){
            for(List<String> res_:res){
                total++;
                String sign=res_.get(1);
                if(sign.equals("，")){
                    score++;

                }
                else{
                    wrongUsedList.add(String.format("\"被告人辩称\"后的标点应该是逗号，而不是\"%s\"", sign));
                }
            }
        }

        res=regexFind("\n[一二三四五六七八九十](.)",ws.getDecision());

        if(res.size()>0){
            for(List<String> res_:res){
                total++;
                String sign=res_.get(1);
                if(sign.equals("、")){
                    score++;
                }
                else{
                    wrongUsedList.add(String.format("\"判项\"后的标点应该是顿号，而不是\"%s\"", sign));
                }
            }
        }

        this.sign_consistency=score/total*100.0;
    }

    public void cal_num_consistency(){
        //在应该出现阿拉伯数字的地方出现阿拉伯数字的次数
        int alb_num_crr_cnt=0;
        //在应该出现阿拉伯数字的地方出现汉字数字的次数
        int alb_num_wrong_cnt=0;
        //在应该出现汉字数字的地方出现汉字数字的次数
        int hanzi_num_crr_cnt=0;
        //在应该出现汉字数字的地方出现阿拉伯数字的次数
        int hanzi_num_wrong_cnt=0;
        //应当是阿拉伯数字的部分
        List<String> alb_num_list=new ArrayList<>();
        //应当是汉字数字的部分
        List<String> hanzi_num_list=new ArrayList<>();

        //主刑、附加刑
        List<Accused> accusedList=ws.getAccused_list();
        for(Accused accused:accusedList){
            hanzi_num_list.addAll(new ArrayList<String>(Arrays.asList(accused.getJudge_res())));
        }
        //法条
        hanzi_num_list.addAll(ws.getLaws());
        //落款
        hanzi_num_list.add(ws.getLuokuan());
        //建议判处
        String pattern_res_suggestion="((((管制)|(拘役)|(有期)|(有期徒刑)|(缓刑)|(剥夺政治权利)|(拘留))" +
                "(?=([零一二三四五六七八九十百千]*年)|([零一二三四五六七八九十百千]*个?月)|([零一二三四五六七八九十百千]*[日天]))" +
                "([零一二三四五六七八九十百千]*年)?([零一二三四五六七八九十百千]*个?月)?" +
                "([零一二三四五六七八九十百千]*[日天])?)|(处罚金[^。，]*元)|(死刑)|(剥夺政治权利终身)|(无期徒刑)|(驱逐出境))";
        List<List<String>> res_suggestion=regexFind(pattern_res_suggestion,ws.getFacts());
        if(res_suggestion.size()>0){
            for(List<String> res_suggestion_:res_suggestion){
                hanzi_num_list.add(res_suggestion_.get(0));
            }
        }
        hanzi_num_list.add(ws.getLuokuan());


        //不应当有汉字数字的部分需要剔除上述应该有汉字数字的部分。
        String[] decisions=ws.getDecision().split("\n");
        for(String decision:decisions){
            if(!find(decision,"(判处)|(罚金)")){
                //主刑和附加刑所在段落，应该剔除
                alb_num_list.add(decision.replaceAll("[一二三四五六七八九十]、",""));
            }
        }

        //将主要段落拼接，正则删除其中应当包含汉字数字的部分。
        String mainPassage=ws.getAccused()+ws.getProcess()+ws.getFacts()+ws.getConclusion();
        //删除掉检方建议量刑
        mainPassage=mainPassage.replaceAll(pattern_res_suggestion,"");
        //删除掉法条
        mainPassage=mainPassage.replaceAll("((《[^，。、《》]+》)?(第?[零一二三四五六七八九十百千]+条)[^第、]*(第?([一二三四五六七八九]、?)*款)?(第?（[一二三四五六七八九]）项)?、?)","");

        String [] splited=mainPassage.split("\n|。|，|；");
        alb_num_list.addAll(Arrays.asList(splited));

        
        
        for(String str:alb_num_list){
            List<List<String>> res=regexFind("\\d+\\.?(\\d*)?",str);
            if(res.size()>0){
                alb_num_crr_cnt+=res.size();
            }
            List<List<String>> res2=regexFind("[一二三四五六七八九十百千万亿]+",str);
            if(res2.size()>0){
                alb_num_wrong_cnt+=res2.size();
//                wrongUsedList.add(str+"数字使用错误");
            }
        }


        for(String str:hanzi_num_list){
            List<List<String>> res=regexFind("\\d+\\.?(\\d*)?",str);
            if(res.size()>0){
                hanzi_num_wrong_cnt+=res.size();
//                wrongUsedList.add(str+" 数字使用错误");
            }
            List<List<String>> res2=regexFind("[一二三四五六七八九十百千万亿]+",str);
            if(res2.size()>0){
                hanzi_num_crr_cnt+=res2.size();

            }
        }
        for(String decision:ws.getDecision().split("\n")){
            if(decision.length()<5){
                continue;
            }
            if(find(decision, "\\d、")){
                hanzi_num_wrong_cnt++;
                //wrongUsedList.add(decision+" 数字使用错误");
            }
            if(find(decision,"[一二三四五六七八九十]、")){
                hanzi_num_crr_cnt++;
            }
        }
        
        
        
        
        this.num_consistency=(alb_num_crr_cnt+hanzi_num_crr_cnt)*100.0/(alb_num_wrong_cnt+hanzi_num_wrong_cnt+alb_num_crr_cnt+hanzi_num_crr_cnt);
    }




}
