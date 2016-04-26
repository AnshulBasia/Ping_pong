import java.awt.BorderLayout;

import javax.swing.JFrame;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Main2{

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
		
		System.out.print("Name : ");
		String name = scanner.nextLine();
		
		System.out.print("Source Port : ");
		int sourcePort = Integer.parseInt(scanner.nextLine());
		
		System.out.print("Destination IP : ");
		String destinationIP = scanner.nextLine();
		
		System.out.print("Destination Port : ");
		int destinationPort = Integer.parseInt(scanner.nextLine());
		//////////////////////////////////////here we will ask which side the player is playing. Based on that, different channel will be called.
		/*Channel channel = new Channel();
		channel.bind(sourcePort);
		channel.start(); // Start Receive
		*/
		System.out.println("Started.");
		
		InetSocketAddress address = new InetSocketAddress(destinationIP, destinationPort);
		/////////////////////////////////////////////////////////////////////////////////////////

        JFrame frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        twoplayer1 two = new twoplayer1();
        two.bind(sourcePort);
        two.start();
        two.getaddress(address);

        frame.add(two, BorderLayout.CENTER);
           
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}