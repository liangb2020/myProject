package pers.qxllb.common.test.comparator;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
public class ComparatorTest {

    public static void sortting1(){
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

        System.out.println("before sort :"+JSON.toJSONString(list));

        //没条件过滤情况，直接原集合多重条件排序,a>b>c
        list.sort(Comparator.comparing(Student::getBuyCnt,Comparator.reverseOrder())
                .thenComparing(Student::getFans,Comparator.reverseOrder())
                .thenComparing(Student::getComments,Comparator.reverseOrder())
        );
        System.out.println("after sort :"+JSON.toJSONString(list));

        //根据条件过滤，多重条件排序，A>B>C
        List<Student> finallyList1= list.stream().filter(t->t.isOnline()).map(t->{
            if (Objects.isNull(t.getBuyCnt())) {
                t.setBuyCnt(0L);
            }
            if(Objects.isNull(t.getFans())){
                t.setFans(0L);
            }
            return t;
        }).sorted(  //Comparator.comparing默认是升序排序，用reversed改成降序
                Comparator.comparing(Student::getBuyCnt,Comparator.reverseOrder())
                        .thenComparing(Student::getFans,Comparator.reverseOrder())
                        .thenComparing(Student::getComments,Comparator.reverseOrder()))
                .collect(Collectors.toList());

        System.out.println(JSON.toJSONString(finallyList1));
    }

    public static void sortting2(){

        Student stu1 = new Student();
        stu1.setRoleName("主持人");

        Student stu2 = new Student();
        stu2.setRoleName("主持人");
        stu2.setWealthRank(1);
        stu2.setUpdatetime(123L);

        Student stu3 = new Student();
        stu3.setRoleName("嘉宾");
        stu3.setWealthRank(1);
        stu3.setUpdatetime(300L);

        Student stu4 = new Student();
        stu4.setWealthRank(5);

        Student stu5 = new Student();
        stu5.setWealthRank(3);

        Student stu6 = new Student();

        List<Student> list = Lists.newArrayList();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);
        list.add(stu4);
        list.add(stu5);
        list.add(stu6);

        List<Student> list1 = list.stream().filter(t-> StringUtils.isNotBlank(t.getRoleName()))
                .sorted(
                Comparator.comparing(Student::getWealthRank)
                .thenComparing(Student::getUpdatetime)
        ).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(list1));

    }

    public static void main(String[] args){

        //sortting1();
        sortting2();


    }

}

@Data
class Student{

    private Long id;

    private boolean isOnline;

    private Long buyCnt;

    private Long fans;

    private Long comments;

    private String roleName;

    private Integer wealthRank=-1;

    private Long updatetime;

}
