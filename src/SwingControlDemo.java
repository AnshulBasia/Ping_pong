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
import java.util.*;
import java.net.*;
public class SwingControlDemo {

   static JFrame mainFrame;
   int problem=0;
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
    static JButton play = new JButton("PLAY");
   static  JTextField ip= new JTextField("Enter IP",20);
   	static JTextField port= new JTextField("Enter PORT",20);
   	static JButton proceed = new JButton("Continue");
   static 	JTextField port_rivals=new JTextField("Enter port to connect to",20);
   static String choices[]={"left","right","up","down"};
  static JComboBox cb=new JComboBox(choices);
    static JLabel label  = new JLabel("", JLabel.CENTER);  
     static JLabel lose  = new JLabel("Some other time..? Tain hard, ", JLabel.CENTER);  
   	twoplayer1 two;
   	static JFrame frame = new JFrame("Pong");

   static JButton play_again = new JButton("PLAY AGIAN");
 
   static SwingControlDemo swingControlDemo ;  
   static String number[]={"1","2","3"};
 	int ai_level=3;
  static JComboBox no_of_players=new JComboBox(number);
   /*public SwingControlDemo(){
      prepareGUI();
   }*/

   public static ArrayList<Integer> destinationPorts = new ArrayList<>();
    public static ArrayList<String> destinationIPs = new ArrayList<>();
    public static ArrayList<InetSocketAddress> addresses = new ArrayList<>();
    public void addAddress(String dip, int dport)
    {
        InetSocketAddress address = new InetSocketAddress(dip, dport);
        addresses.add(address);
    }
    public void addipAddress(String dip)
    {
        destinationIPs.add(dip);
    }
    public void addport(int dport)
    {
        //InetSocketAddress address = new InetSocketAddress(dip, dport);
        destinationPorts.add(dport);
    }
    public ArrayList<InetSocketAddress> totaladdresses()
    {
        return addresses;
    }
    public int getSize()
    {
        return addresses.size();
    }


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
		
		//int sourcePort = Integer.parseInt(scanner.nextLine());
		String p=port.getText();
		int sourcePort = Integer.parseInt(p);
        String destinationIP=ip.getText();
       
        
        String pr=port_rivals.getText();
        int destinationPort = Integer.parseInt(pr);
        String loc = String.valueOf(cb.getSelectedItem());
        String numberr=String.valueOf(no_of_players.getSelectedItem());
         ai_level=Integer.parseInt(numberr);
        destinationPorts.add(destinationPort);
        destinationIPs.add(destinationIP);
		
		
		System.out.println("Started.");
		
		InetSocketAddress address = new InetSocketAddress(destinationIP, destinationPort);
		 addresses.add(address);
		/////////////////////////////////////////////////////////////////////////////////////////
		
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

        two = new twoplayer1(location, sourcePort);
        two.setPreferredSize(new Dimension(500, 500));
        try{
        two.bind(sourcePort);
    }catch(SocketException s){
    	System.out.println("exception");
    }
        two.start();
        two.addAddress(destinationIP,destinationPort);
        String sourceIP="";
        try{
         sourceIP=Inet4Address.getLocalHost().getHostAddress();}
        catch(UnknownHostException e){System.out.println("exception");}
       // System.out.println(sourceIP);
        String ipmsg="add1_"+sourceIP+"_"+sourcePort;
            
                int n= addresses.size();
            	InetSocketAddress tempaddress;
                for(int i=0;i<n;i++){
                	
                     tempaddress=addresses.get(i);
                     try    {
                    two.sendTo(tempaddress,ipmsg);
                    }
                     catch(IOException e){System.out.println("exception");}
                    System.out.println("Message sent to : "+ i );
                }

                
        
        
        //two.location=location;
        //String playmsg = "play";
        /*if(playmsg.equals("play"))
        {
        	System.out.println(two.addresses.size());
        	
        	two.startgamelocal=true;
        	System.out.println(two.startgamelocal);
        	for (int i=0;i<two.addresses.size();i++){
        		try{
                two.sendTo(two.addresses.get(i),playmsg);}
        	 catch(IOException e){System.out.println("exception");}
                System.out.println("play msg sent");
                System.out.println(two.addresses.get(i));
            }
            System.out.println("Local machine play");   
        }*/
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
      
      label.setVisible(true);
      controlPanel.add(statusLabel);
      controlPanel.add(label);
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
     controlPanel.add(no_of_players);
    controlPanel.add(proceed);
    controlPanel.add(play);
    controlPanel.add(back);
    play.setVisible(false);

