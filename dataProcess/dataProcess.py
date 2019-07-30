import pymysql


# 向数据库中添加数据
# cur：游标， data：原始数据， org：机构数据，sheet_name：表名称， index：所需数据在原始数据中的索引， db：数据库
def commit(cur, data, org, sheet_name, index, db):
    for i in organization:
        sql_insert = "insert into " + sheet_name + " values ("
        temp = []
        sql_insert = sql_insert + str(i[0]) + ","
        sql_insert = sql_insert + "\'" + str(i[1]) + "\'" + ","
        for j in range(2004, 2019):
            temp.append(0)
        if i == org[len(org) - 1]:
            break
        isSameOrganization = False
        for j in data:
            if j[0] == i[0]:
                isSameOrganization = True
                year = int(j[2])
                temp[year - 2004] = str(j[index])
            if isSameOrganization and j[0] != i[0]:
                isSameOrganization = False
                break
        value = ", ".join(temp)
        sql_insert = sql_insert + value + ')'
        cur.execute(sql_insert)  # 执行数据插入操作
        db.commit()  # 提交
    print("{}表数据插入成功".format(sheet_name))


connect = pymysql.connect(host="127.0.0.1", user="root", passwd="wang&1684910028",
                          port=3306, db="libdata", charset="utf8")  # 建立数据库连接
cursor = connect.cursor()  # 建立游标
cursor.execute("select * from original_data")  # 查询原始数据
original_data = cursor.fetchall()  # 获取结果
cursor.execute("select * from organization")  # 查询机构数据
organization = cursor.fetchall()
sheets = ["practical_num_of_certificate", "borrow_num", "service_num"]  # 工作表列表
for i in range(0, 3):
    commit(cursor, original_data, organization, sheets[i], i + 4, connect)

