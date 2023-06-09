package common;

/**
 * @author Xuanchi Guo
 * @project QQserver
 * @created 6/7/23
 */
public class User implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
