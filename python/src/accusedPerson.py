class accusedPerson:
    name = ''
    crime = []
    results = []
    circumstances = []

    def __init__(self, name):
        self.name = name

    def add_circumstance(self,circumstance):
        self.circumstances.append(circumstance)

    def add_crime(self,crime):
        self.crime.append(crime)

    def add_results(self,result):
        self.results.append(result)

