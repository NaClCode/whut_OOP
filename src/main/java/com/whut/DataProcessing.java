package com.whut;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Enumeration;
import com.whut.exceptions.DataException;
import com.whut.model.Administrator;
import com.whut.model.Browser;
import com.whut.model.Doc;
import com.whut.model.Operator;
import com.whut.model.User;
import com.whut.utils.Config;
import java.io.File;

public class DataProcessing{
	private static Hashtable<String, User> users;
	private static Hashtable<String, Doc> docs;
	public static Scanner scanner = new Scanner(System.in);
	public static Config config = new Config();
	
	public static void Init(String[] args) throws IOException, DataException{
		config.init(args);
		users = new Hashtable<String, User>();
		String name, password, role;
		String path = config.get("user_filepath");
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		while ((name = br.readLine()) != null){
			password = br.readLine();
			role = br.readLine();

			if (password == null || role == null){
				br.close();
				throw new DataException("数据错误！");
			}

			if (role.equals("Operator"))
				users.put(name, new Operator(name, password, role));
			else if (role.equals("Browser"))
				users.put(name, new Browser(name, password, role));
			else if (role.equals("Administrator"))
				users.put(name, new Administrator(name, password, role));
			else{
				br.close();
				throw new DataException("数据错误！");
			}
		}
		br.close();

		docs = new Hashtable<String, Doc>();
		String ID, creator, description, filename, uploadFileName;
		long timestamp;
		path = config.get("doc_filepath");
		FileReader frf = new FileReader(path);
		BufferedReader brf = new BufferedReader(frf);
		while((ID = brf.readLine()) != null){
			creator = brf.readLine();
			description = brf.readLine();
			filename = brf.readLine();
			timestamp = Long.parseLong(brf.readLine());
			uploadFileName = brf.readLine();
			docs.put(ID, new Doc(ID, creator, description, filename, timestamp, uploadFileName));
		}
		brf.close();

	}
	public static User searchUser(String name, String password){
		if (users.containsKey(name)){
			User temp = users.get(name);
			if ((temp.getPassword()).equals(password))
				return temp;
		}
		return null;
	}

	public static Enumeration<User> getAllUser(){
		Enumeration<User> e = users.elements();
		return e;
	}

	public static ArrayList<User> getAllUsers(){
		Enumeration<User> e = getAllUser();
		ArrayList<User> users = new ArrayList<User>();
		while(e.hasMoreElements()){
			users.add(e.nextElement());
		}
		return users;
	}

	public static boolean updateUser(String name, String password, String role) throws IOException{
		User user;

		if (users.containsKey(name)){
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name, password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name, password, role);
			else 
				user = new Browser(name, password, role);
			users.put(name, user);
			updateUserFile();
			return true;
		}
		else
			return false;
	}

	public static boolean insertUser(String name, String password, String role) throws IOException{
		User user;

		if (users.containsKey(name))
			return false;
		else{
			if (role.equalsIgnoreCase("administrator"))
				user = new Administrator(name, password, role);
			else if (role.equalsIgnoreCase("operator"))
				user = new Operator(name, password, role);
			else
				user = new Browser(name, password, role);
			users.put(name, user);
			updateUserFile();
			return true;
		}
	}

	public static boolean deleteUser(String name) throws IOException{
		if (users.containsKey(name)){
			users.remove(name);
			updateUserFile();
			return true;
		}
		else
			return false;
	}

	public static void updateUserFile() throws IOException{
		String path = config.get("user_filepath");
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		for (String name : users.keySet())
			bw.write(name + "\n" + 
					users.get(name).getPassword() + "\n" +
					users.get(name).getRole() + "\n");
		bw.close();
	}

	public static Doc searchDoc(String ID){
		if (docs.containsKey(ID)) return docs.get(ID);
		return null;
	}

	public static Enumeration<Doc> getAllDocs(){
		Enumeration<Doc> e = docs.elements();
		return e;
	}

	public static ArrayList<Doc> getAllDocss(){
		Enumeration<Doc> e = getAllDocs();
		ArrayList<Doc> docs = new ArrayList<Doc>();
		while(e.hasMoreElements()){
			docs.add(e.nextElement());
		}
		return docs;
	}

	public static boolean deleteDoc(String ID) throws IOException{
		if (!docs.containsKey(ID)) return false;
		String path = config.get("doc_filepath");
		File file = new File(path + "\\" + docs.get(ID).getFilename());
		file.delete();
		docs.remove(ID);
		updateDocFile();
		return true;
	}

	public static boolean insertDoc(Doc doc) throws IOException{
		if (docs.containsKey(doc.getID())) return false;
		docs.put(doc.getID(), doc);
		updateDocFile();
		return true;
	}

	public static void updateDocFile() throws IOException{

		String path = config.get("doc_filepath");

		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		for (String ID : docs.keySet())
			bw.write(
					ID + "\n" +
					docs.get(ID).getCreator() + "\n" +
					docs.get(ID).getDescription() + "\n" +
					docs.get(ID).getFilename() + "\n" + 
					docs.get(ID).getTimestamp() + "\n" + 
					docs.get(ID).getUploadFileName() + "\n");
		bw.close();
	}
}
