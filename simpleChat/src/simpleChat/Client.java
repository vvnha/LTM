package simpleChat;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener{
    
    public JTextArea msgDisplay;
    private JTextField msg;
    private JButton send;
    
    private ArrayList<Socket> cli = new ArrayList<Socket>();
    private ArrayList<DataOutputStream> dose = new ArrayList<DataOutputStream>();
    private ArrayList<DataInputStream> dise = new ArrayList<DataInputStream>();
    
    int port[] = {1999,2002};
    
    public Client(){
        super("Client");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        setSize(600, 400);
        addItem();
        setVisible(true);
    }
    private void addItem() {
        setLayout(new BorderLayout());
        msgDisplay = new JTextArea();
        msgDisplay.setEditable(false);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        msg = new JTextField(40);
        send = new JButton("Send");
        send.addActionListener(this);
        
        p.add(msg);
        p.add(send);
        
        add(new JLabel("   "),BorderLayout.NORTH);
        add(new JLabel("   "),BorderLayout.EAST);
        add(new JLabel("   "),BorderLayout.WEST);
        add(new JScrollPane(msgDisplay),BorderLayout.CENTER);
        add(p,BorderLayout.SOUTH);
        
    }
    public static void main(String[] args) {
        new Client().go();
    }
    private void go() {
    	    
        	ketnoi(port);
    }
    
    public void ketnoi(int port[]) {
    	try {
            msgDisplay.append("Server bắt đầu chạy.\n");
            for(int i = 0; i < port.length ; i++) {
            	cli.add(new Socket("localhost",port[i]));
            	dose.add(new DataOutputStream(cli.get(i).getOutputStream()));
            	dise.add(new DataInputStream(cli.get(i).getInputStream()));
            	msgDisplay.append("Đã kết nối server["+i+"] thành công.\n");
            };
            
            String temp =null;
            
            while(true){
            	for(int i = 0; i < port.length; i++) {
            		temp = dise.get(i).readUTF();
            		if(temp.toUpperCase().equals("QUIT")) {
            			dose.get(i).writeUTF("QUIT");
            			dose.get(i).flush();
            			break;
            		}else msgDisplay.append("Server : "+temp+"\n");
            		}
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.print( "\nfail1\n");
        } catch (IOException e) {
            e.printStackTrace();
            try {
            	for(int i= 0; i < port.length; i++) {
            		dose.get(i).close();
            		dise.get(i).close();
            		cli.get(i).close();
            		msgDisplay.append("Server[" +i+"] đã ngắt kết nối với bạn.\n");
            	}
			} catch (Exception e2) {
				// TODO: handle exception
			}
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(msg.getText().compareTo("")!=0){
                try {
                	for(int i =0; i < port.length; i++) {
                		dose.get(i).writeUTF(msg.getText());
                		dose.get(i).flush();
                	}
                    msgDisplay.append("Client : "+msg.getText()+"\n");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(this, "Kết nối đã ngắt", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            msg.setText("");
        }
    }

}
