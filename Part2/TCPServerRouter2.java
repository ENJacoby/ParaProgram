	import java.net.*;
   import java.io.*;

    public class TCPServerRouter2 {
       public static void main(String[] args) throws IOException {
         Socket clientSocket = null; // socket for the thread
         Object [][] RoutingTable = new Object [10][2]; // routing table
			int SockNum = 5556; // port number
			//int peerNum = 5555; // Peers Port
			String peerAddress = "10.0.0.67"; //Peers address
			Boolean Running = true;
			int ind = 0; // indext in the routing table	

			//Accepting connections
         ServerSocket serverSocket = null; // server socket for accepting connections
         try {
            serverSocket = new ServerSocket(5556);
            System.out.println("ServerRouter is Listening on port: 5556.");
         }
             catch (IOException e) {
               System.err.println("Could not listen on port: 5556.");
               System.exit(1);
            }
			
			// Creating threads with accepted connections
			while (Running == true)
			{
			try {
				System.out.println("Entered Try Block");
				clientSocket = serverSocket.accept();
				System.out.println(clientSocket.isConnected());
				DatagramSocket ds = new DatagramSocket();
				InetAddress ip = InetAddress.getByName("10.0.0.67"); // This IP needs to be the other server router
				byte buf[] = null;
				
				//while (true)
			    //    {
			            String inp = clientSocket.getInetAddress().getHostAddress();
			            System.out.println("Destination from Server: " + inp);
			            // convert the String input into the byte array.
			            buf = inp.getBytes();
			            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 8888);
			            ds.send(DpSend);
			    //      break;
			    //    }
				
				SThread t = new SThread(RoutingTable, clientSocket, ind//, peerAddress, peerNum
						); // creates a thread with a random port
				t.start(); // starts the thread
				ind++; // increments the index
            System.out.println("ServerRouter connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
         }
             catch (IOException e) {
               System.err.println("Client/Server failed to connect.");
               System.exit(1);
            }
			}//end while
			
			//closing connections
		   clientSocket.close();
         serverSocket.close();

      }
       public static StringBuilder data(byte[] a)
       {
           if (a == null)
               return null;
           StringBuilder ret = new StringBuilder();
           int i = 0;
           while (a[i] != 0)
           {
               ret.append((char) a[i]);
               i++;
           }
           return ret;
       }
   }