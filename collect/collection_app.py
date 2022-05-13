from collection import *


datas = []
for i in range(1, 21):
    naver_data = naver_craw(i)
    datas.append(naver_data)

mongo_save(mongo, datas, "greendb", "naverss")
