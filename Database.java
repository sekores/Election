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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            //System.out.println("Verbindung zur Datenbank "+db+" mit dem Benutzer "+username+" wurde erfolgreich hergestellt");
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
			iterator.next();
				
			
            List<String> temp = new ArrayList<String>();
            
            
				while(iterator.hasNext()){
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					
					temp.clear();
					while (cellIterator.hasNext()) {
		                Cell cell = cellIterator.next(); 
		                
		                temp.add(cell.toString());
		                
		            }
					
					if(temp.get(3).length()==19 && temp.get(3).contains("T") && temp.get(3).contains("2016")){
						temp.add(3," ");
					}
					if(temp.get(5).length()==4 && temp.get(5).equals("True")|| temp.get(5).length()==5 && temp.get(5).equals("False")){
						temp.add(5," ");
					}
					
					try {
						Connection election = getConnect();
						
						PreparedStatement insertTweet = election.prepareStatement("INSERT INTO  Tweet(anzahl_geteilt, anzahl_like, text, datum, benutzer, uhrzeit) VALUES ("+temp.get(7)+","+temp.get(8)+",'"+tweet(temp.get(1))+"','"+date(temp.get(4))+"','"+temp.get(0)+"','"+time(temp.get(4))+"')");
						insertTweet.executeUpdate();
						
						
						
						election.close();
						
						insertHashtag(temp.get(1));
						
			    	} catch (Exception e) {
			    		//e.printStackTrace();
			    		//System.out.println("INSERT INTO  Tweet(anzahl_geteilt, anzahl_like, text, datum, benutzer, uhrzeit) VALUES ("+temp.get(7)+","+temp.get(8)+",'"+tweet(temp.get(1))+"','"+date(temp.get(4))+"','"+temp.get(0)+"','"+time(temp.get(4))+"')");
					}
		            
					workbook.close();
					inputStream.close();
				}
				
    	
    	
        
    }
    
	public static void insertHashtag(String input) {
		
		while(input.contains("#")){
			String result="";
			int hashtag_index=input.indexOf("#")+1;
			while(input.charAt(hashtag_index)!=' '){
					
				//Solange an der (Stelle #)+1 bis Space abspeichern
				result=result+input.charAt(hashtag_index);
				hashtag_index++;

			}
				
			input=input.substring(hashtag_index, input.length());
				
			System.out.println(result);
			try {		
				Connection conn = getConnect();
				//In die Tabelle einfügen
				PreparedStatement insertHashtag = conn.prepareStatement( "INSERT INTO Hashtag (text) VALUES ('"+result+"') ");
				insertHashtag.executeUpdate();
				conn.close();
				
			} catch (Exception e) {
					
				e.printStackTrace();
				System.out.println("INSERT INTO Hashtag(text) VALUES ('"+result+"');");
				
			}
		}
	}

	public static String tweet(String input) {
		String result="";
		
		if(input.contains("\"") && input.contains("'")){
			result=input.replace("\"", "");
			result=input.replace("'", "");
			
			return result;
		}else
		
		if (input.contains("\"")) {
			result=input.replace("\"", "");

			return result;
		}else
		
		if(input.contains("'")){
			result=input.replace("'", "");

			return result;
			
		}else{
			return input;
		}
		
	}

	
	public static String date(String input) {
		String result="";
		int i = 0;
		while(input.charAt(i)!='T'){
			result=result+input.charAt(i);
			i++;
		}
		return result;
	}
	
	
	public static String time(String input) {
		String result="";
		int i = input.indexOf('T') + 1;
		
		while(i != input.length()){
			result=result+input.charAt(i);
			i++;
		}
	
		return result;
	}

	//ursprünglich wollten wir es mit der CSV machen,  jedoch ahben wir uns dann auf excel umentschieden
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
