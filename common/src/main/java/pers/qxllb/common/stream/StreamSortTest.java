package pers.qxllb.common.stream;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author liangb
 * @version 1.0
 * @date 2021/6/8 16:00
 */
public class StreamSortTest {

    public static void main(String[] args){

        List<Student> list = Lists.newArrayList();

        Student student = new Student();
        student.setOnline(true);
        student.setId(100L);
        student.setBuyCnt(50L);
        student.setComments(40L);
        student.setFans(30L);
        list.add(student);

        Student student1 = new Student();
        student1.setOnline(true);
        student1.setId(101L);
        student1.setBuyCnt(40L);
        student1.setComments(31L);
        student1.setFans(36L);
        list.add(student1);

        Student student2 = new Student();
        student2.setOnline(true);
        student2.setId(102L);
        student2.setBuyCnt(40L);
        student2.setComments(30L);
        student2.setFans(36L);
        list.add(student2);

        Student student3 = new Student();
        student3.setOnline(true);
        student3.setId(103L);
        student3.setBuyCnt(100L);
        student3.setComments(30L);
        student3.setFans(20L);
        list.add(student3);

       System.out.println(JSON.toJSONString(list));

       List<Student> finallyList= list.stream().filter(t->t.isOnline()).sorted(Comparator.comparingLong(Student::getBuyCnt).reversed()
                            .thenComparingLong(Student::getFans).reversed()).collect(Collectors.toList());

        List<Student> finallyList1= list.stream().filter(t->t.isOnline()).map(t->{
            if (Objects.isNull(t.getBuyCnt())) {
                t.setBuyCnt(0L);
            }
            if(Objects.isNull(t.getFans())){
                t.setFans(0L);
            }
            return t;
        }).sorted(Comparator.comparing(Student::getBuyCnt,Comparator.reverseOrder())
                .thenComparing(Student::getFans,Comparator.reverseOrder())
                .thenComparing(Student::getComments,Comparator.reverseOrder()))
                .collect(Collectors.toList());

       System.out.println(JSON.toJSONString(finallyList1));

    }

}

@Data
class Student{

    private Long id;

    private boolean isOnline;

    private Long buyCnt;

    private Long fans;

    private Long comments;

}
