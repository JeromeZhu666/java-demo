package xin.jerome.lambda;

import org.junit.Test;

import java.util.*;

/**
 * lambda 初体验
 *
 * @author Jerome Zhu
 * @since 2018.08.22 15:50
 */
public class LearnLambda {

    // 原来的匿名内部类
    @Test
    public void demo1() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    // lambda表达式的写法
    @Test
    public void lambda1() {
        Comparator<Integer> comparator = (o1, o2) -> Integer.compare(o1, o2);
        TreeSet<Integer> treeSet = new TreeSet<>(comparator);
    }

    // 获取年龄大于25的人
    List<People> peoples = Arrays.asList(
            new People("qq",11,11.11),
            new People("aa",22,22.22),
            new People("ss",33,33.33),
            new People("dd",44,44.44)
    );

    @Test
    public void filterPeoples() {

        peoples.stream()
                .filter((e) -> e.getAge() < 30)
                .forEach(System.out::println);

        System.out.println("----------------------------------");

        peoples.stream()
                .map(People::getName)
                .forEach(System.out::println);
    }

}
class People {
    private String name;
    private int age;
    private double gz;

    public People() {
    }

    public People(String name, int age, double gz) {
        this.name = name;
        this.age = age;
        this.gz = gz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGz() {
        return gz;
    }

    public void setGz(double gz) {
        this.gz = gz;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gz=" + gz +
                '}';
    }
}
