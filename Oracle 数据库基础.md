# Oracle 数据库基础

Tags： 数据库

---

[TOC]

---

## 结构化查询语言 SQL
> SQL是在关系数据库上执行数据操作、检索及维护所使用的标准语言，可以用来查询数据，操纵数据，定义数据，控制数据

* 数据定义语言DDL D Definition L
* 数据操纵语言DML D Manipulation 
* 事务控制语言TCL Transaction Control L
* 数据查询语言DQL D Query L
* 数据控制语言DCL D Control L

### 数据定义语言 DDL
> 用于建立、修改、删除数据库对象

* `CREATE` 创建表或其他对象的结构
* `ALTER` 修改表或其他对象的结构
* `DROP` 删除表或其他对象的结构
* `TRUNCATE` **删除表数据，保留表结构**

### 数据操纵语言 DML
> 用于改变数据表中的数据
> 和事务相关，执行完后需要经过事务控制语句提交后才真正的将改变应用到数据库中

* `INSERT` 将数据插入到数据表中
* `UPDATE` 更新数据表中已存在的数据
* `DELETE` 删除数据表中的数据

### 事务控制语言 TCL
> 用来维护数据一致性的语句

* `COMMIT` 提交，确认已经进行的数据改变
* `ROLLBACK` 回滚，取消已经进行的数据改变
* `SAVEPOINT` 保存点，使当前的事务可以回退到指定的保存点，便于取消部分改变

### 数据查询语言 DQL
> 用来查询所需要的数据

* `SELECT`

### 数据控制语言 DCL
> 用于执行权限的授予和回收操作

* `GRANT` 授予，用于给用户或角色授予权限
* `REVOKE` 用于收回用户或角色已有的权限
* `CREATE USER` 创建用户


## Oracle 数据类型
### NUMBER 数字类型
`NUMBER(P, S)` 数字的总位数，小数点后面的位数

### CHAR 固定长度的字符类型
`CHAR(N)` 占用的字节数，最大长度2000

### VARCHAR2 变长的字符类型
`VARCHAR2(N)` 最多占用的字节数，最大长度4000

### DATE 定义日期类型的数据
`DATE` 长度7字节，默认格式DD-MON-RR

## SQL（DDL、DML）
### 创建表
```SQL
CREATE TABLE [schema.]table_name (
    column_name datatype [DEFAULT expr][,...]
); -- 创建表

DESC table_name; -- 查看表结构
```
### 修改表
```SQL
RENAME old_name TO new_name; -- 修改表名

ALTER TABLE table_name ADD (
    column datatype [DEFAULT expr][, column datatype...]
); -- 增加列
/* 列只能增加在最后，不能插入到现有的列中 */

ALTER TABLE table_name DROP (column); -- 删除列
/* 删除字段需要从每行中删掉该字段占据的长度和数据，并释放在数据块中占据的空间。
 * 如果表记录比较大，删除字段可能需要比较长的时间。
 */

ALTER TABLE table_name MODIFY (column datatype [DEFAULT expr][, column datatype...]); -- 修改列
/* 建表之后，可以改变表中列的数据类型、长度和默认值，修改仅对以后插入的数据有效 */
```

### DML 语句
```SQL
INSERT INTO table_name [(column[, column...])] VALUES (value[, value...]); -- 插入一条记录

/* 
 * 如果插入的列有日期字段，需要考虑日期的格式，默认格式'DD-MON-RR'。
 * 可以自定义日期格式，用TO_DATE函数转化为日期类型的数据。
 */
 
-- 使用默认日期格式插入记录
INSERT INTO myemp (id, name, job, birth) VALUES (1002, 'martha', 'ANALYST', '01-SEP-03');
-- 使用自定义日期格式插入记录
INSERT INTO myemp (id, name, job, birth) VALUES (1003, 'donna', 'MANAGER', TO_DATE('2009-09-01', 'YYYY-MM-DD'));

UPDATE table_name SET column = value [, column = value]... [WHERE condition]; -- 更新表中的记录

DELETE [FROM] table_name [WHERE condition]; -- 删除表中的记录
```

> * `TRUNCATE` 语句和 `DELETE` 语句的区别：
    * DELETE 可以有条件删除，TRUNCATE将表数据全部删除
    * DELETE是DML语句，可以回退，TRUNCATE是DDL语句，立即生效，无法回退
    * 如果是删除全部表记录，且数据量较大，DELETE语句效率比TRUNCATE语句低

