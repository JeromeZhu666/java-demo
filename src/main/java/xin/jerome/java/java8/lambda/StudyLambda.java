package xin.jerome.java.java8.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * 学习lambda：
 *      Java8 中新引入了 "->" 操作符
 *      左侧：Lambda表达式的参数列表
 *      右侧：Lambda表达式的需要执行的功能
 *  函数式接口：接口中只有一个抽象方法，称为函数式接口。可以使用@FuncationalInterface注解来进行检查是否是函数式接口
 *
 * @author Jerome Zhu
 * @since 2018.08.22 17:38
 */
public class StudyLambda {

    /**
     * 没有参数，没有返回值
     */
    @Test
    public void test1() {
        Runnable r = () -> System.out.println("qq");
    }

    /**
     * 有一个参数，无返回值
     */
    @Test
    public void test2() {
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("hi");
    }

    /**
     * 有一个参数，无返回值,小括号可以不写
     */
    @Test
    public void test3() {
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("hi");
    }

    /**
     * 有两个参数，有多条语句，并且有返回值
     */
    @Test
    public void test4() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("Lambda 多条语句");
            return Integer.compare(x, y);
        };
    }

    /**
     * 有两个参数，有一条语句，并且有返回值，{}和return都可以不写
     */
    @Test
    public void test5() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("Lambda 多条语句");
            return Integer.compare(x, y);
        };
    }

    /**
     * 参数列表类型可以不写，Jvm可以根据上下文推断出来(类型推断)，若要写必须全写
     */
    @Test
    public void test6() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("Lambda 多条语句");
            return Integer.compare(x, y);
        };
    }

    /**
     * 测试自定义的函数式接口
     */
    @Test
    public void test7() {
        MyFun myFun = x -> x + 3;
        System.out.println(myFun.getValue(123));
    }

}
