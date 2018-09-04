package xin.jerome.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 测试Lambda内置四大函数式接口
 *      1、Consumer<T> 消费型接口：
 *          void accept(T t);
 *      2、Supplier<T> 供给型接口：
 *          T get();
 *      3、Function<T, R> 函数型接口：
 *          R apply(T t);
 *      4、Predicate<T> 断言型接口：
 *          boolean test(T t);
 *
 * @author Jerome Zhu
 * @since 2018.08.23 09:37
 */
public class TestLambdaInlayFunction {

    @Test
    public void testConsumer() {
        consumer(3, x -> System.out.println("消费：" + x + "元"));
    }
    private void consumer(Integer i, Consumer<Integer> con) {
        con.accept(i);
    }

    @Test
    public void testSupplier() {
        List<Double> list = getList(8, () -> Math.random());
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }
    private List<Double> getList(int num, Supplier<Double> sup) {
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < num; i++)
            list.add(sup.get());
        return list;
    }

    @Test
    public void testFunction() {
        String string = strHandler("sdWQERDsdf", x -> x.toUpperCase());
        System.out.println(string);
    }
    private String strHandler(String str, Function<String, String> fun) {
        return fun.apply(str);
    }

    @Test
    public void testPredicate() {
        Predicate<Integer> p = x -> x > 5;
        System.out.println(p.test(20));
        System.out.println(p.test(2));
    }

}