## 字符串操作
### 字符串类型
`CHAR` 存放定长字符，可以不指定长度，默认为1
`VARCHAR2` 存放变长字符，必须指定长度
默认单位是字节
`LOGN` 存储变长字符串，有诸多限制
`CLOB` 存储定长或变长字符串，替代`LONG`类型
### 字符串操作
* `CONCAT(char1, char2)`
  -返回两个字符串连接后的结果
  -等价操作：连接操作符 `||`
  -如果任何一个为`NULL`，相当于连接了一个空格
  -多个字串连接，用 `||` 更直观
* `LENGTH(char)`
  -返回字符串的长度
  -`VARCHAR2` 返回实际长度
  -`CHAR` 返回定义长度
* `UPPER(char)` `LOWER(char)` `INITCAP(char)`
  -大小写转换函数，首字符大写
  -输入`NULL`值，返回`NULL`值
* `TRIM(c2 FROM c1)` `LTRIM(c1[,c2])` `RTRIM(c1[,c2])`
  -从c1的前后/左边/右边 截去c2
  -`TRIM` 常用来去掉字符串前后的空格
* `LAPD(char1, n, char2)` `RAPD(char1, n, char2)` 补位函数
  -用于在字符串char1的左端或右端用char2补足到n位，char2可重复多次
* `SUBSTR(char, m[,n])`
  -获取字串，返回从m位开始取n个字符
  -m=0，从首字符开始；m<0，从尾部开始
  -最大长度取到字符串末尾为止
  -字符串的首位计数从1开始
* `INSTR(char1, char2[, n[,m]])`
  -返回字串char2在源字符串char1中的位置
  - 从n的位置开始搜索，没有指定n，从第1个字符开始搜索
  - m用于指定字串的第m次出现次数，如果不指定取值1
  - 如果在char1中没有找到字串char2，返回0

## 数值操作
### 数值类型
* `NUMBER(P)` 表示整数
  -如果没有设置S，则默认取值0
  -P表示数字的总位数，取值为1-38
* `NUMBER(P, S)` 表示浮点数
  -指定了S但是没有指定P，则P默认为38

### 数值函数
* `ROUND(n[,m])` 用于四舍五入
  -n 要被处理的数字
  -m 正、负、零
* `TRUNC(n[,m])` 用于截取数字
* `MOD(m, n)` 返回m除以n后的余数
  -n为0则直接返回m
* `CEIL(n)` `FLOOR(n)` 向上/向下取整

## 日期操作
### 日期类型
* `DATE`
    -固定为7个字节
    1. 世纪+100
    2. 年
    3. 月
    4. 天
    5. 小时+1
    6. 分+1
    7. 秒+1
* `TIMESTAMP`
    -精度为0，用7字节存储
    -精度大于0，用11字节存储
      1-7字节：和DATE相同
      8-11字节：纳秒，内部运算类型为整型

### 日期关键字
* `SYSDATE`
  -返回当前的系统时间，精确到秒
  -默认显示格式：DD-MON-RR
* `SYSTIMESTAMP`
  -返回当前系统日期和时间，精确到毫秒

### 日期转换函数
* `TO_DATE(char[,fmt[,nlsparams]])`
  -将字符串按照指定格式转换为日期类型

 **常见的日期格式**
| ----  | ----     |
| ----- | -------- |
| YY    | 2位数字的年份  |
| YYYY  | 4位数字的年份  |
| MM    | 2位数字的月份  |
| MON   | 简拼的月份    |
| MONTH | 全拼的月份    |
| DD    | 2位数字的天   |
| DY    | 周几的缩写    |
| DAY   | 周几的全拼    |
| HH24  | 24小时制的小时 |
| HH12  | 12小时制的小时 |
| MI    | 显示分钟     |
| SS    | 显示秒      |

* `TO_CHAR(date[,fmt[,nlsparams]])` 
  -将其它类型的数据转换为字符类型

### 日期常用函数
* `LAST_DAY(date)` 返回日期所在月的最后一天
* `ADD_MONTHS(date, i)` 返回日期date加上i个月后的日期值
* `MONTHS_BETWEEN(date1, date2)` 计算两个日期之间间隔月数
* `NEXT_DAY(date, char)` 返回日期的下个周几
* `LEAST/GREATEST(expr1[,expr2[,expr3]]...)` 返回最值
* `EXTRACT(date FROM datetime)` 从参数中提取指定数据，如年、月、日

## 空值操作
> 任何数据类型均可取值`NULL`

* NULL条件查询
```SQL
SELECT * FROM table_name WHERE column IS NULL;
```
### 空值函数 NVL
* `NVL(expr1, expr2)` 将NULL转变为非NULL值
  如果expr1为NULL，则取值expr2
* `NVL2(expr1, expr2, expr3)` 将NULL转变为实际值
  判断expr1是否为NULL，如果不是NULL，返回expr2，如果是NULL，返回expr3

