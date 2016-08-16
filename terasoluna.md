# Terasoluna

---

[TOC]

---

## Java的反射机制

反射是Java中一种特有的机制，通过反射，利用Class类，可以在运行时查看类的信息，创建类的实例、调用类的方法以及访问类的字段。

反射使用的类在`java.lang.reflect`包下，主要有Field、Method、Constructor、Proxy这几个类，并以上面讲到的Class类为基础。

例1：通过类的名字创建类的实例

* 通过类的名字创建类的实例
```java
Class cls = Class.forName(className);
Object obj = cls.newInstance();
```

* 利用带参数的构造函数
```java
public Object newInstance(String className, Object[] args) throws Exception {
    Class newoneClass = Class.forName(className);
    Class[] argsClass = new Class[args.length];
    for (int i = 0, j = args.length; i < j; i++) {
        argsClass[i] = args[i].getClass();
    }
    Constructor cons = newoneClass.getConstructor(argsClass);
    return cons.newInstance(args);
}
```

例2：通过方法的名字执行类的方法
```java
public Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
    Class ownerClass = owner.getClass();
    Class[] argsClass = new Class[args.length];
    for (int i = 0, j = args.length; i < j; i++) {
        argsClass[i] = args[i].getClass();
    }
    Method method = ownerClass.getMethod(methodName, argsClass);
    return method.invoke(owner, args);
}
```

## Java的动态代理

* 代理

就是不去直接调用对象的方法，而是先去调用对象B的方法，再由B去间接调用A的方法。

如下例：

```java
public interface Subject {
	public void request();
}

public class RealSubject implements Subject {
	public RealSubject() {
	}

	public void request() {
		System.out.println("From real subject.");
	}

}

public class ProxySubject implements Subject {
	private RealSubject realSubject;

	public ProxySubject() {
	}

	public void request() {
		preRequest();
		if (realSubject == null) {
			realSubject = new RealSubject();
		}
		realSubject.request();
		afterRequest();
	}

	private void afterRequest() {

	}

	private void preRequest() {
	}

}

public class Test {

	public static void main(String[] args) {
		Subject sub = new ProxySubject();
		sub.request();
	}

}
```

上面这个例子中，RealSubject类的request()方法是需要实现的主要功能，主程序本意是去调用这个方法，但是没有直接去调用，而是去调用实现共同接口的ProxySubject类的request()方法，这个方法中再去调用RealSubject类的request()方法，同时又做了一些附加的操作。
这就是一个代理的过程，其中ProxySubject类是代理类，RealSubject类是被代理类。
这两个类实现了一个共同的接口。
这种代理方法叫“静态代理”。也就是说我们需要自己去编写相应的代理类。这样，对需要代理的不同的类或方法，我们就需要编写大量的代理类。

* 动态代理

根据Java的反射机制，可以根据名字动态生成类的实例，根据名字也可以动态执行类的方法，所以，从JDK1.3开始，引入了“动态代理”的机制。Java动态代理就是由`java.lang.reflect.Proxy`类在运行时期根据接口定义，采用Java反射功能动态生成代理类。实现“动态代理”，需要`java.lang.reflect.Proxy`类和`java.lang.reflect.InvocationHandler`接口。

如下例：

```java
public interface Subject {
	public void request();
}

public class RealSubject implements Subject {
	public RealSubject() {
	}

	public void request() {
		System.out.println("From real subject.");
	}

}

public class DynamicSubject implements InvocationHandler {
	private Object sub;

	public DynamicSubject() {
	}

	public DynamicSubject(Object object) {
		this.sub = object;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before calling " + method);
		method.invoke(sub, args);
		System.out.println("after calling " + method);
		return null;
	}

}
public class Test {

	public static void main(String[] args) {
		RealSubject realObj = new RealSubject();
		InvocationHandler handler = new DynamicSubject(realObj);
		Class cls = realObj.getClass();
		Subject subject = (Subject) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), handler);
		subject.request();
	}

}
```

