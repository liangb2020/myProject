package pers.qxllb.common.test.jvm;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/5/30  15:00
 */

/**
 * JVM 在执行某个类的时候，必须经过加载、连接、初始化和执行方法;
 *  1.加载：在加载类的时候，
 *      1）JVM 会先加载 class 文件，而在 class 文件中除了有类的版本、字段、方法和接口等描述信息外，
 *      2）还有一项信息是常量池 (Constant Pool Table)，用于存放编译期间生成的各种字面量和符号引用
 *          字面量包括字符串（String a=“b”）和基本类型的常量（final 修饰的变量）
 *          符号引用则包括类和方法的全限定名（例如 String 这个类，它的全限定名就是 Java/lang/String）、字段的名称和描述符以及方法的名称和描述符
 *  2.连接：又包括验证、准备、解析三个阶段。
 *  3.初始化：编译器会在.java 文件被编译成.class 文件时，收集所有类的初始化代码，包括静态变量赋值语句、静态代码块、静态方法
 *
 *  方法区：主要是用来存放已被虚拟机加载的类相关信息，包括类信息、运行时常量池、字符串常量池。类信息又包括了类的版本、字段、方法、接口和父类等信息
 *
 *  虚拟机栈：当创建一个线程时，会在虚拟机栈中申请一个线程栈，
 *          用来保存方法的局部变量、操作数栈、动态链接方法和返回地址等信息，并参与方法的调用和返回。
 *          每一个方法的调用都伴随着栈帧的入栈操作，方法的返回则是栈帧的出栈操作
 */

/**
 * 第一步：类文件加载；class 文件加载、验证、准备以及解析，其中准备阶段会为类的静态变量分配内存，初始化为系统的初始值
 * 方法区（线程共享）：JVMCase.class 静态区方法：main() print()；同时初始化成员常量MAN_SEX_TYPE
 */
public class JVMCase {

    // 常量（存放方法区）：class类文件加载解析的阶段，就分配到常量池
    public final static String MAN_SEX_TYPE = "man";

    // 静态变量（存放方法区）:class加载后，进行初始化阶段就开始赋值（还没有创建对象）；静态变量被所有的对象所共享，在内存中只有一个副本，它当且仅当在类初次加载时会被初始化; 后续对象new时，不会再初始化
    public static String WOMAN_SEX_TYPE = "woman";

    public static void main(String[] args) throws Exception{

        //第二步，执行线程方法，通过虚拟机栈存储
        //堆内存中会创建一个 student 对象，stu通过对象引用student（堆的地址）就存放在虚拟机栈栈中
        Student stu = new Student();
        stu.setName("nick");
        stu.setSexType(MAN_SEX_TYPE);
        stu.setAge(20);

        //堆内存继续创建一个 JVMCase对象，jvmcase通过对象引用（堆的地址）就存放在虚拟机栈栈中
        JVMCase jvmcase = new JVMCase();

        //调用静态方法：虚拟机栈持续记录方法地址和入参对象的地址
        print(stu);

        //调用非静态方法：虚拟机栈持续记录方法地址和入参对象的地址
        jvmcase.sayHello(stu);

        System.in.read();

    }


    // 常规静态方法
    public static void print(Student stu) {
        System.out.println("name: " + stu.getName() + "; sex:" + stu.getSexType() + "; age:" + stu.getAge());
    }


    // 非静态方法
    public void sayHello(Student stu) {
        System.out.println(stu.getName() + "say: hello");
    }
}

/**
 * 第一步：类文件加载；class 文件加载、验证、准备以及解析，其中准备阶段会为类的静态变量分配内存，初始化为系统的初始值
 * 方法区：Student.class：成员变量字段名称及描述符和成员方法名称及描述符
 */
class Student{
    String name;
    String sexType;
    int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}