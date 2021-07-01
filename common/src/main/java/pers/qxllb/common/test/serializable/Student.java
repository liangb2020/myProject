package pers.qxllb.common.test.serializable;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.*;

/**
 * 类描述:
 *
 * @author liangb
 * @date 2021/7/1  0:38
 */
@Data
public class Student implements Serializable {

    private Integer age;
    private String name;

    /**
     * 如果某个序列化类的成员变量是对象类型，则该对象类型的类必须实现序列化
     */
    private Teacher teacher;

    /**
     * 静态（static）成员变量是属于类级别的，而序列化是针对对象的，所以不能序列化
     */
    public static String gender = "男";

    /**
     * 它可以阻止修饰的字段被序列化到文件中，在被反序列化后，
     * transient 字段的值被设为初始值，比如int型的值会被设置为 0，对象型初始值会被设置为 null。
     */
    transient  String specialty = "计算机专业";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Student student = new Student();
        student.setAge(25);
        student.setName("jayWei");
        /**
         * Exception in thread "main" java.io.NotSerializableException: pers.qxllb.common.test.serializable.Teacher
         */
        student.setTeacher(new Teacher());
        System.out.println("序列化前："+ JSON.toJSONString(student));

        ObjectOutputStream objectOutputStream = new ObjectOutputStream( new FileOutputStream("D:\\text.out"));
        //对 Student 对象实现序列化
        objectOutputStream.writeObject(student);
        objectOutputStream.flush();
        objectOutputStream.close();

        Student.gender="女";

        //反序列化
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("D:\\text.out"));
        Student student1 = (Student) objectInputStream.readObject();
        System.out.println("反序列化后："+JSON.toJSONString(student1));

    }
}
