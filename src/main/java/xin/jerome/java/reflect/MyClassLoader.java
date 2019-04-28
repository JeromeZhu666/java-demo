package xin.jerome.java.reflect;

import java.io.*;

/**
 * 自定义{@link ClassLoader}
 *
 * @author Jerome Zhu
 * @since 2019.04.28 17:41
 */
public class MyClassLoader extends ClassLoader{

    private String path;
    private String classLoaderName;

    public MyClassLoader(String path, String classLoaderName) {
        this.path = path;
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] data = new byte[0];
        try {
            data = loadData(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.defineClass(name,data,0,data.length);
    }

    private byte[] loadData(String name) throws Exception {
        InputStream in = new FileInputStream(new File(String.format("%s%s.class",path,name)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i = 0;
        try {
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } finally {
            in.close();
            out.close();
        }
        return out.toByteArray();
    }
}
