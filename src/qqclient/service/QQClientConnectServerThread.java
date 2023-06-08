package qqclient.service;

import common.Message;
import common.MessageType;

import java.io.*;
import java.net.Socket;

/**
 * @author Xuanchi Guo
 * @project QQclient
 * @created 6/7/23
 * @description This thread is used to connect to the server and keep listening to the server for new messages
 */
public class QQClientConnectServerThread extends Thread{
    private Socket socket;

    public QQClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        // keep the thread alive, because it is used to listen to the server for new messages
        while (true){
            System.out.println("Client thread is listening to the server for new messages");
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                // Read the message object from the server.
                // readObject() -> This is a blocking method call
                // -> the thread will be blocked here until it receives a new message from the server
                 Message message = (Message)ois.readObject();

                System.out.println("Client thread received a new message from the server");

                switch (message.getMessageType()) {
                    case MessageType.message_ret_onLineFriend -> {
                        // update the friend list
                        String[] onlineFriends = message.getContent().split(" ");
                        for (String onlineFriend : onlineFriends) {
                            System.out.println("Friend " + onlineFriend + " is online");
                        }
                    }
                    case MessageType.message_comm_mes ->
                        // display the message
                            System.out.println("Received a message from " + message.getSender() + ": " + message.getContent());
                    case MessageType.message_group_message ->
                            System.out.println("Received a group message from " + message.getSender() + ": " + message.getContent());
                    case MessageType.message_file_message -> {
                        System.out.println("Received a file message from " + message.getSender());
                        // display the file message
                        FileOutputStream fileOutputStream = new FileOutputStream(message.getDest(), true);
                        fileOutputStream.write(message.getFileBytes());
                        fileOutputStream.close();
                        System.out.println("File " + message.getDest() + " is saved");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Socket getSocket() {
        return socket;
    }


}
