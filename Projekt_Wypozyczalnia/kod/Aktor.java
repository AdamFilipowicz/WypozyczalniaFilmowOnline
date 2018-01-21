
import java.util.ArrayList;

/*
 *  Klasa Wypozyczenie
 *
 *  Autor: Adam Filipowicz
 *  Data: 07 stycznia 2018 r.
 */
public class Aktor {
    private String imie;
    private String nazwisko;
    
    public Aktor(String imie, String nazwisko){
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public String getImie(){
        return imie;
    }
    
    public String getNazwisko(){
        return nazwisko;
    }
    
}
