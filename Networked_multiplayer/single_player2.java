
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;

public class single_player extends JPanel implements ActionListener, KeyListener{
	Random randomno=new Random();

	
   
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean lshiftpressed=false;
    private boolean qpressed=false;
    boolean leave=false;
    private int ballX = 250;
    private int ballY = 250;
    private int diameter = 10;

    
    private int ballDeltaX = 3;
    private int ballDeltaY = 3;
    int temp_x;

    private int playerOneX = 250;                                //this represents the left coordinate of the bat
    private int playerOneY = 425;                               // this represents the topmost coordinated of bat
    private int playerOneWidth = 50;
    private int playerOneHeight = 10;
    private int left_barrier=25;
    private int right_barrier=475;

   

    private int playerFourX = 250;                          //player 4 is the computer player
    private int playerFourY = 25;
    private int playerFourWidth = 50;
    private int playerFourHeight = 10;

		int nextBallLeft;
        int nextBallRight ;
        int nextBallTop ;
        int nextBallBottom;
        int BallTop;
        int BallBottom;

        int playerOneRight;
        int playerOneTop ;
        int playerOneBottom ;

    float playerFourBottom;
    float playerFourLeft;
    float playerFourRight;

    private int paddleSpeed = 5; 
    int comp_paddle;       //represents speed of the bat

    private int playerOneScore = 0;
    
    int i=0;
    int temp=0;
    int temp2=0;
    private int splpwrX=-10000;
    private int splpwrY=-10000;
    private int splpwrspdY=1;
    
    boolean spl=true;
    boolean success=false;
   
   
   int playerOneLives=15;
   
   
   int freeze=0;
   boolean edge=false;

   int boost=200;
   int level;
   boolean pause=false;
   boolean resume=true;
   int gap,conf;
   Timer timer = new Timer(1000/60, this);

    //construct a PongPanel
    public single_player(int a){

        setBackground(Color.BLACK);
        level=a;

        //listen to key presses
        setFocusable(true);

        addKeyListener(this);

        //call step() 60 fps
        


        timer.start();
        reset_ball();
    }


    public void actionPerformed(ActionEvent e){
        step();
    }

    public void exit()
    {
    	 timer.stop();
    	 leave=true;
    	 

    	 SwingControlDemo exit = new SwingControlDemo();  
    	 exit.exit(playerOneScore,playerOneLives);

    }

    public void reset_ball()
    {				

    			 
                    try {
                     Thread.sleep(1000);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                        }

                 ballX = 250;
           		ballY = 250;
           		

               
                int a=randomno.nextInt(2000)%45;
                double x=new Double((4)*Math.cos((a+22.5)*Math.PI/180));
                double y=(4)*Math.sin((a+22.5)*Math.PI/180);

                ballDeltaX=(int)x;
                ballDeltaY=(int)y;

                if(a<22.5)
                {
                	if(a<11){ballDeltaX*=-1;}
                }
                else
                {
                	if(a>34){ballDeltaY*=-1;}
                	else{ballDeltaY*=-1;ballDeltaX*=-1;}
                }

                //System.out.println("  "+a+22.5+"ww "+ballDeltaX+" "+ballDeltaY);
                if(level==2){ballDeltaX*=1.3;ballDeltaY*=1.3;}
			   if(level==3){ballDeltaX*=2.5;ballDeltaY*=1.8;}
               temp_x=ballDeltaX;
/*
                
               ballDeltaX=3*(randomno.nextInt(1));
                if(ballDeltaX>0){ballDeltaX+=3;}
                else{ballDeltaX-=3;}
                ballDeltaY=3*(randomno.nextInt(1));
            	if(ballDeltaY>0){ballDeltaY+=3;}
                else{ballDeltaY-=3;}
                
                */
                playerOneLives--;
                edge=false;


    }
    public int player1_move()
   	  {
        int bool=0;
         if(lshiftpressed && boost>=2) {
        	boost-=2;
        	paddleSpeed=12;
         }
         if(!lshiftpressed){
         	paddleSpeed=5;
         	if(boost<=199 && i%20==0)
         	{boost+=1;}
         }
        if (leftPressed) {  
            bool=1;                                       //means positive direction is below. because on up press, y value is being reduced
            if (playerOneX-paddleSpeed > left_barrier) {
                playerOneX -= paddleSpeed;
            }
        }
        if (rightPressed) {
            if (playerOneX + paddleSpeed + playerOneWidth < right_barrier) {
                playerOneX += paddleSpeed;
            }
        }
       

        return bool;
    	}