## 基础查询
### 查询条件
* 使用 `>`, `<`, `>=`, `<=`, `!=`, `<>`, `=`
* 使用 `AND`, `OR` 关键字
* 使用 `LIKE` 条件（模糊查询）
  * %：表示0到多个字符
  * _：表示单个字符
* 使用 `IN` 和 `NOT IN`
  取出符合/不符合列表范围中的数据
* `BETWEEN...AND...`
  查询符合某个值域范围条件的数据
* 使用 `IS NULL` 和 `IS NOT NULL`
* 使用 `ANY` 和 `ALL` 条件
  不能单独使用，需配合单行比较操作符一起使用
  * > ANY：大于最小
  * < ANY：小于最大
  * > ALL：大于最大
  * < ALL：小于最小
* 查询条件中使用表达式和函数
* 使用 `DISTINCT` 过滤重复

### 排序
* 使用 `ORDER BY` 子句
```SQL
SELECT <*, column [alias], ...> FROM table [WHERE condition(s)] [ORDER BY column [ASC | DESC]];
```
* `ASC` 和 `DESC`
  指定升序排序（默认选项）和降序排序
  **`NULL` 值视作最大**，升序最后，降序最前
* 多个列排序
  每个列需要单独设置排序方式

### 聚合函数（分组函数、多行函数、集合函数）
将表的全部数据划分为几组数据，每组数据统计出一个结果

* `MAX` 和 `MIN`
  任何数据类型，包括数字、字符和日期
* `AVG` 和 `SUM`
  只能操作数字类型，忽略`NULL`值
* `COUNT`
  用来计算表中的记录条数，忽略 `NULL` 值
* 聚合函数对空值的处理
  忽略 `NULL` 值

### 分组
* GROUP BY 子句
```SQL
SELECT <*, column [alias], ...> FROM table [WHERE condition(s)] [GROUP BY group_by_expression] [HAVING group_condition] [ORDER BY column [ASC | DESC]];
```
* HAVING 子句
  对分组后的结果进一步限制
  必须跟在 `GROUP BY` 后面，不能单独存在

### 查询语句执行顺序
1. from 子句：执行顺序为从后往前、从右到左
* 数据量较少的表尽量放在后面
2. where 子句：执行顺序为自下而上、从右到左
* 将能过滤掉最大数量记录的条件写在Where子句的最右
3. group by
* 最好在 `GROUP BY` 前使用 `WHERE` 将不需要的记录在 `GROUP BY` 之前过滤掉
4. having 子句：消耗资源
* 尽力避免使用， `HAVING`  会在检索出所有记录之后才对结果集进行过滤，需要排序等操作
5. select 子句：少用 `*` 号，尽量取字段名称
* ORACLE 在解析的过程中，通过查询数据字典将 `*` 号依次转换成所有的列名，消耗时间
6. order by 子句：执行顺序为从左到右排序，消耗资源

## 关联查询
### 内连接
返回所有满足连接条件的记录
```SQL
SELECT e.ename, d.dname FROM emp e, dept d WHERE e.deptno = d.deptno; -- 隐式内连接
/* 没有INNER JOIN，形成的中间表为两个表的笛卡尔积 */

SELECT e.ename, d.dname FROM emp e `[INNER] JOIN` dept d `ON(e.deptno = d.deptno);` -- 显示内连接
/* 右INNER JOIN，形成的中间表为两个表经过ON条件过滤后的笛卡尔积 */
```
### 外连接
不仅返回满足连接条件的记录，还将返回不满足连接条件的记录
#### 驱动表的概念
```SQL
SELECT table1.column, table2.column FROM table1 LEFT | RIGHT | FULL [OUTER] JOIN table2 ON table1.column1 = table2.column2;
```
#### 左外连接
> 在等值连接的基础上加上主表中的未匹配数据

返回左表的所有行。如果左表的某行在右表中没有匹配行，则在相关联的结果集行中右表的所有选择列表均为空值。
```SQL
/* emp 表做驱动表 */
SELECT e.ename, d.dname FROM emp e `LEFT OUTER JOIN` dept d `ON e.deptno = d.deptno;`
```

#### 右外连接
> 在等值连接的基础上加上被连接表的不匹配数据

返回右表的所有行。如果有表的某行在左表中没有匹配行，则将为左表返回空值。
```SQL
/* dept 表做驱动表 */
SELECT e.ename, d.dname FROM emp e `RIGHT OUTER JOIN` dept d `ON e.deptno = d.deptno;`
```

#### 全外连接
> 在等值连接的基础上将左表和右表的未匹配数据都加上

