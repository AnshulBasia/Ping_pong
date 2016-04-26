/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class Program
{

	public static void main(String[] args) throws IOException
	{

		// Starting main me copy
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Name : ");
		String name = scanner.nextLine();
		
		System.out.print("Source Port : ");
		int sourcePort = Integer.parseInt(scanner.nextLine());
		
		System.out.print("Destination IP : ");
		String destinationIP = scanner.nextLine();
		
		System.out.print("Destination Port : ");
		int destinationPort = Integer.parseInt(scanner.nextLine());
		
		Channel channel = new Channel();
		channel.bind(sourcePort);
		channel.start(); // Start Receive
		
		System.out.println("Started.");
		
		InetSocketAddress address = new InetSocketAddress(destinationIP, destinationPort);

		//finish in main
		
		while(true)
		{

			//starting jaha fram render ho raha hai
			String msg = scanner.nextLine();
			
			if(msg.isEmpty())
				break;
			
			msg = name + " >> " + msg;
			
			channel.sendTo(address, msg);
			System.out.println(msg);
			//end
		}
		//baki game close jaha hota hai
		scanner.close();
		channel.stop();
		
		System.out.println("Closed.");
	}

}
