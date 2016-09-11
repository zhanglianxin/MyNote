# JavaNote

tags : JavaSE

---

0. 多态存在的必要条件：继承； 重写； 父类引用指向子类对象。
1. 基本数据类型在内存中存储的是数据值本身，引用数据类型在内存中存储的是指向该数据的地址，而不是数据本身。
2. 实例关系和继承关系是对象之间的静态关系。（【扩展】对象之间的关系：依赖关系、关联关系、聚合关系、组合关系、继承关系）
3. 浮点数之间不能作双等比较，因为浮点数在表达上有难以避免的微小误差，精确的相等比较无法达到，所以这类比较没有意义。
4. 数组一种引用型变量，每个元素是引用型变量的成员变量。
5. 各种代码块的执行顺序： 静态代码块，构造代码块，构造方法。静态代码块只在类加载时执行一次，构造代码块在每次创建对象时都会执行。
6. Java覆盖： 父类中静态方法和最终方法不能被覆盖，子类方法的权限要大于等于父类。
7. Java抽象类： 抽象方法声明，static, final, private 和abstract不能同时使用。（静态方法不能被重写）
8. Java接口： 抽象类数据成员可以不赋初值，接口的数据成员必须初始化。（`[public] [static] [final] 数据类型 成员变量名 = 常量;`）
   实现类的方法必须显式地使用public修饰符，因为接口中抽象方法的访问修饰符都已指定为public
   接口之间可以多继承
9. 局部内部类里面的方法 访问 局部内部类所在方法里的局部变量，该变量必须 final 修饰，为什么？
   	生命周期问题： 内部类所在方法的生命周期，没有内部类对象的生命周期长。
   			out 方法运行完毕出栈，变量 x 就会死亡，
   			使用 Inner 类的对象 调用 in 方法，造成变量不能访问

   *final 修饰变量 x，和内存的生命周期没有关系
   final 本身含义是固定变量值，终身不变
   javac编译时，直接将内部类方法的变量x替换成常量值1*

   ```java
   public class TestInnerClass {
   	public static void main(String[] args) {
   		Outer outer = new Outer();
   		Interface interface1 = outer.out();
   		interface1.in();
   	}
   }

   interface Interface {
   	public abstract void in();
   }

   class Outer {
   	public Interface out() {
   		final int x = 1;
   		
   		/*
   		// inner class
   		class Inner implements Interface {
   			public void in() {
   		    	// Cannot refer to the non-final local variablex
                    // defined in an enclosing scope
   				System.out.println("local inner class method--in() " + x);
   			}
   		}
   		
   		return new Inner(); */
   		
   		// anonymous inner class
   		return new Interface(){
   		    public void in() {
   		        System.out.println("local inner class method--in() " + x);
   		    }
   		};
   	}
   }
   ```
10. ​