返回左表和右表中的所有行。当某行在另一个表中没有匹配行时，则另一个表的选择列表列包含空值。如果表之间有匹配行，则整个结果集行包含基表的数据值。
```SQL
SELECT e.ename, d.dname FROM emp e `FULL OUTER JOIN` dept d `ON` e.deptno = d.deptno;
```

### 交叉连接（笛卡尔积）
**不带ON子句，返回的是两表的乘积**
返回左表中的所有行，左表中的每一行与右表中的所有行组合。

```SQL
SELECT table1.column, table2.column FROM table1, table2 WHERE table1.column1 = table2.column2; -- 隐式交叉连接
/* 没有CROSS JOIN */

SELECT table1.column, table2.column FROM table1 `CROSS JOIN` table2 WHERE table1.column1 = table2.column2; -- 显示交叉连接
/* 使用CROSS JOIN */
```

### 自连接
数据的来源是一个表，即关联关系来自于单表中的多个列
表中的列参照同一个表中的其它列的情况称作自参照表
自连接是通过将表用别名虚拟成两个表的方式实现，可以是等值或不等值连接

## 高级查询
### 子查询
#### 子查询在WHERE子句中
如果子查询返回多行，主查询中要使用多行比较操作符
```SQL
/* 查询出部门中有SALESMAN但职位不是SALESMAN的员工的信息 */
SELECT empno, ename, job, sal, deptno FROM emp WHERE deptno IN `(SELECT deptno FROM emp WHERE job = 'SALESMAN')` AND job <> 'SALESMAN';
```
在子查询中需要引用到主查询的字段数据，使用 `EXISTS` 关键字，`EXISTS` 后边的子查询至少返回一行数据，则整个条件返回 `TRUE`
```SQL
/* 列出来那些有员工的部门信息 */
SELECT deptno, dname FROM dept d WHERE EXISTS `(SELECT * FROM emp e WHERE d.deptno = e.deptno)`;
```
#### 子查询在HAVING子句中
```SQL
/* 查询列出最低薪水高于部门30的最低薪水的部门信息 */
SELECT deptno, MIN(sal) min_sal FROM emp GROUP BY deptno HAVING MIN(sal) > `(SELECT MIN() FROM emp WHERE deptno = 30)`;
```
#### 子查询在FROM部分
如果要在一个子查询的结果中继续查询，则子查询出现在FROM子句中，这个子查询也称作**行内视图**或者**匿名视图**
把子查询当作视图对待，但视图没有名字，只能在当前的SQL语句中有效
```SQL
/* 查询出薪水比本部门平均薪水高的员工信息 */
SELECT e.deptno, e.ename, e.sal FROM emp e, `(SELECT deptno, AVG(sal) avg_sal FROM emp GROUP BY deptno) x` WHERE e.deptno = x.deptno AND e.sal > x.avg_sal ORDER BY e.deptno;
```
#### 子查询在SELECT部分
把子查询放在SELECT子句部分，可以认为是外连接的另一中表现形式，使用更灵活
```SQL
SELECT e.ename, e.sal, `(SELECT d.deptno FROM dept d WHERE d.deptno = e.deptno) deptno` FROM emp e;
```
### 分页查询
#### ROWNUM
* 被称作伪列，用于返回表示行数据顺序的数字
* 只能从1计数，不能从结果集中直接截取
```SQL
SELECT ROWNUM, empno, ename, sal FROM emp WHERE rownum > 3; -- 查询不到结果
```
* 利用ROWNUM截取结果集中的部分数据，需要用到行内视图
```SQL
SELECT * FROM (SELECT ROWNUM rn, e.* FROM emp e) WHERE rn BETWEEN 8 AND 10;
```
#### 使用子查询进行分页
分页策略：每次只取一页的数据。每次换页，取下一页的数据
```SQL
SELECT * FROM (SELECT ROWNUM rn, t.* FROM (SELECT empno, ename, sal FROM emp ORDER BY sal DESC) t) WHERE rn BETWEEN `((n - 1) * pageSize + 1)` AND `(n * pageSize)`; -- n是当前页数
```
### DECODE 函数
#### DECODE函数基本语法
`DECODE (expr, search1, result1[, search2, result2...][, default])`
类似于switch结构，default可选，如果没有提供default的参数值，当没有匹配到时，将返回NULL
```SQL
SELECT ename, job, sal, 
    DECODE (job, // 条件
        'MANAGER', sal * 1.2,
        'ANALYST', sal * 1.1,
        'SALESMAN', sal * 1.05,
        sal // 默认值
    ) bonus
FROM emp;
```
和DECODE函数功能相似的有CASE语句，实现类似于if-else的操作
```SQL
SELECT ename, job, sal,
    CASE job // 条件
        WHEN 'MANAGER' THEN sal * 1.2
        WHEN 'ANALYST' THEN sal * 1.1
        WHEN 'SALESMAN' THEN sal * 1.05
        ELSE sal /* 默认值 */END 
    bonus
FROM emp;
```
#### DECODE函数在分组查询中的应用
* 按字段内容分组
  场景：计算职位的人数，analyst/manager属于vip，其余是普通员工operation，无法用GROUP BY简单实现
