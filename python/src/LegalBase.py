class LegalBase:
    def __init__(self, law_name, article, paragraph, item):
        self.law_name = law_name
        self.article = article
        self.paragraph = paragraph
        self.item = item

    def print(self):
        print(self.law_name + self.article + self.paragraph + self.item)

    def getLaw(self):
        return self.law_name + self.article + self.paragraph + self.item