    controlPanel.add(play_again);
    play_again.setVisible(false);

   	controlPanel.add(headerLabel);
   	headerLabel.setVisible(false);
   	controlPanel.add(score);
   	score.setVisible(false);
    

    ip.setVisible(false);
    port.setVisible(false);
    port_rivals.setVisible(false);
    cb.setVisible(false);
    proceed.setVisible(false);
    back.setVisible(false);
    no_of_players.setVisible(false);

    controlPanel.add(lose);
    lose.setVisible(false);

      swingControlDemo.showEventDemo();       
   }

   public void exit(int lives){

   	frame.setVisible(false);

   	//prepareGUI();
   		ip.setVisible(false);
        port.setVisible(false);
        port_rivals.setVisible(false);
        play.setVisible(false);
        cb.setVisible(false);
        no_of_players.setVisible(false);
       proceed.setVisible(false);
        back.setVisible(false);
        easy.setVisible(false);
      		//controlPanel.remove(medium);
           medium.setVisible(false);
      		//controlPanel.remove(difficult);
           difficult.setVisible(false);
      		//controlPanel.remove(back2);
           back2.setVisible(false);

   	score.setText(String.valueOf(lives));
   	play_again.setActionCommand("again");
   	play_again.setBackground(Color.YELLOW);
   	play_again.addActionListener(new ButtonClickListener()); 
   	
   	label.setVisible(true);
   	lose.setVisible(true);
   	play_again.setVisible(true);
   	headerLabel.setVisible(true);
   	score.setVisible(true);
   	
   	
   	frame.dispose();

   	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));


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
    
    
   	headerLabel.setText("Ready for the clash...?");
   	
   	back.setActionCommand("back");
    back.addActionListener(new ButtonClickListener()); 
    //back.setBackground(Color.BLUE);
   	proceed.setActionCommand("proceed");
   	proceed.addActionListener(new ButtonClickListener()); 
   	play.setActionCommand("play");
   	play.addActionListener(new ButtonClickListener());
   	//proceed.setBackground(Color.BLUE);
   	//prepareGUI();
   	play.setVisible(true);

   	single.setVisible(false);
    multi.setVisible(false);
   	ip.setVisible(true);
    port.setVisible(true);
    port_rivals.setVisible(true);
    cb.setVisible(true);
    proceed.setVisible(true);
    back.setVisible(true);
    no_of_players.setVisible(true);
   
   	mainFrame.setVisible(true);
   }
   private void singleplayer(){


   	headerLabel.setText("Computers are getting smarter, Be aware of that:");
    
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
         	if(problem==0)
         	{
         		start(1);//Function to start the game
         		problem=1;
         	}
         }
         else if(command.equals( "medium" )){
         	statusLabel.setText("medium");
         	System.out.println("Medium");
         	if(problem==0)
         	{
         		start(2);//Function to start the game
         		problem=1;
         	}
         }
         else if(command.equals( "difficult" )){
         	statusLabel.setText("difficult");
         	System.out.println("difficult");
         	if(problem==0)
         	{
         		start(3);//Function to start the game
         		problem=1;
         	}
         }
         else if(command.equals( "proceed" )){
         	statusLabel.setText("Starting the Game");
         	System.out.println("Started");
         	if(problem==0)
         	{
         		
            System.out.println("Local machine play");  
            start_multi(ai_level);//Function to start the game
         }
         		problem=1;
         	}
         	
         else if(command.equals( "back" )){
         	statusLabel.setText("back");
         	System.out.println("Back");
         	
      		ip.setVisible(false);
        port.setVisible(false);
        port_rivals.setVisible(false);
        play.setVisible(false);
        cb.setVisible(false);
        no_of_players.setVisible(false);
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

         else if(command.equals("play")){

         	two.startgamelocal=true;
          System.out.println(two.startgamelocal);
          for (int i=0;i<two.addresses.size();i++){
            try{
                two.sendTo(two.addresses.get(i),"play");}
           catch(IOException f){System.out.println("exception");}
                System.out.println("play msg sent");
                System.out.println(two.addresses.get(i));
            }

         }
         else if(command.equals("again")){
         	play_again.setVisible(false);
         	label.setVisible(false);
   	
   			headerLabel.setVisible(false);
   			score.setVisible(false);
         	//swingControlDemo.showEventDemo(); 
         	String[] args = new String[0]; // Or String[] args = {};
		main(args);          
         }
         else  {
            statusLabel.setText("Cancel Button clicked.");
         }  	
      }		
   }
}