```SQL
SELECT DECODE (
    job,
    'ANALYST', 'VIP'
    'MANAGER', 'VIP',
    'OPERATION'
) job, COUNT(1) job_cnt
FROM emp
GROUP BY DECODE (
    job,
    'ANALYST', 'VIP'
    'MANAGER', 'VIP',
    'OPERATION'
);
```
* 按字段内容排序
  场景：Dept表中按“OPERATIONS”、“ACCOUNTION”、“SALES”排序，无法按照字面数据排序
```SQL
SELECT deptno, dname, loc
FROM dept
ORDER BY DECODE (
    dname, 
    'OPERATIONS', '1',
    'ACCOUNTING', '2',
    'SALES', '3'
); -- 自定义排序规则
```
### 排序函数
#### ROW_NUMBER
`ROW_NUMBER() OVER (PARTITION BY col1 ORDER BY col2)`
表示根据col1分组，在分组内部根据col2排序
此函数计算的值就表示每组内部排序后的顺序编号，组内连续且唯一
Rownum是伪列，ROW_NUMBER功能更强，可以直接从结果集中取出子集
场景：按照部门编码分组显示，每组内按职员编码排序，并赋予组内编码
```SQL
SELECT deptno, ename, empno, ROW_NUMBER() OVER (PARTITION BY deptno ORDER BY empno) AS emp_id FROM emp;
```
#### RANK
`RANK() OVER (PARTITION BY col1 ORDER BY col2)`
表示根据col1分组，在分组内部根据col2给予等级标识
等级标识即排名，相同的数据返回相同排名
跳跃排序，如果有相同数据，则排名相同，比如并列第二，则两行数据都标记为2，但下一位将是第四名
RANK有重复值，而ROW_NUMBER没有
场景：按照部门编码分组，同组内按薪水倒序排序，相同薪水则按奖金数正序排序，并给予组内等级，用Rank_ID表示
```SQL
SELECT deptno, ename, sal, comm,RANK() OVER(PARTITION BY deptno ORDER BY sal DESC, comm) 'Rank_ID' FROM emp;
```
#### DENSE_RANK
`DENSE_RANK() OVER (PARTITION BY col1 ORDER BY col2)`
表示根据col1分组，在分组内部根据col2给予等级标识
等级标识即排名，相同的数据返回相同排名
连续排序，如果有并列第二，下一个排序将是三，这一点是和RANK的不同，RANK是跳跃排序
场景：关联emp和dept表，按照部门编码分组，每组内按照员工薪水排序，列出员工的部门名字、姓名和薪水
```SQL
SELECT d.dname, e.ename, e.sal 
DENSE_RANK() OVER (
    PARTITION BY e.deptno ORDER BY e.sal
) AS drank 
FROM emp e 
JOIN dept d 
ON e.deptno = d.deptno;
```
### 集合操作、
* 为了合并多个SELECT语句的结果，可以使用集合操作符，实现集合的并、交、差
* 多条作集合操作的SELECT语句的列的个数和数据类型必须匹配
* ORDER BY子句只能放在最后的一个查询语句中
```SQL
SELECT statement1 [UNION | UNION ALL | INTERSECT | MINUS] SELECT statement2;
```
#### UNION、UNION ALL
* 用来获取两个或两个以上结果集的并集
* UNION操作符会自动去掉合并后的重复记录
* UNION ALL返回两个结果集中的所有行，包括重复的行
* UNION操作符对查询结果排序，UNION ALL不排序

#### INTERSECT
* 获得两个结果集的交集
* 只有同时存在于两个结果集中的数据，才被显示输出
* 结果集会以第一列的数据作升序排列

#### MINUS
* 获得两个结果集的差集
* 结果集一减去结果集二的结果

### 高级分组函数
ROLLUP、CUBE和GROUPING SETS运算符是GROUP BY子句的扩展，可以生成与使用UNION ALL来组合单个分组查询时相同的结果集，用来简化和高效的实现统计查询

    GROUP BY ROLLUP(a, b, c)
    GROUP BY CUBE(a, b, c)
    GROUP BY GROUPING SETS((a), (b))

