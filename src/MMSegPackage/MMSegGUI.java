/**
 * 中文自动分词系统
 * 采用正向最大匹配算法(FMM)和逆向最大匹配算法(BMM)
 * 北京理工大学
 * 1120122018
 * 吴一凡
 */
package MMSegPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class MMSegGUI {
	private JFrame frame = new JFrame();
	private JButton jbDictionary = new JButton();
	private JButton jbSource = new JButton();
	private JButton jbResult = new JButton();
	private JButton jbSeg = new JButton();
	private JLabel jlDictionary = new JLabel();
	private JLabel jlSource = new JLabel();
	private JLabel jlResult = new JLabel();
	private JLabel jlOption = new JLabel();
	private JTextField jtfDictionary = new JTextField();
	private JTextField jtfSource = new JTextField();
	private JTextField jtfResult = new JTextField();
	private JRadioButton jrbFMM = new JRadioButton("FMM",true);
	private JRadioButton jrbBMM = new JRadioButton("BMM",true);
	static private ButtonGroup radioGroup = new ButtonGroup() ;
	
	//getter and setter 方法
	
	public JTextField getJtfDictionary() {
		return jtfDictionary;
	}
	public JTextField getJtfSource() {
		return jtfSource;
	}
	public JTextField getJtfResult() {
		return jtfResult;
	}
	
	public MMSegGUI(){	//构造方法建立GUI窗口
		setupFrame();
		setComponent();
	}
	
	private void setupFrame() {	//建立框架，并加入所有构件，以null方式布局（X-Y方式）
    	JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("中文自动分词程序");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        //----------------------加入构件---------------
        frame.add(jbDictionary);
        frame.add(jbSource);
        frame.add(jbResult);
        frame.add(jbSeg);
        frame.add(jlDictionary);
        frame.add(jlSource);
        frame.add(jlResult);
        frame.add(jlOption);
        frame.add(jtfDictionary);
        frame.add(jtfSource);
        frame.add(jtfResult);
        frame.add(jrbFMM);
        frame.add(jrbBMM);
    }
	private void setLabel(){	//设置标签位置和文字属性
		jlDictionary.setBounds(20,20,80,40);
		jlDictionary.setText("词典");
		jlSource.setBounds(20,110,80,40);
		jlSource.setText("源文件");
		jlResult.setBounds(20,200,80,40);
		jlResult.setText("目标文件夹");
		jlOption.setBounds(20,290,80,40);
		jlOption.setText("选择分词类型");
	}
	private void setButton(){	//设置按钮位置和文字属性，并添加监听器
		jbDictionary.setBounds(380, 70, 100, 30);
		jbDictionary.setText("选择词典");
		jbSource.setBounds(380, 160, 100, 30);
		jbSource.setText("选择文件");
		jbResult.setBounds(380, 250, 100, 30);
		jbResult.setText("选择文件夹");
		jbSeg.setBounds(350, 320, 100, 30);
		jbSeg.setText("开始分词");
		
		addActionListener();
	}
	private void addActionListener(){	//对所有的按钮Button添加监听
		jbDictionary.addActionListener( new ActionListener() {	//添加监听
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MMSegFile mmSF = new MMSegFile();
				String s = null;
				s = mmSF.getFile();
				jtfDictionary.setText(s);
			}
		});
		jbSource.addActionListener( new ActionListener() {	//添加监听
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MMSegFile mmSF = new MMSegFile();
				String s = null;
				s = mmSF.getFile();
				jtfSource.setText(s);
			}
		});
		jbResult.addActionListener( new ActionListener() {	//添加监听
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MMSegFile mmSF = new MMSegFile();
				String s = null;
				s = mmSF.getDirectory();
				jtfResult.setText(s);
			}
		});
		
		jbSeg.addActionListener( new ActionListener() {	//添加监听
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//判断词典、源文件和目标文件夹都已选择
				if(jtfDictionary.getText().isEmpty()){
					JOptionPane.showMessageDialog( null,  
							"请选择词典文件",
		                    "请选择文件/文件夹",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}else if(jtfSource.getText().isEmpty()){
					JOptionPane.showMessageDialog( null,  
							"请选择需要分词的源文件",
		                    "请选择文件/文件夹",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}else if(jtfResult.getText().isEmpty()){
					JOptionPane.showMessageDialog( null,  
							"请选择分词后文件所在的目录",
		                    "请选择文件/文件夹",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}
				
				//运行分词算法
				MMSegKernel mmSK = new MMSegKernel();
				frame.setTitle("正在分词...");
				if(jrbFMM.isSelected()){
					JOptionPane.showMessageDialog( null,  
							"分词时间由语料库和词典的大小正相关，语料库和词典越大，分词时间越长，请耐心等候",
		                    "正在分词...",
		                    JOptionPane.INFORMATION_MESSAGE );
					mmSK.FMM();
					JOptionPane.showMessageDialog( null,  
							"正向最大分词(FMM)完成\n分词后的文件保存于result.txt中",
		                    "分词完成",
		                    JOptionPane.INFORMATION_MESSAGE );
				}
				else{
					JOptionPane.showMessageDialog( null,  
							"分词时间由语料库和词典的大小正相关，语料库和词典越大，分词时间越长，请耐心等候",
		                    "正在分词...",
		                    JOptionPane.INFORMATION_MESSAGE );
					mmSK.BMM();
					JOptionPane.showMessageDialog( null,  
							"逆向最大分词(BMM)完成\n分词后的文件保存于result.txt中",
		                    "分词完成",
		                    JOptionPane.INFORMATION_MESSAGE );
				}
				frame.setTitle("分词完毕");
			}
		});
	}
	private void setRadioButton(){	//设置单选按钮，将单选按钮加入到按钮组中
		radioGroup.add(jrbFMM);
		radioGroup.add(jrbBMM);
		jrbFMM.setBounds(20, 320, 80, 40);
		jrbBMM.setBounds(120, 320, 80, 40);
	}
	private void setTextField(){	//设置文本框
		jtfDictionary.setBounds(20, 70, 350, 30);
		jtfDictionary.setEditable(false);
		jtfSource.setBounds(20, 160, 350, 30);
		jtfSource.setEditable(false);
		jtfResult.setBounds(20, 250, 350, 30);
		jtfResult.setEditable(false);
	}
	private void setComponent(){	//设置GUI控件
		setLabel();
		setButton();
		setTextField();
		setRadioButton();
	}
}
