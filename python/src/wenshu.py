class wenshu:
    name = ''
    prosecution_organ = ''
    accused = ''
    process = ''
    accusation = ''
    facts = ''
    reply = ''
    conclusion = ''
    decision = ''
    rubufu = ''
    luokuan = ''
    law = []
    crime = ''
    accused_list = []

    def getJson(self):
        dic = {'name': self.name,
               'prosecution_organ': self.prosecution_organ,
               'accused': self.accused,
               'process': self.process,
               'accusation': self.accusation,
               'facts': self.facts,
               'reply': self.reply,
               'conclusion': self.conclusion,
               'decision': self.decision,
               'rubufu': self.rubufu,
               'luokuan': self.luokuan,
               'law': self.law,
               'crime': self.crime,
               'accused_list': self.accused_list
               }
        return dic

    def __init__(self, name):
        self.name = name
        self.law = []
        self.accused_list = []

    def set_accused_list(self, accused):
        self.accused_list = accused

    def add_law(self, law):
        self.law.append(law)

    def set_crime(self, crime):
        self.crime = crime

    def set_prosecution_organ(self, prosecution_organ):
        self.prosecution_organ = prosecution_organ

    def set_accused(self, accused):
        self.accused = accused

    def set_process(self, process):
        self.process = process

    def set_accusation(self, accusation):
        self.accusation = accusation

    def set_facts(self, facts):
        self.facts = facts

    def set_reply(self, reply):
        self.reply = reply


    def set_conclusion(self, conclusion):
        self.conclusion = conclusion

    def set_decision(self, decision):
        self.decision = decision

    def set_rubufu(self, rubufu):
        self.rubufu = rubufu

    def set_luokuan(self, luokuan):
        self.luokuan = luokuan

    def print(self):
        print("文书名: " + self.name if self.name is not None else '')
        print("公诉机关: " + self.prosecution_organ if self.process is not None else '')
        print("被告人: " + self.accused if self.accused is not None else '')
        print("过程: " + self.process if self.process is not None else '')
        print("指控: " + self.accusation if self.accusation is not None else '')
        print("facts: " + self.facts if self.facts is not None else '')
        print("被告人回复: " + self.reply if self.reply is not None else '')
        print("法院总结: " + self.conclusion if self.conclusion is not None else '')
        print("审判结果: " + self.decision if self.decision is not None else '')
        print("如不服: " + self.rubufu if self.rubufu is not None else '')
        print("落款: " + self.luokuan if self.luokuan is not None else '')
