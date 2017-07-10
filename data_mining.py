import matplotlib.pyplot as plt
import psycopg2 as p
import random

def getConnection():
    con = p.connect("dbname='Election' user='postgres' password='postgres' host='localhost'")
    return con

def query(querystatement):
    dic = {}
    con = getConnection()
    cur = con.cursor()
    cur.execute(querystatement)
    rows = cur.fetchall()

    for i in rows:
        if ((i[0],i[1]) in dic):
            dic.update({(i[0],i[1]):(dic[(i[0],i[1])]+1)})
        elif ((i[1],i[0])  in dic):
            dic.update({(i[1],i[0]):(dic[(i[1],i[0])]+1)})
        else:
            dic.update({(i[0],i[1]):i[2]-1})
    return dic

dic=query("SELECT t1.hashtag_text, t2.hashtag_text, COUNT(t1.tweet_id) FROM tweet_hashtag t1 , tweet_hashtag t2 WHERE t1.hashtag_text != t2.hashtag_text and t1.tweet_id = t2.tweet_id GROUP BY t1.hashtag_text, t2.hashtag_text ORDER BY t1.hashtag_text ASC")


arrayx=[];
arrayy=[];
start=0;
for i in dic:
    arrayx=arrayx+[abs(start-dic[i])];
    arrayy=arrayy+[0];
plt.axis([0,20,-0.0000000000000005,0.0000000000000005]) #[0,130,-0.0000000000000005,0.0000000000000005]
plt.plot(arrayx,arrayy,'ro')
plt.show()
