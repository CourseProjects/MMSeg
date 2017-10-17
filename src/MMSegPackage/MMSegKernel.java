/**
 * 中文自动分词系统
 * 采用正向最大匹配算法(FMM)和逆向最大匹配算法(BMM)
 * 北京理工大学
 * 1120122018
 * 吴一凡
 */
package MMSegPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;


public class MMSegKernel {	//后台内核程序
	public void FMM(){	//FMM算法实现
		MMSegGUI mmSG = MMSegStart.getMmSG();
		String sDictionary = mmSG.getJtfDictionary().getText();	//保存路径字符串
		String sSource = mmSG.getJtfSource().getText();
		String sResult = mmSG.getJtfResult().getText();
		sResult += "\\result.txt";		//目标文件名
//		System.out.println(sDictionary);
//		System.out.println(sSource);
//		System.out.println(sResult);
		
		BufferedReader reader = null;	//读入缓冲区
		BufferedWriter writer = null;	//输出缓冲区
		String readLine = null;			//保存读入的一行
		String appendLine = null;		//保存要输出（追加方式）的一行
		
		int p, m, n;		//算法中的参数
		String w = null;	//每次的词
		int maxDictNum;		//词典中最长单词的字数
		String sDicWords = null;	//保存词典中的词
		
		//预读一次词典文件，找到词典中最长单词的字数，并将词典中的词保存，减少IO操作
		maxDictNum = 0;		//初始化
		try {
			//读取时指明格式为utf-8
//			reader = Files.newBufferedReader( Paths.get(sDictionary), Charset.forName("unicode") );
			reader =new BufferedReader(new FileReader(sDictionary));
			try {
				while((readLine = reader.readLine())!=null){
//					System.out.println(readLine);
					sDicWords += ( readLine + "/" );
					if(maxDictNum < readLine.length()){
						maxDictNum = readLine.length();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog( null,  
						"文件已经读完",
	                    "文件读完",
	                    JOptionPane.INFORMATION_MESSAGE );
			}
			sDicWords = sDicWords.substring(4, sDicWords.length());//去掉开始的null
//			System.out.println("maxDictNum: " + maxDictNum);
//			System.out.println(sDicWords);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"词典文件路径错误",
                    "路径错误",
                    JOptionPane.INFORMATION_MESSAGE );
		}
		
		//-----------开始处理源文件--------------------------------------
		try {
//			reader = Files.newBufferedReader( Paths.get(sSource), Charset.forName("unicode") );	//读入源文件
			reader = new BufferedReader(new FileReader(sSource));
			try {
				int circle = 0;		//计循环次数，每次appendLine清空
//				writer = Files.newBufferedWriter( Paths.get(sResult), Charset.forName("unicode") );//打开输出文件
				writer =new BufferedWriter(new FileWriter(sResult));
				while((readLine = reader.readLine())!=null){//读取源文件中的一行
//					System.out.println("readLine: " + readLine);
					//--------------开始分词------------------------------
					//初始化，第（1）步
					p = m = n = 0;
					w = null;
					//第（2）步
					boolean flag23 = false;	//由(2)到(3)一直循环，直到flag状态改变
					circle = 0;		//初始化循环次数
					while(!flag23){
						circle++;		//循环次数加1
						n = readLine.length() - p;
						if(n > 1){
							m = n < maxDictNum ? n : maxDictNum;
							//第（3）步
							w = readLine.substring(p, p + m);//生成词w
//							System.out.println("生成词w: " + w);
							boolean flagAB = false;
							while(!flagAB){	//由(a)到(b)一直循环，直到flag状态改变
								if(sDicWords.indexOf(w) >= 0){	//是词典中的词(a)
									w += "/";	//词w后面添加一个切分标志--"/"
									flagAB = true;
								}else{	//不是词典中的词(b)
									if(w.length()>1){	//w的长度大于1
										w = w.substring(0, w.length()-1);//去掉右面一个字符
//										System.out.println("w: " + w);
									}else{			//w的长度等于1
										w += "/";	//词w后面添加一个切分标志--"/"
										flagAB = true;
									}
								}
							}
							//(c)
							p += w.length() - 1;	//由于添加了切分标志，故长度-1
							if(circle == 1){
								appendLine = w;		//去掉一开始的null
							}else{
								appendLine += w;	//将分好的词加入要写入文件的行中
							}
//							System.out.println("p = " + p);
							if(p == readLine.length()-1){
								flag23 = true;
							}
						}else{
							w = readLine.substring(p, p + n);//生成词w
							if(circle == 1){
								appendLine = w;
							}
							else{
								appendLine = new StringBuffer(appendLine).insert(0, w).toString();	//将分好的词加入要写入文件的行中
							}
							flag23 = true;
						}
					}
					
					//第（4）步,输出切分结果,结束分词程序。
//					System.out.println("appendLine: " + appendLine);
					writer.append(appendLine + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog( null,  
						"文件已经读完",
	                    "文件读完",
	                    JOptionPane.INFORMATION_MESSAGE );
			}finally{
				writer.close();	//关闭输出文件
				reader.close();	//关闭源文件
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"源文件路径错误",
                    "路径错误",
                    JOptionPane.INFORMATION_MESSAGE );
		}
	}
	public void BMM(){	//BMM算法实现
		MMSegGUI mmSG = MMSegStart.getMmSG();
		String sDictionary = mmSG.getJtfDictionary().getText();	//保存路径字符串
		String sSource = mmSG.getJtfSource().getText();
		String sResult = mmSG.getJtfResult().getText();
		sResult += "\\result.txt";		//目标文件名
//		System.out.println(sDictionary);
//		System.out.println(sSource);
//		System.out.println(sResult);
		
		BufferedReader reader = null;	//读入缓冲区
		BufferedWriter writer = null;	//输出缓冲区
		String readLine = null;			//保存读入的一行
		String appendLine = null;		//保存要输出（追加方式）的一行
		
		int p, m, n;		//算法中的参数
		String w = null;	//每次的词
		int maxDictNum;		//词典中最长单词的字数
		String sDicWords = null;	//保存词典中的词
		
		//预读一次词典文件，找到词典中最长单词的字数，并将词典中的词保存，减少IO操作
		maxDictNum = 0;		//初始化
		try {
			//读取时指明格式为utf-8
			reader =new BufferedReader(new FileReader(sDictionary));
			try {
				while((readLine = reader.readLine())!=null){
//					System.out.println(readLine);
					sDicWords += ( readLine + "/" );
					if(maxDictNum < readLine.length()){
						maxDictNum = readLine.length();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog( null,  
						"文件已经读完",
	                    "文件读完",
	                    JOptionPane.INFORMATION_MESSAGE );
			}
			sDicWords = sDicWords.substring(4, sDicWords.length());//去掉开始的null
//			System.out.println("maxDictNum: " + maxDictNum);
//			System.out.println(sDicWords);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"词典文件路径错误",
                    "路径错误",
                    JOptionPane.INFORMATION_MESSAGE );
		}
		
		//-----------开始处理源文件--------------------------------------
		try {
			reader = new BufferedReader(new FileReader(sSource));	//读入源文件
			try {
				int circle = 0;		//计循环次数，每次appendLine清空
				writer = new BufferedWriter(new FileWriter(sResult));//打开输出文件
				while((readLine = reader.readLine())!=null){//读取源文件中的一行
//					System.out.println("readLine: " + readLine);
					//--------------开始分词------------------------------
					//初始化，第（1）步
					m = n = 0;
					p = readLine.length();	//p指向字串尾
					w = null;
					//第（2）步
					boolean flag23 = false;	//由(2)到(3)一直循环，直到flag状态改变
					circle = 0;		//初始化循环次数
					while(!flag23){
						circle++;		//循环次数加1
						n = p;			//n为当前指针p到子串初始位置的字数
						if(n > 1){
							m = n < maxDictNum ? n : maxDictNum;
							//第（3）步
							w = readLine.substring(p - m, p);//生成词w
//							System.out.println("生成词w: " + w);
							boolean flagAB = false;
							while(!flagAB){	//由(a)到(b)一直循环，直到flag状态改变
								if(sDicWords.indexOf(w) >= 0){	//是词典中的词(a)
									w += "/";	//词w后面添加一个切分标志--"/"
									flagAB = true;
								}else{	//不是词典中的词(b)
									if(w.length() > 1){	//w的长度大于1
										w = w.substring(1, w.length());//去掉左面一个字符
//										System.out.println("w: " + w);
									}else{			//w的长度等于1
										w += "/";	//词w后面添加一个切分标志--"/"
										flagAB = true;
									}
								}
							}
							//(c)
							p -= w.length() - 1;	//由于添加了切分标志，故长度-1
							if(circle == 1){
								appendLine = w;		//去掉一开始的null
							}else{
								appendLine = new StringBuffer(appendLine).insert(0, w).toString();	//将分好的词加入要写入文件的行中
							}
//							System.out.println("p = " + p);
							if(p == 0){
								flag23 = true;
							}
//							System.out.println("flag23: " + flag23);
						}else{
							w = readLine.substring(p - n, p);//生成词w
							if(circle == 1){
								appendLine = w;
							}
							else{
								appendLine = new StringBuffer(appendLine).insert(0, w).toString();	//将分好的词加入要写入文件的行中
							}
							flag23 = true;
						}
					}
					
					//第（4）步,输出切分结果,结束分词程序。
//					System.out.println("appendLine: " + appendLine);
					writer.append(appendLine + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog( null,  
						"文件已经读完",
	                    "文件读完",
	                    JOptionPane.INFORMATION_MESSAGE );
			}finally{
				writer.close();	//关闭输出文件
				reader.close();	//关闭源文件
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"源文件路径错误",
                    "路径错误",
                    JOptionPane.INFORMATION_MESSAGE );
		}
	}
}
