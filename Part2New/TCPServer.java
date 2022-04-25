   import java.io.*;
   import java.net.*;

    public class TCPServer {
       public static void main(String[] args) throws IOException {
      	
			// Variables for setting up connection and communication
         Socket Socket = null; // socket to connect with ServerRouter
         PrintWriter out = null; // for writing to ServerRouter
         BufferedReader in = null; // for reading form ServerRouter
			InetAddress addr = InetAddress.getLocalHost();
			String host = addr.getHostAddress(); // Server machine's IP			
			String routerName = "10.0.0.67"; // ServerRouter host name
			int SockNum = 5556; // port number
			
			// Tries to connect to the ServerRouter
       
				
      	// Variables for message passing			
         String fromServer; // messages sent to ServerRouter
         String fromClient; // messages received from ServerRouter      
 			String address ="10.0.0.67"; // destination IP (Client)
			
 			
// 			DatagramSocket dServer = new DatagramSocket(5556);
// 			byte[] receive = new byte[65535];
// 			DatagramPacket DpReceive = null;
//			while (true)
//			{
//				DpReceive = new DatagramPacket(receive, receive.length);
//				dServer.receive(DpReceive);
//				System.out.println("RECIEVED: " + data(receive));
//				break;
//		        }
 			
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
			
			
 			out.println(address);
 			
			// Communication process (initial sends/receives)
			//----out.println(address);// initial send (IP of the destination Client)
			fromClient = in.readLine();// initial receive from router (verification of connection)
			System.out.println("Reached pt 1");
			System.out.println("ServerRouter2: " + fromClient);
			System.out.println("Reached pt 2");
			// Communication while loop
			
      	while ((fromClient = in.readLine()) != null) {
            System.out.println("Client said: " + fromClient);
            if (fromClient.equals("Bye.")) // exit statement
					break;
				fromServer = fromClient;//.toUpperCase(); // converting received message to upper case
				System.out.println("Server2 said: " + fromServer);
            out.println(fromServer); // sending the converted message back to the Client via ServerRouter
         }
			
			// closing connections
         out.close();
         in.close();
         Socket.close();
         //dServer.close();
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
