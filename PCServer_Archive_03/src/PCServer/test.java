package PCServer;   
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;  
    
public class test implements Runnable{  

      
    int max=1000000;      
    int i=0;         
    int temp;  
    ServerSocket serverSocket;  
    Socket socket[];  
      
    public test(){  
          
        try {  
            serverSocket=new ServerSocket(8080);    
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            System.out.println("can't initate ServerSocket!");  
            return;  
        }  
          
        socket=new Socket[max];  
          
        System.out.println("waiting for connect");  
        try {  
            while((socket[i]=serverSocket.accept())!=null){  
                temp=i;  
                i++;  
                new Thread(this).start();           
                  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    public static void main(String[] args) {  
        new PCServer();  
  
    }  
  
    @Override  
    public void run() {  
        Socket sk=socket[temp];  
        System.out.println("accept:"+sk.getInetAddress().getHostAddress());  
        InputStream is=null;  
        //OutputStream os=null;  
        //BufferedReader br=null;  
        //PrintWriter pw=null;  
        //String msgReply = "Hello";
        
        
        try {  
            is=sk.getInputStream();  
            //os=sk.getOutputStream(); 
            //PrintStream osprintStream = new PrintStream(os);
            //osprintStream.print("hello");
            //osprintStream.close();
            
            //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte buffer [] =new byte[1024*4];
            int tempp;
            File ClientData = new File("ClientData.txt");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            
           
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
             
            //br=new BufferedReader(new InputStreamReader(is));  
            //pw=new PrintWriter(os); 
            
            
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


