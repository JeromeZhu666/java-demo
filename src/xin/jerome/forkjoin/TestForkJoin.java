package xin.jerome.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * TODO  Add Comment
 *
 * @author Jerome Zhu
 * @since 2018.08.23 17:49
 */
public class TestForkJoin {

    @Test
    public void test1(){
        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoin(0L, 1000000000L);

        long sum = pool.invoke(task);
        System.out.println(sum);

        long end = System.currentTimeMillis();

        System.out.println("耗费的时间为: " + (end - start));
    }

    @Test
    public void test2(){
        long start = System.currentTimeMillis();

        long sum = 0L;

        for (long i = 0L; i <= 1000000000L; i++) {
            sum += i;
        }

        System.out.println(sum);

        long end = System.currentTimeMillis();

        System.out.println("耗费的时间为: " + (end - start));
    }

    @Test
    public void test3(){
        long start = System.currentTimeMillis();

        Long sum = LongStream.rangeClosed(0L, 50000000000L)
                .parallel()
                .sum();

        System.out.println(sum);

        long end = System.currentTimeMillis();

        System.out.println("耗费的时间为: " + (end - start)); //2061-2053-2086-18926
    }
}
