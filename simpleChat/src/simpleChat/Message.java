package simpleChat;
import java.net.*;
import java.io.*;

	public class Message implements Serializable{
		// Method of message
	private String method = "";
		// Parameter of message
	private String param = "";
		// ID of computer
	private String ID = "";
		// Computer's Port
	private int port = 0;
		// Message's Lamport clock
	private Lamport lamport = null;
	public String getMethod(){
		return method;		
		}
	public void setMethod(String method){
		this.method = method;		
		}
	public String getParam(){
		return param;		
		}
	public void setParam(String param){
		this.param = param;
		}
	public String getID(){
		return ID;		
		}
	public void setID(String ID){
		this.ID = ID;
		}
	public int getPort(){
		return port;		
	}
	public void setPort(int port){
		this.port = port;
	}
	public Lamport getLamport(){
		return lamport;		
	}
	public void setLamport(Lamport lamport){
		this.lamport = lamport;
	}
}
