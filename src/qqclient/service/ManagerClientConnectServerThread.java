package qqclient.service;

import java.util.HashMap;

/**
 * @author Xuanchi Guo
 * @project QQclient
 * @created 6/7/23
 * @description manage all QQClientConnectServerThread
 */
public class ManagerClientConnectServerThread {
    // key: username, value: QQClientConnectServerThread
    private static HashMap<String, QQClientConnectServerThread> hm = new HashMap<>();

    // add
    public static void addClientConnectServerThread(String username, QQClientConnectServerThread qqClientConnectServerThread){
        hm.put(username, qqClientConnectServerThread);
    }

    // get
    public static QQClientConnectServerThread getClientConnectServerThread(String username){
        return hm.get(username);
    }
}
