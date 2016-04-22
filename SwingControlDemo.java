import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingControlDemo {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public SwingControlDemo(){
      prepareGUI();
   }

   public static void main(String[] args){
      SwingControlDemo swingControlDemo = new SwingControlDemo();  
      swingControlDemo.showEventDemo();       
   }
      
   private void prepareGUI(){
      mainFrame = new JFrame("Java SWING Examples");
      mainFrame.setSize(1200,600);
      mainFrame.setLayout(new GridLayout(3, 1));

    mainFrame.setLayout(new BorderLayout());
    mainFrame.setContentPane(new JLabel(new ImageIcon("a.jpg")));
    mainFrame.setLayout(new FlowLayout());

      headerLabel = new JLabel("",JLabel.CENTER );
      statusLabel = new JLabel("",JLabel.CENTER);        

      statusLabel.setSize(350,100);
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true); 
      
   }

   private void showEventDemo(){
      headerLabel.setText("Welcome To ping pong Game"); 
      prepareGUI();

      JButton single = new JButton("Single Player");
      JButton multi = new JButton("Multiplayer");
      
      single.setActionCommand("Single");
      multi.setActionCommand("Multi");
     
      single.setBackground(Color.YELLOW);
      //single.setLayout(null);
      //controlPanel.setLayout(null);
      //single.setBounds(20,20,20,20);
      //single.setLocation(20,20);
      multi.setBackground(Color.YELLOW);

      single.addActionListener(new ButtonClickListener()); 
      multi.addActionListener(new ButtonClickListener()); 
      

      controlPanel.add(single);
      controlPanel.add(multi);
          

      mainFrame.setVisible(true);  
   }

   private void multiplayer(){
   	headerLabel.setText("Ready for the clash...?");
   	JTextField ip= new JTextField("Enter IP",20);
   	JTextField port= new JTextField("Enter PORT",20);
   	JButton proceed = new JButton("Continue");
   	JButton back = new JButton("BACK");
   	back.setActionCommand("back");
    back.addActionListener(new ButtonClickListener()); 
    //back.setBackground(Color.BLUE);
   	proceed.setActionCommand("proceed");
   	proceed.addActionListener(new ButtonClickListener()); 
   	//proceed.setBackground(Color.BLUE);
   	prepareGUI();
   	controlPanel.add(ip);
   	controlPanel.add(port);
   	controlPanel.add(proceed);
   	controlPanel.add(back);
   	mainFrame.setVisible(true);
   }
   private void singleplayer(){
   	headerLabel.setText("Computers are getting smarter, Be aware of that:");
   	JButton easy=new JButton("EASY");
  	JButton medium=new JButton("MEDIUM");
    JButton difficult=new JButton("DIFFICULT"); 
    JButton back = new JButton("BACK");
   	back.setActionCommand("back");
    back.addActionListener(new ButtonClickListener()); 	
    prepareGUI();
   	controlPanel.add(easy);
   	controlPanel.add(medium);
   	controlPanel.add(difficult);
   	controlPanel.add(back);
   	easy.setActionCommand("easy");
   	easy.addActionListener(new ButtonClickListener()); 
   	medium.setActionCommand("medium");
   	medium.addActionListener(new ButtonClickListener()); 
   	difficult.setActionCommand("difficult");
   	difficult.addActionListener(new ButtonClickListener()); 
   	mainFrame.setVisible(true);
   }

   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         if( command.equals( "Single" ))  {
            statusLabel.setText("Single Button clicked.");
            System.out.println("Single");
            singleplayer();
         }
         else if( command.equals( "Multi" ) )  {
            statusLabel.setText("Multi Button clicked."); 
            System.out.println("MULTI");
            multiplayer();
         }
         else if(command.equals( "easy" )){
         	statusLabel.setText("easy");
         	System.out.println("Easy");
         	//Function to start the game
         }
         else if(command.equals( "medium" )){
         	statusLabel.setText("medium");
         	System.out.println("Medium");
         	//Function to start the game
         }
         else if(command.equals( "difficult" )){
         	statusLabel.setText("difficult");
         	System.out.println("difficult");
         	//Function to start the game
         }
         else if(command.equals( "proceed" )){
         	statusLabel.setText("Starting the Game");
         	System.out.println("Started");
         	//Function to start the game
         }
         else if(command.equals( "back" )){
         	statusLabel.setText("back");
         	System.out.println("Back");
         	showEventDemo();
         }
         else  {
            statusLabel.setText("Cancel Button clicked.");
         }  	
      }		
   }
}