package election;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
   
    public static void getExcel() throws IOException{
    	
    		String excelFilePath = "/home/serkan/Dokumente/Datenbanksysteme/Projekt/american-election-tweets.xlsx";
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = (Sheet) workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
				
				while(iterator.hasNext()){
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					
					while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next();
		                String[] temp;//Hier waren wir stehen geblieben
		                //Idee: Alle Cells in eine Liste von Strings einfügen und damit weiter arbeiten.
		                System.out.println(cell.toString());
		            }
		            System.out.println();
					workbook.close();
					inputStream.close();
				}
    	
    	
        
    }
    
    public static void getCSV() {
    	 	FileReader fr;
			try {
				fr = new FileReader("/home/serkan/Dokumente/Datenbanksysteme/Projekt/american-election-tweets.csv");
				BufferedReader br = new BufferedReader(fr);
				
				try {
					while(br.ready() != false){
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
       
    	
    	try {
			getExcel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
