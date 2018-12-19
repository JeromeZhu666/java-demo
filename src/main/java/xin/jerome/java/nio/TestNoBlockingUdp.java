package xin.jerome.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 非阻塞式IO Udp
 *
 * @author Jerome Zhu
 * @since 2018.12.19 10:20
 */
public class TestNoBlockingUdp {

    @Test
    public void send() throws IOException {
        // 创建通道,设置非阻塞
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(LocalDateTime.now().toString().getBytes());
        byteBuffer.flip();
        // 发送数据包
        datagramChannel.send(byteBuffer, new InetSocketAddress("127.0.0.1", 8080));
        byteBuffer.clear();

        datagramChannel.close();
    }

    @Test
    public void receive() throws IOException {
        // 创建通道，设置非阻塞，绑定端口号
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(8080));
        // 创建选择器
        Selector selector = Selector.open();
        // 注册通道到选择器
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    // 创建缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    // 接收数据bao
                    datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                    byteBuffer.clear();
                }
            }
            iterator.remove();
        }
        datagramChannel.close();
    }
}
