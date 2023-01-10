package it.fi.meucci;
import java.net.Socket;


public class Client{
   private String userName;
   private int ipAddress;
   private java.net.Socket socket = null;

   public Client (String userName, int ipAddress, java.net.Socket socket)
   {
      this.userName = userName;
      this.ipAddress = ipAddress;
      this.socket = socket;
   }

public Client() {
}

public String getUserName() {
    return userName;
}

public void setUserName(String userName) {
    this.userName = userName;
}

public int getIpAddress() {
    return ipAddress;
}

public void setIpAddress(int ipAddress) {
    this.ipAddress = ipAddress;
}

public Socket getSocket() {
    return socket;
}

public void setSocket(Socket socket) {
    this.socket = socket;
}

   
}

    