import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.net.*;

import java.util.*;

public class twoplayer1 extends JPanel implements ActionListener, KeyListener, Runnable{

	public boolean qpressed=false;
	boolean connectionlv=true;
	boolean connectiondh=true;
	boolean connectionuh=true;
	boolean connectionrv=true;
    public int looptimer=0;
    public int waittimer=-1;
    private DatagramSocket socket;
    private boolean running;
    public static ArrayList<InetSocketAddress> addresses = new ArrayList<>();
    public int sourceport;
    private boolean startgameforeign=false;
    public boolean startgamelocal=false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    public String location="";
    private int ballX = 240;
    private int ballY = 240;
    private int diameter = 20;
    private int ballDeltaX = -7;
    private int ballDeltaY = 5;
    private int initialballDeltaX = ballDeltaX;
    private int initialballDeltaY = ballDeltaY;

    private int localplayerX = 25;                              //this represents the left coordinate of the bat
    private int localplayerY = 250;                               // this represents the topmost coordinated of bat
    private int localplayerWidth = 10;
    private int localplayerHeight = 50;

    private int lvX = 0;                               //this represents the left coordinate of the bat
    private int lvY = 0;                               // this represents the topmost coordinated of bat
    private int lvWidth = 10;
    private int lvHeight = 50;
    private boolean lvedge=false;

    private int dhX = 0;                               //this represents the left coordinate of the bat
    private int dhY = 0;                               // this represents the topmost coordinated of bat
    private int dhWidth = 50;
    private int dhHeight = 10;
    private boolean dhedge=false;

    private int rvX = 0;                               //this represents the left coordinate of the bat
    private int rvY = 0;                               // this represents the topmost coordinated of bat
    private int rvWidth = 10;
    private int rvHeight = 50;
    private boolean rvedge=false;

    private int uhX = 0;                               //this represents the left coordinate of the bat
    private int uhY = 0;                               // this represents the topmost coordinated of bat
    private int uhWidth = 50;
    private int uhHeight = 10;
    private boolean uhedge=false;

    private int temp_x=ballDeltaX;
    private int temp_x1=ballDeltaX;
    private int temp_y=ballDeltaY;
    private int temp_y1=ballDeltaY;

    private int horizontalround=15;
    private int verticalround=10;
    private int paddleSpeed = 10;        //represents speed of the bat

    private int comp_paddle=0;
    private int comp_paddle2=0;
    private int comp_paddle3=0;
    private int comp_paddle4=0;

    private int playerOneX = 250;                                //this represents the left coordinate of the bat
    private int playerOneY = 475;                               // this represents the topmost coordinated of bat
    private int playerOneWidth = 50;
    private int playerOneHeight = 10;

    private int playerTwoX = 475;
    private int playerTwoY = 250;
    private int playerTwoWidth = 10;
    private int playerTwoHeight = 50;

    private int playerThreeX = 250;                          //player 4 is the computer player
    private int playerThreeY = 15;
    private int playerThreeWidth = 50;
    private int playerThreeHeight = 10;

    private int playerFourX = 15;                                //this represents the left coordinate of the bat
    private int playerFourY = 250;                               // this represents the topmost coordinated of bat
    private int playerFourWidth = 10;
    private int playerFourHeight = 50;
    int lives=10;

    private int playerlvLives = lives;
    private int playerdhLives = lives;
    private int playerrvLives = lives;
    private int playeruhLives = lives;

    private boolean islv=false;
    private boolean isdh=false;
    private boolean isrv=false;
    private boolean isuh=false;

    public String positionmsg="";

    int deviation=0;


    //%%%
    int boost=200;
    boolean shiftpressed=false;
    int iteration=0;
    boolean uh_present=true;
    boolean lv_present=false;
    boolean rv_present=false;
    boolean dh_present=false;
    int uh_y=250;
    int lv_x=250;
    int rv_x=250;
    int dh_y=250;
    boolean uh_start=false;
    boolean dh_start=false;
    boolean lv_start=false;
    boolean rv_start=false;
    int coord_uh=1000;
    int coord_dh=-1000;
    int coord_lv=1000;
    int coord_rv=-1000;
    int temp_uh=-500000;
    int temp_dh=-50000;
    int temp_lv=-50000;
    int temp_rv=-50000;

    //%%%
    


    //construct a PongPanel
    public twoplayer1(String a, int b){
        location=a;
        sourceport=b;
        Color backColor = new Color(4, 2, 54);
        setBackground(backColor);
        if(location.equals("lv")){
            localplayerX = 15;                               //this represents the left coordinate of the bat
            localplayerY = 250;                               // this represents the topmost coordinated of bat
            localplayerWidth = 10;
            localplayerHeight = 50;
        }
        else if(location.equals("dh")){
            localplayerX = 250;                               //this represents the left coordinate of the bat
            localplayerY = 475;                               // this represents the topmost coordinated of bat
            localplayerWidth = 50;
            localplayerHeight = 10;
        }
        else if(location.equals("rv")){
            localplayerX = 475;                               //this represents the left coordinate of the bat
            localplayerY = 250;                               // this represents the topmost coordinated of bat
            localplayerWidth = 10;
            localplayerHeight = 50;
        }
        else if(location.equals("uh")){
            localplayerX = 250;                               //this represents the left coordinate of the bat
            localplayerY = 15;                               // this represents the topmost coordinated of bat
            localplayerWidth = 50;
            localplayerHeight = 10;
        }
        //System.out.println(localplayerX+""+localplayerY+""+localplayerWidth+""+localplayerHeight);
        //listen to key presses
        setFocusable(true);
        addKeyListener(this);

        //call step() 20 fps
        Timer timer = new Timer(1000/20, this);
        timer.start();
    }

