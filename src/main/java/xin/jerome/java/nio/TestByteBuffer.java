package xin.jerome.java.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 操作字节缓冲区
 *
 * @author Jerome Zhu
 * @since 2018.12.18 10:30
 */
public class TestByteBuffer {

    /**
     * 测试ByteBuffer的创建和获取capacity
     */
    @Test
    public void testCapacity() {
        byte[] bytes = new byte[]{1, 2, 3, 4};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("Buffer [capacity：%d]", byteBuffer.capacity()));
    }

    /**
     * 测试ByteBuffer的获取limit和设置limit
     */
    @Test
    public void testLimit() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d]", byteBuffer.capacity(),
                byteBuffer.limit()));
        byteBuffer.limit(4);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d]", byteBuffer.capacity(),
                byteBuffer.limit()));
    }

    /**
     * 测试ByteBuffer的获取position和设置position,并获取剩余可操作大小
     */
    @Test
    public void testPosition() {
        byte[] bytes = new byte[]{1, 2, 3, 4};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.position(3);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        System.out.println(String.format("Remaining: %d", byteBuffer.remaining()));
    }

    /**
     * 测试ByteBuffer的mark以及位置的重置
     */
    @Test
    public void testMark() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.position(2);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.mark(); // 在位置 2 设置mark
        byteBuffer.position(5); // 重置位置 为 5
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.reset(); // 位置重置
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
    }

    /**
     * 测试将缓冲区的状态还原到初始状态
     */
    @Test
    public void testClear() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.limit(5);
        byteBuffer.position(3);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        // 还原缓冲区到初始状态
        byteBuffer.clear();
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
    }

    /**
     * 测试将缓冲区的翻转，读数据的最佳时机
     */
    @Test
    public void testFlip() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.position(5);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.flip();
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
    }

    /**
     * 测试将缓冲区的倒带，丢弃标记并重置position为0
     */
    @Test
    public void testRewind() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.position(3);
        byteBuffer.limit(5);
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
        byteBuffer.rewind();
        System.out.println(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                byteBuffer.capacity(), byteBuffer.limit(), byteBuffer.position()));
    }

    /**
     * 测试其它的操作
     */
    @Test
    public void testOthers() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 5, 6, 7};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        // 判断是否为只读缓冲区
        System.out.println("byteBuffer.isReadOnly : " + byteBuffer.isReadOnly());
        // 判断是否为直接缓冲区
        System.out.println("byteBuffer.isDirect : " + byteBuffer.isDirect());
        // 判断是否有可访问实现数组
        System.out.println("byteBuffer.hasArray : " + byteBuffer.hasArray());
        // 判断当前位置到limit是否还有剩余
        System.out.println("byteBuffer.hasRemaining : " + byteBuffer.hasRemaining());
    }

    /**
     * 测试分配空间
     */
    @Test
    public void testAllocate() {
        // 创建堆缓冲区
        ByteBuffer heapByteBuffer = ByteBuffer.allocate(666);
        System.out.print(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                heapByteBuffer.capacity(), heapByteBuffer.limit(), heapByteBuffer.position()));
        System.out.println(" heapByteBuffer.isDirect : " + heapByteBuffer.isDirect());
        // 创建直接缓冲区
        ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(555);
        System.out.print(String.format("Buffer [capacity：%d, limit：%d, position: %d]",
                heapByteBuffer.capacity(), heapByteBuffer.limit(), heapByteBuffer.position()));
        System.out.println(" directByteBuffer.isDirect : " + directByteBuffer.isDirect());
    }


    /**
     * 演示  position<=limit<=capacity
     * capacity : 容量，声明的缓冲区的容量，一旦声明不能改变
     * limit : 界限，缓冲区中可以操作的范围
     * position : 位置，表示缓冲区中正在操作的位置
     */
    @Test
    public void testByteBuffer() {
        // 初始化缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        printBuffer(byteBuffer);

        // put  向缓冲区中写数据
        byteBuffer.putChar('q');
        printBuffer(byteBuffer);

        byteBuffer.put("sdfa".getBytes());
        printBuffer(byteBuffer);

        // flip  切换到读的模式
        byteBuffer.flip();
        printBuffer(byteBuffer);

        // get 读取数据
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        printBuffer(byteBuffer);
        System.out.println(new String(bytes, 0, bytes.length));

        //  rewind 倒带  filp后的状态
        byteBuffer.rewind();
        printBuffer(byteBuffer);

        // clear 清空缓冲区 只是将position=0，其实数据还是在的
        byteBuffer.clear();
        printBuffer(byteBuffer);
    }

    /**
     * mark : 标记，用于记录当前position 的位置。可以通过reset(),恢复到mark的位置。
     */
    @Test
    public void testMark1() {
        String str = "shgasdgf";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        printBuffer(byteBuffer);

        byteBuffer.flip();

        byte[] bytes = new byte[10];
        byteBuffer.get(bytes, 0, 2);
        System.out.println(new String(bytes, 0, 2));
        printBuffer(byteBuffer);

        byteBuffer.mark();
        byteBuffer.get(bytes, 2, 2);
        System.out.println(new String(bytes, 2, 2));
        printBuffer(byteBuffer);

        byteBuffer.reset();
        printBuffer(byteBuffer);

        // 判断缓冲区中是否还有可操作的数据
        if (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.remaining());
        }

    }

    @Test
    public void directByte() {
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        System.out.println(direct.isDirect());
    }

    private void printBuffer(ByteBuffer byteBuffer) {
        System.out.println(String.format("Buffer [position：%d,limit：%d,capacity：%d]",
                byteBuffer.position(), byteBuffer.limit(), byteBuffer.capacity()));
    }
}