在这个例子中，代理类有很大变化：
1. 不去实现Subject接口，实现InvocationHandler接口
2. 属性类型为Object，不是RealSubject
3. 执行方法通过“反射机制”执行，所以，代理类不依赖于特定的类和方法，而是在运行时根据“被代理类”的接口动态生成，对接口中所有的调用都转为对代理类中invoke()方法的调用（这一过程由Proxy完成）


**Java动态代理编程步**
1. 抽象被代理类的接口
2. 制作实现InvocationHandler接口的代理类
3. 通过Proxy类的静态方法创建代理类的实例

## Struts相关

### ActionServlet
ActionServlet是Struts的核心，主要做以下两部分工作：
1. 初始化Struts环境，消息资源、数据源、插件（plug-in）等
2. 接收Struts请求，并把请求分发给RequestProcessor进一步处理

### RequestProcessor
RequestProcessor接收ActionServlet传过来的请求，根据请求调用不同的Action，最后根据Action返回的ActionForward执行请求的转发和重定向。

主要的有以下几步：
1. processPath 获得请求的URI，以便和ActionMapping结合找到对应的Action
2. processMapping 处理和请求URI匹配的ActionMapping信息
3. processActionForm 得到ActionMapping对应的ActionForm实例（没有就创建）
4. processPopulate 用请求的表单数据填充ActionForm
5. processValidate 如果需要验证，调用ActionForm的validate()方法
6. processActionCreate 得到Action实例（没有就创建）
7. processActionPerform 执行Action的execute()方法
8. processActionForward 根据execute()方法返回的ActionForward决定流向
   对于定制的RequestProcessor，可在struts-config.xml文件中以<controller>标签进行配置，此时，配置的RequestProcessor将代替Struts标准的RequestProcessor.

### Struts plug-in

　　Struts插件用于Struts的扩展，每一个插件都是一个实现了`org.apache.action.Plugin`接口的Java类。当Struts应用启动时，调用插件的init()方法初始化，当应用关闭时，调用插件的destroy()方法销毁插件。
一个Struts应用可以有多个插件，这些插件在struts-config.xml文件中以<plug-in>标签进行配置。

## Spring相关

### IOC和DI概念

　　这是Spring中最主要的概念，在Spring中，这两个术语是一样的意思。

　　IOC这个术语提出的很早，是1998年由Apache project team的Stefano Mazzocchi提出的，目的是为Apache Server建一个FrameWork，主要涉及Commponents（组件）的装配、配置和生命周期三方面。其核心思想就是：不要去调用 FrameWork，而是由FrameWork在适当的时间来调用你。

　　很多系统和框架也都是这样去做的：比如Windows的消息驱动机制，还有Struts中Action的调用等等。

　　2004年，Martin Fowler（ThoughtWorks的首席科学家）开始研究IOC，并得出结论：只有对象间的依赖关系被反转了。基于此，他给IOC起了个新名字：DI（Dependency Injection）。

　　实际编程中，模块之间大多是有关系的，而不是孤立地存在。

例：
```java
class ClassA {
	ClassB b = null;

	public void methodA() {
		b = new ClassB();
		b.methodB();
	}
}

class ClassB {
	public void methodB() {
		// ...
	}
}
```
这两个类中，classA实现的具体功能依赖于ClassB的实现，这就是一种依赖关系。

而DI的编程思想就是，把程序代码中的这种依赖关系提取出来，放入第三方——容器（或框架）中，同时这些对象的生成以及生命周期的管理等工作也交给容器来管理。

例：
```java
interface ClassB {
	public void method();
}

class ClassBImple implements ClassB {

	public void method() {
		// ...
	}

}

class ClassA {
	ClassB b = null;

	public void setB(ClassB b) {
		this.b = b;
	}

	public void methodA() {
		b.method();
	}

}
```

```xml
<bean name="ClassA" scope="prototype" class="ClassA">
	<property name="b">
		<ref local="classB" />
	</property>
</bean>

<bean id="classB" scope="prototype" class="ClassBImpl">
</bean>
```
这个例子中，ClassA和ClassBImpl都在容器的配置文件（.xml文件）中进行了定义，而且生命周期（scope）也进行了声明，依赖关系（ClassBImple作为ClassA的一个属性）也进行了声明，这些声明都是给容器使用的，容器在需要的时候根据这些声明创建这些类的实例和它们的依赖关系（这一过程称为“装配”），在不需要的时候销毁它们。