#### ROLLUP
* 对ROLLUP的列从右到左以一次少一列的方式进行分组直到所有列都去掉后的分组（也就是全表分组）
* 对于n个参数的ROLLUP，有n+1次分组
  `SELECT a, b, c, SUM(d) FROM test GROUP BY ROLLUP(a, b, c)`
  等价于
  `SELECT a, b, c, SUM(d) FROM test GROUP BY a, b, c UNION ALL
  SELECT a, b, NULL, SUM(d) FROM test GROUP BY a, b UNION ALL
  SELECT a, NULL, NULL, SUM(d) FROM test GROUP BY a UNION ALL
  SELECT NULL, NULL, NULL, SUM(d) FROM test`

#### CUBE
* 对每个参数，都可以理解为取值为参与分组和不参与分组两个值的一个维度，所有维度取值组合的集合就是分组后的集合
* 对于n个参数的cube，有2^n次分组

GROUP BY CUBE(a, b, c)，首先是对(a, b, c)进行GROUP BY，然后依次是(a, b), (a, c), (a), (b, c), (b), (c)，最后对全表进行GROUP BY操作，一共是2^3=8次分组

`SELECT a, b, c, SUM(d) FROM test GROUP BY CUBE(a, b, c)`
等价于
`SELECT a, b, c, SUM(d) FROM test GROUP BY a, b, c UNION ALL
SELECT a, b, NULL, SUM(d) FROM test GROUP BY a, b, c UNION ALL
SELECT a, NULL, c, SUM(d) FROM test GROUP BY a, c UNION ALL
SELECT a, NULL, NULL, SUM(d) FROM test GROUP BY a, b, c UNION ALL
SELECT NULL, b, c, SUM(d) FROM test GROUP BY a, b, c UNION ALL
SELECT NULL, b, NULL, SUM(d) FROM test GROUP BY a, b, c UNION ALL
SELECT NULL, NULL, c, SUM(d) FROM test GROUP BY a, b, c UNION ALL
SELECT NULL, NULL, NULL, SUM(d) FROM test`

#### GROUPING SETS
* 可以生成与使用单个GROUP BY、ROLLUP或CUBE运算符所生成的结果集相同的结果集
* 如果不需要获得由完备的ROLLUP或CUBE运算符生成的全部分组，则可以使用GROUPING SETS仅指定所需的分组
* GROUPING SETS列表可以包含重复的分组

## 视图、序列、索引
### 视图
> * 视图也被称作虚表，是一组数据的逻辑表示
> * 视图对应于一条SELECT语句，结果集被赋予一个名字，即视图名字
> * 视图本身并不包含任何数据，它只包含映射到基表的一个查询语句，当基表数据发生变化时，视图数据也随之变化

#### 视图的概念
创建视图：
`CREATE [OR REPLACE] VIEW view_name[(alias[,alias...])] AS subquery;`
查看视图结构：
`DESC viewname;` 

* 视图创建后，可以像操作表一样操作视图，主要是查询
* Subquery是SELECT查询语句，对应的表被称作基表
* 根据视图所对应的子查询种类分为几种类型
  1. SELECT语句是基于单表建立的，且不包含任何函数运算、表达式或分组函数，叫做**简单视图**，此时视图是基表的子集
  2. SELECT语句是基于单表建立的，但包含了单行函数、表达式、分组函数或GROUP BY子句，叫做**复杂视图**
  3. SELECT语句是基于多个表的，叫做**连接视图**

#### 视图的作用
1. 简化复杂查询
   如果需要经常执行某项复杂查询，可以基于这个复杂查询建立视图，此后查询此视图即可
2. 限制数据访问
   视图本质上就是一条SELECT语句，所以当访问视图时，只能访问到所对应的SELECT语句中涉及到的列，对基表中的其它列起到安全和保密的作用

#### 授权创建视图
* 创建视图语句 `CREATE VIEW`
* 用户必须有权限
* 管理员可以通过DCL语句授予用户创建视图的权限
  `GRANT /* DCL命令，授予权限 */ CREATE VIEW /* 权限名 */ TO user;`

#### 创建简单视图（单表）
#### 查询视图
查询视图和查询表的操作相同：
 `SELECT * FROM viewname;`
此时视图的列名和创建视图时的列名一致，不一定是原列名
#### 对视图进行INSERT操作
* 视图本身并不包含数据，只是基表数据的逻辑映射
* 当对视图执行DML操作时，实际上是对基表的DML操作
* 对视图执行DML操作的基本原则：
  1. 简单视图能够执行DML操作。当基表中定义了非空列，且对视图不可见，无法对视图执行INSERT操作
  2. 如果视图定义中包含了函数、表达式、分组语句、DISTINCT关键字或ROWNUM伪列，不允许执行DML操作
  3. DML操作不能违反基表的约束条件
