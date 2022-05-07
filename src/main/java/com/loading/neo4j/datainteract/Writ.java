package com.loading.neo4j.datainteract;



import java.util.ArrayList;
import java.util.List;

public class Writ {
    //文书名
    private String name;
    //公诉机关
    private String prosecutionOrgan;
    //被告人（长文本，包括辩护人等信息）
    private String accused;
    //审判过程
    private String process;
    //这个全都是空，没有使用
    private String accusation;
    //犯罪事实、证据等
    private String facts;
    //被告人辩称，或者被告人对指控事实无异议
    private String reply;
    //本院认为……
    private String conclusion;
    //处罚决定
    private String decision;
    //如不服
    private String rubufu;
    //落款
    private String luokuan;
    //援引法条
    private List<String> laws;
    //罪名
    private String crime;
    //被告人列表，其中包含详细信息，见另一个类Accused
    private List<Accused> accused_list;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void addAccused(Accused accused) {
        this.accused_list.add(accused);
    }

    public void addLaw(String law) {
        this.laws.add(law);
    }

    public List<String> getLaws() {
        return laws;
    }

    public void setLaws(List<String> laws) {
        this.laws = laws;
    }

    public List<Accused> getAccused_list() {
        return accused_list;
    }

    public void setAccused_list(List<Accused> accused_list) {
        this.accused_list = accused_list;
    }

    public Writ() {
        this.laws=new ArrayList<>();
        this.accused_list=new ArrayList<>();
    }

    public Writ(String name) {
        this.name = name;
        this.laws=new ArrayList<>();
        this.accused_list=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProsecutionOrgan() {
        return prosecutionOrgan;
    }

    public void setProsecutionOrgan(String prosecutionOrgan) {
        this.prosecutionOrgan = prosecutionOrgan;
    }

    public String getAccused() {
        return accused;
    }

    public void setAccused(String accused) {
        this.accused = accused;
    }

    public String getAccusation() {
        return accusation;
    }

    public void setAccusation(String accusation) {
        this.accusation = accusation;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getRubufu() {
        return rubufu;
    }

    public void setRubufu(String rubufu) {
        this.rubufu = rubufu;
    }

    public String getLuokuan() {
        return luokuan;
    }

    public void setLuokuan(String luokuan) {
        this.luokuan = luokuan;
    }


    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }


}
