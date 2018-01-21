
import java.util.ArrayList;

/*
 *  Klasa Wypozyczenie
 *
 *  Autor: Adam Filipowicz
 *  Data: 07 stycznia 2018 r.
 */
public class Film {
    private int ID;
    private String nazwaFilmu;
    private double cena;
    private int rokPowstania;
    private String opis;
    private ArrayList<String> gatunki;
    private ArrayList<Rezyser> rezyserzy;
    private ArrayList<Aktor> aktorzy;
    
    public Film(int ID, String nazwaFilmu, double cena, int rokPowstania, String opis, ArrayList<String> gatunki, ArrayList<Rezyser> rezyserzy, ArrayList<Aktor> aktorzy){
        this.ID = ID;
        this.nazwaFilmu = nazwaFilmu;
        this.cena = cena;
        this.rokPowstania = rokPowstania;
        this.opis = opis;
        this.gatunki = gatunki;
        this.rezyserzy = rezyserzy;
        this.aktorzy = aktorzy;
    }
    
    public int getID(){
        return ID;
    }
    
    public String getNazwaFilmu(){
        return nazwaFilmu;
    }
    
    public double getCena(){
        return cena;
    }
    
    public int getRokPowstania() {
        return rokPowstania;
    }

    public String getOpis() {
        return opis;
    }

    public String getGatunki() {
        StringBuilder sb = new StringBuilder();
        int licznik = 0;
        for(String str: gatunki){
            if (licznik++!=0)
                sb.append(", ");
            sb.append(String.format("%s", str));
        }
        return sb.toString();
    }

    public String getRezyserzy() {
        StringBuilder sb = new StringBuilder();
        int licznik = 0;
        for(Rezyser rez: rezyserzy){
            if (licznik++!=0)
                sb.append(", ");
            sb.append(String.format("%s %s", rez.getImie(), rez.getNazwisko()));
        }
        return sb.toString();
    }

    public String getAktorzy() {
        StringBuilder sb = new StringBuilder();
        int licznik = 0;
        for(Aktor akt: aktorzy){
            if (licznik++!=0)
                sb.append(", ");
            sb.append(String.format("%s %s", akt.getImie(), akt.getNazwisko()));
        }
        return sb.toString();
    }
    
}
