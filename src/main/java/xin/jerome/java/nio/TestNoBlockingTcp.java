package xin.jerome.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 非阻塞式IO
 *
 * @author Jerome Zhu
 * @since 2018.12.19 09:53
 */
public class TestNoBlockingTcp {

    @Test
    public void client() throws IOException {
        // 创建socket通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        // 改为非阻塞状态
        socketChannel.configureBlocking(false);
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(LocalDateTime.now().toString().getBytes());
        byteBuffer.flip();
        // 向通道中写数据
        socketChannel.write(byteBuffer);
        // 清空缓冲区
        byteBuffer.clear();
        // 关闭通道
        socketChannel.close();
    }

    @Test
    public void server() throws IOException {
        // 创建server 通道，并绑定端口号
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 改为非阻塞状态
        serverSocketChannel.configureBlocking(false);
        // 创建选择器
        Selector selector = Selector.open();
        // 注册通道到选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 轮询式XXX
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 通道的连接事件，同样注册到选择器
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 切换连接的通道是非阻塞的
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 创建缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len  = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
                // 取消选择键
                iterator.remove();
            }
        }
    }

}
