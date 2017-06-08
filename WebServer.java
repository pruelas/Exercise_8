
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class WebServer{

  public static void main(String[] args) throws Exception{ 
    //Open socket and listen for a connection
    try (ServerSocket serverSocket = new ServerSocket(8080)) {

        System.out.println("\nWaiting for Client...");
        try (Socket socket = serverSocket.accept()) {
          System.out.println("Client connected");

          OutputStream os = socket.getOutputStream();

          InputStream is = socket.getInputStream();
          InputStreamReader isr = new InputStreamReader(is, "UTF-8");
          BufferedReader br = new BufferedReader(isr);
          String line = br.readLine();
          ArrayList<String> al = new ArrayList<>();

          while(line.equals("") == false){
            al.add(line);
            System.out.println(line);
            line = br.readLine();
          }
         
          String[] s = al.get(0).split(" ");
 
          byte[] htmlF = null;          
          String CL = "";
          String OK = "HTTP/1.1 200 OK\r\n";
          String CT = "Content-Type: text/html\r\n\r\n";
          String p = "www"+ s[1];
   
          try{
            Path path = Paths.get(p);
            htmlF = Files.readAllBytes(path);
            CL = "Content-length: " + Files.size(path);
          }catch(Exception e){
            Path Epath = Paths.get("www/Error.html");
            String Error = "HTTP/1.1 404 Not Found\r\n";
            CL = ("Content-Length: " + Files.size(Epath));
            htmlF = Files.readAllBytes(Epath);
            os.write(Error.getBytes());
            os.write(CT.getBytes());
            os.write(CL.getBytes());
            os.write(htmlF);
      
            os.close();
           System.exit(0);
          }

          os.write(OK.getBytes());
          os.write(CT.getBytes());
          os.write(CL.getBytes());
          os.write(htmlF);
          os.close();
        }
      }         
  }
  
}















