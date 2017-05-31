package election;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Database {

	
    public static Connection getConnect(){
    	
    	Connection c = null;
    	
    	String db = "Election";
        String url = "jdbc:postgresql://localhost:5432/"+db;
        
        String username = "postgres";
        String password = "postgres";
        
        try {
            c = DriverManager.getConnection(url,username,password);
            System.out.println("Verbindung zur Datenbank "+db+" mit dem Benutzer "+username+" wurde erfolgreich hergestellt");
        }catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Verbindung zur Datenbank "+db+" mit dem Benutzer "+username+" konnte nicht hergestellt werden");
        }
        
		return c;
    }
    
    public static void createTable(){
    	try {
			Connection dbs = getConnect();
			PreparedStatement createStatements = dbs.prepareStatement("CREATE TABLE IF NOT EXISTS student( matrikelnummer integer NOT NULL, vorname character(20), nachname character(20), CONSTRAINT matrikelnummer PRIMARY KEY (matrikelnummer));");
			createStatements.executeUpdate();
			System.out.println("Entweder besteht schon solch eine Tabelle oder sie wurde erfolgreich erstellt.");
    	} catch (Exception e) {
    		e.printStackTrace();
			System.out.println("Die Tabelle konnte nicht erstellt werden.");
		}
    }
    
    public static void getData() {
    	 	FileReader fr;
			try {
				fr = new FileReader("/home/serkan/Dokumente/Datenbanksysteme/Projekt/american-election-tweets.xlsx");
				BufferedReader br = new BufferedReader(fr);
				
				try {
					while(br.read() != -1){
						System.out.println(br.readLine());
					}

					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    

    	    
    }
    
    public static void main(String[] args) {
       
    	getConnect();
    	getData();

    }

}
