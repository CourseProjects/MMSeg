/**
 * 中文自动分词系统
 * 采用正向最大匹配算法(FMM)和逆向最大匹配算法(BMM)
 * 北京理工大学
 * 1120122018
 * 吴一凡
 */
package MMSegPackage;

public class MMSegStart {
	private static MMSegGUI mmSG = new MMSegGUI();
	
	public static MMSegGUI getMmSG() {
		return mmSG;
	}

	public static void main(String args[]){	//启动程序，由GUI界面开始
		new MMSegStart();	
		/**创建一个匿名对象，利用构造方法创建GUI的对象，全程序只用这一个对象，
		 * 以免对象不一致导致界面内的路径无法正确传递到内核中
		 */
	}
}
