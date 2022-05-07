import re

if __name__=='__main__':
    # my_string = "123Digital0418 135 304"
    # new_string = re.sub(r"\d+", '', my_string)
    # print(new_string)
    # print(my_string)
    # pattern_LegalBase_article = '(《[^，。、《》]+》)?(第[零一二三四五六七八九十百千]+条)[^第、]*(第[一二三四五六七八九]款)?(第（[一二三四五六七八九]）项)?、?'
    # str="据此，依据《中华人民共和国刑法》第一百三十三条之一第一款第（二）项、第六十七条第三款，判决如下："
    # print(re.search(pattern_LegalBase_article,str))

    res_crime_split = re.findall('([^、]+罪)、', "流氓罪、耍流氓罪、抢劫罪")
    res_crime_split_afer=re.findall('、([^、]+罪)','流氓罪、耍流氓罪、抢劫罪')
    crime_set=set()
    print(res_crime_split)
    print(res_crime_split_afer)
    for element in res_crime_split_afer:
        set.add(element)
    for element in res_crime_split:
        set.add(element)

    print(set)
    crime_list=list(set)

