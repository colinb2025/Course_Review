package edu.virginia.cs.hwseven;

import java.sql.SQLException;

public class loginManager {
    boolean login(String username, String password) throws SQLException {
        databaseManager databaseManager = new databaseManager();
        Student s = databaseManager.getLogin(username);
        if (s.getUserName().equals(username) && s.getPassword().equals(password)) {
            return true;
        }
        else if(s.getUserName().equals(username)&&!s.getPassword().equals(password)){
            throw new IllegalArgumentException("passwords are different");
        }
        return false;
    }
}
