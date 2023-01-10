package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client extends Thread{
    String nomeServer = "localhost";
    int portaServer = 3000;
    Socket miosocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;
    String testo = null;
    String destinatario = null;
    String mittente = null;
    String tipo = null;
    int scelta;
    ObjectMapper mapper = new ObjectMapper();
    Messaggio messaggio = null;
    
    public void run(){
        try {
            //faccio partire il metodo connetti e una volta finito inzia un loop infinito che controlla se arrivano nuovi messaggi
            connetti();
            miosocket = new Socket(nomeServer, portaServer);
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
            for(;;){
                String stringaRicevutaDalServer = inDalServer.readLine();
                Messaggio messaggio = mapper.readValue(stringaRicevutaDalServer, Messaggio.class);
                System.out.println("Mittente: " + messaggio.getMittente());
                System.out.println(messaggio.getTesto());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Socket connetti()
    {
        try
        {
            //creo tutto cio di cui ho isogno per gestire la mia connessione con il server
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new Socket(nomeServer, portaServer);
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
            System.out.println("CLIENT: Client connesso");
            System.out.println("CLIENT: Inserire nome");
            mittente = tastiera.readLine();
            messaggio = new Messaggio(null, mittente, null, null);
            outVersoServer.writeBytes(mapper.writeValueAsString(messaggio) + "\n");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connesione!");
            System.exit(1);
        }

        return miosocket;
    }

    public void comunica(){
        try
        {
            //controllo il tipo che sono andato a scegliere
            System.out.println("CLIENT: Seleziona se mandare un messaggio privato o in brodcast:");
            tipo = tastiera.readLine();
            if(tipo.equals("privato")){
                //se è privato chiedo il nome del mittente e poi spedisco al server
                System.out.println("CLIENT: inserisci il destinatario");
                destinatario = tastiera.readLine();
                System.out.println("CLIENT: inserisci il testo da inviare");
                testo = tastiera.readLine();
                messaggio = new Messaggio(testo, mittente, destinatario, tipo);
                outVersoServer.writeBytes(mapper.writeValueAsString(messaggio) + "\n");
            }else if(tipo.equals("brodcast")){
                //se invece è brodcast faccio scrivere solo il messaggio e poi invio
                System.out.println("CLIENT: inserisci il testo da inviare");
                testo = tastiera.readLine();
                messaggio = new Messaggio(testo, mittente, null, tipo);
                outVersoServer.writeBytes(mapper.writeValueAsString(messaggio) + "\n");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
            
    }

}
