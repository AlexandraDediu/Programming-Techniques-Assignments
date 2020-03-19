package View;
import Model.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;


public class View extends JFrame{
	
	//private JPanel contentPane;
	private JTextField polynom1TextField;
	private JTextField polynom2TextField;
	private JTextField resultTextField;
	private JButton addButton;
	private JButton substractButton;
	private JButton multiplyButton;
	private JButton derivateButton;
	private JButton integrateButton;
//	private JPanel panel;
	private static Poly p1,p2,rez;
	
	public View() {
		p1=new Poly();
		p2=new Poly();
        rez=new Poly();
        
		this.setTitle("Polynomial processing");
		this.setBounds(100, 100, 393, 304);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        //contentPane = new JPanel();
        //setContentPane(contentPane);
        
        JLabel poly1Label= new JLabel("P1:");
        poly1Label.setBounds(10,11,46,14);
        getContentPane().add(poly1Label);
        
        JLabel poly2Label= new JLabel("P2:");
        poly2Label.setBounds(10, 36, 46, 14);
        getContentPane().add(poly2Label);
        
        JLabel resultLabel=new JLabel("Result:");
        resultLabel.setBounds(10,63,46,14);
        getContentPane().add(resultLabel);
        
        polynom1TextField=new JTextField();
        polynom1TextField.setBounds(50, 8, 86, 20);
        getContentPane().add(polynom1TextField);
        polynom1TextField.setColumns(20);
        
        polynom2TextField=new JTextField();
        polynom2TextField.setBounds(50, 33, 86, 20);
        getContentPane().add(polynom2TextField);
        polynom2TextField.setColumns(20);
        
        resultTextField=new JTextField(); 
        resultTextField.setBounds(50,60,86,20);
        getContentPane().add(resultTextField);
        resultTextField.setColumns(20);
        
        addButton= new JButton("Add");
        addButton.setBounds(224, 7,89, 23);
        getContentPane().add(addButton);
        //addition.addActionListener(this);
        
        substractButton =new JButton("Substract");
        substractButton.setBounds(224,32,89,23);
        //substractButton.setPreferredSize(new Dimension(40, 40));
        getContentPane().add(substractButton);
       
        multiplyButton= new JButton("Multiply");
        multiplyButton.setBounds(224,57,89,23);
        getContentPane().add(multiplyButton);
        
        derivateButton=new JButton("Derivate");
        derivateButton.setBounds(224,82,89,23);
        getContentPane().add(derivateButton);
        
        integrateButton=new JButton("Integrate");
        integrateButton.setBounds(224,106,89,23);
        getContentPane().add(integrateButton);
        
        this.setVisible(true);


}
//	addButton;
//	private JButton substractButton;
//	private JButton multiplyButton;
//	private JButton derivateButton;
//	private JButton integrateButton
	

	public void addListenerAddBtn(ActionListener e) {
		addButton.addActionListener(e);
	}
	
	public void addListenerSubstractBtn(ActionListener e) {
		substractButton.addActionListener(e);
	}
	
	public void addListenerMultiplyBtn(ActionListener e) {
		multiplyButton.addActionListener(e);
	}
	
	public void addListenerDerivateBtn(ActionListener e) {
		derivateButton.addActionListener(e);
	}
	
	public void addListenerIntegrateBtn(ActionListener e) {
		integrateButton.addActionListener(e);
	}
	
	public void pol1ListenerAddBtn(ActionListener e) {
		polynom1TextField.addActionListener(e);
	}
	
	public void pol2ListenerAddBtn(ActionListener e) {
		polynom2TextField.addActionListener(e);
	}
	
	public String pol1GetText() {
		return polynom1TextField.getText();
	}
	
	public String pol2GetText() {
		return polynom2TextField.getText();
	}
	
	public void rezSetText (String x) {
		 resultTextField.setText(x);
	}
	
	public void showError (String s) {
		JOptionPane.showMessageDialog(this, s);
	}
	
}