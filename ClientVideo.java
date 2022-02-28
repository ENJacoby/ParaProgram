package servertest;
import java.io.*;
import java.net.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientVideo {

  public final static int SOCKET_PORT = 5555;
  public final static String SERVER = "10.0.0.200";
  public final static String
       FILE_TO_RECEIVED = "‪C:\\Users\\Erik\\Desktop\\video.mp4";

  public final static int FILE_SIZE = Integer.MAX_VALUE;

  public static void main (String [] args ) throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");

      // receive file
      System.out.println("Hello");
      byte [] mybytearray  = new byte [1024];
      System.out.println("Hello2");
      InputStream is = sock.getInputStream();
      System.out.println("Hello3");
      File myFile = new File("C:\\Users\\Erik\\Desktop\\video.mp4");
      fos = new FileOutputStream(myFile);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }    
}
