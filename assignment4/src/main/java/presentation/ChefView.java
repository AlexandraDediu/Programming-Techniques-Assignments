package presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import restaurant.model.MenuItem;

public class ChefView implements Observer {
	private JFrame view;
	private JTextArea textArea;
	private List<MenuItem> ordersToPrepare;

	public ChefView() {
		ordersToPrepare = new ArrayList<MenuItem>();
	}

	public void draw() {
		view = new JFrame();

		textArea = new JTextArea();
		textArea.setBounds(200, 150, 400, 300);

		StringBuilder sp = new StringBuilder();

		for (MenuItem menuItem : ordersToPrepare) {
			sp.append(menuItem.getName());
			sp.append("\n");

		}
		textArea.setText(sp.toString());

		view.add(textArea);
		view.setSize(950, 600);
		view.setLayout(null);// using no layout managers
		view.setVisible(true);// making the frame visible
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@SuppressWarnings("unchecked")
	public void update(Observable arg0, Object arg1) {
		ordersToPrepare.addAll((Collection<MenuItem>) arg1);
        
		StringBuilder sp = new StringBuilder();

		for (MenuItem menuItem : ordersToPrepare) {
			sp.append(menuItem.getName());
			sp.append("\n");

		}
		if(textArea !=null) textArea.setText(sp.toString());
	}

}
