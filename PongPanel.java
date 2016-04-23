
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

public class PongPanel extends JPanel implements ActionListener, KeyListener{
	Random randomno=new Random();
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private int ballX = 250;
    private int ballY = 250;
    private int diameter = 20;

    
    private int ballDeltaX = 3;
    private int ballDeltaY = 3;

    private int playerOneX = 25;                                //this represents the left coordinate of the bat
    private int playerOneY = 250;                               // this represents the topmost coordinated of bat
    private int playerOneWidth = 10;
    private int playerOneHeight = 50;

    private int playerTwoX = 465;
    private int playerTwoY = 250;
    private int playerTwoWidth = 10;
    private int playerTwoHeight = 50;

    private int playerThreeX = 250;
    private int playerThreeY = 445;
    private int playerThreeWidth = 50;
    private int playerThreeHeight = 10;

    private int playerFourX = 250;                          //player 4 is the computer player
    private int playerFourY = 15;
    private int playerFourWidth = 50;
    private int playerFourHeight = 10;

    private int paddleSpeed = 5;        //represents speed of the bat

    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    int i=0;
    int temp=0;
    int temp2=0;
    private int splpwrX=10000;
    private int splpwrY=10000;
    private int splpwrspdX=-1;
    private int splpwr2X=10000;
    private int splpwr2Y=10000;
    private int splpwr2spdX=1;
    boolean spl=true;
    boolean success=false;
    boolean success2=false;
   
   int playerOneLives=15;
   int playerTwoLives=15;
   


    //construct a PongPanel
    public PongPanel(){

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

    public void step(){

    	randomno=new Random();


        //move player 1
        if (upPressed) {                                            //means positive direction is below. because on up press, y value is being reduced
            if (playerOneY-paddleSpeed > 0) {
                playerOneY -= paddleSpeed;
            }
        }
        if (downPressed) {
            if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
                playerOneY += paddleSpeed;
            }
        }

        //move player 2
        if (wPressed) {
            if (playerTwoY-paddleSpeed > 0) {
                playerTwoY -= paddleSpeed;
            }
        }
        if (sPressed) {
            if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
                playerTwoY += paddleSpeed;
            }
        }



        //where will the ball be after it moves?
        int nextBallLeft = ballX + ballDeltaX;
        int nextBallRight = ballX + diameter + ballDeltaX;
        int nextBallTop = ballY + ballDeltaY;
        int nextBallBottom = ballY + diameter + ballDeltaY;

        int playerOneRight = playerOneX + playerOneWidth;
        int playerOneTop = playerOneY;
        int playerOneBottom = playerOneY + playerOneHeight;

        float playerTwoLeft = playerTwoX;
        float playerTwoTop = playerTwoY;
        float playerTwoBottom = playerTwoY + playerTwoHeight;

        
        //ball bounces off top and bottom of screen
        //if (nextBallTop < 0 || nextBallBottom > getHeight()) {
          if (nextBallBottom>getHeight()){
            ballDeltaY *= -1;
        }

