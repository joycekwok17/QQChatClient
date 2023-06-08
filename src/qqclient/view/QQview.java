package qqclient.view;
import common.Message;
import common.MessageType;
import common.User;
import qqclient.service.FileClientService;
import qqclient.service.PrivateMessageClient;
import qqclient.service.UserClientService;
import qqclient.utils.Utility;
/**
 * @author Xuanchi Guo
 * @project QQclient
 * @created 6/7/23
 */
public class QQview {
    private boolean loop = true;
    private String key = "";
    private UserClientService userClientService = new UserClientService();
    private PrivateMessageClient privateMessageClient = new PrivateMessageClient();
    public static void main(String[] args) {
        QQview qqview = new QQview();
        qqview.mainMenu();
        System.out.println("Client exited");
    }
    private void mainMenu(){
        while(loop) {
            System.out.println("==========Welcome to QQ==========");
            System.out.println("\t1. Login");
            System.out.println("\t2. Log out");
            System.out.println("please input your selection: ");
            key = Utility.readString(1);

            switch (key) {
                case "1" -> {
                    System.out.println("Please input your username: ");
                    String username = Utility.readString(20);
                    System.out.println("Please input your password: ");
                    String password = Utility.readString(20);

                    // verify username and password with server
                    if (userClientService.checkUser(username, password)) { // login successfully
                        System.out.println("=================Welcome " + username + "=================");
                        System.out.println("Login successfully");

                        while (loop) {
                            System.out.println("==========Welcome to QQ Second Menu "+ username + "==========");
                            System.out.println("\t1. Show online users");
                            System.out.println("\t2. Send Group message");
                            System.out.println("\t3. Send private message");
                            System.out.println("\t4. Send file");
                            System.out.println("\t9. Exit");
                            System.out.println("please input your selection: ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1" -> userClientService.onlineFriendsList();
                                case "2" -> {
                                    System.out.println("Send Group message");
                                    System.out.println("Please input the message: ");
                                    String content = Utility.readString(100);
                                    PrivateMessageClient.sendGroupMessage(content, username);
                                }
                                case "3" -> {
                                    System.out.println("Please input the (online) receiver's username: ");
                                    String receiver = Utility.readString(20);
                                    System.out.println("Please input the message: ");
                                    String content = Utility.readString(100);
                                    PrivateMessageClient.sendPrivateMessage(content, username, receiver);
                                }
                                case "4" -> {
                                    System.out.println("Send file");
                                    System.out.println("Please input the source path: ");
                                    String srcPath = Utility.readString(100);
                                    System.out.println("Please input the destination path: ");
                                    String destPath = Utility.readString(100);
                                    String sender = username;
                                    System.out.println("Please input the (online) receiver's username: ");
                                    String receiver = Utility.readString(20);
                                    FileClientService.sendFileToOne(srcPath, destPath, sender, receiver);
                                }

                                case "9" -> {
                                    loop = false;
                                    System.out.println("Exit");
                                    userClientService.logout();
                                }
                                default -> System.out.println("Invalid input, please input again");
                            }
                        }
                    } else { // login failed
                        System.out.println("===========Login failed===========");
                    }
                }
                case "2" -> {
                    loop= false; // break the loop
                    System.out.println("Log out");
                }
                default -> System.out.println("Invalid input, please input again");
            }
        }
    }
}