    public void step(){


    	if(qpressed){
    		exit();
    	}
        

    	if(!pause)
    	{
    		
    		
    	

    	randomno=new Random();

    	int move1=player1_move();   // Player 1 movement
        

        //where will the ball be after it moves?
        nextBallLeft = ballX + ballDeltaX-(diameter/2);
         nextBallRight = ballX + (diameter/2) + ballDeltaX;
        nextBallTop = ballY + ballDeltaY-(diameter/2);
        nextBallBottom = ballY + (diameter/2) + ballDeltaY;
        BallTop = ballY -(diameter/2);
        BallBottom = ballY + (diameter/2) ;

         playerOneRight = playerOneX + playerOneWidth;
         playerOneTop = playerOneY;
         playerOneBottom = playerOneY + playerOneHeight;

        
        //ball bounces off left and right of screen
        
          if (nextBallLeft<left_barrier){
            ballDeltaX *= -1;
        }
        if (nextBallRight>right_barrier){
            ballDeltaX *= -1;
        }

        
        if (BallBottom >= playerOneY) { 
            //is it going to miss the paddle?
            if (nextBallLeft > playerOneRight || nextBallRight < playerOneX) {
            	playerOneLives--;
               
               reset_ball();
            }
            else {
                ballDeltaY *= -1;
                
                
                if((ballX>playerOneX&&ballX-playerOneX<=5)||ballX<playerOneX+50&&ballX>=playerOneX+5)
                {
                	edge=true;
            	}

                if(edge)
                {
                    temp_x=ballDeltaX;
                    ballDeltaX*=2;

                }
                else
                {
                    ballDeltaX=temp_x;
                }
                
                
            }
        }


        //will the ball go off the right side?
        
       
       
        
 
   
      //  int temp = (int)randomno.nextGaussian()*4;
       // computerplay(i,edge,freeze,playerFourX,playerFourY,ballDeltaX,ballDeltaY,ballX,ballY);
         playerFourBottom=playerFourY+playerFourHeight;
         playerFourLeft=playerFourX;
         playerFourRight=playerFourX+playerFourWidth;

         	if(level==1){gap=50; conf=5000;}
         	if(level==2){gap=25;conf=2000;}
         	if(level==3){gap=10; conf=400;}
        
   			if(i%conf==0){freeze=i;edge=true;}
   			if(edge)
   			{
   				if(i-freeze>=gap)
   				{
   					edge=false;
   				}
   			}
   			else
   			{
                AI2();
   				//AI(level);
   				//move_computer();
   			}

   			if(BallTop<playerFourBottom){

            if(nextBallLeft > playerFourRight || nextBallRight < playerFourX)
            {
                playerFourX=250;
                reset_ball();


               
            }
            else{
                
                ballDeltaY *=-1;
            }
        }

       
       // System.out.println(ballDeltaX);
   	/*
       if(edge&&i<=freeze+20){playerFourX=500-ballX;}
       if(i==freeze+21){playerFourX=ballX;}
       */
        

       
        i++;
         //move the ball
         ballX += ballDeltaX;
        ballY += ballDeltaY;

        //stuff has moved, tell this JPanel to repaint itself
    }
        repaint();
       // randomno.randomize();
    }
    
    

    public void move_computer()
    {

    	
    	if(playerFourX+playerFourWidth+ballDeltaX<right_barrier && playerFourX+ballDeltaX>left_barrier)
    	{
    		playerFourX+=ballDeltaX;
    	}
    	
    }

