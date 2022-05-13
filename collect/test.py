from turtle import title
import requests
from datetime import datetime
from bs4 import BeautifulSoup


# 방법1
# 파이프라인 방식은 추천X, 호출하는 쪽에서 머리 아프다

# 메서드는 한 개의 책임을 가져야 한다(재사용을 위해)
def num_to_aid(num):
    num_str = str(num)
    return num_str.zfill(10)


# dependency -> num_to_aid()
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
