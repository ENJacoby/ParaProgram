	import javax.sound.sampled.AudioFileFormat;
	import javax.sound.sampled.AudioFormat;
	import javax.sound.sampled.AudioInputStream;
	import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.BufferedInputStream;
import java.io.File;
	import java.io.OutputStream;
	import java.net.InetSocketAddress;
	import java.net.ServerSocket;
	import java.net.Socket;


public class Server2 {
	private static BufferedInputStream inputStream;
	public static void main(String[] args) {
		try { 
			ServerSocket ss = new ServerSocket(); //Create new Server Socket
			ss.bind(new InetSocketAddress(5555)); //Connect on port 5555
			Socket client = null;
			if(ss.isBound()) {
				client = ss.accept(); //Wait for client to connect, and accept the connection
				System.out.println("Client Connected!");
				inputStream = new BufferedInputStream(client.getInputStream()); //Define InputStream from Client
				AudioInputStream ais = AudioSystem.getAudioInputStream(inputStream); //Create AudioInputStream
				Clip clip = AudioSystem.getClip(); //Logic for playing Audio
				clip.open(ais);
				clip.start();
				while(inputStream != null) {
					System.out.println("Clip Playing");
					if(clip.isActive()) {
						System.out.println(inputStream.available());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