    public void bind(int port) throws SocketException
    {
        sourceport=port;
        socket = new DatagramSocket(port);
    }
    
    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void stop()
    {
        running = false;
        socket.close();
    }

    public void getaddress(ArrayList<InetSocketAddress> addressespassed)
    {
        addresses=addressespassed;  
    }

    
    public void sendTo(InetSocketAddress address1, String msg) throws IOException
    {
        byte[] buffer = msg.getBytes();
        
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setSocketAddress(address1);
        
        socket.send(packet);
    }


    public void actionPerformed(ActionEvent e){
        if(startgamelocal)
        {
            //System.out.println("game started");
            step();
        }
    }

    public int countdashes(String s)
    {
        int c=0;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='_')
            {
                c++;
            }
        }
        return c;
    }

    public void addAddress(String dip, int dport)
    {
        InetSocketAddress address = new InetSocketAddress(dip, dport);
        System.out.println(" >>>>> "+ address.getHostString());
        boolean a = true;
        for (int i=0;i<addresses.size();i++)
        {
            
            if(dip.equals(addresses.get(i).getHostString()))
                a=false;

        }
        if(a)
        {
            addresses.add(address);
            System.out.println("added");    
        }
        
    }
    
    @Override
    public void run()
    {
        byte[] buffer = new byte[128];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        
        running = true;
        while(running)
        {
            try
            {
                socket.receive(packet);
                
                String msg = new String(buffer, 0, packet.getLength());
                System.out.println(msg);// iski jagah positions yahan banani hain
                String sourceIP=Inet4Address.getLocalHost().getHostAddress();
                
                  
                      
                //System.out.println(msg.substring(0,3));
                
                if(msg.length()>4&&msg.substring(0,4).equals("add1"))
                {
                    String address[]=msg.split("_");
                    String IP=address[1];
                    int port=Integer.parseInt(address[2]);
                     
                    int n= addresses.size();
                    
                       addAddress(IP,port);
                    n= addresses.size();
                    //Main mm=new Main();
                    //mm.addAddress(IP,port);
                    System.out.println(n);
                    for(int i=0;i<n-1;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        String msg2="add2_"+IP+"_"+port;
                        String msg3="add2_"+tempaddress.getHostString()+"_"+tempaddress.getPort();
                        sendTo(tempaddress,msg2);
                        sendTo(addresses.get(addresses.size()-1),msg3);
                    //System.out.println("Message sent to : "+ i );
                    }
                }
                else if(msg.length()>4&&msg.substring(0,4).equals("add2"))
                {
                    String address[]=msg.split("_");
                    String IP=address[1];
                    System.out.println(addresses.size());
                    int port=Integer.parseInt(address[2]);
                    addAddress(IP,port);
                }
                
                                

                if(countdashes(msg)==2)
                {
                    String coordinates[]=msg.split("_");
                    if(coordinates[0].equals("lv"))
                    {
                        if(connectionlv){islv=true;}
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        lvX=Integer.parseInt(tempxcoordinates);
                        lvY=Integer.parseInt(tempycoordinates);
                        
                    }
                    else if(coordinates[0].equals("dh"))
                    {
                        if(connectiondh){isdh=true;}
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        dhX=Integer.parseInt(tempxcoordinates);
                        dhY=Integer.parseInt(tempycoordinates);  
                    }   
                    else if(coordinates[0].equals("rv"))
                    {
                       if(connectionrv) {isrv=true;}
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        rvX=Integer.parseInt(tempxcoordinates);
                        rvY=Integer.parseInt(tempycoordinates);  
                    }
                    else if(coordinates[0].equals("uh"))
                    {
                        if(connectionuh){isuh=true;}
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        uhX=Integer.parseInt(tempxcoordinates);
                        uhY=Integer.parseInt(tempycoordinates);  
                    } 
                }
                else if(msg.equals("play"))
                {
                    startgamelocal=true;
                    System.out.println("play recieved here");
                }
                if(countdashes(msg)==6)
                //else
                {
                    String coordinates[]=msg.split("_");
                    
                    if(coordinates[0].equals("lv"))
                    {
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        lvX=Integer.parseInt(tempxcoordinates);
                        lvY=Integer.parseInt(tempycoordinates);
                        String balldeltax=coordinates[3];
                        String balldeltay=coordinates[4];
                        String ballx=coordinates[5];
                        String bally=coordinates[6];
                        ballDeltaX=Integer.parseInt(balldeltax);
                        ballDeltaY=Integer.parseInt(balldeltay);
                        ballX=Integer.parseInt(ballx);
                        ballY=Integer.parseInt(bally);
                        
                        System.out.println("ball collision msg recieved");
                        
                
                    }
                    if(coordinates[0].equals("dh"))
                    {
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        dhX=Integer.parseInt(tempxcoordinates);
                        dhY=Integer.parseInt(tempycoordinates);
                       
                        String balldeltax=coordinates[3];
                        String balldeltay=coordinates[4];
                        String ballx=coordinates[5];
                        String bally=coordinates[6];
                        ballDeltaX=Integer.parseInt(balldeltax);
                        ballDeltaY=Integer.parseInt(balldeltay);
                        ballX=Integer.parseInt(ballx);
                        ballY=Integer.parseInt(bally);
                        
                        
                        System.out.println("ball collision msg recieved");
                    } 
                    if(coordinates[0].equals("rv"))
                    {
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        rvX=Integer.parseInt(tempxcoordinates);
                        rvY=Integer.parseInt(tempycoordinates);
                        String balldeltax=coordinates[3];
                        String balldeltay=coordinates[4];
                        String ballx=coordinates[5];
                        String bally=coordinates[6];
                        ballDeltaX=Integer.parseInt(balldeltax);
                        ballDeltaY=Integer.parseInt(balldeltay);
                        ballX=Integer.parseInt(ballx);
                        ballY=Integer.parseInt(bally);
                        System.out.println("ball collision msg recieved");
                        
                
                    }
                    if(coordinates[0].equals("uh"))
                    {
                        String tempxcoordinates=coordinates[1];
                        String tempycoordinates=coordinates[2];
                        uhX=Integer.parseInt(tempxcoordinates);
                        uhY=Integer.parseInt(tempycoordinates);
                        //playerThreeY=Integer.parseInt(sycoordinates2);
                        //440 change hoga
                        //playerThreeX+=440;
                        String balldeltax=coordinates[3];
                        String balldeltay=coordinates[4];
                        String ballx=coordinates[5];
                        String bally=coordinates[6];
                        ballDeltaX=Integer.parseInt(balldeltax);
                        ballDeltaY=Integer.parseInt(balldeltay);
                        ballX=Integer.parseInt(ballx);
                        ballY=Integer.parseInt(bally);
                        System.out.println("ball collision msg recieved");
                      
                    }
                }
                if(msg.charAt(msg.length()-1)=='z')
                {
                    ballDeltaX=0;
                    ballDeltaY=0;
                    ballX=240;
                    ballY=240;
                    if(msg.charAt(0)=='l')
                    {
                        playerlvLives--;
                    }
                    if(msg.charAt(0)=='d')
                    {
                        playerdhLives--;
                    }
                    if(msg.charAt(0)=='r')
                    {
                        playerrvLives--;
                    }
                    if(msg.charAt(0)=='u')
                    {
                        playeruhLives--;
                    }
		    reset_AI();
                }
                else if(msg.equals("1"))
                {
                    ballDeltaX=initialballDeltaX;
                    ballDeltaY=initialballDeltaY;
                }

                if(msg.equals("lv_quit"))
                {
                    islv=false;
                    connectionlv=false;
                    //AI(2,"left");
                }
                if(msg.equals("dh_quit"))
                {
                    isdh=false;
                     connectiondh=false;
                    //AI(2,"down");
                }
                if(msg.equals("rv_quit"))
                {
                    isrv=false;
                     connectionrv=false;
                    //AI(2,"right");
                }
                if(msg.equals("uh_quit"))
                {
                    isuh=false;
                     connectionuh=false;
                    //AI(2,"up");
                }

            } 
            catch (IOException e)
            {
                break;
            }
        }
    }


    public void step(){

    	//%%%
    	iteration++;
    	if(shiftpressed && boost>=2) {
        	boost-=2;
        	paddleSpeed=20;
         }
         else{
         	paddleSpeed=10;
         }
         if(!shiftpressed&&iteration%12==0){
         	paddleSpeed=10;
         	if(boost<=199 )
         	{boost+=1;}
         }


    	//%%%

    	 if(location.equals("lv")&&playerlvLives<=0)
    	 {
    	 	System.out.println("YOU LOST");
    	 	qpressed=true;

    	 }
    	  if(location.equals("dh")&&playerdhLives<=0)
    	 {
    	 	System.out.println("YOU LOST");
    	 	qpressed=true;
    	 	
    	 }
    	 	 if(location.equals("uh")&&playeruhLives<=0)
    	 {
    	 	System.out.println("YOU LOST");
    	 	qpressed=true;
    	 	
    	 }
    	  if(location.equals("rv")&&playerrvLives<=0)
    	 {
    	 	System.out.println("YOU LOST");
    	 	qpressed=true;
    	 	
    	 }
    	if(qpressed)
    	{
    		System.out.println("quitting");
            if(location.equals("lv")){
            	try{
                    int n= addresses.size();
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"lv_quit");
                    }
                }
                catch(IOException e){
                    System.out.println("exception");
                }
            SwingControlDemo exit = new SwingControlDemo();  
    	 	exit.exit(playerlvLives);
            }

            if(location.equals("dh")){
            	try{
                    int n= addresses.size();
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"dh_quit");
                    }
                }
                catch(IOException e){
                    System.out.println("exception");
                }
            SwingControlDemo exit = new SwingControlDemo();  
    	 	exit.exit(playerdhLives);
            }

            if(location.equals("rv")){
            	try{
                    int n= addresses.size();
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"rv_quit");
                    }
                }
                catch(IOException e){
                    System.out.println("exception");
                }
            SwingControlDemo exit = new SwingControlDemo();  
    		 exit.exit(playerrvLives);
            }

            if(location.equals("uh")){
            	try{
                    int n= addresses.size();
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"uh_quit");
                    }
                }catch(IOException e){
                    System.out.println("exception");
                }

            SwingControlDemo exit = new SwingControlDemo();  
    	 	exit.exit(playeruhLives);
            }

            qpressed=false;
            location="lu";   
    	}

        //where will the ball be after it moves?
        float nextBallLeft = ballX + ballDeltaX;
        float nextBallRight = ballX + diameter + ballDeltaX;
        float nextBallTop = ballY + ballDeltaY;
        float nextBallBottom = ballY + diameter + ballDeltaY;
        if(waittimer>0)
        {
            waittimer--;
        }else if(waittimer==0){
            ballDeltaX=initialballDeltaX;
            ballDeltaY=initialballDeltaY;
            waittimer=-1;
            try{
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                    InetSocketAddress tempaddress=addresses.get(i);
                    sendTo(tempaddress,"1");
                }
            }
            catch(IOException e)
            {
                System.out.println("IOException caught");
            }
        }
        if(!location.equals("lv")&& !islv)
        {
            
            AI(2,"left",nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
        if(!location.equals("dh")&& !isdh)
        {
            AI(2,"down",nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
        if(!location.equals("rv")&& !isrv)
        {
            AI(2,"right",nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
        if(!location.equals("uh")&& !isuh)
        {
            AI(2,"up",nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //move player 1
        //int move1=0;
        //System.out.println(ballX+" "+ballY+" "+looptimer++);
        if(location.equals("lv"))
        {
            lv(nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
        else if(location.equals("dh"))
        {
            dh(nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
        else if(location.equals("rv"))
        {
            rv(nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
        else if(location.equals("uh"))
        {
            uh(nextBallLeft, nextBallRight, nextBallTop, nextBallBottom);
        }
    
        try
        {
            int n= addresses.size();  
            for(int i=0;i<n;i++){
            InetSocketAddress tempaddress=addresses.get(i);
            sendTo(tempaddress,positionmsg);
            }   
    
        }
        catch(IOException e)
        {
            System.out.println("IOException caught");
        }
        
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        /*int playerTwoLeft = playerTwoX;
        int playerTwoTop = playerTwoY;
        int playerTwoBottom = playerTwoY + playerTwoHeight;
        */
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////       
        //ball bounces off top and bottom of screen
        /*if (nextBallTop < 0 || nextBallBottom > getHeight()) {
          //if (nextBallBottom>getHeight()){
            ballDeltaY *= -1;
        }*/

                //will the ball go off the left side?
        
       
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //move the ball
        ballX += ballDeltaX;
        ballY += ballDeltaY;

        //stuff has moved, tell this JPanel to repaint itself
        repaint();
    }

    public void lv(float nextBallLeft,float nextBallRight,float nextBallTop,float nextBallBottom)
    {
        playerlv_move();
            int localplayerRight = localplayerX + localplayerWidth;
            int localplayerTop = localplayerY;
            int localplayerBottom = localplayerY + localplayerHeight;

            if (nextBallLeft < localplayerRight) { 
            //is it going to miss the paddle?
                //here also write the code of corner case
            if ( nextBallTop > localplayerBottom || nextBallBottom < localplayerTop) {

               // playerTwoScore ++;
                try{
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"lv_z");
                    }
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
                waittimer=20;

                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playerlvLives--;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
		reset_AI();
                
            }
            else {
                System.out.println("collision detected");
                ballDeltaX *= -1;
               /* if((ballY>localplayerY&&ballY-localplayerY<=5)||ballY<localplayerY+localplayerHeight&&ballY>=localplayerY+localplayerHeight-5)
                {
                    lvedge=true;
                }

                
              if(lvedge)
                {
                    temp_y1=ballDeltaY;
                    ballDeltaY*=2;

                }
                else
                {
                    if(ballDeltaY>0){ballDeltaY=Math.abs(temp_y1);}
                    else{ballDeltaY=-1*Math.abs(temp_y1);}
                }
                */

                String positionballmsg="";
                positionballmsg=positionballmsg+"lv"+"_"+localplayerX+"_"+localplayerY+"_"+ballDeltaX+"_"+ballDeltaY+"_"+ballX+"_"+ballY;
                //System.out.println(positionballmsg+" "+looptimer++);
                //System.out.println(ballX+" "+ballY);
                try
                {
                    System.out.println("entered try");
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,positionballmsg);
                    } 
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
            }
        
        }
        positionmsg="lv"+"_"+localplayerX+"_"+localplayerY;
        //positionmsg=positionmsg;
        
    }
    public void dh(float nextBallLeft,float nextBallRight,float nextBallTop,float nextBallBottom)
    {
        playerdh_move();
            int localplayerTop = localplayerY;
            int localplayerRight = localplayerX + localplayerWidth;  
            int localplayerLeft = localplayerX ;

            if (nextBallBottom > localplayerTop) { 
            //is it going to miss the paddle?
            if ( nextBallRight<localplayerLeft|| nextBallLeft > localplayerRight) {

               // playerTwoScore ++;
                try{
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"dh_z");
                    }
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
                waittimer=20;
                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playerdhLives--;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
		reset_AI();
                
            }
            else {
                System.out.println("collision detected");
                ballDeltaY *= -1;

                /*

                if((ballX>localplayerX&&ballX-localplayerX<=5)||ballX<localplayerX+localplayerWidth&&ballX>=localplayerX+localplayerWidth-5)
                {
                    dhedge=true;
                }

                if(dhedge)
                {
                    temp_x=ballDeltaX;
                    ballDeltaX*=2;

                }
                else
                {
                    if(ballDeltaX>0){ballDeltaX=Math.abs(temp_x);}
                    else{ballDeltaX=-1*Math.abs(temp_x);}
                }
                */
                String positionballmsg="";
                positionballmsg=positionballmsg+"dh"+"_"+localplayerX+"_"+localplayerY+"_"+ballDeltaX+"_"+ballDeltaY+"_"+ballX+"_"+ballY;
                try
                {
                    System.out.println("entered try");
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,positionballmsg);
                    }  
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
            }
        }

        positionmsg="dh"+"_"+localplayerX+"_"+localplayerY;

    }
    public void rv(float nextBallLeft,float nextBallRight,float nextBallTop,float nextBallBottom)
    {
        playerrv_move();
            int localplayerLeft= localplayerX;
            int localplayerTop = localplayerY;
            int localplayerBottom = localplayerY + localplayerHeight;

            if (nextBallRight > localplayerLeft) { 
            //is it going to miss the paddle?
                //here also write the code of corner case
            if ( nextBallTop > localplayerBottom || nextBallBottom < localplayerTop) {

               // playerTwoScore ++;
                try{
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"rv_z");
                    }
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
                waittimer=20;
                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
                playerrvLives--;
		reset_AI();
                
            }
            else {
                System.out.println("collision detected");
                ballDeltaX *= -1;
                /*

                if((ballY>localplayerY&&ballY-localplayerY<=5)||ballY<localplayerY+localplayerHeight&&ballY>=localplayerY+localplayerHeight-5)
                {
                    rvedge=true;
                }

                if(rvedge)
                {
                    temp_y=ballDeltaY;
                    ballDeltaY*=2;

                }
                else
                {
                    if(ballDeltaY>0){ballDeltaY=Math.abs(temp_y);}
                    else{ballDeltaY=-1*Math.abs(temp_y);}
                }
                */

                String positionballmsg="";
                positionballmsg=positionballmsg+"rv"+"_"+localplayerX+"_"+localplayerY+"_"+ballDeltaX+"_"+ballDeltaY+"_"+ballX+"_"+ballY;
                try
                {
                    System.out.println("entered try");
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,positionballmsg);
                    }   
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
            }
        }
        positionmsg="rv"+"_"+localplayerX+"_"+localplayerY;

    }
    public void uh(float nextBallLeft,float nextBallRight,float nextBallTop,float nextBallBottom)
    {
            playeruh_move();
            int localplayerBottom = localplayerY+localplayerHeight;
            int localplayerLeft = localplayerX;  
            int localplayerRight = localplayerX + localplayerWidth;

            if (nextBallTop < localplayerBottom) { 
            //is it going to miss the paddle?
                System.out.println(nextBallTop);
            if ( nextBallRight<localplayerLeft|| nextBallLeft > localplayerRight) {

               // playerTwoScore ++;
                try{
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,"uh_z");
                    }
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
                waittimer=20;
                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playeruhLives--;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
		reset_AI();                
            }
            else {
                System.out.println("collision detected");
                ballDeltaY *= -1;
                /*

                if((ballX>localplayerX&&ballX-localplayerX<=5)||ballX<localplayerX+localplayerWidth&&ballX>=localplayerX+localplayerWidth-5)
                {
                    uhedge=true;
                }

                if(uhedge)
                {                   
                    temp_x1=ballDeltaX;
                    ballDeltaX*=2;

                }
                else
                {
                    if(ballDeltaX>0){ballDeltaX=Math.abs(temp_x1);}
                    else{ballDeltaX=-1*Math.abs(temp_x1);}
                }
                */
                String positionballmsg="";
                positionballmsg=positionballmsg+"uh"+"_"+localplayerX+"_"+localplayerY+"_"+ballDeltaX+"_"+ballDeltaY+"_"+ballX+"_"+ballY;
                try
                {
                    System.out.println("entered try");
                    int n= addresses.size();  
                    for(int i=0;i<n;i++){
                        InetSocketAddress tempaddress=addresses.get(i);
                        sendTo(tempaddress,positionballmsg);
                    }   
                }
                catch(IOException e)
                {
                    System.out.println("IOException caught");
                }
            }
        }
        positionmsg="uh"+"_"+localplayerX+"_"+localplayerY;
    }
    public void playerlv_move()
    {
        //int bool=0;
        if (upPressed) {  
            //bool=1;                                       //means positive direction is below. because on up press, y value is being reduced
            if (localplayerY-paddleSpeed > 0) {
                localplayerY -= paddleSpeed;
            }
        }
        if (downPressed) {
            if (localplayerY + paddleSpeed + localplayerHeight < getHeight()) {
                localplayerY += paddleSpeed;
            }
        }
        //return bool;
    }
    
    public void playerrv_move()
    {
        //int bool=0;
        if (upPressed) {  
            //bool=1;                                       //means positive direction is below. because on up press, y value is being reduced
            if (localplayerY-paddleSpeed > 0) {
                localplayerY -= paddleSpeed;
            }
        }
        if (downPressed) {
            if (localplayerY + paddleSpeed + localplayerHeight < getHeight()) {
                localplayerY += paddleSpeed;
            }
        }
        //return bool;
    }
    public void playerdh_move()
    {
        //int bool=0;
        if (leftPressed) {  
            //bool=1;                                       //means positive direction is below. because on up press, y value is being reduced
            if (localplayerX-paddleSpeed > 0) {
                localplayerX -= paddleSpeed;
            }
        }
        if (rightPressed) {
            if (localplayerX + paddleSpeed + localplayerWidth < getHeight()) {
                localplayerX += paddleSpeed;
            }
        }
        //return bool;
    }
    public void playeruh_move()
    {
        //int bool=0;
        if (leftPressed) {  
            //bool=1;                                       //means positive direction is below. because on up press, y value is being reduced
            if (localplayerX-paddleSpeed > 0) {
                localplayerX -= paddleSpeed;
            }
        }
        if (rightPressed) {
            if (localplayerX + paddleSpeed + localplayerWidth < getHeight()) {
                localplayerX += paddleSpeed;
            }
        }
        //return bool;
    }

	public void reset_AI(){
		playerOneX = 250;                                
	    playerOneY = 475;
		playerTwoX = 475;
        playerTwoY = 250;
		playerThreeX = 250;                          
        playerThreeY = 15;
		playerFourX = 15;                                  
        playerFourY = 250;   

                                                          
	}
    public void AI(int level, String position,float nextBallLeft,float nextBallRight,float nextBallTop,float nextBallBottom)           //Different AI implementation to test on different circumstances
    {
            //if(level==1){gap=100; conf=5000;}
            //if(level==2){gap=250;conf=2000;}
            //if(level==3){gap=400; conf=400;}
            int up_barrier=playerThreeY+playerThreeHeight;
            int down_barrier=playerOneY;
            int left_barrier=playerFourX+playerFourWidth;
            int right_barrier=playerTwoX;

             if(position.equals("down")){

                if(ballDeltaX<=8&& ballDeltaX>=-8){comp_paddle=ballDeltaX;}
                else {
                    if(ballDeltaX>0){comp_paddle=8;}
                    else{comp_paddle=-8;}
                }
                
                if(comp_paddle>0)
                {
                    if(playerOneX+playerOneWidth+comp_paddle<right_barrier){playerOneX+=comp_paddle;}
                }
                else
                {
                    if(playerOneX+comp_paddle>left_barrier){playerOneX+=comp_paddle;}
                }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if (nextBallBottom > playerOneY) { 
            //is it going to miss the paddle?
            if ( nextBallRight<playerOneX|| nextBallLeft > playerOneX+playerOneWidth) {

               // playerTwoScore ++;
                
                waittimer=20;
                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playerdhLives--;
		reset_AI();                              
                
            }
            else {
                //System.out.println("collision detected");
                ballDeltaY *= -1;
                /*

                if((ballX>playerOneX&&ballX-playerOneX<=5)||ballX<playerOneX+playerOneWidth&&ballX>=playerOneX+playerOneWidth-5)
                {
                    dhedge=true;
                }

                if(dhedge)
                {
                    temp_x=ballDeltaX;
                    ballDeltaX*=2;

                }
                else
                {
                    if(ballDeltaX>0){ballDeltaX=Math.abs(temp_x);}
                    else{ballDeltaX=-1*Math.abs(temp_x);}
                }
                */
            }
        }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }

            if(position.equals("right")){


                 if(ballDeltaY<=8&& ballDeltaY>=-8){comp_paddle2=ballDeltaY;}
                else {
                    if(ballDeltaY>0){comp_paddle2=8;}
                    else{comp_paddle2=-8;}
                }
                
                if(comp_paddle2>0)
                {
                    if(playerTwoY+playerOneHeight+comp_paddle2<down_barrier){playerTwoY+=comp_paddle2;}
                }
                else
                {
                    if(playerTwoY+comp_paddle2>up_barrier){playerTwoY+=comp_paddle2;}
                }
/////////////////////////////////////////////////////////////////////////////////////////////////////
                 if (nextBallRight > playerTwoX) { 
            //is it going to miss the paddle?
                //here also write the code of corner case
                if ( nextBallTop > playerTwoY+playerTwoWidth || nextBallBottom < playerTwoY) {

               // playerTwoScore ++;
                
                waittimer=20;
                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playerrvLives--;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
		reset_AI();
            }
            else {
                System.out.println("collision detected");
                ballDeltaX *= -1;
                /*

                if((ballY>playerTwoY&&ballY-playerTwoY<=5)||ballY<playerTwoY+playerTwoHeight&&ballY>=playerTwoY+playerTwoHeight-5)
                {
                    rvedge=true;
                }

                if(rvedge)
                {
                    temp_y=ballDeltaY;
                    ballDeltaY*=2;

                }
                else
                {
                    if(ballDeltaY>0){ballDeltaY=Math.abs(temp_y);}
                    else{ballDeltaY=-1*Math.abs(temp_y);}
                }
				*/
            }
        }  
//////////////////////////////////////////////////////////////////////////////////////////////////////

            }

            if(position.equals("up")){

                if(ballDeltaX<=8&& ballDeltaX>=-8){comp_paddle3=ballDeltaX;}
                else {
                    if(ballDeltaX>0){comp_paddle3=8;}
                    else{comp_paddle3=-8;}
                }
                
                if(comp_paddle3>0)
                {
                    if(playerThreeX+playerThreeWidth+comp_paddle3<right_barrier){playerThreeX+=comp_paddle3;}
                }
                else
                {
                    if(playerThreeX+comp_paddle3>left_barrier){playerThreeX+=comp_paddle3;}
                }

                ///////////////////////////////////////////////////////////////////////////////////////////////////
                if (nextBallTop < playerThreeY+playerThreeHeight) { 
            //is it going to miss the paddle?
                System.out.println(nextBallTop);
            if ( nextBallRight<playerThreeX|| nextBallLeft > playerThreeX+playerThreeWidth) {

               // playerTwoScore ++;
                
                waittimer=20;
                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playeruhLives--;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
		reset_AI();                 
                
            }
            else {
                System.out.println("collision detected");
                ballDeltaY *= -1;
                /*

                if((ballX>playerThreeX&&ballX-playerThreeX<=5)||ballX<playerThreeX+playerThreeWidth&&ballX>=playerThreeX+playerThreeWidth-5)
                {
                    uhedge=true;
                }

                if(uhedge)
                {                   
                    temp_x1=ballDeltaX;
                    ballDeltaX*=2;

                }
                else
                {
                    if(ballDeltaX>0){ballDeltaX=Math.abs(temp_x1);}
                    else{ballDeltaX=-1*Math.abs(temp_x1);}
                }
                */
                
            }
        }
                ////////////////////////////////////////////////////////////////////////////////////////////////////

            }

            if(position.equals("left")){
            	

                if(ballDeltaY<=8&& ballDeltaY>=-8){comp_paddle4=ballDeltaY;}
                else {
                    if(ballDeltaY>0){comp_paddle4=8;}
                    else{comp_paddle4=-8;}
                }
                
                if(comp_paddle4>0)
                {
                    if(playerFourY+playerOneHeight+comp_paddle4<down_barrier){playerFourY+=comp_paddle4;}
                }
                else
                {
                    if(playerFourY+comp_paddle4>up_barrier){playerFourY+=comp_paddle4;}
                }

                ///////////////////////////////////////////////////////////////////////////////////////////////

            if (nextBallLeft < playerFourX+playerFourWidth) { 
            //is it going to miss the paddle?
                //here also write the code of corner case
            if ( nextBallTop > playerFourY+playerFourHeight || nextBallBottom < playerFourY) {

               
                waittimer=20;

                ballX = 240;
                ballY = 240;
                ballDeltaX = 0;
                ballDeltaY = 0;
                playerlvLives--;
                //send a message for telling to increse the score and 
                //also to place the ball again at centre
                
		reset_AI();                          
            }
            else {
                System.out.println("collision detected");
                ballDeltaX *= -1;
                /*
                if((ballY>playerFourY&&ballY-playerFourY<=5)||ballY<playerFourY+playerFourHeight&&ballY>=playerFourY+playerFourHeight-5)
                {
                    lvedge=true;
                }

                if(lvedge)
                {
                    temp_y1=ballDeltaY;
                    ballDeltaY*=2;

                }
                else
                {
                    if(ballDeltaY>0){ballDeltaY=Math.abs(temp_y1);}
                    else{ballDeltaY=-1*Math.abs(temp_y1);}
                }
                */
            }
        
        }
                ///////////////////////////////////////////////////////////////////////////////////////////////

            }
        
    }

    //paint the game screen
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Color textColor = new Color(36, 255, 9);
        g.setColor(Color.GREEN);

        if(location.equals("lv")){
            g.drawLine(localplayerX+localplayerWidth, 0,localplayerX+localplayerWidth, getHeight());
        }
        else if(location.equals("dh")){
            g.drawLine(0,localplayerY,getWidth(), localplayerY);
        }
        else if(location.equals("rv")){
            g.drawLine(localplayerX, 0,localplayerX, getHeight());
        }
        else if(location.equals("uh")){
            g.drawLine(0,localplayerY+localplayerHeight,getWidth(), localplayerY+localplayerHeight);
        }
        //draw the scores
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
        //g.drawString(String.valueOf(playerOneScore), 100, 100);
        //g.drawString(String.valueOf(playerTwoScore), 400, 100);

        //draw the ball
        g.fillOval(ballX, ballY, diameter, diameter);

        //draw the paddles
        g.fillRoundRect(localplayerX, localplayerY, localplayerWidth, localplayerHeight, horizontalround, verticalround);

        if(islv)
        {
            g.drawLine(lvX+lvWidth, 0,lvX+lvWidth, getHeight());
            g.fillRoundRect(lvX, lvY, lvWidth, lvHeight, horizontalround, verticalround);
        }
        if(isdh)
        {
            g.drawLine(0,dhY, getWidth(), dhY);
            g.fillRoundRect(dhX, dhY, dhWidth, dhHeight, horizontalround, verticalround);
        }
        if(isrv)
        {
            g.drawLine(rvX, 0,rvX, getHeight());
            g.fillRoundRect(rvX, rvY, lvWidth, lvHeight, horizontalround, verticalround);
        }
        if(isuh)
        {
            g.drawLine(0,uhY+uhHeight, getWidth(), uhY+uhHeight);
            g.fillRoundRect(uhX, uhY, uhWidth, uhHeight, horizontalround, verticalround);
        }

        if(!location.equals("lv")&& !islv)
        {
        		
            g.drawLine(playerFourX+playerFourWidth, 0,playerFourX+playerFourWidth, getHeight());
            g.fillRoundRect(playerFourX, playerFourY, playerFourWidth, playerFourHeight, horizontalround, verticalround);
        }
        if(!location.equals("dh")&& !isdh)
        {
            g.drawLine(0,playerOneY, getWidth(), playerOneY);
            g.fillRoundRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight, horizontalround, verticalround);
        }
        if(!location.equals("rv")&& !isrv)
        {
            g.drawLine(playerTwoX, 0,playerTwoX, getHeight());
            g.fillRoundRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight, horizontalround, verticalround);
        }
        if(!location.equals("uh")&& !isuh)
        {
            g.drawLine(0,playerThreeY+playerThreeHeight, getWidth(), playerThreeY+playerThreeHeight);
            g.fillRoundRect(playerThreeX, playerThreeY, playerThreeWidth, playerThreeHeight, horizontalround, verticalround);
        }
        /*g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
        g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
        g.fillRect(playerThreeX, playerThreeY, playerThreeWidth, playerThreeHeight);*/
        //g.fillRect(playerFourX, playerFourY, playerFourWidth, playerFourHeight);

         g.setColor(Color.WHITE);
         g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
       	 g.drawString("1-  "+String.valueOf(playeruhLives), 250, 100);
       	 g.drawString("2-  "+String.valueOf(playerrvLives), 400, 250);
       	 g.drawString("3-  "+String.valueOf(playerlvLives), 100, 250);
       	 g.drawString("4-  "+String.valueOf(playerdhLives), 250, 400);

       	  //%%%
       	 g.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
       	 g.drawString("Boost-  "+String.valueOf(boost), 250, 250);

       	 g.setColor(Color.RED);

       	 if(location.equals("uh"))
       	 {

       		 if(iteration%400==0)
       	 	{	System.out.println(iteration);
       	 		uh_start=true;
       	 		 coord_uh=(iteration%250)+100;
       	 	
       	 	}
       	 	if(uh_start)
       	 	{
       	 		g.fillRect(coord_uh,uh_y,5,5);
       	 		uh_y-=2;
       	 	}


       	 	//Detect collision from uh and set uh_start=false and temp_uh 		uhX, uhY, uhWidth, uhHeight,

       	 	if(uh_y<=playerThreeY+uhHeight)
       	 	{
       	 		if(coord_uh>localplayerX&&coord_uh<localplayerX+uhWidth)
       	 		{
       	 			
       	 			temp_uh=iteration;
       	 			System.out.println("xD"+temp_uh);
       	 		}
       	 		uh_start=false;
       	 		uh_y=250;
       	 		System.out.println("xD"+coord_uh+" "+playerThreeX+" "+uhX+" "+localplayerX);
       	 		
       	 	}

       	 	if(iteration-temp_uh<=100)
       	 	{
       	 		localplayerWidth=100;
       	 	}
       	 	else
       	 	{
       	 		localplayerWidth=50;
       	 	}

       	 }

       	 if(location.equals("dh"))
       	 {

       		 if(iteration%400==100)
       	 	{	System.out.println(iteration);
       	 		dh_start=true;
       	 		 coord_dh=(iteration%250)+100;
       	 	
       	 	}
       	 	if(dh_start)
       	 	{
       	 		g.fillRect(coord_dh,dh_y,5,5);
       	 		dh_y+=2;
       	 	}


       	 	//Detect collision from uh and set uh_start=false and temp_uh 		uhX, uhY, uhWidth, uhHeight,

       	 	if(dh_y>=playerOneY)
       	 	{
       	 		if(coord_dh>localplayerX&&coord_dh<localplayerX+dhWidth)
       	 		{
       	 			
       	 			temp_dh=iteration;
       	 			System.out.println("xD"+temp_dh);
       	 		}
       	 		dh_start=false;
       	 		dh_y=250;
       	 		System.out.println("xD"+coord_dh+" "+playerOneX+" "+dhX+" "+localplayerX);
       	 		
       	 	}

       	 	if(iteration-temp_dh<=100)
       	 	{
       	 		localplayerWidth=100;
       	 	}
       	 	else
       	 	{
       	 		localplayerWidth=50;
       	 	}

       	 }

       	  if(location.equals("lv"))
       	 {

       		 if(iteration%400==200)
       	 	{	System.out.println(iteration);
       	 		lv_start=true;
       	 		 coord_lv=(iteration%250)+100;
       	 	
       	 	}
       	 	if(lv_start)
       	 	{
       	 		g.fillRect(lv_x,coord_lv,5,5);
       	 		lv_x-=2;
       	 	}


       	 	//Detect collision from uh and set uh_start=false and temp_uh 		uhX, uhY, uhWidth, uhHeight,

       	 	if(lv_x<=playerFourX+playerFourWidth)
       	 	{
       	 		if(coord_lv>localplayerY&&coord_lv<localplayerY+playerFourHeight)
       	 		{
       	 			
       	 			temp_lv=iteration;
       	 			System.out.println("xD"+temp_lv);
       	 		}
       	 		lv_start=false;
       	 		lv_x=250;
       	 		System.out.println("xD"+coord_lv+" "+playerFourX+" "+uhX+" "+localplayerX);
       	 		
       	 		
       	 	}

       	 	if(iteration-temp_lv<=100)
       	 	{
       	 		localplayerHeight=100;
       	 		
       	 	}
       	 	else
       	 	{
       	 		localplayerHeight=50;
       	 	}

       	 }

       	  if(location.equals("rv"))
       	 {

       		 if(iteration%300==0)
       	 	{	System.out.println(iteration);
       	 		rv_start=true;
       	 		 coord_rv=(iteration%250)+100;
       	 	
       	 	}
       	 	if(rv_start)
       	 	{
       	 		g.fillRect(rv_x,coord_rv,5,5);
       	 		rv_x+=2;
       	 	}


       	 	//Detect collision from uh and set uh_start=false and temp_uh 		uhX, uhY, uhWidth, uhHeight,

       	 	if(rv_x>=playerTwoX)
       	 	{
       	 		if(coord_rv>localplayerY&&coord_rv<localplayerY+playerFourHeight)
       	 		{
       	 			
       	 			temp_rv=iteration;
       	 			System.out.println("xD"+temp_rv);
       	 		}
       	 		rv_start=false;
       	 		rv_x=250;
       	 		System.out.println("xD"+coord_rv+" "+playerFourX+" "+uhX+" "+localplayerX);
       	 		
       	 		
       	 	}

       	 	if(iteration-temp_rv<=100)
       	 	{
       	 		localplayerHeight=100;
       	 		
       	 	}
       	 	else
       	 	{
       	 		localplayerHeight=50;
       	 	}

       	 }







       	 //%%%


         
    }

    public void keyTyped(KeyEvent e) {}



    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        else if(e.getKeyCode()==KeyEvent.VK_Q){
        	qpressed=true;
        }
         //%%%
        else if(e.getKeyCode()==KeyEvent.VK_SHIFT){
        	shiftpressed=true;
        }
        //%%%
    }


    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            wPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            sPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
         //%%%
        else if(e.getKeyCode()==KeyEvent.VK_SHIFT){
        	shiftpressed=false;
        }
        //%%%
    }

}
