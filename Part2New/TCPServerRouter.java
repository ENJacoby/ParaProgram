	import java.net.*;
   import java.io.*;

    public class TCPServerRouter {
       public static void main(String[] args) throws IOException {
         Socket clientSocket = null; // socket for the thread
         Object [][] RoutingTable = new Object [10][2]; // routing table
			int SockNum = 5555; // port number
			int peerNum = 5556; // Peers Port;
			String peerAddress = "10.0.0.67"; //Peers address
			String rString = "";
			Boolean Running = true;
			int ind = 0; // indext in the routing table	
			//Accepting connections
         ServerSocket serverSocket = null; // server socket for accepting connections
         try {
            serverSocket = new ServerSocket(5555);
            System.out.println("ServerRouter is Listening on port: 5555.");
         }
             catch (IOException e) {
               System.err.println("Could not listen on port: 5555.");
               System.exit(1);
            }
			
			// Creating threads with accepted connections
			while (Running == true)
			{
			try {
				clientSocket = serverSocket.accept();
				System.out.println(clientSocket.toString());
				SThread t1 = new SThread(RoutingTable, clientSocket, ind);
				t1.start();
				
				DatagramSocket ds = new DatagramSocket(8888);
				byte[] receive = new byte[65535];
			  
				DatagramPacket DpReceive = null;
				while (true)
				{
					
					// Step 2 : create a DatgramPacket to receive the data.
					DpReceive = new DatagramPacket(receive, receive.length);
			  
					// Step 3 : revieve the data in byte buffer.
					ds.receive(DpReceive);
			  
					System.out.println("IP RECIEVED: " + data(receive));
					rString = data(receive).toString();
					break;
			        }
				
				String ipString = rString.substring(0, rString.indexOf('|'));
				int socketInt = Integer.valueOf(rString.substring(rString.indexOf('|')+1));
				ds.close();
				
				clientSocket = new Socket(ipString, socketInt);
				System.out.println(clientSocket.toString()+ "~~~~~~~");
				SThread t = new SThread(RoutingTable, clientSocket, ind); //, peerAddress, peerNum
						//); // creates a thread with a random port
				t.start(); // starts the thread
				ind++; // increments the index
				System.out.println("ServerRouter1 connected with Client/Server: " + clientSocket.getInetAddress().getHostAddress());
				
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