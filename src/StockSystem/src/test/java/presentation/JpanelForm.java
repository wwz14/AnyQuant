package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JpanelForm {

	/**
	 * @param args
	 */

	JFrame frame = new JFrame("��̬panel");

	final JPanel p1 = new JPanel();// �������1

	final JPanel p2 = new JPanel();// �������2

	final JPanel p3 = new JPanel();// �������3

	JPanel contentPanel = new JPanel();// �����

	JPanel topPanel = new JPanel();// �����

	final JPanel centerPanel = new JPanel();// �м����

	@SuppressWarnings("deprecation")
	public JpanelForm() {

		frame.setSize(600, 450);
		contentPanel.setBackground(Color.GRAY);
		frame.setContentPane(contentPanel);
		contentPanel.setLayout(new BorderLayout());

		topPanel.setBackground(Color.yellow);
		topPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), 50));

		centerPanel.setBackground(Color.WHITE);
		centerPanel.setLayout(null);

		p1.setBackground(Color.BLUE);
		p2.setBackground(Color.GREEN);
		p3.setBackground(Color.RED);
		p1.add(new JLabel("===============panel1======="));
		p2.add(new JLabel("===============panel2======="));
		p3.add(new JLabel("===============panel3======="));

		JButton jb1 = new JButton("panel1");
		jb1.addActionListener(new java.awt.event.ActionListener() {// jb1�¼�

			public void actionPerformed(java.awt.event.ActionEvent e) {
				xiaoGuo(p1);

			}

		});
		JButton jb2 = new JButton("panel2");
		jb2.addActionListener(new java.awt.event.ActionListener() {// jb1�¼�

			public void actionPerformed(java.awt.event.ActionEvent e) {
				xiaoGuo(p2);
			}

		});
		JButton jb3 = new JButton("panel3");
		jb3.addActionListener(new java.awt.event.ActionListener() {// jb1�¼�

			public void actionPerformed(java.awt.event.ActionEvent e) {
				xiaoGuo(p3);
				System.out.println("panel3========================/n");
			}

		});

		JButton jb4 = new JButton("����");
		jb4.addActionListener(new java.awt.event.ActionListener() {// jb1�¼�

			public void actionPerformed(java.awt.event.ActionEvent e) {
				returnPanel(p1);
			}

		});
		topPanel.add(jb1);
		topPanel.add(jb2);
		topPanel.add(jb3);
		topPanel.add(jb4);

		contentPanel.add(topPanel, BorderLayout.NORTH);
		contentPanel.add(centerPanel, BorderLayout.CENTER);

		frame.show();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// returnPanel
	// ����Ч��
	@SuppressWarnings("unchecked")
	public void returnPanel(final JPanel panel) {
		panel.setBounds(0, 0, centerPanel.getWidth(), centerPanel.getHeight());// ���û�����ʼλ��
		int count = centerPanel.getComponentCount();// ��ȡcenterPanel�пؼ���
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();//
		for (Component comp : centerPanel.getComponents()) {
			list.add(comp);// ��list��ֵ
		}
		if (count > 0) {// ���centerPanel�пؼ�������0��ִ��Ч��
			for (int i = 0; i < count; i++) {
				Component comp = centerPanel.getComponent(i);// ��ø�λ�õĿؼ�

				if (comp instanceof JPanel) {// �жϿؼ�����
					final JPanel currentPanel = (JPanel) comp;// ��õ�ǰpanel
					if (currentPanel != panel) {

						new Thread() {

							public void run() {

								@SuppressWarnings("unused")
								Rectangle rec = currentPanel.getBounds();// ��õ�ǰ����λ����Ϣ
								int y = centerPanel.getWidth();

								for (int i = 0; i >= -centerPanel.getWidth(); i -= 20) {
									// �������λ��
									currentPanel.setBounds(i, 0, centerPanel.getWidth(), centerPanel.getHeight());
									panel.setBounds(y, 0, centerPanel.getWidth(), centerPanel.getHeight());
									try {
										Thread.sleep(5);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									y -= 20;
								}

								centerPanel.remove(currentPanel);// �Ƴ���ǰ���

								panel.setBounds(0, 0, centerPanel.getWidth(), centerPanel.getHeight());

							}
						}.start();
						break;
					}
				}
			}
		}

		if (!list.contains(panel)) {
			centerPanel.add(panel);// ���Ҫ�л������
		}

		centerPanel.validate();// �ع��������
		centerPanel.repaint();// �ػ��������
	}

	// ����Ч������
	@SuppressWarnings("unchecked")
	public void xiaoGuo(final JPanel panel) {
		panel.setBounds(0, 0, centerPanel.getWidth(), centerPanel.getHeight());// ���û�����ʼλ��
		int count = centerPanel.getComponentCount();// ��ȡcenterPanel�пؼ���
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();//
		for (Component comp : centerPanel.getComponents()) {
			list.add(comp);// ��list��ֵ
		}
		if (count > 0) {// ���centerPanel�пؼ�������0��ִ��Ч��
			for (int i = 0; i < count; i++) {
				Component comp = centerPanel.getComponent(i);// ��ø�λ�õĿؼ�

				if (comp instanceof JPanel) {// �жϿؼ�����
					final JPanel currentPanel = (JPanel) comp;// ��õ�ǰpanel
					if (currentPanel != panel) {

						new Thread() {

							public void run() {

								@SuppressWarnings("unused")
								Rectangle rec = currentPanel.getBounds();// ��õ�ǰ����λ����Ϣ
								int y = -centerPanel.getWidth();

								for (int i = 0; i <= centerPanel.getWidth(); i += 20) {
									// �������λ��
									currentPanel.setBounds(i, 0, centerPanel.getWidth(), centerPanel.getHeight());
									panel.setBounds(y, 0, centerPanel.getWidth(), centerPanel.getHeight());
									try {
										Thread.sleep(5);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									y += 20;
								}

								centerPanel.remove(currentPanel);// �Ƴ���ǰ���

								panel.setBounds(0, 0, centerPanel.getWidth(), centerPanel.getHeight());

							}
						}.start();
						break;
					}
				}
			}
		}

		if (!list.contains(panel)) {
			centerPanel.add(panel);// ���Ҫ�л������
		}

		centerPanel.validate();// �ع��������
		centerPanel.repaint();// �ػ��������
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JpanelForm();
	}

}
