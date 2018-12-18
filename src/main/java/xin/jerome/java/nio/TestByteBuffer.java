package xin.jerome.java.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 测试字节缓冲区
 *
 * @author Jerome Zhu
 * @since 2018.12.18 10:30
 */
public class TestByteBuffer {

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
        System.out.println(new String(bytes,0,bytes.length));

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
    public void testMark() {
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
        if(byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.remaining());
        }

    }

    @Test
    public void directByte() {
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        System.out.println(direct.isDirect());
    }

    private void printBuffer(ByteBuffer byteBuffer) {
        System.out.println(String.format("缓冲区当前位置：%d,可操作范围：%d,容量大小：%d",
                byteBuffer.position(),byteBuffer.limit(),byteBuffer.capacity()));
    }
}
