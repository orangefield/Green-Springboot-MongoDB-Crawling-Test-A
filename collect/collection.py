from pymongo import MongoClient
import requests
from datetime import datetime
from bs4 import BeautifulSoup


# 접속
mongo = MongoClient("localhost", 20000)


# aid 만드는 함수
def num_to_aid(num, size=10):  # default=10
    num_str = str(num)
    return num_str.zfill(size)


# dependency -> num_to_aid()
# naver_craw 함수
def naver_craw(num):  # 클래스 내부가 아니니까 self 안붙여도 됨
    result = {"title": "", "company": "국민일보", "createdAt": datetime.now()}

    response = requests.get(
        f"https://entertain.naver.com/read?oid=005&aid={num}")

    html = response.text
    html_bs = BeautifulSoup(html, "html.parser")

    title = html_bs.select(".end_tit")[0].get_text().strip()
    # select : 배열을 받는다

    result["title"] = title
    return result


# save 함수
def mongo_save(mongo, datas, db_name=None, collection_name=None):
    result = mongo[db_name][collection_name].insert_many(datas).inserted_ids
    return result
