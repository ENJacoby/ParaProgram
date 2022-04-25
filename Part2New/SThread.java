
import java.io.*;
import java.net.*;
import java.lang.Exception;

	
public class SThread extends Thread 
{
	private Object [][] RTable; // routing table
	private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
    private BufferedReader in; // reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; // communication strings
	private Socket outSocket; // socket for communicating with a destination
	private int ind; // indext in the routing table
	private int thisnum;
	// Constructor
	SThread(Object [][] Table, Socket toClient, int index//, int num//, String peerName, int peerNum
			) throws IOException
	{
			out = new PrintWriter(toClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
			RTable = Table;
			addr = toClient.getInetAddress().getHostAddress();
			RTable[index][0] = addr; // IP addresses 
			RTable[index][1] = toClient; // sockets for communication
			//System.out.println(addr);
			//System.out.println(toClient.toString());
			ind = index;
			//peerAddress = peerName;
			//peerPort = peerNum;
	}
	
	// Run method (will run for each machine that connects to the ServerRouter)
	public void run()
	{
		try
		{
		// Initial sends/receives
		destination = in.readLine(); // initial read (the destination for writing)
		System.out.println(destination);
		System.out.println("Forwarding to " + destination);
		out.println("Connected to the router."); // confirmation of connection
		
		// waits 10 seconds to let the routing table fill with all machines' information
		try{
    		Thread.currentThread().sleep(10000); 
	   }
		catch(InterruptedException ie){
		System.out.println("Thread interrupted");
		}
		
		// loops through the routing table to find the destination
		for ( int i=0; i<10; i++) 
				{
					if (destination.equals((String) RTable[i][0])){
						outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
						System.out.println("~~~~~" + outSocket.toString());
						System.out.println("Found destination: " + destination);
						outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
					}
//					} else {
//						Socket peer = new Socket(peerAddress,peerPort);
//						PrintWriter out = null; // for writing to ServerRouter
//				        BufferedReader in = null; // for reading form ServerRouter
//				        
//					}
				}
		
		// Communication loop	
		while ((inputLine = in.readLine()) != null) {
            System.out.println("Client/Server" + thisnum + " said: " + inputLine);
            
            if (inputLine.equals("Bye.")) // exit statement
					break;
            outputLine = inputLine.toUpperCase(); // passes the input from the machine to the output string for the destination
				
				if ( outSocket != null){				
				outTo.println(outputLine); // writes to the destination
				}			
       }// end while		 
		 }// end try
			catch (IOException e) {
               System.err.println("Could not listen to socket.");
               System.exit(1);
         }
	}
}


/*
import java.io.*;
import java.net.*;
import java.lang.Exception;

	
public class SThread extends Thread 
{
	private Object [][] RTable; // routing table
	private PrintWriter out, outTo; // writers (for writing back to the machine and to destination)
   private BufferedReader in; // reader (for reading from the machine connected to)
	private String inputLine, outputLine, destination, addr; // communication strings
	private Socket outSocket; // socket for communicating with a destination
	private int ind; // indext in the routing table
	InputStream nIn = null;
	OutputStream nOut = null;
	String newFileName = "testfile.txt";
	SThread(Object [][] Table, Socket toClient, int index) throws IOException
	{
			nOut = new FileOutputStream(newFileName);
			nIn = toClient.getInputStream();
			out = new PrintWriter(toClient.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
			RTable = Table;
			addr = toClient.getInetAddress().getHostAddress();
			RTable[index][0] = addr; // IP addresses 
			RTable[index][1] = toClient; // sockets for communication
			ind = index;
	}
	
	// Run method (will run for each machine that connects to the ServerRouter)
	public void run()
	{
		try
		{
		// Initial sends/receives
		destination = in.readLine(); // initial read (the destination for writing)
		System.out.println("Forwarding to " + destination);
		out.println("Connected to the router."); // confirmation of connection
		
		// waits 10 seconds to let the routing table fill with all machines' information
		try{
    		Thread.currentThread().sleep(10000); 
	   }
		catch(InterruptedException ie){
		System.out.println("Thread interrupted");
		}
		long t0, t1, t;
		t1=0;
		// loops through the routing table to find the destination
		t0 = System.currentTimeMillis();
		for ( int i=0; i<10; i++) 
				{
					if (destination.equals((String) RTable[i][0])){
						
						
						outSocket = (Socket) RTable[i][1]; // gets the socket for communication from the table
						System.out.println("Found destination: " + destination);
						t1 = System.currentTimeMillis();
						outTo = new PrintWriter(outSocket.getOutputStream(), true); // assigns a writer
					
				}}
		
		t = t1-t0;
		System.out.println("RTable Lookup: " + t + "ms");
		
		/*
		try {
			
		} catch(IOException ex) {
			System.out.println("Can't get input");
		}
		try {
			
		} catch(IOException ex) {
			System.out.println("Can't create output");
		}
		
		System.out.println("!~~~~~~ 1");
		t0 = System.currentTimeMillis();
		byte[] bytes = new byte[16*1024];
		int count;
		while((count = nIn.read(bytes)) > 0) {
			nOut.write(bytes,0,count);
			System.out.println("!~~~~~~ 2");
		}
		System.out.println("!~~~~~~ 3");
		t1 = System.currentTimeMillis();
		System.out.println("!~~~~~~ 4");
		t= t1-t0;
		System.out.println("!~~~~~~ 5");
		System.out.println("Time to read file: " + t + "ms");
		long fileSize = 0;
//		for(int i = 0; i < bytes.length; i++) {
//			if(bytes[i]>0) {
//				fileSize++;
//			}
//		}
		File file = new File(newFileName);
		fileSize = file.length();
		System.out.println("File Size: " + fileSize + " bytes");
		nIn.close();
		nOut.close();
		
		// Communication loop
		/*
		while ((inputLine = in.readLine()) != null) {
            System.out.println("Client/Server said: " + inputLine);
            if (inputLine.equals("Bye.")) // exit statement
					break;
            outputLine = inputLine; // passes the input from the machine to the output string for the destination
				
				if ( outSocket != null){				
				outTo.println(outputLine); // writes to the destination
				}			
       }
       // end while		 
		 }// end try
			catch (IOException e) {
               System.err.println("Could not listen to socket.");
               e.printStackTrace();
               System.exit(1);
         }
	}
}
*/