* 简单视图可以通过DML操作影响到基表数据

#### 创建具有CHECK OPTION约束的视图
`CREATE [OR REPLACE] VIEW view_name[(alias[, alias...])] AS subquery [WITH CHECK OPTION];`

* WITH CHECK OPTION短语表示，通过视图所做的修改，必须在视图的可见范围内
* 假设INSERT，新增的记录在视图仍可查看
* 假设UPDATE，修改后的结果必须能通过视图查看到
* 假设DELETE，只能删除现有视图里能查到的记录
* DML操作失败，部门20不在视图可见范围内

#### 创建具有READ ONLY约束的视图
`CREATE [OR REPLACE] VIEW view_name[(alias[, alias...])] AS subquery [WITH READ ONLY];`

* 对简单视图进行DML操作是合法的，但是不安全的
* 在建立视图时声明为只读来避免这种情况，保证视图对应的基表数据不会被非法修改

#### 通过查询user_views获取相关信息
* 和视图相关的数据字典：
  `USER_OBJECTS` `USER_VIEWS` `USER_UPDATE_COLUMNS`
* 在数据字典`USER_OBJECTS`中查询所有视图名称
  `SELECT object_name FROM user_objects WHERE object_type = 'VIEW';`
* 在数据字典`USER_VIEWS`中查询指定视图
  `SELECT text FROM user_views WHERE view_name = 'V_EMP_10';`
* 在数据字典`USER_UPDATE_COLUMNS`中查询视图
  `SELECT column_name, insertable, updatable, deletable FROM user_updatable_columns WHERE table_name = 'V_EMP_10';`

#### 创建复杂视图
* 必须为子查询中的表达式或函数定义别名
* 复杂视图不允许DML操作

#### 删除视图
`DROP VIEW view_name;`
视图虽然是存放在数据字典中的独立对象，但视图仅仅是基于表的一个查询定义，所以对视图的删除不会导致基表数据的丢失，不会影响基表数据

### 序列
> * 序列是一种用来生成唯一数字值的数据库对象
> * 序列的值由Oracle程序按递增或递减顺序自动生成，通常用来自动产生表的主键值，是一种高效率获得唯一键值的途径
> * 序列是独立的数据库对象，和表是独立的对象，序列并不依附于表
> * 通常情况下，一个序列为一个表提供主键值，但一个序列也可以为多个表提供主键值

#### 创建序列
`CREATE SEQUENCE [shema.]sequence_name [START WITH i][INCREMENT BY j][MAXVALUE m | NOMAXVALUE][MINVALUE n | NOMINVALUE][CYCLE | NOCYCLE][CACHE p | NOCACHE]`

* sequence_name是序列名，将创建在schema方案下
* 第一个序列值是i，步进是j（正数递增，负数递减）
* 可生成的最大值是m，最小值是n
* 如果不设置任何可选参数，起始值是1，步进是1
* CYCLE表示增长至最值之后是否继续生成序列号，默认NOCYCLE
* CACHE用来指定先预取p个数据在缓存中，以提高序列值的生成效率，默认是20

#### 使用序列
* 序列中有两个伪列：
  1. NEXTVAL：获取序列的下个值
  2. CURRVAL：获取序列的当前值
* 当序列创建以后，必须先执行一次NEXTVAL，之后才能使用CURRVAL

#### 删除序列
`DROP SEQUENCE sequence_name;`

### 索引
> * 索引是一种允许直接访问数据表中某一数据行的树形结构，为了提高查询效率而引入，是独立于表的对象，可以存放在与表不同的表空间中
> * 索引记录中存有**索引关键字**和**指向表中数据的指针**
> * 对索引进行的IO操作比对表进行操作要少很多
> * 索引一旦被建立就将被Oracle系统自动维护，查询语句中不用指定使用哪个索引

