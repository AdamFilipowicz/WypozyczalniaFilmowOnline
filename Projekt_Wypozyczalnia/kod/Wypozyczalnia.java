/*
 *  Klasa Wypozyczalnia
 *
 *  Klasa zawiera wiele metod uzywanych w klasach okienkowych.
 *  Klasa bazuje na obiektach klasy Konto, Film i Wypozyczenie.
 *
 *  Autor: Adam Filipowicz
 *  Data: 07 stycznia 2018 r.
 */

import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.util.ArrayList;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;



class Wypozyczalnia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Połączenie z bazą danych.
     */
    private Connection con;

    private PreparedStatement stmt;
    
    private ResultSet rs;

    /**
     * Metoda inicjalizuje połączenie z bazą danych.
     * @throws SQLException 
     */
    void initializeConnection(String dbConnect, String user, String password) throws IOException, InterruptedException, ClassNotFoundException, SQLException{
        DriverManager.registerDriver(new SQLServerDriver());
        con = DriverManager.getConnection(dbConnect, user, password);
    }
    
    /**
     * Metoda przydziela użytkownikowi rolę aplikacji
     */
    void userRole() throws SQLException{
        Statement stmt2 = con.createStatement(); 
        String SQL = "USE Wypozyczalnia EXEC sp_setapprole 'users', 'usersPassword192837465' SELECT * from Aktorzy"; 
        rs = stmt2.executeQuery( SQL );

        rs.close();
        stmt2.close();
    }
    
    /**
     * Metoda przydziela pracownikowi rolę aplikacji
     */
    void workerRole() throws SQLException{
        Statement stmt2 = con.createStatement(); 
        String SQL = "USE Wypozyczalnia EXEC sp_setapprole 'workers', 'workersPassword5847359' SELECT * from Aktorzy"; 
        rs = stmt2.executeQuery( SQL );

        rs.close();
        stmt2.close();
    }

    /**
     * Metoda dodaje film o podanej nazwie do tablicy filmow.
     * @param nazwa - nazwa filmu do dodania
     * @param cena - cena filmu.
     * @param ilosc - ilosc filmu.
     * @param opis - opis filmu.
     * @throws Exception - wyjatek zglaszany, gdy nazwa filmu jest pusta lub gdy film juz istnieje
     */
    void dodajFilm(String nazwa, String gatunek, int rok, String opis, double cena, String rezyserzy, String aktorzy) throws Exception{
        String imie, nazwisko;
        int id2;
        
        if(nazwa==null || nazwa.equals("")) throw(new Exception("Nazwa filmu nie moze byc pusta"));
        if(cena<=0) throw(new Exception("Bledna cena"));
        if(rok<=0) throw(new Exception("Bledny rok"));
        if(opis.equals("")) throw(new Exception("Opis nie moze byc pusty"));
        if(gatunek.equals("")) throw(new Exception("Gatunek nie moze byc pusty"));
        if(rezyserzy.equals("")) throw(new Exception("Rezyser nie moze byc pusty"));
        if(aktorzy.equals("")) throw(new Exception("Aktor nie moze byc pusty"));
        if(nazwa.substring(nazwa.length()-3).equals("XWX")) throw(new Exception("Nazwa nie może kończyć się na XWX"));

        stmt = con.prepareStatement("SELECT * FROM Filmy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery();
        
        rs.last();
        int id;
        if(rs.getRow()==0)
            id = 0;
        else
            id=rs.getInt("idF") + 1;
        rs.moveToInsertRow();
        rs.updateInt("idF", id);
        rs.updateString("nazwaF", nazwa);
        rs.updateDouble("cenaF", cena);
        rs.updateString("opisF", opis);
        rs.updateInt("rokPowstaniaF", rok);
        
        rs.insertRow();
             
        stmt.close();
        rs.close();
        
        String[] tabRezyserow = rezyserzy.split("\\s*,\\s*");
        for(int i=0;i<tabRezyserow.length;i++){
            String[] tabRez2 = tabRezyserow[i].split(" ");
            stmt = con.prepareStatement("SELECT * FROM Rezyserzy WHERE imieR = ? AND nazwiskoR = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            imie = tabRez2[0];
            nazwisko = tabRez2[1];
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            rs = stmt.executeQuery();
            
            rs.first();
            if(rs.getRow()==0){    //jesli nie ma takiego rezysera to dodaje go
                rs.close();
                stmt.close();
                
                stmt = con.prepareStatement("SELECT * FROM Rezyserzy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();
                
                rs.last();
                if(rs.getRow()==0)
                    id2 = 0;
                else
                    id2=rs.getInt("idR");
                rs.moveToInsertRow();
                rs.updateInt("idR", id + 1);
                rs.updateString("imieR", imie);
                rs.updateString("nazwiskoR", nazwisko);
                rs.insertRow();
                
                rs.close();
                stmt.close();
                
                //i dodaje do tabeli Filmy_Rezyserzy
                stmt = con.prepareStatement("SELECT * FROM Filmy_Rezyserzy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();
                
                rs.last();
                rs.moveToInsertRow();
                rs.updateInt("idR", id2);
                rs.updateInt("idF", id);
                rs.insertRow();
                
                rs.close();
                stmt.close();
            }
            else{   //jesli jest to wyszukuje go i dodaje do tabeli Filmy_Rezyserzy
                id2=rs.getInt("idR");
                
                stmt = con.prepareStatement("SELECT * FROM Filmy_Rezyserzy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();
                
                rs.last();
                rs.moveToInsertRow();
                rs.updateInt("idR", id2);
                rs.updateInt("idF", id);
                rs.insertRow();
                 
                rs.close();
                stmt.close();
            }
        }
        //to samo dla aktorów
        String[] tabAktorow = aktorzy.split("\\s*,\\s*");
        for(int i=0;i<tabAktorow.length;i++){
            String[] tabAkt2 = tabAktorow[i].split(" ");
            stmt = con.prepareStatement("SELECT * FROM Aktorzy WHERE imieA = ? AND nazwiskoA = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            imie = tabAkt2[0];
            nazwisko = tabAkt2[1];
            stmt.setString(1, imie);
            stmt.setString(2, nazwisko);
            rs = stmt.executeQuery();

            rs.first();
            if(rs.getRow()==0){    //jesli nie ma takiego rezysera to dodaje go
                rs.close();
                stmt.close();

                stmt = con.prepareStatement("SELECT * FROM Aktorzy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();

                rs.last();
                if(rs.getRow()==0)
                    id2 = 0;
                else
                    id2=rs.getInt("idA");
                rs.moveToInsertRow();
                rs.updateInt("idA", id + 1);
                rs.updateString("imieA", imie);
                rs.updateString("nazwiskoA", nazwisko);
                rs.insertRow();

                rs.close();
                stmt.close();

                //i dodaje do tabeli Filmy_Aktorzy
                stmt = con.prepareStatement("SELECT * FROM Filmy_Aktorzy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();

                rs.last();
                rs.moveToInsertRow();
                rs.updateInt("idA", id2);
                rs.updateInt("idF", id);
                rs.insertRow();

                rs.close();
                stmt.close();
            }
            else{   //jesli jest to wyszukuje go i dodaje do tabeli Filmy_Aktorzy
                id2=rs.getInt("idA");

                stmt = con.prepareStatement("SELECT * FROM Filmy_Aktorzy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmt.executeQuery();

                rs.last();
                rs.moveToInsertRow();
                rs.updateInt("idA", id2);
                rs.updateInt("idF", id);
                rs.insertRow();

                rs.close();
                stmt.close();
            }
        }
        //to samo dla gatunków
        String[] tabGatunkow = gatunek.split("\\s*,\\s*");
        for(int i=0;i<tabGatunkow.length;i++){
            
            //dodaje od razu do Filmy_Gatunki
            stmt = con.prepareStatement("SELECT * FROM Filmy_Gatunki", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            rs.last();
            rs.moveToInsertRow();
            rs.updateString("nazwaG", tabGatunkow[i]);
            rs.updateInt("idF", id);
            rs.insertRow();

            rs.close();
            stmt.close();
        }
    }

    /**
     * Metoda wycofuje film o podanej nazwie z wypozyczalni (usuwa z tablicy)
     * @param nazwa - nazwa filmu do usuniecia
     * @throws Exception - wyjatek zglaszany, gdy nazwa filmu jest pusta lub bledna
     */
    void wycofajFilm(String nazwa) throws Exception{
        if(nazwa==null) throw(new Exception("Brak filmu"));

        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()==0) {
            stmt.close();
            rs.close();
            throw(new Exception("Nie znaleziono podanego filmu"));
        }
        rs.updateString("nazwaF", rs.getString("nazwaF")+"XWX");
        
        rs.updateRow();
             
        stmt.close();
        rs.close();
    }
    
    /**
     * Metoda zmienia parametry filmu o podanej nazwie.
     * @param nazwa - nazwa filmu do dodania
     * @param cena - cena filmu.
     * @param ilosc - ilosc filmu.
     * @param opis - opis filmu.
     * @throws Exception - wyjatek zglaszany, gdy nazwa filmu jest pusta lub gdy film juz istnieje
     */
    void zmienFilm(String nazwa, int rok, String opis, double cena) throws Exception{
        if(nazwa==null || nazwa.equals("")) throw(new Exception("Nazwa filmu nie moze byc pusta"));
        if(cena<=0) throw(new Exception("Bledna cena"));
        if(rok<=0) throw(new Exception("Bledny rok"));
        if(!znajdzFilm(nazwa)) throw(new Exception("Brak filmu"));
        if(opis.equals("")) throw(new Exception("Opis nie moze byc pusty"));

        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        
        rs.updateString("nazwaF", nazwa);
        rs.updateDouble("cenaF", cena);
        rs.updateString("opisF", opis);
        rs.updateInt("rokPowstaniaF", rok);
        
        rs.updateRow();
        
        stmt.close();
        rs.close();
    }

    /**
     * Metoda znajduje czy szukany film znajduje się w bazie
     * @param nazwa - nazwa wyszukiwanego filmu
     * @return true - gdy film zostanie znaleziony na liscie
     *          false - gdy film nie zostanie znaleziony na liscie
     */
    boolean znajdzFilm(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()==0){
            stmt.close();
            rs.close();
            return false;
        }
        String nazwaFilmu = rs.getString("nazwaF");
        if(nazwaFilmu.substring(nazwaFilmu.length()-3).equals("XWX")){
            stmt.close();
            rs.close();
            return false;
        }
        stmt.close();
        rs.close();
        return true;
    }
    
    /**
     * Metoda znajduje czy szukany film znajduje się w bazie
     * @param nazwa - nazwa wyszukiwanego filmu
     * @return true - gdy film zostanie znaleziony na liscie
     *          false - gdy film nie zostanie znaleziony na liscie
     */
    Film znajdzFilmm(String nazwa) throws SQLException{
        Film film;
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()==0){
            stmt.close();
            rs.close();
            return null;
        }
        String nazwaFilmu = rs.getString("nazwaF");
        if(nazwaFilmu.substring(nazwaFilmu.length()-3).equals("XWX")){
            stmt.close();
            rs.close();
            return null;
        }
        
        int id = rs.getInt("idF");
        String nazwaa = rs.getString("nazwaF");
        double cena = rs.getDouble("cenaF");
        int rok = rs.getInt("rokPowstaniaF");
        String opis = rs.getString("opisF");

        stmt.close();
        rs.close();
        
        stmt = con.prepareStatement("SELECT * FROM Filmy_Gatunki fg JOIN Filmy f ON (fg.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        ArrayList<String> gatunki = new ArrayList<String>();
        while(rs.getRow()!=0){
            gatunki.add(rs.getString("nazwaG"));
            rs.next();
        }
        
        stmt.close();
        rs.close();
        
        stmt = con.prepareStatement("SELECT * FROM Rezyserzy r JOIN Filmy_Rezyserzy fr ON (r.idR = fr.idR) JOIN Filmy f ON (fr.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        ArrayList<Rezyser> rezyserzy = new ArrayList<Rezyser>();
        while(rs.getRow()!=0){
            rezyserzy.add(new Rezyser(rs.getString("imieR"), rs.getString("nazwiskoR")));
            rs.next();
        }
        
        stmt.close();
        rs.close();
        
        stmt = con.prepareStatement("SELECT * FROM Aktorzy a JOIN Filmy_Aktorzy fa ON (a.idA = fa.idA) JOIN Filmy f ON (fa.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        ArrayList<Aktor> aktorzy = new ArrayList<Aktor>();
        while(rs.getRow()!=0){
            aktorzy.add(new Aktor(rs.getString("imieA"), rs.getString("nazwiskoA")));
            rs.next();
        }
        
        stmt.close();
        rs.close();
        
        film = new Film(id, nazwaa, cena, rok, opis, gatunki, rezyserzy, aktorzy);
        return film;
    }
    
    /**
     * Metoda znajduje film o podanym indeksie na liscie filmow i zwraca jego nazwe.
     * @param index - indeks wyszukiwanego filmu
     * @return String - gdy film zostanie znaleziony na liscie
     * 		   null - gdy film nie zostanie znaleziony na liscie
     */
    String znajdzNazweFilmu(int index) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE idF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, index);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()==0){
            stmt.close();
            rs.close();
            return null;
        }
        String nazwaFilmu = rs.getString("nazwaF");
        if(nazwaFilmu.substring(nazwaFilmu.length()-3).equals("XWX")){
            stmt.close();
            rs.close();
            return "XWX";
        }
        stmt.close();
        rs.close();
        return nazwaFilmu;
    }
    
    /**
     * Metoda znajduje film o podanym indeksie na liscie filmow i zwraca jego nazwe.
     * @param index - indeks wyszukiwanego filmu
     * @return String - gdy film zostanie znaleziony na liscie
     * 		   null - gdy film nie zostanie znaleziony na liscie
     */
    String znajdzFilmy(String nazwa, String typ, int rok, int numerFilmu) throws SQLException{
        if(nazwa.equals("")){
            if(typ.equals("")){
                stmt = con.prepareStatement("SELECT * FROM Filmy WHERE rokPowstaniaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setInt(1, rok);
            }
            else if(rok==0){
                stmt = con.prepareStatement("SELECT * FROM Filmy f JOIN Filmy_Gatunki fg ON (fg.idF = f.idF) WHERE fg.nazwaG = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, typ);
            }
            else{
                stmt = con.prepareStatement("SELECT * FROM Filmy f JOIN Filmy_Gatunki fg ON (fg.idF = f.idF) WHERE fg.nazwaG = ? AND f.rokPowstaniaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, typ);
                stmt.setInt(2, rok);
            }
        }
        else if(typ.equals("")){
            if(rok==0){
                stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, nazwa);
            }
            else{
                stmt = con.prepareStatement("SELECT * FROM Filmy WHERE rokPowstaniaF = ? AND nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setInt(1, rok);
                stmt.setString(2, nazwa);
            }
        }
        else if(rok==0){
            stmt = con.prepareStatement("SELECT * FROM Filmy f JOIN Filmy_Gatunki fg ON (fg.idF = f.idF) WHERE fg.nazwaG = ? AND f.nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, typ);
            stmt.setString(2, nazwa);
        }
        else{
            stmt = con.prepareStatement("SELECT * FROM Filmy f JOIN Filmy_Gatunki fg ON (fg.idF = f.idF) WHERE fg.nazwaG = ? AND f.nazwaF = ? AND f.rokPowstaniaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, typ);
            stmt.setString(2, nazwa);
            stmt.setInt(3, rok);
        }

        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()==0){
            stmt.close();
            rs.close();
            return null;
        }
        String nazwaFilmu = rs.getString("nazwaF");
        for(int i=0;i<numerFilmu-1;i++){
            rs.next();
            if(rs.getRow()==0){
                stmt.close();
                rs.close();
                return null;
            }
            nazwaFilmu = rs.getString("nazwaF");
            if(nazwaFilmu.substring(nazwaFilmu.length()-3).equals("XWX")){
                i--;
            }
        }
        if(rs.getRow()==0){
            stmt.close();
            rs.close();
            return null;
        }
        stmt.close();
        rs.close();
        return nazwaFilmu;
    }

    
    
    /**
     * Metoda ustawiajaca dana cene jako cene filmu.
     * @param nowaCena - cena jaka chcemy przypisac cenie filmu.
     */
    void setCena(String nazwa, double nowaCena) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()!=0){
            rs.updateFloat("cenaF", (float)nowaCena);
            rs.updateRow();
        }
        stmt.close();
        rs.close();
    }
    
    /**
     * Metoda ustawiajaca dana opis jako opis filmu.
     * @param opis - podany opis filmu
     */
    void setOpis(String nazwa, String opis) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()!=0){
            rs.updateString("opisF", opis);
            rs.updateRow();
        }
        stmt.close();
        rs.close();
    }
    
    /**
     * Metoda zwracajaca cene filmu.
     * @return cena - cena filmu.
     */
    float getCena(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        Float cena = rs.getFloat("cenaF");
        stmt.close();
        rs.close();
        return cena;
    }
    
    /**
     * Metoda zwracajaca opis filmu.
     * @return opis - opis filmu.
     */
    String getOpis(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        String opis = rs.getString("opisF");
        stmt.close();
        rs.close();
        return opis;
    }
    
    /**
     * Metoda zwracajaca gatunki filmu.
     * @return gatunki - gatunki filmu.
     */
    String getGatunki(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy_Gatunki fg JOIN Filmy f ON (fg.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        StringBuilder sb = new StringBuilder();
        rs.first();
        int n = 0;
        while(rs.getRow()!=0){
            if(n++ != 0)
                sb.append(String.format(", "));
            sb.append(String.format("%s", rs.getString("nazwaG")));
            rs.next();
        }
        stmt.close();
        rs.close();
        return sb.toString();
    }
    
    /**
     * Metoda zwracajaca rok powstania filmu.
     * @return rok - rok powstania filmu.
     */
    int getRok(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        int rok = rs.getInt("rokPowstaniaF");
        stmt.close();
        rs.close();
        return rok;
    }
    
    /**
     * Metoda zwracajaca reżyserów filmu.
     * @return rezyserzy - reżyserzy filmu.
     */
    String getRezyserzy(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Rezyserzy r JOIN Filmy_Rezyserzy fr ON (r.idR = fr.idR) JOIN Filmy f ON (fr.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        StringBuilder sb = new StringBuilder();
        rs.first();
        int n = 0;
        while(rs.getRow()!=0){
            if(n++ != 0)
                sb.append(String.format(", "));
            sb.append(String.format("%s %s", rs.getString("imieR"), rs.getString("nazwiskoR")));
            rs.next();
        }
        stmt.close();
        rs.close();
        return sb.toString();
    }
    
    /**
     * Metoda zwracajaca aktorów filmu.
     * @return aktorzy - aktorzy z filmu.
     */
    String getAktorzy(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Aktorzy a JOIN Filmy_Aktorzy fa ON (a.idA = fa.idA) JOIN Filmy f ON (fa.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        StringBuilder sb = new StringBuilder();
        rs.first();
        int n = 0;
        while(rs.getRow()!=0){
            if(n++ != 0)
                sb.append(String.format(", "));
            sb.append(String.format("%s %s", rs.getString("imieA"), rs.getString("nazwiskoA")));
            rs.next();
        }
        stmt.close();
        rs.close();
        return sb.toString();
    }

    /**
     * Metoda sprawdza czy podane konto nalezy do sprzedawcy.
     * @param daneKonto - podane konto, do sprawdzenia
     * @return true - gdy podane konto faktycznie jest kontem sprzedawcy
     * 		   false - gdy podane konto nie jest kontem sprzedawcy
     */
    boolean sprawdzCzyKontoSprzedawcy(String nazwaKonta) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Pracownicy p JOIN Uzytkownicy u ON (p.idU = u.idU) WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwaKonta);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()!=0){
            stmt.close();
            rs.close();
            return true;
        }
        stmt.close();
        rs.close();
        return false;
    }

    /**
     * Metoda dodaje konto o podanej nazwie do tablicy kont.
     * @param nazwa - nazwa konta do dodania
     * @param haslo - haslo konta do dodania
     * @param adres - adres konta do dodania
     * @param nazwisko - nazwisko konta do dodania
     * @throws Exception - wyjatek zglaszany, gdy nazwa konta jest pusta lub gdy konto o podanej nazwie juz istnieje
     */
    void zalozKonto(String nazwa, String haslo, String imie, String nazwisko, String wojewodztwo, String miasto, String ulica, int nrDomu, int nrMieszkania) throws Exception {
        if (nazwa==null || nazwa.equals("")) throw(new Exception("Nazwa konta nie moze byc pusta"));
        if (znajdzKonto(nazwa)!= null) throw(new Exception("Konto o podanej nazwie już istnieje"));
        
        if (haslo.equals("")) throw(new Exception("Haslo nie moze byc puste"));
        if (haslo.length()<8) throw(new Exception("Podaj haslo dluzsze niz 7 znakow")); 
        if (haslo.length()>16) throw(new Exception("Podaj haslo krotsze niz 17 znakow")); 
        if (imie.equals("")) throw(new Exception("Imię nie moze byc puste"));
        if (nazwisko.equals("")) throw(new Exception("Nazwisko nie moze byc puste"));
        if (wojewodztwo.equals("")) throw(new Exception("Nazwa województwa nie moze byc pusta"));
        if (miasto.equals("")) throw(new Exception("Nazwa miasta nie moze byc pusta"));
        if (nrDomu==0) throw(new Exception("Numer domu nie moze byc pusty"));
        
        PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM AdresyKorespondencyjne WHERE miastoAD = ? AND ulicaAD = ? AND numerDomuAD = ? AND numerMieszkaniaAD = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); 
        stmt2.setString(1, miasto);
        stmt2.setString(2, ulica);
        stmt2.setInt(3, nrDomu);
        stmt2.setInt(4, nrMieszkania);
        ResultSet rs2 = stmt2.executeQuery();
        
        rs2.first();
        int id;
        if(rs2.getRow()!=0){ //jeśli jest adres to zbieramy jego ID
            id = rs2.getInt("idAD");
        }
        else{   //jeśli nie ma to dodajemy 
            stmt.close();
            rs.close();
            
            stmt = con.prepareStatement("SELECT * FROM AdresyKorespondencyjne", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
            rs = stmt.executeQuery();
            rs.last();
            
            id = rs.getInt("idAD") + 1;
            rs.moveToInsertRow();
            rs.updateInt("idAD", id);
            rs.updateString("miastoAD", miasto);
            rs.updateString("ulicaAD", ulica);
            rs.updateInt("numerDomuAD", nrDomu);
            rs.updateInt("numerMieszkaniaAD", nrMieszkania);
            rs.updateString("nazwaW", wojewodztwo);

            rs.insertRow();
        }
             
        stmt.close();
        rs.close();
        
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
        rs = stmt.executeQuery();

        rs.last();
        int id2 = rs.getInt("idU");
        rs.moveToInsertRow();
        rs.updateInt("idU", id2 + 1);
        rs.updateString("nazwaU", nazwa);
        rs.updateInt("hasloU", haslo.hashCode());
        rs.updateString("imieU", imie);
        rs.updateString("nazwiskoU", nazwisko);
        rs.updateInt("idAD", id);
        
        rs.insertRow();
             
        stmt.close();
        rs.close();
    }

    /**
     * Metoda usuwa podane konto (usuwa z tablicy)
     * @param konto - konto do usuniecia
     * @throws Exception - wyjatek zglaszany, gdy nazwa konta jest pusta
     */
    void usunKonto(String nazwa) throws Exception {
        if (nazwa==null || nazwa.equals("")) throw(new Exception("Nie podano nazwy konta"));
        
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();

        rs.first();

        rs.updateInt("hasloU", (int)(Math.random()*Integer.MAX_VALUE));
        
        rs.updateRow();
             
        stmt.close();
        rs.close();
    }

    /**
     * Metoda znajduje konto o podanej nazwie na liscie kont i zwraca go.
     * @param nazwa - nazwa wyszukiwanego konta
     * @return nazwaKonta - gdy konto zostanie znalezione na liscie
     *          null - gdy konto nie zostanie znalezione na liscie
     */
    String znajdzKonto(String nazwa) throws Exception {
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()!=0){
            String nazwaa = rs.getString("nazwaU");
            stmt.close();
            rs.close();
            return nazwaa;
        }
        return null;
    }

    /**
     * Metoda znajduje konto o podanej nazwie na liscie kont i zwraca go jesli podano poprawne haslo.
     * @param nazwa - nazwa wyszukiwanego konta
     * @param haslo - haslo wyszukiwanego konta
     * @return konto - gdy konto zostanie znalezione na liscie i haslo jest poprawne
     * 		   null - gdy konto nie zostanie znalezione na liscie lub/i gdy haslo jest niepoprawne
     */
    String znajdzKonto(String nazwa, String haslo) throws Exception {
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ? AND hasloU = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        stmt.setInt(2, haslo.hashCode());
        rs = stmt.executeQuery();
        rs.first();
       
        if(rs.getRow()!=0){
            String nazwaa = rs.getString("nazwaU");
            stmt.close();
            rs.close();
            return nazwaa;
        }
        return null;
    }

    /**
     * Metoda zwracajaca liste kont jako String.
     * @return lista kont jako String
     */
    String listaKont() throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy u JOIN AdresyKorespondencyjne a ON (u.idAD = a.idAD) JOIN Wojewodztwa w ON (w.nazwaW = a.nazwaW)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery();
        
        StringBuilder sb = new StringBuilder();
        int n = 0;
        rs.first();
        while(rs.getRow()!=0){
            if (n++ != 0) sb.append("\n");
            sb.append(String.format("Nazwa: %s. Imię: %s. Nazwisko: %s. Adres: %s.", rs.getString("nazwaU"), rs.getString("imieU"), rs.getString("nazwiskoU"), "Województwo "+rs.getString("nazwaW")+"\n"+rs.getString("miastoAD")+" ul. "+rs.getString("ulicaAD")+" "+rs.getInt("numerDomuAD")+" "+rs.getInt("numerMieszkaniaAD")));
            rs.next();
        }
        stmt.close();
        rs.close();
        return sb.toString();
    }
    
    /**
     * Metoda przypisujaca atrybutowi adres podanego String'a.
     * @param adres - adres jaki chcemy ustawic dla naszego konta
     */
    void setAdres(String wojewodztwo, String miasto, String ulica, int nrDomu, int nrMieszkania, String nazwa) throws SQLException, Exception{
        if (miasto.equals("")) throw(new Exception("Nazwa miasta nie moze byc pusta"));
        if (nrDomu==0) throw(new Exception("Numer domu nie moze byc pusty"));
        
        stmt = con.prepareStatement("SELECT * FROM AdresyKorespondencyjne WHERE miastoAD = ? AND ulicaAD = ? AND numerDomuAD = ? AND numerMieszkaniaAD = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
        stmt.setString(1, miasto);
        stmt.setString(2, ulica);
        stmt.setInt(3, nrDomu);
        stmt.setInt(4, nrMieszkania);
        rs = stmt.executeQuery();
        
        rs.first();
        int id;
        if(rs.getRow()!=0){ //jeśli jest adres to zbieramy jego ID
            id = rs.getInt("idAD");
        }
        else{   //jeśli nie ma to dodajemy 
            stmt = con.prepareStatement("SELECT * FROM AdresyKorespondencyjne", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
            rs = stmt.executeQuery();
            rs.last();
            
            id = rs.getInt("idAD") + 1;
            rs.moveToInsertRow();
            rs.updateInt("idAD", id);
            rs.updateString("miastoAD", miasto);
            rs.updateString("ulicaAD", ulica);
            rs.updateInt("numerDomuAD", nrDomu);
            rs.updateInt("numerMieszkaniaAD", nrMieszkania);
            rs.updateString("nazwaW", wojewodztwo);

            rs.insertRow();
        }
             
        stmt.close();
        rs.close();
        
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();

        rs.first();
        rs.updateInt("idAD", id);
        rs.updateRow();
             
        stmt.close();
        rs.close();
    }
    
    /**
     * Metoda przypisujaca atrybutowi nazwisko podanego String'a.
     * @param nazwisko - nazwisko jakie chcemy ustawic dla naszego konta
     */
    void setNazwisko(String nazwisko, String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        rs.updateString("nazwiskoU", nazwisko);
        rs.updateRow();
        stmt.close();
        rs.close();
        return;
    }
    
    /**
     * Metoda ustawiajaca nowe haslo pod warunkiem podania poprawnego starego hasla.
     * @param stareHaslo - stare haslo
     * @param noweHaslo - nowe haslo
     * @throws Exception - wyjatek zglaszany gdy stare haslo jest niepoprawne
     */ 
    void setHaslo(String stareHaslo, String noweHaslo, String nazwa) throws Exception {
        if(noweHaslo.length()<8)    throw(new Exception("Podaj haslo dluzsze niz 7 znakow")); 
        if(noweHaslo.length()>16)   throw(new Exception("Podaj haslo krotsze niz 17 znakow")); 
        
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()!=0){
            if (stareHaslo==null || stareHaslo.hashCode()!=rs.getInt("hasloU")) throw new Exception("Bledne haslo");
            rs.updateInt("hasloU", noweHaslo.hashCode());
            rs.updateRow();
        }
        stmt.close();
        rs.close();
    }
    
    /**
     * Metoda sprawdzajaca czy podane haslo jest prawidlowe.
     * @param podaneHaslo - podane haslo do sprawdzenia
     * @return - true, jesli haslo jest poprawna
     * 		   - false, jesli haslo jest niepoprawne.
     */
    boolean sprawdzHaslo(String podaneHaslo, String nazwa) throws Exception {
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        if(rs.getRow()==0){
            throw new Exception("Bledna nazwa konta");
        }
        if (podaneHaslo==null || podaneHaslo.hashCode()!=rs.getInt("hasloU")){
            stmt.close();
            rs.close();
            return false;
        }
        stmt.close();
        rs.close();
        return true;
        
    }
    
    /**
     * Metoda zwracajaca nazwisko.
     * @return nazwisko - nazwisko właściciela konta
     */
    String getNazwisko(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        String nazwisko = rs.getString("nazwiskoU");
        stmt.close();
        rs.close();
        return nazwisko;
    }
    
    /**
     * Metoda zwracajaca imię.
     * @return imie - imię wlasciciela konta
     */
    String getImie(String nazwa) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        
        rs.first();
        String imie = rs.getString("imieU");
        stmt.close();
        rs.close();
        return imie;
    }
    
    /**
     * Metoda zwracajaca adres.
     * @return adres - adres konta
     */
    String getAdres(String nazwa) throws SQLException{
        String adres = null;
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, nazwa);
        rs = stmt.executeQuery();
        rs.first();
        if(rs.getRow()!=0){
            int id = rs.getInt("idAD");
            stmt.close();
            rs.close();
            
            stmt = con.prepareStatement("SELECT * FROM WidokAdres WHERE idAD = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            rs.first();
            if(rs.getRow()!=0){
                adres = "Województwo "+rs.getString("nazwaW")+"\n"+rs.getString("miastoAD")+" ul. "+rs.getString("ulicaAD")+" "+rs.getString("numerDomuAD")+" "+rs.getString("numerMieszkaniaAD");
            }
            
        }
        stmt.close();
        rs.close();
        return adres;
    }

    /**
     * Metoda dodaje wypozyczenie o podanych parametrach do historii wypozyczen.
     * @param nazwa - nazwa filmu
     * @param cena - cena filmu
     * @param ilosc - ilosc zakupionego filmu
     * @param nazwaKlienta - nazwa klienta ktory zakupil film
     */
    void dodajDoHistoriiWypozyczen(String nazwa, double cena, String nazwaKlienta, String dataWypozyczenia, String dataZwrotu) throws SQLException, ParseException{
        PreparedStatement stmt2;
        ResultSet rs2;
        stmt2 = con.prepareStatement("SELECT * FROM Filmy WHERE nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt2.setString(1, nazwa);
        rs2 = stmt2.executeQuery();

        rs2.first();

        int idFilmu=rs2.getInt("idF");
        
        stmt2.close();
        rs2.close();
        
        stmt2 = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt2.setString(1, nazwaKlienta);
        rs2 = stmt2.executeQuery();

        rs2.first();

        int idUzytkownika=rs2.getInt("idU");
        
        stmt2.close();
        rs2.close();
        
        stmt = con.prepareStatement("SELECT * FROM Wypozyczenia", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        rs = stmt.executeQuery();

        rs.last();
        int id;
        if(rs.getRow()==0)
            id = 0;
        else
            id=rs.getInt("idW");
        rs.moveToInsertRow();
        rs.updateInt("idW", id + 1);
        rs.updateDouble("cenaW", cena);
        rs.updateDate("dataWypozyczeniaW", Date.valueOf(dataWypozyczenia));
        rs.updateDate("dataZwrotuW", Date.valueOf(dataZwrotu));
        rs.updateInt("idF", idFilmu);
        rs.updateInt("idU", idUzytkownika);
        
        rs.insertRow();
             
        stmt.close();
        rs.close();
    }

    /**
     * Metoda zwracajaca historie wypozyczen dla danego konta jako String.
     * @return historia wypozyczen dla danego konta jako String
     */
    String wyswietlHistorieWypozyczenKlienta(String nazwaKonta) throws SQLException{
        String nazwaFilmu;
        stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, nazwaKonta);
        rs = stmt.executeQuery();
        
        rs.first();
        
        int id=rs.getInt("idU");
        
        stmt.close();
        rs.close();
        
        stmt = con.prepareStatement("SELECT * FROM Wypozyczenia WHERE idU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt.setInt(1, id);
        rs = stmt.executeQuery();
        
        StringBuilder sb = new StringBuilder();
        int n = 0;
        rs.first();
        ArrayList<Wypozyczenie> listaFilmow = new ArrayList<Wypozyczenie>();
        while(rs.getRow()!=0){
            listaFilmow.add(new Wypozyczenie(0,"","",rs.getDouble("cenaW"),rs.getString("dataWypozyczeniaW"),rs.getString("dataZwrotuW"),rs.getInt("idF")));
            rs.next();
        }
        rs.close();
        stmt.close();
        for (Wypozyczenie wyp : listaFilmow) {
            stmt = con.prepareStatement("SELECT * FROM Filmy WHERE idF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, wyp.getidFilmu());
            rs = stmt.executeQuery();

            rs.first();

            nazwaFilmu = rs.getString("nazwaF");

            stmt.close();
            rs.close();
            
            if (n++ != 0) sb.append("\n");
            sb.append(String.format("Nazwa: %s. Cena: %.2f. Nazwa klienta: %s. Data wypozyczenia: %s. Data zwrotu: %s.", nazwaFilmu, wyp.getCena(), nazwaKonta, wyp.getDataWypozyczenia(), wyp.getDataZwrotu()));
        }
        return sb.toString();
    }

    /**
     * Metoda znajduje wypozyczenie o podanym numerze indeksu i zwraca go.
     * @param id - indeks wyszukiwanego wypozyczenia
     * @return Wypozyczenie - gdy wypozyczenie zostanie znalezione na liscie
     * 		   null - gdy wypozyczenie nie zostanie znalezione na liscie
     */
    Wypozyczenie znajdzWypozyczenieCale(int id) throws SQLException{
        stmt = con.prepareStatement("SELECT * FROM Wypozyczenia", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = stmt.executeQuery();
        
        rs.last();
        for(int i=0;i<id-1;i++){
            rs.previous();
        }
        if(rs.getRow()!=0){
            Wypozyczenie wyp = new Wypozyczenie(rs.getInt("idW"), "", "", rs.getDouble("cenaW"), rs.getString("dataWypozyczeniaW"), rs.getString("dataZwrotuW"), 0);
            int idUU = rs.getInt("idU");
            int idFF = rs.getInt("idF");
            stmt.close();
            rs.close();
            stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE idU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, idUU);
            rs = stmt.executeQuery();

            rs.first();

            String nazwaUzytkownika=rs.getString("nazwaU");

            stmt.close();
            rs.close();

            stmt = con.prepareStatement("SELECT * FROM Filmy WHERE idF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, idFF);
            rs = stmt.executeQuery();

            rs.first();

            String nazwaFilmu=rs.getString("nazwaF");

            stmt.close();
            rs.close();
            
            wyp.setNazwaFilmu(nazwaFilmu);
            wyp.setNazwaKlienta(nazwaUzytkownika);
            return wyp;
        }
        stmt.close();
        rs.close();
        return null;
    }

    Wypozyczenie znajdzWypozyczenieCaleWyszukane(int id, String nazwaF, String nazwaU, int numerWypozyczenia) throws SQLException{
        
        if(nazwaU.equals("")){
            if(nazwaF.equals("")){
                stmt = con.prepareStatement("SELECT * FROM Wypozyczenia WHERE idW = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setInt(1, id);
            }
            else if(id==0){
                stmt = con.prepareStatement("SELECT * FROM Wypozyczenia w JOIN Filmy f ON (w.idF = f.idF) WHERE f.nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, nazwaF);
            }
            else{
                stmt = con.prepareStatement("SELECT * FROM Wypozyczenia w JOIN Filmy f ON (w.idF = f.idF) WHERE f.nazwaF = ? AND w.idW = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, nazwaF);
                stmt.setInt(2, id);
            }
        }
        else if(nazwaF.equals("")){
            if(id==0){
                stmt = con.prepareStatement("SELECT * FROM Wypozyczenia w JOIN Uzytkownicy u ON (w.idU = u.idU) WHERE u.nazwaU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setString(1, nazwaU);
            }
            else{
                stmt = con.prepareStatement("SELECT * FROM Wypozyczenia w JOIN Uzytkownicy u ON (w.idU = u.idU) WHERE u.nazwaU = ? AND w.idW = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmt.setInt(1, id);
                stmt.setString(2, nazwaU);
            }
        }
        else if(id==0){
            stmt = con.prepareStatement("SELECT * FROM Wypozyczenia w JOIN Uzytkownicy u ON (w.idU = u.idU) JOIN Filmy f ON (f.idF = w.idF) WHERE u.nazwaU = ? AND f.nazwaF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, nazwaU);
            stmt.setString(2, nazwaF);
        }
        else{
            stmt = con.prepareStatement("SELECT * FROM Wypozyczenia w JOIN Uzytkownicy u ON (w.idU = u.idU) JOIN Filmy f ON (f.idF = w.idF) WHERE u.nazwaU = ? AND f.nazwaF = ? AND w.idW = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, nazwaU);
            stmt.setString(2, nazwaF);
            stmt.setInt(3, id);
        }
        rs = stmt.executeQuery();
        
        rs.last();
        for(int i=0;i<numerWypozyczenia-1;i++){
            rs.previous();
        }
        if(rs.getRow()!=0){
            Wypozyczenie wyp = new Wypozyczenie(rs.getInt("idW"), "", "", rs.getDouble("cenaW"), rs.getString("dataWypozyczeniaW"), rs.getString("dataZwrotuW"), 0);
            int idUU = rs.getInt("idU");
            int idFF = rs.getInt("idF");
            stmt.close();
            rs.close();
            stmt = con.prepareStatement("SELECT * FROM Uzytkownicy WHERE idU = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, idUU);
            rs = stmt.executeQuery();

            rs.first();

            String nazwaUzytkownika=rs.getString("nazwaU");

            stmt.close();
            rs.close();

            stmt = con.prepareStatement("SELECT * FROM Filmy WHERE idF = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, idFF);
            rs = stmt.executeQuery();

            rs.first();

            String nazwaFilmu=rs.getString("nazwaF");

            stmt.close();
            rs.close();
            
            wyp.setNazwaFilmu(nazwaFilmu);
            wyp.setNazwaKlienta(nazwaUzytkownika);
            return wyp;
        }
        stmt.close();
        rs.close();
        return null;
    }
    
}