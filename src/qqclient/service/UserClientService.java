package qqclient.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author Xuanchi Guo
 * @project QQclient
 * @created 6/7/23
 * @description This class is used to help user to login and logout from the server and get the online friends list
 * */
public class UserClientService {
    private User user = new User();
    private Socket socket;

    // This method is used to check if the user is valid
    public boolean checkUser(String username, String password) {
        boolean isValid = false;
        user.setUsername(username);
        user.setPassword(password);

        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999); // Connect to the server, server port is 9999
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user); // Send the user object to the server

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message message = (Message) ois.readObject(); // Read the message object from the server

            if (message.getMessageType().equals(MessageType.message_login_succeed)) {
                // create a thread to keep listening to the server for new messages -> QQClientConnectServerThread
                QQClientConnectServerThread ccst = new QQClientConnectServerThread(socket);
                ccst.start(); // start the thread

                // add the thread to the thread manager
                ManagerClientConnectServerThread.addClientConnectServerThread(username, ccst);

                isValid = true;
            }
            else {
//                oos.close();
//                ois.close();
                socket.close(); // close the socket because the user is not valid
            }
            return isValid;

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void onlineFriendsList()  {
        Message message = new Message();
        message.setMessageType(MessageType.message_get_onLineFriend);
        message.setSender(user.getUsername());
        try {
            // get the socket from the thread manager
            QQClientConnectServerThread clientConnectServerThread = ManagerClientConnectServerThread.getClientConnectServerThread(user.getUsername());
            Socket socket = clientConnectServerThread.getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout(){
        Message message = new Message();
        message.setMessageType(MessageType.message_client_exit);
        message.setSender(user.getUsername());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManagerClientConnectServerThread.getClientConnectServerThread(user.getUsername()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println("User " + user.getUsername() + " is offline");
            System.exit(0);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
