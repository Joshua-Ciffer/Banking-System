//============================================================================//
// Name        : Login.java         	     								  //
// Author      : Joshua Ciffer												  //
// Date        : 11/19/2017													  //
//============================================================================//

package bank.gui ; 
import javax.swing.JFrame ;
import java.awt.GridLayout ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
import javax.swing.JLabel ;
import javax.swing.JPanel;
import javax.swing.JButton ;
import javax.swing.JTextField ;
import bank.account.BankAccount ;
import javax.swing.JPasswordField ;

final class Login extends JPanel {

	private GridLayout loginLayout ;
	private JLabel loginLabel ;
	private JTextField accountNumberField ;
	private JPasswordField accountPinField ;
	private JButton loginButton, backButton ;
	
	public Login() {
		frameSetup() ;
		layoutSetup() ;
		componentSetup() ;
	}
	
	private void frameSetup() {
		setVisible(true) ;
		setSize(500, 500) ;
		setLayout(loginLayout = new GridLayout()) ;
	} 
	
	private void layoutSetup() {
		loginLayout.setColumns(1) ;
		loginLayout.setRows(4) ;
		loginLayout.setVgap(50) ;
	}
	
	private void componentSetup() {
		loginLabel = new JLabel("Login") ;
		accountNumberField = new JTextField() ;
		accountPinField = new JPasswordField() ;
		loginButton = new JButton("Login") ;
		backButton = new JButton("Back") ;
		add(loginLabel) ;
		add(accountNumberField) ;
		add(accountPinField) ;
		add(loginButton) ;
		add(backButton) ;
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(BankAccount.GET_ACCOUNT(Integer.parseInt(accountNumberField.getText()), accountPinField.getText()).toString()) ;
				} catch (Exception e1) {
					e1.printStackTrace() ;
				}
			}
		}) ;
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back Pressed") ;
				setVisible(false) ;
			}
		}) ;
	}
	
}