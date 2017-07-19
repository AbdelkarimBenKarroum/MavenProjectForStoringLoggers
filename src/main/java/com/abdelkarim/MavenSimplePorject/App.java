package com.abdelkarim.MavenSimplePorject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.AsynchronousFileChannel;
import java.util.Arrays;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import netscape.javascript.JSObject;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static Logger LOG=Logger.getLogger(App.class.getName());
	
    public static void main( String[] args )
    {
		try {
//			JSONObject obj=new JSONObject();
//			obj.put("Name", "Abdelkarim");
//			obj.put("Profession", "stuent");
			
			LOG.setLevel(Level.INFO);
			
			FileHandler fHandler=new FileHandler("loglama.log",true);
			fHandler.setLevel(Level.INFO);
			LOG.addHandler(fHandler);
			
			String toBeAdded=convertToJSON("loglama.log");
			
			
			//connectivity with mongodb
			//first create client then assign it args to localhost and port
			//use the instance of mongoclient to get the database
			
			MongoClient mongoClient=new MongoClient(new MongoClientURI("mongodb://127.0.1.1:27017"));
			
			MongoDatabase database = mongoClient.getDatabase("test");
			System.out.println("connected successfully");
			System.out.println("Server is ready");
			//document to be added to selected database
			Document person = Document.parse(toBeAdded);
			//insertion operation
			MongoCollection collection = database.getCollection("loggers");
			collection.insertOne(person);
			mongoClient.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		
    }
    public static String convertToJSON(String filePath) throws IOException {
    	String result = null;
    	try {
    		File f=new File(filePath);
			FileInputStream FIS=new FileInputStream(f);
			BufferedReader BR=new BufferedReader(new InputStreamReader(FIS));
			String Source,toBeConverted=null;
			while ((Source=BR.readLine())!=null) {
				toBeConverted+=Source;	
			}
			JSONObject obj=XML.toJSONObject(toBeConverted);
			String jsonObj=obj.toString(4);
			result=jsonObj;
			FIS.close();
			BR.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
}
