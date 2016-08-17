# Java中的数据传递问题

---

原题是这样的：

```java
public class Example {
	String str = "good";
	char[] ch = {'a', 'b', 'c'};

	public static void main(String[] args) {
		Example ex = new Example();
		ex.change(ex.str, ex.ch);
		System.out.print(ex.str + " and ");
		System.out.print(ex.ch);
	}

	private void change(String str, char[] ch) {
		str = "test ok";
		ch[0] = 'g';
	}
	
}
```

后来被我改成了这样：

```java
package mytest;

/*
 * 基本数据类型传值，引用数据类型传地址
 * 
 * 引用类型的参数传递问题：
 *    
 * 字符串和数组都是引用类型，传址
 * 
 */
public class Example {
	String str = "good";
	char[] ch = {'a', 'b', 'c'};

	public static void main(String[] args) {
		Example ex = new Example();
		ex.change(ex.str, ex.ch);
		System.out.print(ex.str + " and ");
		System.out.print(ex.ch);
		
		System.out.print("\n---------\n");
		Fish[] fishs = {new Fish(1, "1", "1")};
		ex.change2(fishs);
		for (Fish fish : fishs) {
          	// fish2
			System.out.print(fish);
		}
	}

	private void change(String str, char[] ch) {
		// TODO explain something
//		System.out.println(str); // good
//		System.out.println(this.str); // good
		str = "test ok";
//		System.out.println(str); // test ok
//		System.out.println(this.str); // good
		
//		ch[0] = 'g';
		
		// Array constants can only be used in initializers
//		ch = {'h', 'a', 'h', 'a'}; 
		// 静态初始化只能在定义时使用，此处编译器认为不是在定义，而是在使用，所以报错
		
//		ch = new char[] { 'h', 'a', 'h', 'a' };
		// 定义了一个新数组对象，将ch指向这个数组（作为数组的引用）
	}
	
	private void change2(Fish[] fishs) {
		fishs[0] = new Fish(2, "2", "2");
	}
}

class Fish extends Animal {
	private String sex;

	public Fish() {
		super();
	}

	public Fish(int id, String name, String sex) {
		super(id, name);
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Fish [sex=" + sex + ", " + super.toString() + "]";
	}

}
```

> 值得研究的地方
> [精选30道Java笔试题解答](http://www.cnblogs.com/lanxuezaipiao/p/3371224.html)
> [java中String类型变量的赋值问题介绍](http://www.jb51.net/article/81393.htm)



> 一本正经地胡说八道
> [Java误点学习2_成员变量与局部变量](http://www.delin.site/post/327197_f404dc) 

[《Java核心技术：卷1—基础篇》二次阅读笔记](http://www.aemiot.com/java-reader-note.html)


[《Java核心技术：卷1—基础篇》二次阅读笔记][1]


[1]: http://www.aemiot.com/java-reader-note.html
