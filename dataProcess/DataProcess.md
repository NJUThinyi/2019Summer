# DataProcess

## 缘起

有张Excel数据表需要处理，原始数据如图一所示，需要展示为图二的效果

- 图一

  ![eJSLgH.png](https://s2.ax1x.com/2019/07/30/eJSLgH.png)

- 图二

  ![eJpi8g.png](https://s2.ax1x.com/2019/07/30/eJpi8g.png)

因为对Excel操作不是很熟悉，用Excel操作没有成功，所以尝试着通过coding来解决问题

## 思路

- 直接对Excel文件读取操作
- 将数据导入数据库，再对数据库进行操作

下面记录一下第二种方案的实操过程

### 将Excel原始数据导入数据库中

借鉴的网上的经验，流程如下

- 将Excel文件原始数据表的工作表转换为csv文件

- 我这里是利用可视化工具Navicat for MySQL通过导入向导进行的操作（具体过程可参照网上教程<https://blog.csdn.net/weixin_38437243/article/details/78974346>）

- 为了便于后续处理，将机构代码和机构名称不重复地刷选出来新建了一个表

  `create table organization (select DISTINCT 机构代码,机构名称 from original_data)`

  (参见 *sql将查询结果建立为新表* <https://blog.csdn.net/longshenlmj/article/details/17719323>)

### 利用Python建立MySQL数据库连接，并且对数据库进行操作

- 部分代码如下

~~~python
connect = pymysql.connect(host="127.0.0.1", user="root", passwd="123123",
                          port=3306, db="libdata", charset="utf8")  # 建立数据库连接
cursor = connect.cursor()  # 建立游标
cursor.execute("select * from original_data")  # 查询原始数据
original_data = cursor.fetchall()  # 获取结果

# 具体代码略过

cur.execute(sql_insert)  # 执行数据插入操作，sql_insert为数据库插入语句
db.commit()  # 提交
~~~

- 几点注意事项

  - 假如数据库表里面有中文列名，sql语句中的中文名称前后要加单引号`'`才能识别，否则会报错（至少我的问题似乎是出在这）

    ```python
    sql_insert = "insert into borrow_num values ('南京大学')"
    ```

  - 游标执行完插入操作`cur.execute(sql)`之后，**必须提交**`db.commit()`才能对数据库表进行更新