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
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener{
    public JTextArea msgDisplay;
    private JTextField msg;
    private JButton send;
    
    private DataOutputStream dos;
    private DataInputStream dis;
    private JTextField txtPort;
    private int portTD[] = {1999,2002};
    private int j;
    public Server(){
        super("Server");
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
    public static void main(String[] args) {
        new Server().go();
    }
    private void go() {
        // tao socket o day
    	j = 0;
    	ketnoi(j);
    }
    private void addItem() {
        getContentPane().setLayout(new BorderLayout());
        msgDisplay = new JTextArea();
        msgDisplay.setEditable(false);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        msg = new JTextField(40);
        send = new JButton("Send");
        send.addActionListener(this);
        
        txtPort = new JTextField();
        p.add(txtPort);
        txtPort.setColumns(4);
        
        p.add(msg);
        p.add(send);
        
        getContentPane().add(new JLabel("   "),BorderLayout.NORTH);
        getContentPane().add(new JLabel("   "),BorderLayout.EAST);
        getContentPane().add(new JLabel("   "),BorderLayout.WEST);
        getContentPane().add(new JScrollPane(msgDisplay),BorderLayout.CENTER);
        getContentPane().add(p,BorderLayout.SOUTH);
        
    }
    
     void ketnoi(int port) {
    	 if(port<=portTD.length) {
    	try {
    		ServerSocket server = new ServerSocket(portTD[port]);
    		txtPort.setText(portTD[port]+"");
            msgDisplay.append("Server["+(port+1)+"] bắt đầu chạy.\n");
            Socket client = server.accept();
            msgDisplay.append("Client đã kết nối với bạn.\n");
            
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            
            String temp =null;
            
            while(true){
                temp = dis.readUTF();
                if(temp.toUpperCase().equals("QUIT")){
                    dos.writeUTF("QUIT");
                    dos.flush();
                    break;
                }
                else {
                	msgDisplay.append("Client : "+temp+"\n");
                	dos.writeUTF("Reply["+port+"]: "+temp+"-ok");
                	dos.flush();
                	msgDisplay.append("Server said : "+temp+"\n");
                }
            }
            msgDisplay.append("Client đã ngắt kết nối.\n");
            dis.close();
            dos.close();
            client.close();
            msgDisplay.append("ok");
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			System.out.print("Da su dung cong nay roi, dang chuyen cong khac! \n");
			//msgDisplay.append("fail");
			ketnoi(j++);
		}
    }
 }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(msg.getText().compareTo("")!=0){
            try {
                dos.writeUTF(msg.getText());
                dos.flush();
                msgDisplay.append("Server : "+msg.getText()+"\n");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "Kết nối đã ngắt", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
            msg.setText("");
        }
        
    }
}