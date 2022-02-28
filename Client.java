package servertest;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	//Define Socket
    private static Socket socket;
    //Define InputStream
    private static BufferedInputStream inputStream;
    //Begin Main
    public static void main(String[] args) throws LineUnavailableException {
        try {
        	//Try Connecting to Socket
            socket = new Socket("10.0.0.200", 5555);
            if (socket.isConnected()) {
            	//If connected, print connected to server
            	System.out.println("Connected to Server");
            	OutputStream out = socket.getOutputStream(); //Define OupuStream for Socket
            	while(true) {
            		try {
            			AudioInputStream din = null;
            			File myFile = new File("C:\\Users\\Erik\\Desktop\\CantinaBand3.wav");
            			AudioInputStream in = AudioSystem.getAudioInputStream(myFile);
                        AudioFormat baseFormat = in.getFormat();
                        AudioFormat decodedFormat =
                                new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, baseFormat.getSampleRate(),
                                        8, baseFormat.getChannels(), baseFormat.getChannels(),
                                        baseFormat.getSampleRate(), false);
                        din = AudioSystem.getAudioInputStream(decodedFormat, in);
                        if(din != null) {
                			System.out.println("Sending File");
                    		AudioSystem.write(din, AudioFileFormat.Type.WAVE, out);
                    	}
                    } catch (Exception e) {
                        // Handle exception.
                        e.printStackTrace();
                    }
            		}
            	
            	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}