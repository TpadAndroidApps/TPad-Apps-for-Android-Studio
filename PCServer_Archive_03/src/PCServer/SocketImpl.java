package PCServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketImpl extends Thread{
	Socket sk; 
	
    public SocketImpl(Socket sk){
    	this.sk = sk;		   
	   }
    @Override 
    public void run() {  
        System.out.println("accept:"+sk.getInetAddress().getHostAddress());  
        InputStream is=null;  
       
        try {  
            is=sk.getInputStream();  
            byte buffer [] =new byte[1024*4];
            int tempp;
            
           // create file and add more info to the data(dates)
            File ClientData = new File("ClientData.txt");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            
           //write data to the txt file
            try{
            	if (ClientData.exists() == false){
            		ClientData.createNewFile();
            	}
            	while((tempp = is.read(buffer))!= -1){
            		System.out.println(new String(buffer,0,tempp));
            		PrintWriter pw = new PrintWriter(new FileWriter(ClientData, true));
            		pw.println(dateFormat.format(date)+"  From:"+sk.getInetAddress().getHostAddress()+"\n");
            		pw.println(new String(buffer,0,tempp)+"\n");
             		pw.close();
             		System.out.println("Log Successfully");            		
            }
            }catch(IOException e){
            	System.out.println("COULD NOT LOG!");
            }
           //data logging ends here
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            try {  
                sk.close(); 
                
            } catch (IOException e1) {  
                // TODO Auto-generated catch block  
                e1.printStackTrace();  
            }  
            return;  
        }  
     
       
          
    }   
}  
