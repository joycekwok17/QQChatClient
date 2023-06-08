package qqclient.service;

import common.Message;
import common.MessageType;

import java.io.*;

/**
 * @author Xuanchi Guo
 * @project QQclient
 * @created 6/8/23
 * @description This class is used to send files
 */
public class FileClientService {
    /**
     * send file to one person
     * @param src
     * @param dest
     * @param sender
     * @param receiver
     */
    public static void sendFileToOne(String src, String dest, String sender, String receiver){
        System.out.println("Sending file from " + sender + " to " + receiver);
        Message message = new Message();
        message.setMessageType(MessageType.message_file_message);
        message.setSrc(src);
        message.setDest(dest);
        message.setSender(sender);
        message.setReceiver(receiver);
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int) new File(src).length()];
        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes); // read file into byte array fileBytes from fileInputStream
            message.setFileBytes(fileBytes); // set fileBytes to message
            message.setFileLength(fileBytes.length); // set file length to message
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(message.getSender() + " is sending a file" + src +" to " + message.getReceiver() + " " + dest);
        try {
            new ObjectOutputStream(ManagerClientConnectServerThread.getClientConnectServerThread(sender).getSocket().getOutputStream()).writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
