/**
 * �����Զ��ִ�ϵͳ
 * �����������ƥ���㷨(FMM)���������ƥ���㷨(BMM)
 * ��������ѧ
 * 1120122018
 * ��һ��
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
	
	//getter and setter ����
	
	public JTextField getJtfDictionary() {
		return jtfDictionary;
	}
	public JTextField getJtfSource() {
		return jtfSource;
	}
	public JTextField getJtfResult() {
		return jtfResult;
	}
	
	public MMSegGUI(){	//���췽������GUI����
		setupFrame();
		setComponent();
	}
	
	private void setupFrame() {	//������ܣ����������й�������null��ʽ���֣�X-Y��ʽ��
    	JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setSize(500,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("�����Զ��ִʳ���");
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        //----------------------���빹��---------------
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
	private void setLabel(){	//���ñ�ǩλ�ú���������
		jlDictionary.setBounds(20,20,80,40);
		jlDictionary.setText("�ʵ�");
		jlSource.setBounds(20,110,80,40);
		jlSource.setText("Դ�ļ�");
		jlResult.setBounds(20,200,80,40);
		jlResult.setText("Ŀ���ļ���");
		jlOption.setBounds(20,290,80,40);
		jlOption.setText("ѡ��ִ�����");
	}
	private void setButton(){	//���ð�ťλ�ú��������ԣ�����Ӽ�����
		jbDictionary.setBounds(380, 70, 100, 30);
		jbDictionary.setText("ѡ��ʵ�");
		jbSource.setBounds(380, 160, 100, 30);
		jbSource.setText("ѡ���ļ�");
		jbResult.setBounds(380, 250, 100, 30);
		jbResult.setText("ѡ���ļ���");
		jbSeg.setBounds(350, 320, 100, 30);
		jbSeg.setText("��ʼ�ִ�");
		
		addActionListener();
	}
	private void addActionListener(){	//�����еİ�ťButton��Ӽ���
		jbDictionary.addActionListener( new ActionListener() {	//��Ӽ���
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MMSegFile mmSF = new MMSegFile();
				String s = null;
				s = mmSF.getFile();
				jtfDictionary.setText(s);
			}
		});
		jbSource.addActionListener( new ActionListener() {	//��Ӽ���
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MMSegFile mmSF = new MMSegFile();
				String s = null;
				s = mmSF.getFile();
				jtfSource.setText(s);
			}
		});
		jbResult.addActionListener( new ActionListener() {	//��Ӽ���
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MMSegFile mmSF = new MMSegFile();
				String s = null;
				s = mmSF.getDirectory();
				jtfResult.setText(s);
			}
		});
		
		jbSeg.addActionListener( new ActionListener() {	//��Ӽ���
	    	   
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//�жϴʵ䡢Դ�ļ���Ŀ���ļ��ж���ѡ��
				if(jtfDictionary.getText().isEmpty()){
					JOptionPane.showMessageDialog( null,  
							"��ѡ��ʵ��ļ�",
		                    "��ѡ���ļ�/�ļ���",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}else if(jtfSource.getText().isEmpty()){
					JOptionPane.showMessageDialog( null,  
							"��ѡ����Ҫ�ִʵ�Դ�ļ�",
		                    "��ѡ���ļ�/�ļ���",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}else if(jtfResult.getText().isEmpty()){
					JOptionPane.showMessageDialog( null,  
							"��ѡ��ִʺ��ļ����ڵ�Ŀ¼",
		                    "��ѡ���ļ�/�ļ���",
		                    JOptionPane.INFORMATION_MESSAGE );
					return ;
				}
				
				//���зִ��㷨
				MMSegKernel mmSK = new MMSegKernel();
				frame.setTitle("���ڷִ�...");
				if(jrbFMM.isSelected()){
					JOptionPane.showMessageDialog( null,  
							"�ִ�ʱ�������Ͽ�ʹʵ�Ĵ�С����أ����Ͽ�ʹʵ�Խ�󣬷ִ�ʱ��Խ���������ĵȺ�",
		                    "���ڷִ�...",
		                    JOptionPane.INFORMATION_MESSAGE );
					mmSK.FMM();
					JOptionPane.showMessageDialog( null,  
							"�������ִ�(FMM)���\n�ִʺ���ļ�������result.txt��",
		                    "�ִ����",
		                    JOptionPane.INFORMATION_MESSAGE );
				}
				else{
					JOptionPane.showMessageDialog( null,  
							"�ִ�ʱ�������Ͽ�ʹʵ�Ĵ�С����أ����Ͽ�ʹʵ�Խ�󣬷ִ�ʱ��Խ���������ĵȺ�",
		                    "���ڷִ�...",
		                    JOptionPane.INFORMATION_MESSAGE );
					mmSK.BMM();
					JOptionPane.showMessageDialog( null,  
							"�������ִ�(BMM)���\n�ִʺ���ļ�������result.txt��",
		                    "�ִ����",
		                    JOptionPane.INFORMATION_MESSAGE );
				}
				frame.setTitle("�ִ����");
			}
		});
	}
	private void setRadioButton(){	//���õ�ѡ��ť������ѡ��ť���뵽��ť����
		radioGroup.add(jrbFMM);
		radioGroup.add(jrbBMM);
		jrbFMM.setBounds(20, 320, 80, 40);
		jrbBMM.setBounds(120, 320, 80, 40);
	}
	private void setTextField(){	//�����ı���
		jtfDictionary.setBounds(20, 70, 350, 30);
		jtfDictionary.setEditable(false);
		jtfSource.setBounds(20, 160, 350, 30);
		jtfSource.setEditable(false);
		jtfResult.setBounds(20, 250, 350, 30);
		jtfResult.setEditable(false);
	}
	private void setComponent(){	//����GUI�ؼ�
		setLabel();
		setButton();
		setTextField();
		setRadioButton();
	}
}
