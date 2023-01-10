package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerThread extends Thread{
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    ObjectMapper mapper = new ObjectMapper();
    ArrayList<Client> listaClient;

    public ServerThread(Socket c, ArrayList<Client> listaClient){
        this.client = c;
        this.listaClient = listaClient;
    }

    public void run(){
        System.out.println("SERVER: Client connesso");
        try {

            this.comunica();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void comunica () throws Exception
    {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());
        for(;;)
        {
            String stringa_ricevuta = inDalClient.readLine();
            Messaggio messaggio = mapper.readValue(stringa_ricevuta, Messaggio.class);
            System.out.println(stringa_ricevuta);
            if(messaggio.getTipo().equals("privato")){
                //se il tipo del messaggio è privato vado a cercare il destinatario e glielo spedisco
                System.out.println("SERVER: Ricerca destinatario");
                for(int i = 0; i < MainServer.client.size(); i++){
                    if(messaggio.getDestinatario().equals(MainServer.client.get(i).getUserName())){
                        System.out.println("SERVER: Destinatario trovato");
                        outVersoClient = new DataOutputStream(listaClient.get(i).getSocket().getOutputStream());
                        outVersoClient.writeBytes(mapper.writeValueAsString(messaggio) + "\n");
                        System.out.println("SERVER: Messaggio mandato");
                    }
                }
            }else if(messaggio.getTipo().equals("brodcast")){
                //se invece il tipo è brodcast creo un for che lo manda a tutti
                System.out.println("SERVER: Invio messaggi in brodcast");
                for(int i = 0; i < listaClient.size(); i++){
                        outVersoClient = new DataOutputStream(MainServer.client.get(i).getSocket().getOutputStream());
                        outVersoClient.writeBytes(mapper.writeValueAsString(messaggio) + "\n");
                }
                System.out.println("SERVER: Messaggi inviati");
            }else if(messaggio.getTipo() == null){
                System.out.println("SERVER: Client in disconnessione");
                client.close();
            }
            }
        }
}

