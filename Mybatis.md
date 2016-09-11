# Mybatis

tags： Mybatis

---

## 通过xml
`userMapper.xml`

    XxxMapper.xml
    -------------
    <mapper namespace="cn.zhlx.entity.UserMapper">
    	<select id="" parameterType="" resultType="">SQL_Clause</select>
    	<insert id="" parameterType="" resultType="">SQL_Clause</insert>
    	<update id="" parameterType="" resultType="">SQL_Clause</update>
    	<delete id="" parameterType="" resultType="">SQL_Clause</delete>
    </mapper>

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="cn.zhlx.entity.UserMapper">
	<select id="selectUser" resultType="cn.zhlx.entity.User">
		select * from user where id = #{id}
	</select>
	
	<select id="selectAll" resultType="cn.zhlx.entity.User">
		select * from user
	</select>
	
	<insert id="insert" parameterType="cn.zhlx.entity.User">
		insert into user (userName, userPwd) values (#{userName}, #{userPwd})
	</insert>
	
	<update id="update" parameterType="cn.zhlx.entity.User">
		update user set userName = (#{userName}), userPwd = (#{userPwd}) where id = (#{id})
	</update>
	
	<delete id="delete" parameterType="int">
		delete from user where id = (#{id})
	</delete>
</mapper>
```
`mybatis-config.xml`

    <mapper resource="cn/zhlx/entity/UserMapper.xml" />

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  
<configuration>
	<!-- 读取外部数据源文件 -->
	<properties resource="db.properties"></properties>
	
	<environments default="development">
		<environment id="users">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="${db.driver}" />
				<property name="url" value="${db.url}" />
				<property name="username" value="${db.username}" />
				<property name="password" value="${db.password}" />
			</dataSource>
		</environment>
	</environments>
	
	<mappers>
		<!-- xml -->
		<mapper resource="cn/zhlx/entity/UserMapper.xml" />
		
		<!-- interface -->
		<!-- 严格限制不可使用"/"代替"." -->
		<!--<mapper class="cn.zhlx.entity.UserMapper"/>-->
		
		<!-- 使用注解配置要删掉xml配置 -->
	</mappers>
</configuration>
```
`java`

    String statement = namespace + id;

```java
package cn.zhlx.test;

import java.util.List;

/**
 * via XXXMapper.xml
 * 
 */
public class Test {
	public static void main(String[] args) {
		SqlSession session = MybatisUtils.openSession();
		
		// 查询单条记录
		String statement = "cn.zhlx.entity.UserMapper.selectUser";
		User user = session.selectOne(statement, 1);
		System.out.println(user);
		
		// 查询多条记录
		statement = "cn.zhlx.entity.UserMapper.selectAll";
		List<User> users = session.selectList(statement);
//		System.out.println(users.getClass().getName()); // ArrayList
		System.out.println(users); // 直接打印集合
		// 遍历集合
//		for (User u : users) {
//			System.out.println(u);
//		}
		
		// 插入一条记录
		statement = "cn.zhlx.entity.UserMapper.insert";
//		user = new User(0, "zhang", "zhang");
		user = new User();
		user.setUserName("张");
		user.setUserPwd("zhang");
		session.insert(statement, user);
		session.commit(); // 提交事务
		
		// 修改记录
		statement = "cn.zhlx.entity.UserMapper.update";
		user = new User(13, "李", "李");
		session.update(statement, user);
		session.commit(); // 提交事务
		
		// 删除记录
		statement = "cn.zhlx.entity.UserMapper.delete";
//		user = new User(15, null, null);
//		session.delete(statement, user);
		session.delete(statement, 15);
		session.commit();
		
		session.close();
	}
}
```
## 通过接口
`UserMapper.java`

    接口中添加抽象方法，方法名 ==> xml配置中的id

```java
package cn.zhlx.entity;

import java.util.List;

public interface UserMapper {
	public abstract User selectUser(int id);
	public abstract List<User> selectAll();
	public abstract void insert(User user);
	public abstract void update(User user);
	public abstract void delete(int id);
}
```

`mybatis-config.xml`

    <!-- 严格限制不可使用"/"代替"." -->
    <mapper class="cn.zhlx.entity.UserMapper" />

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
  
<configuration>
	<!-- 读取外部数据源文件 -->
	<properties resource="db.properties"></properties>
	
	<environments default="development">
		<environment id="users">
			<transactionManager type="JDBC" />
			<dataSource type="POOLED">
				<property name="driver" value="${db.driver}" />
				<property name="url" value="${db.url}" />
				<property name="username" value="${db.username}" />
				<property name="password" value="${db.password}" />
			</dataSource>
		</environment>
	</environments>
	
	<mappers>
		<!-- xml -->
		<!-- <mapper resource="cn/zhlx/entity/UserMapper.xml" /> -->
		
		<!-- interface -->
		<!-- 严格限制不可使用"/"代替"." -->
		<mapper class="cn.zhlx.entity.UserMapper"/>
		
		<!-- 使用注解配置要删掉xml配置 -->
	</mappers>
</configuration>
```

`java`

```java
package cn.zhlx.test;

import java.util.List;

/**
 * via Interface (Xxxmapper.java)
 *
 */
public class Test2 {
	public static void main(String[] args) {
		SqlSession session = MybatisUtils.openSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		
		// 查询单条记录
		User user = mapper.selectUser(1);
		System.out.println(user);

		// 查询多条记录
		List<User> users = mapper.selectAll();
		// System.out.println(users.getClass().getName()); // ArrayList
		System.out.println(users); // 直接打印集合
		// 遍历集合
		// for (User u : users) {
		// System.out.println(u);
		// }

		// 插入一条记录
		// user = new User(0, "zhang", "zhang");
		user = new User();
		user.setUserName("张");
		user.setUserPwd("zhang");
		mapper.insert(user);
		session.commit(); // 提交事务

		// 修改记录
		user = new User(13, "李", "李");
		mapper.update(user);
		session.commit(); // 提交事务

		// 删除记录
		// user = new User(15, null, null);
		// session.delete(statement, user);
		mapper.delete(12);
		session.commit();

		session.close();
	}
}
```

## 通过SQL命令注解
`UserMapper.java`

    要删除xml配置，在接口XxxMapper中添加SQL注解

```java
package cn.zhlx.entity;

import java.util.List;

public interface UserMapper {
	@Select("select * from user where id = #{id}")
	public abstract User selectUser(int id);

	@Select("select * from user")
	public abstract List<User> selectAll();

	@Insert("insert into user (userName, userPwd) values (#{userName}, #{userPwd})")
	public abstract void insert(User user);

	@Update("update user set userName = (#{userName}), userPwd = (#{userPwd}) where id = (#{id})")
	public abstract void update(User user);

	@Delete("delete from user where id = (#{id})")
	public abstract void delete(int id);
}
```

## 通过SqlProvider类
`SqlProvider.java`

```java
package cn.zhlx.util;

public class SqlProvider {
	public String selectUser() {
		return "select * from user where id = #{id}";
	}

	public String selectAll() {
		return "select * from user";
	}

	public String insert() {
		return "insert into user (userName, userPwd) values (#{userName}, #{userPwd})";
	}

	public String update() {
		return "update user set userName = (#{userName}), userPwd = (#{userPwd}) where id = (#{id})";
	}

	public String delete() {
		return "delete from user where id = (#{id})";
	}
}
```

`UserMapper.java`

    要删除xml配置，在SqlProvider类的方法中提供SQL语句
    在XxxMapper接口抽象方法上添加 @XxxProvider注解

```java
package cn.zhlx.entity;

import java.util.List;

public interface UserMapper {
	@SelectProvider(type = SqlProvider.class, method = "selectUser")
	public abstract User selectUser(int id);

	@SelectProvider(type = SqlProvider.class, method = "selectAll")
	public abstract List<User> selectAll();

	@InsertProvider(type = SqlProvider.class, method = "insert")
	public abstract void insert(User user);

	@UpdateProvider(type = SqlProvider.class, method = "update")
	public abstract void update(User user);

	@DeleteProvider(type = SqlProvider.class, method = "delete")
	public abstract void delete(int id);
}
```