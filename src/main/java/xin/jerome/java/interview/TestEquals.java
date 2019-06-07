package xin.jerome.java.interview;

/**
 * equals()方法相关
 *
 * @author Jerome Zhu
 * @since 2019.06.07 15:33
 */
public class TestEquals {

    public static void main(String[] args) {
//        ==和equals的区别
//        compareEquals();
//        两个hashCode()相同的对象,equals()的结果不一定true
        sameHashCodeEqualsResult();
    }

    /**
     * 两个hashCode()相同的对象,equals()的结果不一定true
     */
    private static void sameHashCodeEqualsResult() {
        String str1 = "通话";
        String str2 = "重地";
        System.out.println(String.format("str1 HashCode:%d,str2 HashCode:%d.", str1.hashCode(), str2.hashCode()));
        System.out.println(str1.equals(str2)); // false
    }

    /**
     * ==和equals的区别
     */
    private static void compareEquals() {
        String a = new String("ab"); // a 为一个对象引用
        String b = new String("ab"); // b 为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找,能在常量池中找到
        System.out.println(a == b);// false 两个对象地址不同
        System.out.println(a.equals(b));// true String类重写了equals方法,先比较地址再判断类型再比较值.
        System.out.println(aa == bb);// true 一个对象的两个引用,常量池中只存在一个"ab".
        System.out.println(aa.equals(bb));// true 值相等
        System.out.println(b == bb);// false 两个对象地址不同
        System.out.println(b.equals(bb));// true 两个对象的值相同
        System.out.println("============");
        Integer p = new Integer(1);
        Integer q = new Integer(1);
        Integer pp = p;
        int qq = 1;
        System.out.println(p == q);// false 两个对象
        System.out.println(p.equals(q));// true 先判断类型再比较值
        System.out.println(p == pp);// true 同一个对象
        System.out.println(pp == qq);// true // 因为 pp 会自动拆箱转换成int类型
        System.out.println(pp.equals(qq));// 因为 1 这个int类型的值会被自动装箱提升为Integer类型
    }
}
