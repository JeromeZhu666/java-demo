package xin.jerome.java.java8.lambda;

import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 构造器引用：
 *      语法格式：
 *         ClassName::new;
 *      注意：必须有一个构造器的参数列表和函数式接口的参数列表一致
 *
 * @author Jerome Zhu
 * @since 2018.08.23 11:25
 */
public class StudyConstructorRef {

    @Test
    public void test1() {
        Supplier<Student> p = Student::new;
        System.out.println(p.get());
    }

    @Test
    public void test2() {
        Function<Integer,Student> s = Student::new;
        System.out.println(s.apply(12));
    }
}
class Student{
    private int id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(int id) {
        this.id = id;
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
