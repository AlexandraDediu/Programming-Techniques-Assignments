

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import producer.ClientScheduler;
import producer.RandomClientGenerator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final JFrame inputFrame = new JFrame("Queue simulation");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame.setSize(500,500);
        
        JLabel intervalArrivalLbl = new JLabel("Arrival interval between assets");
        intervalArrivalLbl.setBounds(70, 70, 250, 40);
        inputFrame.add(intervalArrivalLbl);
        
        JLabel minIntervalArrivalLbl = new JLabel("Min");
        minIntervalArrivalLbl.setBounds(90, 100 , 40, 40);
        inputFrame.add(minIntervalArrivalLbl);
        
        final JFormattedTextField minINtervalArrivalTxt = new JFormattedTextField();
        minINtervalArrivalTxt.setBounds(150, 100 , 40, 40);
        inputFrame.add(minINtervalArrivalTxt);
        
        JLabel maxIntervalArrivalLbl = new JLabel("Max");
        maxIntervalArrivalLbl.setBounds(90, 140, 40, 40);
        inputFrame.add(maxIntervalArrivalLbl);
        
        final JFormattedTextField maxINtervalArrivalTxt = new JFormattedTextField();
        maxINtervalArrivalTxt.setBounds(150, 140 , 40, 40);
        inputFrame.add(maxINtervalArrivalTxt);
        
        
        JLabel intervalServiceLbl = new JLabel("Service time");
        intervalServiceLbl.setBounds(70, 180, 100, 40);
        inputFrame.add(intervalServiceLbl);
        
        JLabel minIServiceTimeLbl = new JLabel("Min");
        minIServiceTimeLbl.setBounds(90, 210 , 40, 40);
        inputFrame.add(minIServiceTimeLbl);
        
        final JFormattedTextField minServiceTxt = new JFormattedTextField();
        minServiceTxt.setBounds(150, 210 , 40, 40);
        inputFrame.add(minServiceTxt);
        
        JLabel maxServiceLbl = new JLabel("Max");
        maxServiceLbl.setBounds(90, 250, 40, 40);
        inputFrame.add(maxServiceLbl);
        
        final JFormattedTextField maxServiceTxt = new JFormattedTextField();
        maxServiceTxt.setBounds(150, 250 , 40, 40);
        inputFrame.add(maxServiceTxt);
        
        JLabel nbrQueuesLbl = new JLabel("Number of queues");
        nbrQueuesLbl.setBounds(70, 290, 140, 40);
        inputFrame.add(nbrQueuesLbl);
        
        final JFormattedTextField nbrQueuesTxt = new JFormattedTextField();
        nbrQueuesTxt.setBounds(230, 290 , 40, 40);
        inputFrame.add(nbrQueuesTxt);
        
        JLabel simulationLbl = new JLabel("Simulation interval");
        simulationLbl.setBounds(70, 330, 140, 40);
        inputFrame.add(simulationLbl);
        
        final JFormattedTextField simulationTxt = new JFormattedTextField();
        simulationTxt.setBounds(230, 330 , 40, 40);
        inputFrame.add(simulationTxt);
        
        JPanel queuePanel = new JPanel ();
        queuePanel.setBorder ( (Border) new TitledBorder ( new EtchedBorder (), "Status" ) );

        final JTextArea queueDisplay = new JTextArea ( 50, 58 );
        queueDisplay.setEditable ( false ); // set textArea non-editable
        JScrollPane queueScroll = new JScrollPane ( queueDisplay );
        queueScroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        queuePanel.add ( queueScroll );
       

        
        final JFrame outputQueueFrame = new JFrame ();
        outputQueueFrame.add(queuePanel);
        outputQueueFrame.pack ();
        outputQueueFrame.setLocationRelativeTo ( null );
        outputQueueFrame.setVisible ( true );
        
        JPanel clientPanel = new JPanel ();
        clientPanel.setBorder ( (Border) new TitledBorder ( new EtchedBorder (), "Client" ) );
        
        final JTextArea clientDisplay = new JTextArea ( 50, 58 );
        clientDisplay.setEditable ( false ); // set textArea non-editable
        JScrollPane clientScroll = new JScrollPane ( clientDisplay );
        clientScroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        clientPanel.add ( clientScroll );
       

        final JFrame outputClientFrame = new JFrame ();
        outputClientFrame.add(clientPanel);
        outputClientFrame.pack ();
        outputClientFrame.setLocationRelativeTo ( outputQueueFrame );
        outputClientFrame.setVisible ( true );
        
        
        final JButton button = new JButton("Start");
        button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int minArrival = Integer.parseInt(minINtervalArrivalTxt.getText());
					int maxArrival = Integer.parseInt(maxINtervalArrivalTxt.getText());
					int minService = Integer.parseInt(minServiceTxt.getText());
					int maxService = Integer.parseInt(maxServiceTxt.getText());
					int numQueues = Integer.parseInt(nbrQueuesTxt.getText());
					int simTime = Integer.parseInt(simulationTxt.getText());
					RandomClientGenerator clientGenerator = new RandomClientGenerator(minArrival, maxArrival, minService, maxService, numQueues);
					ClientScheduler scheduler = new ClientScheduler(simTime, clientGenerator, numQueues, queueDisplay, clientDisplay);
					Thread schedulerThread = new Thread(scheduler);
					schedulerThread.start();
					button.setEnabled(false);
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(inputFrame, "Invalid input");
				} 
				
			}
		});
        button.setBounds(70, 370, 70, 40);        
        inputFrame.add(button); 
        inputFrame.setLayout(null);
        inputFrame.setVisible(true);
        
        
    }
}
