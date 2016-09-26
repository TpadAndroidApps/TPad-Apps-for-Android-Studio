package PCServer;   
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;  
    
public class PCServer{  

      
    int max=1000000;      
    int i=0;         
    int temp;  
    ServerSocket serverSocket;  
    Socket socket[]=new Socket[max];  
      
    public PCServer(){  
          
        try {  
            serverSocket=new ServerSocket(8080);    
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            System.out.println("can't initate ServerSocket!");  
            return;  
        };
        
        System.out.println("waiting for connect");  
        
        try {  
            while((socket[i]=serverSocket.accept())!=null){  
                temp=i;  
                i++; 
                SocketImpl socketImpl = new SocketImpl(socket[temp]);
                socketImpl.start();
                  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    
    public static void main(String[] args) {  
        new PCServer();  
  
    }  
    
}  


