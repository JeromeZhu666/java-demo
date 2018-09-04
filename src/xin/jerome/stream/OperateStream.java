package xin.jerome.stream;

import org.junit.Test;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Stream的中间操作
 *      筛选、切片、映射、排序、查找、匹配
 * Stream的终止操作
 *      规约、收集、分组
 *
 * @author Jerome Zhu
 * @since 2018.08.23 13:50
 */
public class OperateStream {

    List<People> peoples = Arrays.asList(
            new People("aa",22,22.22,"码农"),
            new People("ss",33,33.33,"程序员"),
            new People("vv",66,66.66,"项目经理"),
            new People("bb",99,33.99,"码农"),
            new People("qq",11,11.11,"项目经理"),
            new People("qq",11,11.11,"码农"),
            new People("dd",44,44.44,"程序员")
    );
    List<String> strList = Arrays.asList("ddd","aaa","bbb","ccc","eee");

    // 对流中的元素进行过滤
    @Test
    public void testFilter() {
        // filter 接收一个断言函数式接口
        peoples.stream()
                // 中间操作不会执行任何操作
                .filter(p -> {
                    System.out.println("进行比较");
                    return p.getAge() > 30;
                })
                // 一次执行全部内容
                .forEach(System.out::println);
    }

    // 对流中的元素进行截取操作
    @Test
    public void testLimit() {
        peoples.stream()
                .limit(3)
                .forEach(System.out::println);
    }

    // 跳过流中的前n个元素，若大于流的大小返回空流
    @Test
    public void testSkip() {
        peoples.stream()
                .skip(4)
                .forEach(System.out::println);
    }

    // 去除流中的重复元素，对象必须重写HashCode和equals方法
    @Test
    public void testDistinct() {
        peoples.stream()
                .distinct()
                .forEach(System.out::println);
    }

    // 将元素转换为其他形式或者提取信息
    @Test
    public void testMap() {
        strList.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
        peoples.stream()
                .map(People::getName)
                .forEach(System.out::println);
    }

    // 接收一个函数作为参数，函数的作用是将流中的每个元素都转换成流对象；然后再由FlatMap整合成一个流
    @Test
    public void testFlatMap() {
        strList.stream()
                .flatMap(x -> {
                    List<Character> charList= new ArrayList<>();
                    char[] arr = x.toCharArray();
                    for (int i = 0; i < arr.length; i++) {
                        charList.add(arr[i]);
                    }
                    return charList.stream();
                })
                .forEach(System.out::println);
    }

    // 自然排序(comparable)
    @Test
    public void testSorted() {
        strList.stream()
                .sorted()
                .forEach(System.out::println);
    }

    // 定制排序(comparator)
    @Test
    public void testSortedByMy() {
        peoples.stream()
                .sorted((p1,p2) -> p1.getAge().compareTo(p2.getAge()))
                .forEach(System.out::println);
    }

    // 检查是否匹配所有的元素
    @Test
    public void testAllMatch() {
        boolean b = peoples.stream()
                .allMatch( p -> p.getJob().equals("程序员"));
        System.out.println(b);
    }

    // 检查是否匹配一个元素
    @Test
    public void testAnyMatch() {
        boolean b = peoples.stream()
                .anyMatch( p -> p.getJob().equals("程序员"));
        System.out.println(b);
    }

    // 检查是否没有匹配的元素
    @Test
    public void testNoneMatch() {
        boolean b = peoples.stream()
                .noneMatch(p -> p.getJob().equals("总监"));
        System.out.println(b);
    }

    // 获取第一个
    @Test
    public void testFindFirst() {
        Optional<People> op = peoples.stream()
                .findFirst();
        System.out.println(op.get());
    }

    // 获取第一个
    @Test
    public void testFindAny() {
        // 并行流：多个线程下手找
        Optional<People> op = peoples.parallelStream()
                .filter(people -> people.getJob().equals("码农"))
                .findAny();
        System.out.println(op.get());
    }

    // 返回流中对象的个数
    @Test
    public void testCount() {
        System.out.println(peoples.stream().count());
    }

    // 获取最大值
    @Test
    public void testMaxAndMin() {
        System.out.println(peoples.stream().max(((o1, o2) -> o1.getAge().compareTo(o2.getAge()))).get());
        System.out.println(peoples.stream().min(((o1, o2) -> o1.getAge().compareTo(o2.getAge()))).get());
    }



    // 规约：将流中元素反复结合起来，得到一个值
    @Test
    public void testReduce() {
        Optional<String> reduce = strList.stream().reduce((x, y) -> x + y);
        System.out.println(reduce.get());
    }

    // 收集： 将流转化成其他形式
    @Test
    public void testCollector() {
        List<String> nameList = peoples.stream().map(People::getName).collect(Collectors.toList());
        System.out.println(nameList);

        // 总数
        Long cout = peoples.stream().collect(Collectors.counting());
        // 平均数
        Double avg = peoples.stream().collect(Collectors.averagingDouble(People::getGz));
        // 总和
        Double sum = peoples.stream().collect(Collectors.summingDouble(People::getGz));
        // 最大的
        peoples.stream()
                .collect(Collectors.maxBy((p1,p2) -> p1.getGz().compareTo(p2.getGz())));

    }

    // 分组
    @Test
    public void testGroup() {
        Map<String, List<People>> map = peoples.stream()
                .collect(Collectors.groupingBy(People::getJob));
        System.out.println(map);
    }

    // 多级分组
    @Test
    public void testGroups() {
        Map<String, Map<String, List<People>>> map = peoples.stream()
                .collect(Collectors.groupingBy(People::getJob, Collectors.groupingBy((e) -> {
                        if(((People)e).getGz() < 30)
                            return "初级";
                        else if (((People)e).getGz() > 60)
                            return "高级";
                        return "中级";
                    })));
        System.out.println(map);
    }

    //分片
    @Test
    public void testPartition() {
        Map<Boolean, List<People>> map = peoples.stream()
                .collect(Collectors.partitioningBy(p -> ((People)p).getGz() > 40));
        System.out.println(map);
    }

    // 连接
    @Test
    public void testJoin() {
        String string = peoples.stream()
                .map(People::getName)
//                .collect(Collectors.joining("--"));
                .collect(Collectors.joining("--","{","}"));
        System.out.println(string);
    }
}
class People {
    private String name;
    private Integer age;
    private Double gz;
    private String job;

    public People() {
    }

    public People(String name, int age, Double gz, String job) {
        this.name = name;
        this.age = age;
        this.gz = gz;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getGz() {
        return gz;
    }

    public void setGz(Double gz) {
        this.gz = gz;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return age == people.age &&
                Double.compare(people.gz, gz) == 0 &&
                Objects.equals(name, people.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gz, job);
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gz=" + gz +
                ", job=" + job +
                '}';
    }
}
