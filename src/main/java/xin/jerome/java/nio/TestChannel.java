package xin.jerome.java.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 测试通道
 *  Channel 本身不存储数据，配合缓冲区进行传输数据
 *
 * @author Jerome Zhu
 * @since 2018.12.18 14:33
 */
public class TestChannel {

    /**
     * 文件的复制（非直接缓冲区）
     */
    @Test
    public void testCopyFile() {

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        FileChannel inputStreamChannel = null;
        FileChannel outputStreamChannel = null;
        try {
            String inFilePath = "C:\\Users\\user\\Pictures\\Saved Pictures\\th2.jpg";
            String outFilePath = "C:\\Users\\user\\Pictures\\Saved Pictures\\2.jpg";
            fileInputStream = new FileInputStream(inFilePath);
            fileOutputStream = new FileOutputStream(outFilePath);
            // 获取通道
            inputStreamChannel = fileInputStream.getChannel();
            outputStreamChannel = fileOutputStream.getChannel();
            // 分配缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            while (inputStreamChannel.read(allocate) != -1) {
                allocate.flip();
                outputStreamChannel.write(allocate);
                allocate.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStreamChannel != null) {
                try {
                    inputStreamChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamChannel != null) {
                try {
                    outputStreamChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 直接缓冲区，使用内存映射的关系
     */
    @Test
    public void testCopyFile2() throws IOException {
        String inputFile = "C:\\Users\\user\\Pictures\\Saved Pictures\\th2.jpg";
        String outputFile = "C:\\Users\\user\\Pictures\\Saved Pictures\\3.jpg";
        FileChannel readFileChannel = FileChannel.open(Paths.get(inputFile), StandardOpenOption.READ);
        FileChannel writeFileChannel = FileChannel.open(Paths.get(outputFile), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        MappedByteBuffer inMappedByteBuffer = readFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, readFileChannel.size());
        MappedByteBuffer outMappedByteBuffer = writeFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, readFileChannel.size());

        byte[]  bytes = new byte[inMappedByteBuffer.limit()];
        inMappedByteBuffer.get(bytes);
        outMappedByteBuffer.put(bytes);

        readFileChannel.close();
        writeFileChannel.close();

    }

    /**
     * 通道之间的数据传输(直接缓冲区的方式)
     */
    @Test
    public void testTransfer() throws IOException {
        String inputFile = "C:\\Users\\user\\Pictures\\Saved Pictures\\th2.jpg";
        String outputFile = "C:\\Users\\user\\Pictures\\Saved Pictures\\3.jpg";
        FileChannel readFileChannel = FileChannel.open(Paths.get(inputFile), StandardOpenOption.READ);
        FileChannel writeFileChannel = FileChannel.open(Paths.get(outputFile), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

//        readFileChannel.transferTo(0, readFileChannel.size(), writeFileChannel);
        writeFileChannel.transferFrom(readFileChannel, 0, readFileChannel.size());

        readFileChannel.close();
        writeFileChannel.close();
    }

    /**
     * 分散读取、聚合写入
     * @throws IOException
     */
    @Test
    public void testScatterRead() throws IOException {
        // 获取通道
        RandomAccessFile inFile = new RandomAccessFile("C:\\Users\\user\\Desktop\\project\\cstrq-asm\\服务器.txt","rw");
        FileChannel inFileChannel = inFile.getChannel();

        // 缓冲区对象
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(10);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] byteBuffers = {byteBuffer1, byteBuffer2};

        // 分散读取
        inFileChannel.read(byteBuffers);
        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
        }
        System.out.println(new String(byteBuffer1.array(), 0 , byteBuffer1.limit()));
        System.out.println(new String(byteBuffer2.array(), 0 , byteBuffer2.limit()));

        // 聚合写
        RandomAccessFile outFile = new RandomAccessFile("C:\\Users\\user\\Desktop\\project\\cstrq-asm\\1.txt","rw");
        FileChannel outFileChannel = outFile.getChannel();

        outFileChannel.write(byteBuffers);

    }

    /**
     * 字符集的编码和解码
     */
    @Test
    public void testCharset() throws CharacterCodingException {
        // 查看支持的字符集
        /*SortedMap<String, Charset> charsets = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = charsets.entrySet();
        for (Map.Entry<String, Charset> entry : entries) {
            System.out.println(entry.getKey() +"=" + entry.getValue());
        }*/

        Charset charset = Charset.forName("GBK");

        // 获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();

        // 获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("你猜我猜不猜！");
        charBuffer.flip();

        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);
        for (int i = 0; i < byteBuffer.limit(); i++) {
            System.out.println(byteBuffer.get());
        }

        byteBuffer.flip();
        CharBuffer decode = charsetDecoder.decode(byteBuffer);
        System.out.println(decode.toString());


    }
}