        //will the ball go off the left side?
        if (nextBallLeft < playerOneRight) { 
            //is it going to miss the paddle?
            if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

                playerTwoScore ++;

                ballX = 250;
                ballY = 250;
               ballDeltaX=3*(randomno.nextInt(1));
                if(ballDeltaX>0){ballDeltaX+=3;}
                else{ballDeltaX-=3;}
                ballDeltaY=3*(randomno.nextInt(1));
            	if(ballDeltaY>0){ballDeltaY+=3;}
                else{ballDeltaY-=3;}
                playerOneLives--;
            }
            else {
                ballDeltaX *= -1;
            }
        }


        //will the ball go off the right side?
        if (nextBallRight > playerTwoLeft) {
            //is it going to miss the paddle?
            if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

                playerOneScore ++;

                ballX = 250;
                ballY = 250;
                ballDeltaX=3*(randomno.nextInt(1));
                if(ballDeltaX>0){ballDeltaX+=3;}
                else{ballDeltaX-=3;}
                ballDeltaY=3*(randomno.nextInt(1));
            	if(ballDeltaY>0){ballDeltaY+=3;}
                else{ballDeltaY-=3;}
                playerTwoLives--;
            }
            else {
                ballDeltaX *= -1;
            }
        }


        //move the ball
        ballX += ballDeltaX;
        ballY += ballDeltaY;

        
 
   // check next Gaussian value  
        int noise = (int)randomno.nextGaussian()*100;
        //computerplay(noise, ballX, ballDeltaX, playerFourWidth);
        int temp = (int)randomno.nextGaussian()*4;
        if(i%30==0)
       { playerFourX=ballX-2*ballDeltaX-playerFourWidth/2+temp;}
   		playerFourX+=ballDeltaX;
       // System.out.println(ballDeltaX);
        float playerFourBottom=playerFourY+playerFourHeight;
        float playerFourLeft=playerFourX;
        float playerFourRight=playerFourX+playerFourWidth;

        if(nextBallTop<playerFourY){

            if(nextBallLeft<playerFourRight && nextBallRight>playerFourLeft)
            {
                ballDeltaY *=-1;
                randomno = new Random();
                System.out.println("yo");
            }
            else{
                //setBackground(Color.GREEN);
                ballX=250;
                ballY=250;
                ballDeltaX=3*(randomno.nextInt(1));
                if(ballDeltaX>0){ballDeltaX+=3;}
                else{ballDeltaX-=3;}
                ballDeltaY=3*(randomno.nextInt(1));
            	if(ballDeltaY>0){ballDeltaY+=3;}
                else{ballDeltaY-=3;}

            }
        }
        i++;
        //stuff has moved, tell this JPanel to repaint itself
        repaint();
    }

    public void computerplay(int noise,int ballX,int ballDeltaX,int playerFourWidth)
    {
        playerFourX=ballX-ballDeltaX-playerFourWidth/2+noise;
        float playerFourBottom=playerFourY+playerFourHeight;
        float playerFourLeft=playerFourX;
        float playerFourRight=playerFourX+playerFourWidth;

        int nextBallLeft = ballX + ballDeltaX;
        int nextBallRight = ballX + diameter + ballDeltaX;
        int nextBallTop = ballY + ballDeltaY;
        int nextBallBottom = ballY + diameter + ballDeltaY;


        if(nextBallTop<playerFourBottom){
            if(nextBallLeft<playerFourRight && nextBallRight>playerFourLeft)
            {
                ballDeltaY *=-1;
            }
            else{
                setBackground(Color.GREEN);
                //ballX=250;
                //ballY=250;
            }
        }
    }

    //paint the game screen
    public void paintComponent(Graphics g){


        super.paintComponent(g);
        g.setColor(Color.WHITE);
        System.out.println(i);
        int playerOneRight = playerOneX + playerOneWidth;
        int playerTwoLeft =  playerTwoX;
        int playerThreeTop = playerThreeY;
        int playerFourBottom = playerFourY + playerFourHeight;
        int playerOneTop = playerOneY;
        int playerOneBottom = playerOneY + playerOneHeight;
        float playerTwoTop = playerTwoY;
        float playerTwoBottom = playerTwoY + playerTwoHeight;

        //draw dashed line down center
        //for (int lineY = 0; lineY < getHeight(); lineY += 50) {
          //  g.drawLine(250, lineY, 250, lineY+25);
        //}

        //draw "goal lines" on each side
        g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
        g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());
        g.drawLine(0, playerThreeTop, getWidth(), playerThreeTop);
        g.drawLine(0, playerFourBottom, getWidth(),playerFourBottom);

        //draw the scores
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
        g.drawString(String.valueOf(playerOneScore), 100, 100);
        g.drawString(String.valueOf(playerTwoScore), 400, 100);
        g.drawString(String.valueOf(playerOneLives), 100, 400);
        g.drawString(String.valueOf(playerTwoLives), 400, 400);

        //draw the ball
        g.fillOval(ballX, ballY, diameter, diameter);

        g.setColor(Color.green);
        g.fillRect(splpwrX,splpwrY,10,10);
        g.setColor(Color.white);
        if(i%300==0){splpwrX=250; splpwrY=250+ randomno.nextInt(250);  System.out.println("   "+splpwrY);}
        splpwrX+=splpwrspdX;
        if (!success && splpwrX < playerOneRight) { 
            //is it going to miss the paddle?
            if (splpwrY < playerOneBottom && splpwrY> playerOneTop) {

                success=true; temp=i;
                splpwrX=10000;
    		    splpwrY=10000;
    		    playerOneScore+=5;
            }
            else {
                success=false;
            }
        }
         if(success && i<=temp+200){ g.setColor(Color.yellow);playerOneHeight=100;}
        else{success=false; playerOneHeight=50;g.setColor(Color.white);}

       // else{success=false;}

        g.setColor(Color.red);
        g.fillRect(splpwr2X,splpwr2Y,10,10);
         g.setColor(Color.white);
        if(i%500==0){splpwr2X=250; splpwr2Y=250+ randomno.nextInt(250);  System.out.println("   "+splpwrY);}
        splpwr2X+=splpwr2spdX;
        if (!success2 && splpwr2X > playerTwoLeft) { 
            //is it going to miss the paddle?
            if (splpwr2Y < playerTwoBottom && splpwr2Y> playerTwoTop) {

                success2=true; temp2=i;
                splpwr2X=10000;
    		    splpwr2Y=10000;
    		    playerTwoScore+=5;
            }
            else {
                success2=false;
            }
        }

        if(success2 && i<=temp2+200){ g.setColor(Color.yellow);playerTwoHeight=100;}
        else{success2=false; playerTwoHeight=50;g.setColor(Color.white);}






/*

        if(i%200==0){spl=true;}

        if(i>=200&&splpwrX>=playerOneRight&&spl){
        	g.fillRect(splpwrX,splpwrY,10,10);
        	
        }
        if(splpwrX<playerOneRight&&spl){temp=i;splpwrX=250; splpwrY=250+ randomno.nextInt(25); spl=false;}

        //if(i>=200&&spl){splpwrX+=splpwrspdX;}

        if (splpwrX < playerOneRight) { 
            //is it going to miss the paddle?
            if (splpwrY < playerOneBottom && splpwrY> playerOneTop) {

                success=true;
            }
            else {
                success=false;
            }
        }
        if(success && i<=temp+250){playerOneHeight=100;}
        else{success=false; playerOneHeight=50;}


*/

        //draw the paddles
        g.setColor(Color.green);
        g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
        g.setColor(Color.red);
        g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
        g.setColor(Color.white);
        g.fillRect(playerThreeX, playerThreeY, playerThreeWidth, playerThreeHeight);
        g.fillRect(playerFourX, playerFourY, playerFourWidth, playerFourHeight);
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
    }

}
