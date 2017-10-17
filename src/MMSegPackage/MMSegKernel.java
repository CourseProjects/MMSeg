/**
 * �����Զ��ִ�ϵͳ
 * �����������ƥ���㷨(FMM)���������ƥ���㷨(BMM)
 * ��������ѧ
 * 1120122018
 * ��һ��
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


public class MMSegKernel {	//��̨�ں˳���
	public void FMM(){	//FMM�㷨ʵ��
		MMSegGUI mmSG = MMSegStart.getMmSG();
		String sDictionary = mmSG.getJtfDictionary().getText();	//����·���ַ���
		String sSource = mmSG.getJtfSource().getText();
		String sResult = mmSG.getJtfResult().getText();
		sResult += "\\result.txt";		//Ŀ���ļ���
//		System.out.println(sDictionary);
//		System.out.println(sSource);
//		System.out.println(sResult);
		
		BufferedReader reader = null;	//���뻺����
		BufferedWriter writer = null;	//���������
		String readLine = null;			//��������һ��
		String appendLine = null;		//����Ҫ�����׷�ӷ�ʽ����һ��
		
		int p, m, n;		//�㷨�еĲ���
		String w = null;	//ÿ�εĴ�
		int maxDictNum;		//�ʵ�������ʵ�����
		String sDicWords = null;	//����ʵ��еĴ�
		
		//Ԥ��һ�δʵ��ļ����ҵ��ʵ�������ʵ������������ʵ��еĴʱ��棬����IO����
		maxDictNum = 0;		//��ʼ��
		try {
			//��ȡʱָ����ʽΪutf-8
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
						"�ļ��Ѿ�����",
	                    "�ļ�����",
	                    JOptionPane.INFORMATION_MESSAGE );
			}
			sDicWords = sDicWords.substring(4, sDicWords.length());//ȥ����ʼ��null
//			System.out.println("maxDictNum: " + maxDictNum);
//			System.out.println(sDicWords);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"�ʵ��ļ�·������",
                    "·������",
                    JOptionPane.INFORMATION_MESSAGE );
		}
		
		//-----------��ʼ����Դ�ļ�--------------------------------------
		try {
//			reader = Files.newBufferedReader( Paths.get(sSource), Charset.forName("unicode") );	//����Դ�ļ�
			reader = new BufferedReader(new FileReader(sSource));
			try {
				int circle = 0;		//��ѭ��������ÿ��appendLine���
//				writer = Files.newBufferedWriter( Paths.get(sResult), Charset.forName("unicode") );//������ļ�
				writer =new BufferedWriter(new FileWriter(sResult));
				while((readLine = reader.readLine())!=null){//��ȡԴ�ļ��е�һ��
//					System.out.println("readLine: " + readLine);
					//--------------��ʼ�ִ�------------------------------
					//��ʼ�����ڣ�1����
					p = m = n = 0;
					w = null;
					//�ڣ�2����
					boolean flag23 = false;	//��(2)��(3)һֱѭ����ֱ��flag״̬�ı�
					circle = 0;		//��ʼ��ѭ������
					while(!flag23){
						circle++;		//ѭ��������1
						n = readLine.length() - p;
						if(n > 1){
							m = n < maxDictNum ? n : maxDictNum;
							//�ڣ�3����
							w = readLine.substring(p, p + m);//���ɴ�w
//							System.out.println("���ɴ�w: " + w);
							boolean flagAB = false;
							while(!flagAB){	//��(a)��(b)һֱѭ����ֱ��flag״̬�ı�
								if(sDicWords.indexOf(w) >= 0){	//�Ǵʵ��еĴ�(a)
									w += "/";	//��w�������һ���зֱ�־--"/"
									flagAB = true;
								}else{	//���Ǵʵ��еĴ�(b)
									if(w.length()>1){	//w�ĳ��ȴ���1
										w = w.substring(0, w.length()-1);//ȥ������һ���ַ�
//										System.out.println("w: " + w);
									}else{			//w�ĳ��ȵ���1
										w += "/";	//��w�������һ���зֱ�־--"/"
										flagAB = true;
									}
								}
							}
							//(c)
							p += w.length() - 1;	//����������зֱ�־���ʳ���-1
							if(circle == 1){
								appendLine = w;		//ȥ��һ��ʼ��null
							}else{
								appendLine += w;	//���ֺõĴʼ���Ҫд���ļ�������
							}
//							System.out.println("p = " + p);
							if(p == readLine.length()-1){
								flag23 = true;
							}
						}else{
							w = readLine.substring(p, p + n);//���ɴ�w
							if(circle == 1){
								appendLine = w;
							}
							else{
								appendLine = new StringBuffer(appendLine).insert(0, w).toString();	//���ֺõĴʼ���Ҫд���ļ�������
							}
							flag23 = true;
						}
					}
					
					//�ڣ�4����,����зֽ��,�����ִʳ���
//					System.out.println("appendLine: " + appendLine);
					writer.append(appendLine + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog( null,  
						"�ļ��Ѿ�����",
	                    "�ļ�����",
	                    JOptionPane.INFORMATION_MESSAGE );
			}finally{
				writer.close();	//�ر�����ļ�
				reader.close();	//�ر�Դ�ļ�
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"Դ�ļ�·������",
                    "·������",
                    JOptionPane.INFORMATION_MESSAGE );
		}
	}
	public void BMM(){	//BMM�㷨ʵ��
		MMSegGUI mmSG = MMSegStart.getMmSG();
		String sDictionary = mmSG.getJtfDictionary().getText();	//����·���ַ���
		String sSource = mmSG.getJtfSource().getText();
		String sResult = mmSG.getJtfResult().getText();
		sResult += "\\result.txt";		//Ŀ���ļ���
//		System.out.println(sDictionary);
//		System.out.println(sSource);
//		System.out.println(sResult);
		
		BufferedReader reader = null;	//���뻺����
		BufferedWriter writer = null;	//���������
		String readLine = null;			//��������һ��
		String appendLine = null;		//����Ҫ�����׷�ӷ�ʽ����һ��
		
		int p, m, n;		//�㷨�еĲ���
		String w = null;	//ÿ�εĴ�
		int maxDictNum;		//�ʵ�������ʵ�����
		String sDicWords = null;	//����ʵ��еĴ�
		
		//Ԥ��һ�δʵ��ļ����ҵ��ʵ�������ʵ������������ʵ��еĴʱ��棬����IO����
		maxDictNum = 0;		//��ʼ��
		try {
			//��ȡʱָ����ʽΪutf-8
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
						"�ļ��Ѿ�����",
	                    "�ļ�����",
	                    JOptionPane.INFORMATION_MESSAGE );
			}
			sDicWords = sDicWords.substring(4, sDicWords.length());//ȥ����ʼ��null
//			System.out.println("maxDictNum: " + maxDictNum);
//			System.out.println(sDicWords);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"�ʵ��ļ�·������",
                    "·������",
                    JOptionPane.INFORMATION_MESSAGE );
		}
		
		//-----------��ʼ����Դ�ļ�--------------------------------------
		try {
			reader = new BufferedReader(new FileReader(sSource));	//����Դ�ļ�
			try {
				int circle = 0;		//��ѭ��������ÿ��appendLine���
				writer = new BufferedWriter(new FileWriter(sResult));//������ļ�
				while((readLine = reader.readLine())!=null){//��ȡԴ�ļ��е�һ��
//					System.out.println("readLine: " + readLine);
					//--------------��ʼ�ִ�------------------------------
					//��ʼ�����ڣ�1����
					m = n = 0;
					p = readLine.length();	//pָ���ִ�β
					w = null;
					//�ڣ�2����
					boolean flag23 = false;	//��(2)��(3)һֱѭ����ֱ��flag״̬�ı�
					circle = 0;		//��ʼ��ѭ������
					while(!flag23){
						circle++;		//ѭ��������1
						n = p;			//nΪ��ǰָ��p���Ӵ���ʼλ�õ�����
						if(n > 1){
							m = n < maxDictNum ? n : maxDictNum;
							//�ڣ�3����
							w = readLine.substring(p - m, p);//���ɴ�w
//							System.out.println("���ɴ�w: " + w);
							boolean flagAB = false;
							while(!flagAB){	//��(a)��(b)һֱѭ����ֱ��flag״̬�ı�
								if(sDicWords.indexOf(w) >= 0){	//�Ǵʵ��еĴ�(a)
									w += "/";	//��w�������һ���зֱ�־--"/"
									flagAB = true;
								}else{	//���Ǵʵ��еĴ�(b)
									if(w.length() > 1){	//w�ĳ��ȴ���1
										w = w.substring(1, w.length());//ȥ������һ���ַ�
//										System.out.println("w: " + w);
									}else{			//w�ĳ��ȵ���1
										w += "/";	//��w�������һ���зֱ�־--"/"
										flagAB = true;
									}
								}
							}
							//(c)
							p -= w.length() - 1;	//����������зֱ�־���ʳ���-1
							if(circle == 1){
								appendLine = w;		//ȥ��һ��ʼ��null
							}else{
								appendLine = new StringBuffer(appendLine).insert(0, w).toString();	//���ֺõĴʼ���Ҫд���ļ�������
							}
//							System.out.println("p = " + p);
							if(p == 0){
								flag23 = true;
							}
//							System.out.println("flag23: " + flag23);
						}else{
							w = readLine.substring(p - n, p);//���ɴ�w
							if(circle == 1){
								appendLine = w;
							}
							else{
								appendLine = new StringBuffer(appendLine).insert(0, w).toString();	//���ֺõĴʼ���Ҫд���ļ�������
							}
							flag23 = true;
						}
					}
					
					//�ڣ�4����,����зֽ��,�����ִʳ���
//					System.out.println("appendLine: " + appendLine);
					writer.append(appendLine + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog( null,  
						"�ļ��Ѿ�����",
	                    "�ļ�����",
	                    JOptionPane.INFORMATION_MESSAGE );
			}finally{
				writer.close();	//�ر�����ļ�
				reader.close();	//�ر�Դ�ļ�
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog( null,  
					"Դ�ļ�·������",
                    "·������",
                    JOptionPane.INFORMATION_MESSAGE );
		}
	}
}
