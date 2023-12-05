package com.whut;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Enumeration;
import com.whut.model.Administrator;
import com.whut.model.Browser;
import com.whut.model.Doc;
import com.whut.model.Operator;
import com.whut.model.User;
import com.whut.utils.Config;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataProcessing{
	public static Config config = new Config();

	private static Connection connection = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	private static boolean connectedToDatabase = false;
	private static String jdbcUrl;
	private static String jdbcUser;
	private static String jdbcPassword;

	private static void connectDB(String sql) throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
        connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		preparedStatement = connection.prepareStatement(sql);
		connectedToDatabase = true;
	}

	private static void disconnectDB() throws SQLException{
		if(connectedToDatabase){
			resultSet.close();
			preparedStatement.close();
			connection.close();
		}
	}

	public static void Init(String[] args){
		config.init(args);
		System.out.println("数据库连接测试:");
		try {
			jdbcUrl = config.get("jdbc_url");
			jdbcUser = config.get("jdbc_username");
			jdbcPassword = config.get("jdbc_password");
			connectDB("select * from user");
			//disconnectDB();
			System.out.println(">> 数据库连接成功");
		} catch (SQLException e) {
			System.out.println(">> 数据库连接失败");
			System.exit(0);
		}
	}

	public static User searchUser(String name, String password){
		String searchUserSql = "SELECT * FROM user WHERE username = ? AND password = ?";
		try {
			connectDB(searchUserSql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				name = resultSet.getString("username");
				password = resultSet.getString("password");
				String role = resultSet.getString("role");
				User user = null;
				if (role.equals("Operator"))
					user = new Operator(name, password, role);
				else if (role.equals("Administrator"))
					user = new Administrator(name, password, role);
				else
					user = new Browser(name, password, role);

				disconnectDB();
				return user;
			}else{
				return null;
			}
		} catch (SQLException e) {
			System.out.println("searchUser Error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}

	public static Enumeration<User> getAllUser(){
		String sql = "select * from user";
		ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

		try {
			connectDB(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				String name = resultSet.getString("username");
				String password = resultSet.getString("password");
				String role = resultSet.getString("role");
				User aUser;
				if (role.equals("Operator")) {
					aUser = new Operator(name, password, role);
				} else if (role.equals("Administrator")) {
					aUser = new Administrator(name, password, role);
				} else {
					aUser = new Browser(name, password, role);
				}
				users.put(name, aUser);
			}
			disconnectDB();
			return users.elements();
		} catch (SQLException e) {
			System.out.println("getAllUser Error:" + e.getMessage());
			return users.elements();
		}
		
		
	}

	public static ArrayList<User> getAllUsers(){
		Enumeration<User> e = getAllUser();
		ArrayList<User> users = new ArrayList<User>();
		while(e.hasMoreElements()){
			users.add(e.nextElement());
		}
		return users;
	}

	public static boolean updateUser(String name, String password, String role){
		String sql = "update user set password = ?,role = ? where username = ?";
		try{
			connectDB(sql);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, role);
			preparedStatement.setString(3, name);
			preparedStatement.executeUpdate();
			disconnectDB();
			return true;
		}catch(SQLException e){
			System.out.println("updateUser Error:" + e.getMessage());
			return false;
		}
		
	}

	public static boolean insertUser(String name, String password, String role){

		try{
			String sql = "insert into user (username, password, role) values (?, ?, ?)";
			connectDB(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, role);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("insertUser Error:" + e.getMessage());
			return false;
		}
		
	}

	public static boolean deleteUser(String name){
		try {
			String sql = "delete from user where username = ?";
			connectDB(sql);
			preparedStatement.setString(1, name);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("deleteUser Error:" + e.getMessage());
			return false;
		}
	}

	public static Doc searchDoc(String ID){
		try {
			String searchDocSql = "select * from doc where ID = ?";

			connectDB(searchDocSql);
			preparedStatement.setString(1, ID);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				ID = resultSet.getString("ID");
				String creator = resultSet.getString("creator");
				long timestamp = resultSet.getTimestamp("file_timestamp").getTime();
				String name = resultSet.getString("file_name");
				String description = resultSet.getString("file_description");
				String uploadFileName = resultSet.getString("uploadFileName");
				return new Doc(ID, creator, description, name, timestamp, uploadFileName);
			}
			return null;
		} catch (SQLException e) {
			System.out.println("searchDoc Error:" + e.getMessage());
			return null;
		}
		
	}

	public static Enumeration<Doc> getAllDocs(){
		ConcurrentHashMap<String, Doc> docs = new ConcurrentHashMap<String, Doc>();
		try {
			String sql = "select * from doc";
			connectDB(sql);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				String ID = resultSet.getString("ID");
				String creator = resultSet.getString("creator");
				long timestamp = resultSet.getTimestamp("file_timestamp").getTime();
				String name = resultSet.getString("file_name");
				String description = resultSet.getString("file_description");
				String uploadFileName = resultSet.getString("uploadFileName");
				docs.put(ID, new Doc(ID, creator, description, name, timestamp, uploadFileName));
			}
			return docs.elements();
		} catch (SQLException e) {
			System.out.println("getAllDocs Error:" + e.getMessage());
			return docs.elements();
		}
		

	}

	public static ArrayList<Doc> getAllDocss(){
		Enumeration<Doc> e = getAllDocs();
		ArrayList<Doc> docs = new ArrayList<Doc>();
		while(e.hasMoreElements()){
			docs.add(e.nextElement());
		}
		return docs;
	}

	public static boolean insertDoc(Doc doc){
		try{
			String sql = "insert into doc (ID, creator, file_timestamp, file_description, file_name, uploadFileName) values (?,?,?,?,?,?)";
			connectDB(sql);
			preparedStatement.setString(1, doc.getID());
			preparedStatement.setString(2, doc.getCreator());
			preparedStatement.setTimestamp(3, new Timestamp(doc.getTimestamp()));
			preparedStatement.setString(4, doc.getDescription());
			preparedStatement.setString(5, doc.getFilename());
			preparedStatement.setString(6, doc.getUploadFileName());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
