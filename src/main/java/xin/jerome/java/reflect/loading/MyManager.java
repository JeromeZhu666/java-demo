package xin.jerome.java.reflect.loading;

/**
 * BaseManager的子类，此类需要实现java类的热加载功能
 */
public class MyManager implements BaseManager {

	@Override
	public void logic() {
		System.out.println("此类正在热部署！123");
	}

}
