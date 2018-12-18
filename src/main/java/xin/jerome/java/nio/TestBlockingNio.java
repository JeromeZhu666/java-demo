package xin.jerome.java.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 *
 *
 * @author Jerome Zhu
 * @since 2018.12.18 16:50
 */
public class TestBlockingNio {

    // 客户端
    @Test
    public void client() {
        SocketChannel socketChannel = null;
        try {
            // 获取通道
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));

            // 缓冲区
            CharBuffer charBuffer = CharBuffer.allocate(1024);
            // 放入字符并编码
            charBuffer.put("你好");
            charBuffer.flip();
            Charset charset = Charset.forName("UTF-8");
            ByteBuffer byteBuffer = charset.encode(charBuffer);
            // 向通道中写
            int write = socketChannel.write(byteBuffer);
            System.out.println("写入了" + write);
            byteBuffer.clear();


            ByteBuffer resultByteBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(resultByteBuffer) != -1) {
                resultByteBuffer.flip();
                CharBuffer resultCharBuffer = charset.decode(resultByteBuffer);
                System.out.println("Server:" + resultCharBuffer);
                resultByteBuffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 服务端
    @Test
    public void server() {
        ServerSocketChannel serverChannel = null;
        SocketChannel socketChannel = null;
        try {
            // 创建服务端通道
            serverChannel = ServerSocketChannel.open();
            // 绑定端口号
            serverChannel.bind(new InetSocketAddress(8080));
            // 监听端口
            socketChannel = serverChannel.accept();
            // 创建缓冲区，读取信息
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();

            Charset charset = Charset.forName("UTF-8");
            CharBuffer charBuffer = charset.decode(byteBuffer);
            System.out.println("client:" + charBuffer);

            CharBuffer responseCharBuffer = CharBuffer.allocate(1024);
            responseCharBuffer.put("收到消息-" + charBuffer);
            responseCharBuffer.flip();
            ByteBuffer responseByteBuffer = charset.encode(responseCharBuffer);
            socketChannel.write(responseByteBuffer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
                serverChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
