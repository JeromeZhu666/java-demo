package xin.jerome.java.nio.buffer;

import java.nio.ByteBuffer;

/**
 * 测试字节缓冲区
 *
 * @author Jerome Zhu
 * @since 2018.12.18 10:30
 */
public class TestByteBuffer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.capacity());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.position());

    }

}