注意的一点是，增加了一个接口，也就是程序中ClassA对ClassBImpl的依赖改成对它的抽象接口的依赖，DI通过此措施实现模块间的松散耦合。

所以DI编程需要实现三点：
1. 把模块抽象在接口中
2. 程序中的依赖变成对接口的依赖
3. 各模块的创建形式、生命周期、依赖关系声明给第三方——容器（通过配置文件）

### AOP概念

　　AOP翻译成汉语就是“面向切面编程”；是对OOP编程模式的一个补充。

　　“切面”就是应用处理流程中的某一点，“面向切面编程”也就是对处理流程中的某些点进行编程。

　　举例来说：

　　正常Java程序中，每个方法的入口和出口就是两个点，通常在入口处打印LOG，在出口处也要打印LOG。应用中所有类的方法都要编写这样的打印LOG的代码。

　　而应用AOP技术，就可以把这些打印LOG的代码抽取出来，另外封装成一个类，而原来的方法中不再编写这些代码，在程序的运行过程中，在进入方法前和退出方法后动态调用抽取出去的代码。

　　这就是AOP的编程思想。

### Spring简介

　　 Spring是一个开源的轻量级框架，由Rod Johnson于2003年创建。Spring的主要出发点是针对EJB，使J2EE的开发变得容易。

Spring的特点：
1. 实现了DI容器：由DI容器管理Bean的创建、初始化以及销毁
2. 实现了AOP框架：把AOP与DI容器结合起来，通过配置文件即可实现“面向切面编程”
3. POJO：Spring中的Java类都是普通的Java类，和框架本身没有联系，所以很容易移植和测试（对比Struts的Action和ActionForm就很清晰）
4. Spring框架提供了用于开发企业应用的所有层面：表现层、服务层、持久化层
5. Spring框架可以与其它现有技术良好的集成性

### Spring框架组成

