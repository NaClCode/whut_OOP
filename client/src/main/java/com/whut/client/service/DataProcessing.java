package com.whut.client.service;

import com.whut.client.common.ClientMsg;
import com.whut.client.common.ServerMsg;
import com.whut.client.model.Administrator;
import com.whut.client.model.Browser;
import com.whut.client.model.Doc;
import com.whut.client.model.Operator;
import com.whut.client.model.User;
import com.whut.client.utils.Config;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Vector;
import java.net.Socket;
import java.io.IOException;

@Slf4j
public class DataProcessing{
	public static Config config = new Config();
	private static String serverIp;
	private static int serverPort;
	private static Socket client;
	private static ObjectOutputStream out;
	private static ObjectInputStream in;
	private static Boolean isConnected = false;

	public static boolean connectServer() {
		try {
			if(!isConnected){
				log.info("正在尝试连接...");
				client = new Socket(serverIp, serverPort);
				client.setSoTimeout(1000);
				out = new ObjectOutputStream(client.getOutputStream());
				in = new ObjectInputStream(client.getInputStream());
				out.write(ClientMsg.DATABASES);
				log.info("连接到" + client.getInetAddress().getHostName());
				isConnected = true;
			}

		} catch(IOException e){
			log.error("连接失败", e);
		}
		return isConnected;
		
	}


	public static boolean disconnectServer() {
		try {

			int msg = (int)writeAndRead(ClientMsg.EXIT, null);
			if(msg == ServerMsg.EXIT && isConnected){
				in.close();
				out.close();
				client.close();
				isConnected = false;
			}
			
		} catch (Exception e) {
			log.error("关闭连接失败", e);
		}
		return isConnected;
		
	}

	public static void Init(String[] args){
		config.init(args);
		log.info("服务器连接测试");
		serverIp = config.get("server_ip");
		serverPort = Integer.parseInt(config.get("server_port"));
		connectServer();
	}


	private static void writeMsg(HashMap<String, Object> msg) throws IOException{
		out.writeObject(msg);
		out.flush();
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> readObject() throws IOException, ClassNotFoundException{
		HashMap<String, Object> msg = (HashMap<String, Object>) in.readObject();
		return msg;
	}

	/*
	 * 传输的数据格式为
	 * Status 
	 * - 1 成功
	 * - 0 失败
	 * Data
	 * - 传输对象/错误信息
	 */

	private static Object writeAndRead(String type, Object data) throws Exception{
		try {
			HashMap<String, Object> msg = new HashMap<String, Object>();
			msg.put("Type", type);
			msg.put("Data", data);
			writeMsg(msg);
			HashMap<String, Object> rMsg = (HashMap<String, Object>) readObject();
			if((int)rMsg.get("Status") == 1){
				return rMsg.get("Data");
			}else{
				throw new Exception((String)rMsg.get("Data"));
			}
		}catch (IOException | ClassNotFoundException e) {
			log.error("Error", e.getMessage());
			throw new Exception(e);
		}
	}

	public static User searchUser(String name, String password) throws Exception{
		String[] args = {name, password};
		String[] user = (String[])writeAndRead(ClientMsg.SEARCH_USER, args);
		if(user.length != 3){
			return null;
		}else{
			if(user[2].equals("Operator")){
				return new Operator(user[0], user[1], user[2]);
			}else if(user[2].equals("Administrator")){
				return new Administrator(user[0], user[1], user[2]);
			}else{
				return new Browser(user[0], user[1], user[2]);
			}
		}
	}


	public static Vector<User> getAllUsers() throws Exception{
		String[][] users = (String[][])writeAndRead(ClientMsg.GET_ALL_USER, new String[0]);
		Vector<User> userVector = new Vector<User>();
		for(String[] user : users){
			if(user.length == 3){
				if(user[2].equals("Operator")){
					userVector.add(new Operator(user[0], user[1], user[2]));
				}else if(user[2].equals("Administrator")){
					userVector.add(new Administrator(user[0], user[1], user[2]));
				}else{
					userVector.add(new Browser(user[0], user[1], user[2]));
				}
			}
		}
		return userVector;
	}

	public static boolean updateUser(String name, String password, String role) throws Exception{
		String[] args = {name, password, role};
		return (Boolean)writeAndRead(ClientMsg.UPDATE_USER, args);
	}

	public static boolean deleteUser(String name) throws Exception{
		String[] args = {name};
		return (Boolean)writeAndRead(ClientMsg.DELETE_USER, args);
		
	}

	public static boolean insertUser(String name, String password, String role) throws Exception{
		String[] args = {name, password, role};
		return (Boolean)writeAndRead(ClientMsg.INSERT_USER, args);
	}

	public static Doc searchDoc(String ID) throws Exception{
		String[] args = {ID};
		String[] doc = (String[])writeAndRead(ClientMsg.SEARCH_DOC, args);
		if(doc.length == 6)
			return new Doc(doc[0], doc[1], doc[2], doc[3], Long.parseLong(doc[4]), doc[5]);
		else return null;
	}

	public static Vector<Doc> getAllDocs() throws Exception{
		String[][] docs = (String[][])writeAndRead(ClientMsg.GET_ALL_DOC, new String[0]);
		Vector<Doc> docVector = new Vector<Doc>();
		for(String[] doc : docs){
			if(doc.length == 7){
				docVector.add(new Doc(doc[0], doc[1], doc[2], doc[3], Long.parseLong(doc[4]), doc[5], Integer.parseInt(doc[6])));
			}
		}
		return docVector;

	}

	public static boolean insertDoc(Doc doc) throws Exception {
		String[] args = {doc.getID(), doc.getCreator(), doc.getDescription(), 
			doc.getFilename(), String.valueOf(doc.getTimestamp()), doc.getUploadFileName()};
		return (Boolean)writeAndRead(ClientMsg.INSERT_DOC, args);
	}

	public static boolean sendCode(String email) throws Exception {
		return (Boolean)writeAndRead(ClientMsg.SEND_CODE, new String[]{email});
	}

	public static boolean verifyCode(String email, String code) throws Exception{
		return (Boolean)writeAndRead(ClientMsg.VERIFY_CODE, new String[]{email, code});
	}

}