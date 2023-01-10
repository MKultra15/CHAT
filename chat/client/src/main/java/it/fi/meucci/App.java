package it.fi.meucci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Client client = new Client();
        BufferedReader tastiera; 
        int scelta;
        client.start();
        //metto un delay di 5 secondi sennò la scritta sottostante e quella all'interno di connetti mi verrebbero attaccate
        TimeUnit.SECONDS.sleep(5);
        for(;;){
                tastiera = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Inserire 1 per comunicare o 2 per disconnettersi");
                scelta = Integer.parseInt(tastiera.readLine());
                if(scelta == 1){
                    client.comunica();
                }else{
                    System.out.println("CLIENT: In chiusura");
                    client.miosocket.close();
                    client.interrupt();
                    break;
                } 
    }
}
}
