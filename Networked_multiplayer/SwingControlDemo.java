import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import java.awt.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class SwingControlDemo {

   static JFrame mainFrame;
    static  JTextField score= new JTextField("score",20);
   static JLabel headerLabel  = new JLabel("arbit text",JLabel.CENTER );
   static JLabel statusLabel= new JLabel("",JLabel.CENTER); 
   static JPanel controlPanel;
   static JButton single = new JButton("Single Player");
   static JButton multi = new JButton("Multiplayer");
   static JButton easy=new JButton("EASY");
  	static JButton medium=new JButton("MEDIUM");
    static JButton difficult=new JButton("DIFFICULT"); 
    static JButton back = new JButton("BACK");
    static JButton back2 = new JButton("BACK");
   static  JTextField ip= new JTextField("Enter IP",20);
   	static JTextField port= new JTextField("Enter PORT",20);
   	static JButton proceed = new JButton("Continue");
   static 	JTextField port_rivals=new JTextField("Enter port to connect to",20);
   static String choices[]={"left","right","up","down"};
  static JComboBox cb=new JComboBox(choices);
    static JLabel label  = new JLabel("", JLabel.CENTER);  
   	
   	static JFrame frame = new JFrame("Pong");

   static JButton play_again = new JButton("PLAY AGIAN");
 
   static SwingControlDemo swingControlDemo ;  
   static String number[]={"1","2","3","4"};
 
  static JComboBox no_of_players=new JComboBox(number);
   /*public SwingControlDemo(){
      prepareGUI();
   }*/

   public void start(int level)
   {

   		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED));
        single_player pongPanel = new single_player(level);
        frame.add(pongPanel, BorderLayout.CENTER);


        frame.setSize(500, 500);
        frame.setVisible(true);
        System.out.println("huh");
   }
   public void start_multi(int level)
   {
         Scanner scanner = new Scanner(System.in);
		
		//System.out.print("Name : ");
		//String name = scanner.nextLine();
		
		//System.out.print("Source Port : ");
		int sourcePort=1234;
		//int sourcePort = Integer.parseInt(scanner.nextLine());
		String p=port.getText();
        String i=ip.getText();
        String pr=port_rivals.getText();
        String loc = String.valueOf(cb.getSelectedItem());
        String n=String.valueOf(no_of_players.getSelectedItem());
		
		System.out.print("Destination IP : ");
		//String destinationIP = "192.168.146.51";
		String destinationIP2 = "10.234.11.217";
		String destinationIP = "10.192.51.85";
		//String destinationIP = scanner.nextLine();
		
		System.out.print("Destination Port : ");
		int destinationPort2=2345;
		int destinationPort=3456;
		//int destinationPort = Integer.parseInt(scanner.nextLine());
		//////////////////////////////////////here we will ask which side the player is playing. Based on that, different channel will be called.
		/*Channel channel = new Channel();
		channel.bind(sourcePort);
		channel.start(); // Start Receive
		*/
		System.out.println("Started.");
		String playmsg = "play";
		InetSocketAddress address = new InetSocketAddress(destinationIP, destinationPort);
		/////////////////////////////////////////////////////////////////////////////////////////
		InetSocketAddress address2 = new InetSocketAddress(destinationIP2, destinationPort2);
		
        JFrame frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        System.out.println("Enter location:");
       // String location = scanner.nextLine();
        String location="qw";
        if(loc=="up"){location="uh";}
        if(loc=="right"){location="rv";}
        if(loc=="down"){location="dh";}
        if(loc=="left"){location="lv";}

        twoplayer1 two = new twoplayer1(location);
        two.setPreferredSize(new Dimension(500, 500));
        try{
        two.bind(sourcePort);
    }catch(SocketException s){
    	System.out.println("exception");
    }
        two.start();
        two.getaddress(address, address2);
        //two.getaddress(address2);
        
        
        //two.location=location;
        if(playmsg.equals("play"))
        {
        	two.startgamelocal=true;
        	System.out.println(two.startgamelocal);
        	try{
        	two.sendTo(address, playmsg);
        	two.sendTo(address2,playmsg);}
        	catch(IOException e){
        		System.out.println("except");
        	}
        	two.getdestinationports(destinationPort,destinationPort2);
        	System.out.println("Local machine play");	
        }
        //frame.setResizable(false);
        
        frame.add(two, BorderLayout.CENTER);
        /*
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
		int width = screenSize.width;
        */
        frame.pack();
        //frame.setSize(500,500);

        frame.setVisible(true);
   }

   public static void main(String[] args){
   	 swingControlDemo = new SwingControlDemo();  

      prepareGUI();
      single.setActionCommand("Single");
      multi.setActionCommand("Multi");
      single.setBackground(Color.YELLOW);
      multi.setBackground(Color.YELLOW);
     
      
      
      
      controlPanel.add(headerLabel);
      controlPanel.add(single);
      single.setVisible(false);
      controlPanel.add(multi);
      multi.setVisible(false);
      label.setText("<html>INSTRUCTIONS: <br>1.Use Right/Left or Up/Down arrow keys to control the paddle. <br> 2. Try to catch the power ups, it'll incrase the size of your paddle for few seconds. <br>3. Press Left shift to contol the boost, it'll significantly increase the paddle speed but boost is limited and will refill slowly when you'd not be using it. Use wisely.<br>4. Press space bar to pause the game and to resume, press it again.<br>5. Press q to quit the game.<br></html>");
        
      label.setOpaque(true);
      label.setBackground(Color.GRAY);
      label.setForeground(Color.WHITE);
      controlPanel.add(label);
      label.setVisible(false);
      controlPanel.add(statusLabel);
      controlPanel.add(easy);
       controlPanel.add(medium);
        controlPanel.add(difficult);
        controlPanel.add(back2);
      easy.setVisible(false);
      medium.setVisible(false);
      difficult.setVisible(false);
      back2.setVisible(false);

      controlPanel.add(ip);
    controlPanel.add(port);
    controlPanel.add(port_rivals);
     controlPanel.add(cb);
    controlPanel.add(proceed);
    controlPanel.add(back);

    ip.setVisible(false);
    port.setVisible(false);
    port_rivals.setVisible(false);
    cb.setVisible(false);
    proceed.setVisible(false);
    back.setVisible(false);

      swingControlDemo.showEventDemo();       
   }

   public void exit(int lives){

   	frame.setVisible(false);

   	prepareGUI();
   	score.setText(String.valueOf(lives));
   	play_again.setActionCommand("again");
   	play_again.setBackground(Color.YELLOW);
   	play_again.addActionListener(new ButtonClickListener()); 
   	controlPanel.add(play_again);
   	controlPanel.add(headerLabel);
   	controlPanel.add(score);


   }
      
   public static void prepareGUI(){
      mainFrame = new JFrame("Ping Pong");
      mainFrame.setSize(1200,600);
      mainFrame.setLayout(new GridLayout(3, 1));

    mainFrame.setLayout(new BorderLayout());
   // mainFrame.setContentPane(new JLabel(new ImageIcon("a.jpg")));
    mainFrame.setLayout(new FlowLayout());

         

      statusLabel.setSize(350,100);
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
     // gridBackLayout gc= new GridBackLayout();
      controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
      

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
     
      mainFrame.setVisible(true); 
      
   }

   private void showEventDemo(){
   	
      headerLabel.setText("Welcome To ping pong Game"); 
      single.setVisible(true);
      multi.setVisible(true);
       single.addActionListener(new ButtonClickListener()); 
      multi.addActionListener(new ButtonClickListener()); 
      mainFrame.setVisible(true);  
   }

   private void multiplayer(){
    label.setVisible(false);
    
   	headerLabel.setText("Ready for the clash...?");
   	
   	back.setActionCommand("back");
    back.addActionListener(new ButtonClickListener()); 
    //back.setBackground(Color.BLUE);
   	proceed.setActionCommand("proceed");
   	proceed.addActionListener(new ButtonClickListener()); 
   	//proceed.setBackground(Color.BLUE);
   	//prepareGUI();

   	single.setVisible(false);
    multi.setVisible(false);
   	ip.setVisible(true);
    port.setVisible(true);
    port_rivals.setVisible(true);
    cb.setVisible(true);
    proceed.setVisible(true);
    back.setVisible(true);
   
   	mainFrame.setVisible(true);
   }
   private void singleplayer(){


   	headerLabel.setText("Computers are getting smarter, Be aware of that:");
    label.setVisible(false);
   	
   	back2.setActionCommand("BACK");
    back2.addActionListener(new ButtonClickListener()); 	
    
    single.setVisible(false);
    multi.setVisible(false);
    easy.setVisible(true);
    medium.setVisible(true);
    difficult.setVisible(true);
    back2.setVisible(true);
   	
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

         	start(1);//Function to start the game
         }
         else if(command.equals( "medium" )){
         	statusLabel.setText("medium");
         	System.out.println("Medium");
         	start(2);//Function to start the game
         }
         else if(command.equals( "difficult" )){
         	statusLabel.setText("difficult");
         	System.out.println("difficult");
         	start(3);//Function to start the game
         }
         else if(command.equals( "proceed" )){
         	statusLabel.setText("Starting the Game");
         	System.out.println("Started");
         	start_multi(2);//Function to start the game
         }
         else if(command.equals( "back" )){
         	statusLabel.setText("back");
         	System.out.println("Back");
         	controlPanel.remove(ip);

      		ip.setVisible(false);
        port.setVisible(false);
        port_rivals.setVisible(false);
        cb.setVisible(false);
       proceed.setVisible(false);
        back.setVisible(false);
        single.setVisible(true);
      multi.setVisible(true);
      label.setVisible(true);
         }
         else if(command.equals("BACK")){
         	statusLabel.setText("back");
         	System.out.println("Back---");
         	//controlPanel.remove(easy);
          easy.setVisible(false);
      		//controlPanel.remove(medium);
           medium.setVisible(false);
      		//controlPanel.remove(difficult);
           difficult.setVisible(false);
      		//controlPanel.remove(back2);
           back2.setVisible(false);
      		single.setVisible(true);
         multi.setVisible(true);
         label.setVisible(true);


         }
         else if(command.equals("again")){
         	play_again.setVisible(false);
         	swingControlDemo.showEventDemo();           
         }
         else  {
            statusLabel.setText("Cancel Button clicked.");
         }  	
      }		
   }
}