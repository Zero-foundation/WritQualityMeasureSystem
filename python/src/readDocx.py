import docx

def readDocx(filename):
    doc = docx.Document(filename)
    for para in doc.paragraphs:
        print(para.text)

if __name__=='__main__':
    readDocx(r'D:\PythonProcect\xingchu\1004卢祥故意伤害罪一审刑事判决书.docx')