    public void AI(int level)			//Different AI implementation to test on different circumstances
    {
    		if(level==1){gap=100; conf=5000;}
         	if(level==2){gap=250;conf=2000;}
         	if(level==3){gap=400; conf=400;}


    	if(ballY-playerFourY<=gap)
    	{
    		if(ballX-(playerFourX+25)>=0)
    		{
    			if(playerFourX+playerFourWidth+paddleSpeed<right_barrier )
    			{playerFourX+=paddleSpeed;}
    		}
    		else
    		{
    			if(playerFourX-paddleSpeed>left_barrier)
    			{playerFourX-=paddleSpeed;}
    		}
    	}
    }
    public void AI2()
    {       
                if(ballDeltaX<=8&& ballDeltaX>=-8){comp_paddle=ballDeltaX;}
                else {
                    if(ballDeltaX>0){comp_paddle=8;}
                    else{comp_paddle=-8;}
                }
                
                if(comp_paddle>0)
                {
                    if(playerFourX+playerFourWidth+comp_paddle<right_barrier){playerFourX+=comp_paddle;}
                }
                else
                {
                    if(playerFourX-comp_paddle>left_barrier){playerFourX+=comp_paddle;}
                }
            
                
            
               
        

    }

    public void special_bottom()
    {
    	 if(!pause){
        if(i%300==0){splpwrX=250+randomno.nextInt(250); splpwrY=125;}
        splpwrY+=splpwrspdY;
        
        if (!success && splpwrY >= playerOneTop) { 
            //is it going to miss the paddle?
            
            if (splpwrX < playerOneRight && splpwrX> playerOneX) {

                success=true;
                 temp=i;
                splpwrX=-10000;
    		    splpwrY=-10000;
    		    playerOneScore+=5;
            }
            else {
                success=false;
            }
        }
         if(success && i<=temp+200){playerOneWidth=100; }
        else{success=false; playerOneWidth=50;}
    }
    }


    //paint the game screen
    public void paintComponent(Graphics g){


        super.paintComponent(g);
        g.setColor(Color.WHITE);
       //System.out.println(i);
       if(i==1){
        try {
    		Thread.sleep(1000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
   				 Thread.currentThread().interrupt();
				}
			}
        int playerOneRight = playerOneX + playerOneWidth;
       
        int playerFourBottom = playerFourY + playerFourHeight;
        int playerOneTop = playerOneY;
        int playerOneBottom = playerOneY + playerOneHeight;
        

        //draw dashed line down center
        //for (int lineY = 0; lineY < getHeight(); lineY += 50) {
          //  g.drawLine(250, lineY, 250, lineY+25);
        //}

        //draw "goal lines" on each side
       

        //draw the scores
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
        g.drawString("boost  "+String.valueOf(boost), 100, 100);
       
        g.drawString(String.valueOf(playerOneLives), 100, 400);
       

        //draw the ball
        g.fillOval(ballX, ballY, diameter, diameter);

        
        special_bottom();
       
        g.setColor(Color.green);
        g.fillRect(splpwrX,splpwrY,10,10);
        g.setColor(Color.white);

       



    

        //draw the paddles
        g.setColor(Color.green);
        g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
       /* g.setColor(Color.red);
        g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
        g.setColor(Color.white);
        g.fillRect(playerThreeX, playerThreeY, playerThreeWidth, playerThreeHeight);
        */
        g.fillRect(playerFourX, playerFourY, playerFourWidth, playerFourHeight);
    }

    public void keyTyped(KeyEvent e) {}



    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
       
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
        	lshiftpressed=true;
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE){
        	if(pause){resume =true; pause=false;}
        	else{pause=true;}
        }
        else if(e.getKeyCode()==KeyEvent.VK_Q){
        	qpressed=true;
        }
    }


    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT){
        	lshiftpressed=false;
        }
        else if(e.getKeyCode()==KeyEvent.VK_Q){
        	qpressed=false;
        }
    }

}
