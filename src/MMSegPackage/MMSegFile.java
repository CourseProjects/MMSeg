/**
 * �����Զ��ִ�ϵͳ
 * �����������ƥ���㷨(FMM)���������ƥ���㷨(BMM)
 * ��������ѧ
 * 1120122018
 * ��һ��
 */
package MMSegPackage;

import javax.swing.JFileChooser;

public class MMSegFile {	//�ļ�������ص���
	public String getDirectory(){	//��ʾѡ���ļ��жԻ��򣬻�ȡ�ļ���·��
		JFileChooser pathChooser = new JFileChooser();
	    pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
//	    //���á����ļ��жԻ��򡱵�Ĭ��·��---------û����������Ĭ��·����ѡ��
//	    pathChooser.setCurrentDirectory(
//	    		Paths.get("F:\\").toFile()); 
	    //���öԻ������
	    pathChooser.setDialogTitle("ѡ���ļ���");
	    //����ȷ����ť��
	    pathChooser.setApproveButtonText("ȷ��");  
	    //��ʾ���ļ��Ի���
	    int result = pathChooser.showOpenDialog( null );
	    //���¡�ȡ������ť�����ؿ�
	    if ( result == JFileChooser.CANCEL_OPTION )
	         return null;

	    String pathName = pathChooser.getSelectedFile().getAbsolutePath();
	    return pathName;
	}
	public String getFile(){	//��ʾѡ���ļ��Ի��򣬻�ȡ�ļ�·��
		JFileChooser pathChooser = new JFileChooser();
	    pathChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//	    //���á����ļ��жԻ��򡱵�Ĭ��·��---------û����������Ĭ��·����ѡ��
//	    pathChooser.setCurrentDirectory(
//	    		Paths.get("F:\\").toFile()); 
	    //���öԻ������
	    pathChooser.setDialogTitle("ѡ���ļ�");
	    //����ȷ����ť��
	    pathChooser.setApproveButtonText("ȷ��");  
	    //��ʾ���ļ��Ի���
	    int result = pathChooser.showOpenDialog( null );
	    //���¡�ȡ������ť�����ؿ�
	    if ( result == JFileChooser.CANCEL_OPTION )
	         return null;

	    String pathName = pathChooser.getSelectedFile().getAbsolutePath();
	    return pathName;
	}
}
