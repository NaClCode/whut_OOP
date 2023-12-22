package com.whut.server.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.whut.server.utils.Config;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======

import lombok.extern.slf4j.Slf4j;

>>>>>>> origin
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
public class DataProcessing{
	public static Config config = new Config();

	private static Connection connection = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	private static boolean connectedToDatabase = false;
	private static String jdbcUrl;
	private static String jdbcUser;
	private static String jdbcPassword;

	private static int getResultSetSize() throws SQLException{
		resultSet.last();
		int num = resultSet.getRow();
		resultSet.beforeFirst();
		return num;
	}

	private static void connectDB(String sql) throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
        connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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
		log.info("数据库连接测试:");
		try {
			jdbcUrl = config.get("jdbc_url");
			jdbcUser = config.get("jdbc_username");
			jdbcPassword = config.get("jdbc_password");
			
			connectDB("select * from user");
			//disconnectDB();
			log.info(">> 数据库连接成功");
		} catch (SQLException e) {
<<<<<<< HEAD
			log.error(">> 数据库连接失败", e);
=======
			log.error(">> 数据库连接失败");
			System.exit(0);
>>>>>>> origin
		}
	}

	public static String[] searchUser(String name, String password){
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
				disconnectDB();
				String[] user = {name, password, role};
				return user;
			}else{
				return new String[0];
			}
		} catch (SQLException e) {
			log.error("searchUser Error:" + e.getMessage());
			return new String[0];
		}
		
	}

	public static String[][] getAllUser(){
		String sql = "select * from user";

		try {
			connectDB(sql);
			resultSet = preparedStatement.executeQuery(sql);
			int num = getResultSetSize();
			int i = 0;
			log.info(String.valueOf(num));
			String[][] user = new String[num][3];
			while (resultSet.next()) {
				String name = resultSet.getString("username");
				String password = resultSet.getString("password");
				String role = resultSet.getString("role");
				user[i][0] = name;
				user[i][1] = password;
				user[i][2] = role;
				i ++; 
			}
			disconnectDB();
			return user;
		} catch (SQLException e) {
			log.error("getAllUser Error:" + e.getMessage());
			return new String[0][3];
		}
		
		
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
			log.error("updateUser Error:" + e.getMessage());
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
			log.error("insertUser Error:" + e.getMessage());
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
			log.error("deleteUser Error:" + e.getMessage());
			return false;
		}
	}

	public static String[] searchDoc(String ID){
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
				String[] doc = {ID, creator, description, name, String.valueOf(timestamp), uploadFileName};
<<<<<<< HEAD
				updateDocDownloadCount(ID);
=======
>>>>>>> origin
				return doc;
			}
			return null;
		} catch (SQLException e) {
			log.error("searchDoc Error:" + e.getMessage());
			return null;
		}
		
	}

<<<<<<< HEAD
	public static boolean updateDocDownloadCount(String ID){
		try {
			String updateDocDownloadCountSql = "update doc set downloadCount = downloadCount + 1 where ID = ?";

			connectDB(updateDocDownloadCountSql);
			preparedStatement.setString(1, ID);
			preparedStatement.executeUpdate();
			return true;
		}catch(SQLException e){
			log.error("updateDocDownloadCount Error:" + e.getMessage());
			return false;
		}
	}


=======
>>>>>>> origin
	public static String[][] getAllDocs(){
		try {
			String sql = "select * from doc";
			connectDB(sql);

			resultSet = preparedStatement.executeQuery();
			int num = getResultSetSize();
<<<<<<< HEAD
			String[][] docs = new String[num][7];
=======
			String[][] docs = new String[num][6];
>>>>>>> origin
			int i = 0;
			while(resultSet.next()){
				String ID = resultSet.getString("ID");
				String creator = resultSet.getString("creator");
				long timestamp = resultSet.getTimestamp("file_timestamp").getTime();
				String name = resultSet.getString("file_name");
				String description = resultSet.getString("file_description");
				String uploadFileName = resultSet.getString("uploadFileName");
<<<<<<< HEAD
				int downloadCount = resultSet.getInt("downloadCount");
=======
>>>>>>> origin
				docs[i][0] = ID;
				docs[i][1] = creator;
				docs[i][2] = description;
				docs[i][3] = name;
				docs[i][4] = String.valueOf(timestamp);
				docs[i][5] = uploadFileName;
<<<<<<< HEAD
				docs[i][6] = String.valueOf(downloadCount);
=======
>>>>>>> origin
				i ++ ;
				
			}
			return docs;
		} catch (SQLException e) {
			log.error("getAllDocs Error:" + e.getMessage());
<<<<<<< HEAD
			return new String[0][7];
=======
			return new String[0][3];
>>>>>>> origin
		}
		

	}

	public static boolean insertDoc(String ID, String creator, String description, String name, String time, String uploadFileName){
		try{
			String sql = "insert into doc (ID, creator, file_timestamp, file_description, file_name, uploadFileName, downloadCount) values (?,?,?,?,?,?,0)";
			connectDB(sql);
			preparedStatement.setString(1, ID);
			preparedStatement.setString(2, creator);
			preparedStatement.setTimestamp(3, new Timestamp(Long.parseLong(time)));
			preparedStatement.setString(4, description);
			preparedStatement.setString(5, name);
			preparedStatement.setString(6, uploadFileName);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
