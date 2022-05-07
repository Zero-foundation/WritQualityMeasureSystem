import os

# import docx
# import re
import docx
from win32com import client as wc


def parse(file_dir):
    print("starting parsing")
    for root, dirs, files in os.walk(file_dir):

        for name in files:
            docx_path = os.path.join(root, name)
            if name.endswith('.doc') and not name.startswith('~$'):
                word = wc.Dispatch("Word.Application")
                doc = word.Documents.Open(docx_path)
                doc.SaveAs(docx_path + 'x', 12)
                doc.Close()
                os.remove(docx_path)
            # print("段落数:" + str(len(file.paragraphs)))  # 输出段落数


if __name__ == '__main__':
    parse(r"D:\PythonProcect\judgementSpider\judgements")