#### 索引的原理
![B-tree索引的结构](http://hi.csdn.net/attachment/201011/25/375658_1290673318JGq3.jpg)
ROWID：伪列，唯一标识一条数据记录，可以理解为行地址
![B-tree索引的结构2](http://hi.csdn.net/attachment/201011/25/375658_1290673318JBRI.jpg)

#### 创建索引
`CREATE [UNIQUE] INDEX index_name ON table(column[, column...]);`

* 复合索引也叫多列索引，是基于多个列的索引
* 如果经常在ORDER BY子句中使用job和sal作为排序依据，可以建立复合索引
  `CREATE INDEX idx_emp_job_sal ON emp(job, sal);`
* 当做下面的查询时，会自动应用索引
  `SELECT empno, ename, sal, job FROM emp ORDER BY job, sal;`

#### 创建基于函数的索引
* 如果需要在emp表的ename列上执行大小写无关搜索，可以在此列上建立一个基于UPPER函数的索引
  `CREATE INDEX emp_ename_upper_idx ON emp(UPPER(ename));`
* 当做下面的查询时，会自动应用索引
  `SELECT * FROM emp WHERE UPPER(ename) = 'KING';`

#### 修改和删除索引
* 如果经常在索引列上执行DML操作，需要定期重建索引，提高索引的空间利用率
  `ALTER INDEX index_name REBUILD;`
* 当一个表上有不合理的索引，会导致操作性能下降，删除索引：`DROP INDEX index_name;`

#### 合理使用索引提升查询效率
* 为经常出现在WHERE子句中的列创建索引
* 为经常出现在ORDER BY、DISTINCT后面的字段建立索引。如果建立的是复合索引，索引的字段顺序要和这些关键字后面的字段顺序一致
* 为经常作为表的连接条件的列上建立索引
* 不要在经常作DML操作的表上建立索引
* 不要在小表上建立索引
* 限制表上的索引数目，索引并不是越多越好
* 删除很少被使用的、不合理的索引

## 约束
### 约束概述
#### 约束的作用
* 全称是 约束条件，也称作 完整性约束条件
* 约束是在数据表上强制执行的一些数据校验规则，当执行DML操作时，数据必须符合这些规则，如果不符合则无法执行
* 约束条件可以保证表中数据的完整性，保证数据间的商业逻辑

#### 约束的类型
* 非空约束 NN
* 唯一性约束 UK
* 主键约束 PK
* 外键约束 FK
* 检查约束 CK

### 非空约束
> 确保字段值不为空

建表时添加非空约束
`CREATE TABLE table_name (column_name column_type() NOT NULL);`
修改表时添加非空约束
`ALTER TABLE table_name MODIFY (column_name column_type() NOT NULL);`
取消非空约束
`ALTER TABLE table_name MODIFY (column_name column_type() NULL);`

### 唯一性约束
> 保证字段或者字段的组合不出现重复值

建表时添加唯一性约束
`CREATE TABLE table_name (column_name column_type() UNIQUE);`
建表后添加唯一性约束
`ALTER TABLE table_name (ADD CONSTRAINT constraint_name UNIQUE(column_name));`

### 主键约束
> * 功能上相当于非空且唯一的组合
> * 可以是但字段或多字段组合，即：
>   在主键约束下的但字段或者多字段组合上不允许有空值，也不允许有重复值
> * 可以用来在表中唯一地确定一行数据
> * 一个表上只允许建立一个主键，而其它约束条件则没有明确的个数限制

#### 主键选取的原则
* 主键应是对系统无意义的数据
* 永远也不要更新主键，让主键除了唯一标识一行之外，再无其他的用途
* 主键不应包含动态变化的数据，如时间戳
* 主键应自动生成，不要认为干预，以免使它带有除了唯一标识一行以外的意义
* 主键尽量建立在单列上

建表时添加主键约束
`CREATE TABLE table_name (column_name column_type() PRIMARY KEY);`
建表后添加主键约束
`ALTER TABLE table_name (ADD CONSTRAINT constraint_name PRIMARY KEY(column_name));`

### 外键约束
> 定义在两个表的字段或一个表的两个字段上，用于保证相关两个字段的关系

建表后添加外键约束
`ALTER TABLE table_name (ADD CONSTRAINT constraint_name FOREIGN KEY(fk_column_name) REFERENCES primary_table(refered_column));`

#### 外键约束对一致性的维护
* 外键约束条件包括两个方面的数据约束：
1. 从表定义的外键的列值，必须从主表被参照的列值中选取，或者为NULL
2. 当主表参照的值被从表参照时，主表的该行记录不允许被删除

#### 外键约束对性能的降低
* 如果在一个频繁DML操作的表上建立外键，每次DML操作，都将导致数据库自动对外键所关联的对应表做检查，产生开销，如果已在程序中控制逻辑，这些判断将增加额外负担，可以省去
* 外键确定了主从表的先后生成关系，有时会影响业务逻辑

#### 关联不一定需要外键约束
* 保证数据完整性可由程序或触发器控制
* 简化开发，维护数据时不用考虑外键约束
* 大量数据DML操作时不需考虑外键耗费时间

### 检查约束
> * 用来强制在字段上的每个值都要满足CHECK中定义的条件
> * 当定义了CHECK约束的列新增或修改数据时，数据必须符合CHECK约束中定义的条件

添加约束检查
`ALTER TABLE table_name (ADD CONSTRAINT constraint_name CHECK(condition));`
