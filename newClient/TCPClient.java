   import java.io.*;
   import java.net.*;

    public class TCPClient {
       public static void main(String[] args) throws IOException {
      	
			// Variables for setting up connection and communication
         Socket Socket = null; // socket to connect with ServerRouter
         PrintWriter out = null; // for writing to ServerRouter
         BufferedReader in = null; // for reading form ServerRouter
			InetAddress addr = InetAddress.getLocalHost();
			String host = addr.getHostAddress(); // Client machine's IP
      	String routerName = "10.0.0.67"; // ServerRouter host name
			int SockNum = 5555; // port number
			
			// Tries to connect to the ServerRouter
         try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
         } 
             catch (UnknownHostException e) {
               System.err.println("Don't know about router: " + routerName);
               System.exit(1);
            } 
             catch (IOException e) {
               System.err.println("Couldn't get I/O for the connection to: " + routerName);
               System.exit(1);
            }
				
      	// Variables for message passing	
         //Reader reader = new FileReader("/Users/erik/eclipse-workspace/NewPP/src/CantinaBand3.wav"); 
		//	BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
         int fromServer; // messages received from ServerRouter
         int fromUser; // messages sent to ServerRouter
			String address ="10.0.0.67"; // destination IP (Server)
			long t0, t1, t;
			
			// Communication process (initial sends/receives
			out.println(address);// initial send (IP of the destination Server)
			fromServer = in.read();//initial receive from router (verification of connection)
			System.out.println("ServerRouter: " + fromServer);
			//out.println(host); // Client sends the IP of its machine as initial send
			t0 = System.currentTimeMillis();
      	    // Hey :-)
			File file = new File("/Users/erik/eclipse-workspace/test/src/test.mp4");
			long length = file.length();
			byte[] bytes = new byte[2147483645];
			InputStream nIn = new FileInputStream(file);
			OutputStream nOut = Socket.getOutputStream();
			
			int count;
			while((count = nIn.read(bytes))>0) {
				nOut.write(bytes,0,count);
			}
			
			nOut.close();
			nIn.close();
			
			/*
			// Communication while loop
         while ((fromServer = in.read()) != -1) {
            System.out.println("Server: " + fromServer);
				t1 = System.currentTimeMillis();
           // if (fromServer.equals("Bye.")) // exit statement
           //    break;
				t = t1 - t0;
				System.out.println("Cycle time: " + t);
          
            fromUser = fromFile.read(); // reading strings from a file
            if (fromUser != -1) {
               System.out.println("Client: " + fromUser + "----");
               out.println(fromUser); // sending the strings to the Server via ServerRouter
					t0 = System.currentTimeMillis();
            }
         }	*/
      	
			// closing connections
         out.close();
         in.close();
         Socket.close();
      }
   }
