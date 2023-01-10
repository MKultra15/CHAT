package it.fi.meucci;

public class Messaggio {
    String testo;
    String mittente;
    String destinatario;
    String tipo;
    
    public Messaggio(String testo, String mittente, String destinatario, String tipo) {
        this.testo = testo;
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.tipo = tipo;
    }

    public Messaggio() {
    }
    
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTesto() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo = testo;
    }
    public String getMittente() {
        return mittente;
    }
    public void setMittente(String mittente) {
        this.mittente = mittente;
    }
    public String getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    

}
