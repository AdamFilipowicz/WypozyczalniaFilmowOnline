/*
 *  Klasa Wypozyczenie
 *
 *  Autor: Adam Filipowicz
 *  Data: 07 stycznia 2018 r.
 */
public class Wypozyczenie {
    private int ID;
    private String nazwaKlienta;
    private String nazwaFilmu;
    private double cena;
    private String dataWypozyczenia;
    private String dataZwrotu;
    private int idFilmu;
    
    public Wypozyczenie(int ID, String nazwaKlienta, String nazwaFilmu, double cena, String dataWypozyczenia, String dataZwrotu, int idFilmu){
        this.ID = ID;
        this.nazwaKlienta = nazwaKlienta;
        this.nazwaFilmu = nazwaFilmu;
        this.cena = cena;
        this.dataWypozyczenia = dataWypozyczenia;
        this.dataZwrotu = dataZwrotu;
        this.idFilmu = idFilmu;
    }
    
    public int getID(){
        return ID;
    }
    
    public String getNazwaKlienta(){
        return nazwaKlienta;
    }
    
    public void setNazwaKlienta(String nazwaK){
        nazwaKlienta=nazwaK;
    }
    
    public String getNazwaFilmu(){
        return nazwaFilmu;
    }
    
    public void setNazwaFilmu(String nazwaF){
        nazwaFilmu=nazwaF;
    }
    
    public String getDataWypozyczenia(){
        return dataWypozyczenia;
    }
    
    public String getDataZwrotu(){
        return dataZwrotu;
    }
    
    public double getCena(){
        return cena;
    }
    
    public int getidFilmu(){
        return idFilmu;
    }
    
}
