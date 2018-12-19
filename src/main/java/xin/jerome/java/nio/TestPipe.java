package xin.jerome.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * 管道
 *
 * @author Jerome Zhu
 * @since 2018.12.19 10:36
 */
public class TestPipe {

    @Test
    public void pipe() throws IOException {
        // 创建管道
        Pipe pipe = Pipe.open();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 向管道中发送数据
        Pipe.SinkChannel sink = pipe.sink();
        byteBuffer.put("你猜猜".getBytes());
        byteBuffer.flip();
        sink.write(byteBuffer);
        byteBuffer.clear();

        // 从管道中接收数据
        Pipe.SourceChannel source = pipe.source();
        source.read(byteBuffer);
        byteBuffer.flip();
        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
        byteBuffer.clear();
        sink.close();
        source.close();
    }

}
