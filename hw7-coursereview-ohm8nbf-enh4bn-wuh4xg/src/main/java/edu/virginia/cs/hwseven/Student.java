package edu.virginia.cs.hwseven;

public class Student {
    //this is autoIncrement primary key
  private String userName;
  private String password;

    public Student(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    Student(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
