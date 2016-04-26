
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.*;

public class single_player extends JPanel implements ActionListener, KeyListener{
	Random randomno=new Random();
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean lshiftpressed=false;

    private int ballX = 250;
    private int ballY = 250;
    private int diameter = 10;

    
    private int ballDeltaX = 3;
    private int ballDeltaY = 3;

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

        int playerOneRight;
        int playerOneTop ;
        int playerOneBottom ;

    float playerFourBottom;
    float playerFourLeft;
    float playerFourRight;

    private int paddleSpeed = 5;        //represents speed of the bat

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

    //construct a PongPanel
    public single_player(){

        setBackground(Color.BLACK);

        //listen to key presses
        setFocusable(true);
        addKeyListener(this);

        //call step() 60 fps
        Timer timer = new Timer(1000/60, this);
        timer.start();
    }


    public void actionPerformed(ActionEvent e){
        step();
    }

    public void reset_ball()
    {
    			 ballX = 250;
           		ballY = 250;
           		

               
                int a=randomno.nextInt(2000)%45;
                double x=new Double((4.2)*Math.cos((a+22.5)*Math.PI/180));
                double y=(4.2)*Math.sin((a+22.5)*Math.PI/180);

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

    	randomno=new Random();

    	int move1=player1_move();   // Player 1 movement
        

        //where will the ball be after it moves?
        nextBallLeft = ballX + ballDeltaX;
         nextBallRight = ballX + diameter + ballDeltaX;
        nextBallTop = ballY + ballDeltaY;
        nextBallBottom = ballY + diameter + ballDeltaY;

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

        
        if (nextBallBottom > playerOneY) { 
            //is it going to miss the paddle?
            if (nextBallRight > playerOneRight || nextBallLeft < playerOneX) {

                
               reset_ball();
            }
            else {
                ballDeltaY *= -1;
                if(move1==1)
                {
                    ballDeltaX -=paddleSpeed/6;
                }
                else
                {
                    ballDeltaX +=paddleSpeed/6;
                }
                /*
                if(ballDeltaY>playerOneY+25)
                {
                	if(playerOneY-ballDeltaY<=53&&playerOneY-ballDeltaY>=47)
                	{
                		 freeze=i;//to make computer freeze
                		 edge=true;
                	}
                	else
                	{
                		edge=false;
                	}
                }
            	else
            	{
            		if(playerOneY-ballDeltaY<=5||playerOneY-ballDeltaY>=-5)
            		{
            			 freeze=i;//to make computer freeze
            			 edge=true;
            		}
            		else
            		{
            			edge=false;
            		}
            	}
                
                */
            }
        }


        //will the ball go off the right side?
        
       
       
        
 
   
      //  int temp = (int)randomno.nextGaussian()*4;
       // computerplay(i,edge,freeze,playerFourX,playerFourY,ballDeltaX,ballDeltaY,ballX,ballY);
         playerFourBottom=playerFourY+playerFourHeight;
         playerFourLeft=playerFourX;
         playerFourRight=playerFourX+playerFourWidth;
        
   			if(i%400==0){freeze=i;edge=true;}
   			if(edge)
   			{
   				if(i-freeze>=50)
   				{
   					edge=false;
   				}
   			}
   			else
   			{
   				AI();
   				//move_computer();
   			}

   			if(nextBallTop<playerFourBottom){
            if(nextBallLeft<playerFourRight && nextBallRight>playerFourLeft)
            {
                ballDeltaY *=-1;
                randomno = new Random();
            }
            else{
                
                reset_ball();
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

    public void AI()			//Different AI implementation to test on different circumstances
    {

    	if(ballY-playerFourY<=600)
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

    //paint the game screen
    public void paintComponent(Graphics g){


        super.paintComponent(g);
        g.setColor(Color.WHITE);
       // System.out.println(i);
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

        g.setColor(Color.green);
        g.fillRect(splpwrX,splpwrY,10,10);
        g.setColor(Color.white);
        if(i%300==0){splpwrX=250+randomno.nextInt(250); splpwrY=125;  System.out.println("   "+splpwrY);}
        splpwrY+=splpwrspdY;
        //System.out.println("ko"+playerOneTop+"  "+splpwrY);
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
         if(success && i<=temp+200){ g.setColor(Color.yellow);playerOneWidth=100; }
        else{success=false; playerOneWidth=50;g.setColor(Color.white);}

       





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
    }

}