> [Spring 系列: Spring 框架简介](https://www.ibm.com/developerworks/cn/java/wa-spring1/)

> [浅析Spring框架中的设计结构](http://www.tianmaying.com/tutorial/51706)

Spring是一个模块化的框架，由七个模块组成（下图）。
![Spring 框架的 7 个模块](http://assets.tianmaying.com/md-image/06210cf8cf786433f9d74df5f7ef5cee.png)

* Core Container：DI容器，用于生成、装配、管理Bean，是Spring框架的基础
* Application context：用于维护框架的环境，像JNDI、国际化信息等等
* Web Context：提供面向WEB应用的集成功能，例如：文件上传、通过Servlet Listener启动IOC容器、WEB应用的Context、以及与Struts或WebWork的集成
* DAO：提供JDBC层，处理JDBC编码和DB异常，并提供事务管理功能（transaction management）
* ORM：用于集成Hibernate、MyBatis等
* AOP：提供面向切面的编程功能
* MVC：Spring自己的WEB应用框架

### Spring中的DI

Spring中的主要部分就是DI容器，通过ApplicationContext类来管理所有的类。

### Spring中的AOP

Spring框架的第二个技术亮点就是AOP框架的实现。

Spring中的AOP是基于Java的动态代理实现的，并与DI容器结合在一起使用。

在2.0以后版本中，Spring引入了Aspectj的AOP技术，可以通过“注释”或直接在xml文件中配置AOP的所有内容

AOP中有很多比较混乱、难懂的概念，但主要的有以下几个概念：
1. Aspect：切面，是AOP解决问题所有信息的一个风转，其中包括切入点的信息、切面（代理）处理程序的信息
2. PointCut：切入点，插入切面程序的地方，通常是某个类的某个公有方法
3. Advice：定义在切入点的何处插入什么样的代码，有before、after、after-returning、after-throwing等等。

在Spring中，以上这些都可以在`applicationContext.xml`文件中进行配置，例：

```xml
<aop:config>
	<aop:aspect id="aspectDemo" ref="aspectBean">
		<aop:pointcut id="myPointcut" expression="execution(* Component.business*(..))" />
		<aop:before pointcut-ref="myPointcut" method="validateUser" />
		<aop:before pointcut-ref="myPointcut" method="writeLogInfo" />
		<aop:before pointcut-ref="myPointcut" method="beginTransaction" />
		<aop:after-returning pointcut-ref="myPointcut"
			method="endTransaction" />
		<aop:after-returning pointcut-ref="myPointcut"
			method="writeLogInfo" />
		<aop:before pointcut-ref="myPointcut" method="validateUser" />
	</aop:aspect>
</aop:config>

<bean id="aspectBean" class="AspectBean" />
<bean id="component" class="ComponentImpl" />
```

这里，AspectBean就是切面类（相当于前面讲的代理程序），ComponentImpl就是目标类（相当于被代理类），aop:pointcut指定当执行类似Componet.business的方法时，执行Advice(插入的程序)，<aop:before pointcut-ref="myPointcut" method="writeLogInfo" />指名在到达这个切入点之前先执行AspectBean类的writeLogInfo()方法

***

## 框架总体构成

1. Struts 担当WEB层
2. Spring与Sturts集成，提供IOC容器、AOP功能、ORM、DAO
3. Terasoluna扩展的一些附加功能

## Terasoluna框架的执行流程

1. 客户端发送请求
2. ActionServlet（Struts提供）
   根据struts-config.xml的<controller>，调用RequestProcessorEx
3. RequestProcessorEx（Terasoluna提供）
   根据struts-config.xml的配置，填充FormBean，再结合applicationContext.xml和moduleContext.xml找到需调用的Action（继承于AbstractBLoginAction），调用doExecute()方法
4. Action的doExecute()方法
   此方法做三件事：
   1. 根据blogic-io.xml的配置设置执行业务需要的参数（Form表单中提交的数据）
   2. 调用Action的实装doExecuteBLogic()方法，进行业务处理（把准备的参数传过去）
   3. 根据blogic-io.xml的配置对doExecuteBLogic()方法返回的BLogicResult对象进行处理，设置返回的数据、设置ActionForward
5. Action的doExecuteBLogic()方法
   调用实装的BLogic的方法存取DB（根据applicationContext.xml中MyBatis的配置和sqlMap.xml中定义的SQL文），把得到的数据和ActionForward用的字符串（例：success）封装到BLogicResult对象中，并返回该BLogicResult对象
6. RequestProcessorEx
   根据返回的ActionForward，返回到客户端

## Terasoluna开发需要做的工作

* 代码的编写
  1. Action的编写
     继承于AbstractBLogicAction
     声明相应的Logic属性及对应的set和get方法
     实现doExecuteBLogic()方法（返回BLogicResult对象）
  2. Form的编写
     静态Form：继承于ValidatorActionFormEx
     动态Form：在xml文件中定义
  3. Business logic的编写（POJO）
     Business logic接口的定义
     Business logic的具体实现
  4. SQL文的定义
     在sqlMap.xml文件中

* 模块的配置
  1. Action的配置
     struts-config.xml中的ActionMapping，定义URI路径、Form、Scope、Forward信息
     定义Spring中的配置（在moduleContext.xml文件中），定义对应于Action的Bean的信息
  2. Form的配置
     同Struts
  3. Business logic的配置
     在moduleContext.xml文件中，Spring配置
  4. SQL文的配置
     applicationContext.xml、sqlMapConfig.xml、sqlMap.xml
  5. I/O配置
     blogic-io.xml文件中

## 各部分详细说明

* 编写Action

  1、 程序结构
  ```java
  public class InsertBLogic implements BLogic<InsertInput> {
      private QueryDAO queryDAO = null;
      private UpdateDao updateDAO = null;
      
      public void setQueryDAO(QeuryDAO queryDAO) {
          this.queryDAO = queryDAO;
      }
      
      public void setUpdateDAO(UpdateDAO updateDAO) {
          this.updateDAO = updateDAO;
      }
      
      public BLogicResult execute(InsertInput param) {
          BLogicResult result = new BLogicResult();
          
          // SelectUserOutput selectUserOutput = queryDAO.executeForObject("getUser", param, SelectUserOutput.class);
          /* 业务逻辑处理 */
          
          BLogicMessages message = new BLogicMessages();
          // 错误信息处理
          if (false) {
              // messages.add(Globals.ERROR_KEY, new BLogicMessage(errorKey));
              messages.add("message", new BLogicMessage("errors.input.id.repeat"))；
              result.setErrors(messages); // 异常时处理
              result.setResultString("failure");
          } else {
              messages.add("message", new BLogicMessage("message.regist", param.getId()));
              result.setMessages(messages);
              result.setResultString("success");
              
              // updateDAO.execute("insertUser", param);
              // result.setResultObject(bean); // 正常时处理
          }
                    
          return result;
      }
  }
  ```
  **注释：
  > P：业务逻辑处理中需要的参数类型，对应于blogic-io.xml中对应的blogic-param的bean-name属性；param由AbstractBLogicAction设置，一般数据从提交的表单中来。
  > errorKey：属性文件中定义的错误消息的key

  2、BLogicResult介绍
  这个类中存放三个信息：DB操作的返回结果、ActionForward的信息和异常时的错误信息，最终由AbstractBLogicAction进行处理


* BLogic的编写
  1. 编写接口（POJO）

  ```java
    public interface BLogic<P> {
        BLogicResult execute(P params);
    }
  ```

  1. 编写接口的实现（POJO）

  ```java
    public class HelloWorldBLogic implements BLogic<HelloWorldInput> {
        private QueryDAO queryDAO = null;
        
        public void setQueryDAO(QueryDAO queryDAO) {
            this.queryDAO = queryDAO;
        }
        
        /* public JavaBean XXX(P param) {
              JavaBean bean = dao.XXX("SQLName", param, ...);
              return bean;
           } */
        public BLogicResult execute(HelloWorldInput param) {
            BLogicResult result = new BLogicResult();
            
            HelloWorldOutput helloWorldOutput = queryDAO.executeForObject("helloWorld", param, HelloWorldOutput.class);
            result.setResultObject(helloWorldOutput);
            result.setResultString("success");
            
            return result;
        }
    }
  ```
    **注释：
    > SQLName：sqlMap.xml文件中定义的SQL文的id

* I/O配置说明（blogic-io.xml文件中）
  1. blogic-io.xml定义BLogic的入/出力情报，由BLogicIOPlugIn在Struts启动时以plug-in的方式加载到ServletContext中，这个文件关联到.jsp、Action、Form和BLogic。
  ```xml

  ```
  1. <blogic-params ...>：设定BLogic的入参数
     bean-name：BLogic输入参数的类型（），对应于上面的P
     blogic-property：bean-name中指定的Class中的属性名
     source：指定输入数据的来源（form/session）
     property：对应于source指定的form/session中的属性名

  2. <blogic-result>：设定BLogic的出力结果
     blogic-property：bean-name中指定的Class中的属性名
     property：对应于dest指定的form/session中的属性名
     `<set-property property="USER_VALUE_OBJECT" blogic-property="uvo" dest="session" />`就是把BLogic结果中uvo属性的值放入session的“USER_VALUE_OBJECT”属性中

* SQL文的定义
  > 基于MyBatis，定义在sources/sqlMap.xml文件中

  **注释：
  > 1. parameterClass：SQL文的输入参数的类型，对应于上面的P
  > 2. resultClass：SQL文的返回结果的类型，对应于blogic-io.xml中定义的<blogic-result>
  > 3. \#XXX\#：
  >    (1) 如果输入参数为基本数据类型，对应于该类型的值
  >    (2) 非基本数据类型，对应于该类的相应属性的值

## DataSource的设置
1. 在WEB-INF/jdbc.properties文件中定义数据源的信息
2. 在applicationContext.xml中定义数据源

## MyBatis设置
1. 定义SQL文配置文件
2. 定义WEB-INF/sqlMapConfig.xml文件关联SQL文配置文件
3. 在applicationContext.xml文件中设置MyBatis

## DAO定义




## DB相关的事务处理（commit、rollback）

> 参见Terasoluna机能说明书