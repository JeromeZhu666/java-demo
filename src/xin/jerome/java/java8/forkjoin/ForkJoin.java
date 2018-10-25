package xin.jerome.java.java8.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 窃取工作模式
 *
 * @author Jerome Zhu
 * @since 2018.08.23 17:27
 */
public class ForkJoin extends RecursiveTask<Long> {

    private static final long serialVersionUID = 13475679780L;

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L; //临界值

    public ForkJoin(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if(length <= THRESHOLD){
            long sum = 0;

            for (long i = start; i <= end; i++) {
                sum += i;
            }

            return sum;
        }else{
            long middle = (start + end) / 2;

            ForkJoin left = new ForkJoin(start, middle);
            left.fork(); //拆分，并将该子任务压入线程队列

            ForkJoin right = new ForkJoin(middle+1, end);
            right.fork();

            return left.join() + right.join();
        }

    }
}
