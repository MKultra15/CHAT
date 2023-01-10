package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MainServer {
    private ServerSocket serverSocket;      // socket
    static public ArrayList<Client> client = new ArrayList<>();
    DataOutputStream outVersoClient;
    ObjectMapper mapper = new ObjectMapper();
    BufferedReader inDalClient;
    String string_ricevuta = null;
    Socket socket = new Socket();

    public MainServer(){
        try {
            this.serverSocket = new ServerSocket(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avvia(){
        try {
            System.out.println("SERVER: Avvio");
            for(;;){
                //faccio connettere i client e poi li aggiungo a una lista che passerò nella classe ServerThread
                socket = serverSocket.accept();
                inDalClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                outVersoClient = new DataOutputStream(socket.getOutputStream());
                String stringa_ricevuta = inDalClient.readLine();
                System.out.println(stringa_ricevuta);
                Messaggio messaggio = mapper.readValue(stringa_ricevuta, Messaggio.class);
                Client cli = new Client(messaggio.getMittente(), socket.getPort(), socket);
                System.out.println("SERVER: Nome = " + cli.getUserName());
                client.add(cli);
                ServerThread thread = new ServerThread(socket, client);
                thread.start();
                client.add(cli);
            }   

        } catch (Exception e) {
            System.out.println("SERVER: spento");
        }        
    }
}
