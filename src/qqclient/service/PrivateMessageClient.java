package qqclient.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Xuanchi Guo
 * @project QQclient
 * @created 6/8/23
 * @description This class is used to send private messages
 */
public class PrivateMessageClient {

    public static void sendPrivateMessage(String content, String sender, String receiver) {
        Message message = new Message();
        message.setMessageType(MessageType.message_comm_mes);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        System.out.println(sender + " is sending a private message to " + receiver + ": " + content);
        try {
            new ObjectOutputStream(ManagerClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream()).writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendGroupMessage(String content, String sender){
        Message message = new Message();
        message.setMessageType(MessageType.message_group_message);
        message.setSender(sender);
        message.setContent(content);
        message.setSendTime(new java.util.Date().toString());
        System.out.println(sender + " is sending a group message: " + content);
        try {
            new ObjectOutputStream(ManagerClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream()).writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
