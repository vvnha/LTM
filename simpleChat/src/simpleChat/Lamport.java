package simpleChat;
import java.net.*;
import java.io.*;

public class Lamport implements Serializable{
	private int c;
    public Lamport() {
    	  c = 0;
    }
    public int getValue() {
        return c;
    }
    public void tick() {
        c = c + 1;
    }
   public void sendAction() {
        c = c + 1;
    }
    
    public void receiveAction(int sentValue) {
    	if(sentValue > c)
    		c = sentValue + 1;
    	else 
    	 	c = c + 1;        
    }        
}

