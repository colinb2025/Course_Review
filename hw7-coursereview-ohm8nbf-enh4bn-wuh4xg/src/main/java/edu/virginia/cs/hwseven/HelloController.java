package edu.virginia.cs.hwseven;

//https://stackoverflow.com/questions/40631744/how-to-make-javafx-text-wrap-work
//Used text wrap limiter on line 472 to make long reviews multiple lines long

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelloController {
    @FXML
    TextField searchedClass;
    @FXML
    TextField createUsername;
    @FXML
    TextField createpassword1;
    @FXML
    TextField createpassword2;
    @FXML
    TextField loginemail;
    @FXML
    TextField loginpassword;
    @FXML
    Label loginError;
    @FXML
    Label createAccountError;
    @FXML
    Label viewReviewsError;
    @FXML
    Label leaveReviewError;
    @FXML
    TextField courseReview;
    @FXML
    TextField courseRating;
    @FXML
    TextField leaveReviewClass;
    @FXML
    Label leaveReviewAR;
    @FXML
    Label leaveReviewReviews;
    @FXML
    VBox viewReviews;
    @FXML
    VBox viewReviewsReviewsBox;

    databaseManager databaseManager = new databaseManager();
    Student student;
    @FXML
    private void switchTocreateAccount(ActionEvent event) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
        Scene scene = new Scene(newScene, 560, 800);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void switchTologin(ActionEvent event) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(newScene, 560, 800);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void Login(ActionEvent event) throws IOException {
        boolean login = false;
        boolean exists = true;
        boolean unFilled= true;
        loginManager loginManager = new loginManager();
        System.out.println(loginemail.getText()+ loginpassword.getText());
        try{
            if(loginemail.getText().length()==0|| loginpassword.getText().length()==0){
                login = false;
                exists = false;
                System.out.println(login+"" +""+exists+""+unFilled);
            }
            else{
            login = loginManager.login(loginemail.getText(), loginpassword.getText());
            System.out.println(login);
            unFilled=false;
            }}
        catch (SQLException e){
                login = false;
                exists = false;
                unFilled=false;
        }
        catch(IllegalArgumentException e){
            //make an error here for password is wrong
            System.out.println("im here");
        }

        if(login&&!unFilled) {
            student = new Student(loginemail.getText(), loginpassword.getText());
            databaseManager.setCurStudent(student);
            //if username and password are correct and in database
            Parent newScene = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(newScene, 560, 800);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        else if(!exists&&!unFilled) {
            //if username doesnt exist
            loginemail.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            loginpassword.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            loginError.setText("The username doesn't exist, please create a new account");
            loginError.setStyle("-fx-text-fill: red");

            Timeline loginErrorNoUsername = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                loginemail.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                loginpassword.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                loginError.setStyle("-fx-text-fill: transparent;");
            }));
            loginErrorNoUsername.play();

            loginemail.clear();
            loginemail.requestFocus();

            loginpassword.clear();
        }

        else if(!login&&exists) {
            //if username and password aren't right
            loginemail.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            loginpassword.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            loginError.setText("The username and password don't match, try again");
            loginError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline loginErrorDontMatch = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                loginemail.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                loginpassword.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                loginError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            loginErrorDontMatch.play();

            loginemail.clear();
            loginemail.requestFocus();

            loginpassword.clear();
        }
        else if(!login&&!exists&&unFilled){
            loginemail.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            loginpassword.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            loginError.setText("Please populate both fields");
            loginError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline loginErrorDontMatch = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                loginemail.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                loginpassword.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                loginError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            loginErrorDontMatch.play();

            loginemail.clear();
            loginemail.requestFocus();

            loginpassword.clear();
        }
    }

    @FXML
    private void createAccount(ActionEvent event) throws IOException {
        //if passwords aren't the same
        if (!createpassword1.getText().equals(createpassword2.getText())) {
            System.out.println(createpassword1.getText() +createpassword2.getText());
            createpassword1.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            createpassword2.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            createAccountError.setText("The passwords don't match, try again");
            createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline createErrorPasswordsDontMatch = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                createpassword1.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                createpassword2.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            createErrorPasswordsDontMatch.play();

            createpassword1.clear();
            createpassword2.requestFocus();

            createpassword2.clear();
        }

        else if (createUsername.getText().length() == 0) {
            createUsername.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            createAccountError.setText("Please provide a username");
            createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline createErrorPasswordsDontMatch = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                createUsername.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            createErrorPasswordsDontMatch.play();

            createUsername.requestFocus();
        }
        else if(createpassword1.getText().length()==0||createpassword2.getText().length()==0){
            createUsername.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            createAccountError.setText("Please populate all fields");
            createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline createErrorPasswordsDontMatch = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                createUsername.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            createErrorPasswordsDontMatch.play();

            createUsername.requestFocus();
        }
        else {
            try {
                Student s = new Student(createUsername.getText(), createpassword2.getText());
                databaseManager.addStudent(s);
                databaseManager.setCurStudent(s);
                //go back to the login scene
                Parent newScene = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
                Scene scene = new Scene(newScene, 560, 800);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.show();
            }

            catch (IllegalStateException e){
                System.out.println(e);
                //do something for they already exist here
                createpassword1.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                createpassword2.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                createUsername.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                createAccountError.setText("This username already exists, please log in");
                createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline createErrorPasswordsDontMatch = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    createUsername.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    createpassword1.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    createpassword2.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    createAccountError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                createErrorPasswordsDontMatch.play();

                createUsername.clear();
                createUsername.requestFocus();

                createpassword1.clear();

                createpassword2.clear();
            }
        }
    }

    @FXML
    private void goToleaveReview(ActionEvent event) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("leaveReview.fxml"));
        Scene scene = new Scene(newScene, 560, 800);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void submitReview(ActionEvent event) throws IOException {
        //check if any are empty
        int rating = 0;
        Course course=null;

        if(courseRating.getText().equals("")|courseReview.getText().equals("")|leaveReviewClass.getText().equals("")){
            System.out.println("YOU NEED TO FILL OUT ALL FIELDS");
            courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            leaveReviewError.setText("Please fill out all fields");
            leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline submitReviewEmptyField = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            submitReviewEmptyField.play();
        }


        else {
            boolean courseRight = true;
            try {
                courseRight = true;
                rating = Integer.parseInt(courseRating.getText());
                String[] courseStuff = leaveReviewClass.getText().split(" ");
                try {
                    course = new Course(courseStuff[0].toUpperCase(), Integer.parseInt(courseStuff[1]));
                } catch (NumberFormatException e) {
                    courseRight = false;
                    throw e;
                }
                if (rating < 1 || rating > 5) {
                    courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                    leaveReviewError.setText("The rating should be a value between 1 and 5");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                    Timeline submitReviewRatingRange = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                        courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                    }));
                    submitReviewRatingRange.play();
                } else if (!courseStuff[0].equals(courseStuff[0].toUpperCase())) {
                    leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                    leaveReviewError.setText("Course names must be capitalized");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                    Timeline submitReviewRatingRange = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                        leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                    }));
                    submitReviewRatingRange.play();
                }
                else if(courseStuff[1].length()!=4){
                    System.out.println("yay");
                }
                else if (rating != 0) {
                    boolean falied = false;
                    try {
                        student = databaseManager.getCurStudent();
                        Review review = new Review(student.getUserName(), courseReview.getText(), rating);
                        databaseManager.addReview(review, course, student);
                    } catch (SQLException e) {
                        System.out.println(e.toString());
                        courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                        leaveReviewError.setText("Invalid character submitted. Please review");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                        Timeline submitReviewInvalidCourse = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                            leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                            leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                        }));
                        submitReviewInvalidCourse.play();
                        falied = true;
                    }
                    if (!falied) {
                        courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: green; -fx-border-radius: 10px;");
                        courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: green; -fx-border-radius: 10px;");
                        leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: green; -fx-border-radius: 10px;");
                        leaveReviewError.setText("Submitted");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: green; -fx-font-family: Arial;");

                        Timeline submitReviewMultipleSubmissions = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                            courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                            courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                            leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                            leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");

                            Parent newScene = null;
                            try {
                                newScene = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Scene scene = new Scene(newScene, 560, 800);
                            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        }));
                        submitReviewMultipleSubmissions.play();

                        courseRating.clear();
                        courseReview.clear();
                        leaveReviewClass.clear();
                    }

                }
                else{
                    leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                    leaveReviewError.setText("Course should be a 4 digit number");
                    System.out.println("1");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                    Timeline submitReviewRatingType = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                        leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                    }));
                    submitReviewRatingType.play();

                }
            } catch (IllegalThreadStateException e) {
                //put error code here for already left review
                courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                leaveReviewError.setText("You have already left a review for this class!");
                leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline submitReviewMultipleSubmissions = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    courseReview.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                submitReviewMultipleSubmissions.play();
            } catch (NumberFormatException e) {
                if (!courseRight) {
                    leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                    leaveReviewError.setText("Course should be a 4 digit number");
                    System.out.println("2");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                    Timeline submitReviewRatingType = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                        leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                    }));
                    submitReviewRatingType.play();
                } else {
                    courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                    leaveReviewError.setText("The rating should be a number between 1 and 5");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                    Timeline submitReviewRatingType = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                        courseRating.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                        leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                    }));
                    submitReviewRatingType.play();
                }
            } catch (IllegalArgumentException e) {
                leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                leaveReviewError.setText("Course name should be equal to or less than 4 characters long");
                System.out.println("3");
                leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline submitReviewRatingType = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                submitReviewRatingType.play();
            } catch (IndexOutOfBoundsException e) {
                leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                leaveReviewError.setText("Please provide a valid course name and number");
                leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline submitReviewInvalidCourse = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                submitReviewInvalidCourse.play();
            }
        }
    }

    @FXML
    private void goToviewReviews(ActionEvent event) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("viewReviews.fxml"));
        Scene scene = new Scene(newScene, 560, 800);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void returnTologin(ActionEvent event) throws IOException {
        Parent newScene = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(newScene, 560, 800);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void viewReviews(ActionEvent event) throws IOException {

        //searchedClass
        //viewReviewsError
        String[] newCourse = searchedClass.getText().split(" ");
        int depNum = 0;
        Course course = null;
        ArrayList<Review> reviews = null;
        try {
            depNum = Integer.parseInt(newCourse[1]);
            if (!newCourse[0].equals(newCourse[0].toUpperCase())){
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                viewReviewsError.setText("Course names must be capitalized");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline viewReviewCourseNumber = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                viewReviewCourseNumber.play();
            }

            else if(newCourse[1].length()!=4){
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
                viewReviewsError.setText("Please provide a valid course number");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline viewReviewCourseNumber = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                viewReviewCourseNumber.play();
            }


            else{
                viewReviewsReviewsBox.getChildren().clear();

                if (depNum != 0) {
                    course = new Course(newCourse[0], depNum);
                }

                reviews = databaseManager.getReviews(course);
                double totalRating = 0;
                if (reviews.size() > 0) {
                    viewReviews.setStyle("-fx-opacity: 1.0");
                    for (Review r : reviews) {
                        totalRating += r.getRating();
                    }

                    double averageRating = totalRating / reviews.size();
                    double roundedAverageRating = Math.round(averageRating * 100.0) / 100.0;

                    leaveReviewAR.setText(String.valueOf(roundedAverageRating) + " / 5");


                    //getting the reviews
                    for (Review r : reviews) {
                        Region spacer = new Region();
                        spacer.setPrefHeight(10.0);

                        Label newLabel = new Label(r.getReviewText());
                        newLabel.setAlignment(Pos.CENTER_LEFT);
                        newLabel.setPrefSize(500.0, Region.USE_COMPUTED_SIZE);
                        newLabel.setStyle("-fx-font-weight: normal; -fx-font-size: 20px; -fx-font-family: Arial; -fx-padding: 20px; -fx-background-radius: 20px; -fx-background-color: white; -fx-border-color: lightgrey; -fx-border-radius: 20px;");
                        newLabel.setWrapText(true);

                        viewReviewsReviewsBox.getChildren().add(newLabel);
                        viewReviewsReviewsBox.getChildren().add(spacer);
                    }
                }
            }
        }

        catch (NumberFormatException e) {
            searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            viewReviewsError.setText("Please provide a valid course name");
            viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline viewReviewCourseName = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            viewReviewCourseName.play();
        }

        catch(NullPointerException e){
            searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            viewReviewsError.setText("This course does not have any reviews yet");
            viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline viewReviewCourseName = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            viewReviewCourseName.play();
        }

        catch (IllegalArgumentException e) {
            searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            if(reviews==null&&newCourse[1].length()==4&&newCourse.length>1&&newCourse[0].length()>0&&newCourse.length<5){
                viewReviewsError.setText("This course does not have any reviews yet");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline viewReviewCourseName = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                viewReviewCourseName.play();
            }
            else{
                viewReviewsError.setText("Please provide a valid course number");
            viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline viewReviewCourseNumber = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            viewReviewCourseNumber.play();

            System.out.println("Current error");}
            //error for course dep or catalog num was bad or reviews was bad
        }

        catch (IndexOutOfBoundsException e) {
            searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
            viewReviewsError.setText("please provide a valid course name");
            viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline viewReviewCourseName = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            viewReviewCourseName.play();
        } catch (SQLException e) {System.out.println(newCourse[1].length());
            if(newCourse[1].length()!=4||newCourse[0].length()<2||newCourse.length>=5){
                viewReviewsError.setText("Please enter a valid course");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

                Timeline viewReviewCourseName = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                    searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                    viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
                }));
                viewReviewCourseName.play();
            }
            else{
            viewReviewsError.setText("This course does not have any reviews yet");
            viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");

            Timeline viewReviewCourseName = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
                searchedClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
                viewReviewsError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
            }));
            viewReviewCourseName.play();
            System.out.println("current error");}

        }

        //this is where you will need to do your magic with the reviews array list
        /*can do for(Review r: reviews){
                somehow display:
                                 r.getReview
                                 then average the ratings
        }
         */

//        if (reviews.size() == 0) {
//            leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: red; -fx-border-radius: 10px;");
//            leaveReviewError.setText("No reviews yet, be the first one!");
//            leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: red; -fx-font-family: Arial;");
//
//            Timeline submitReviewInvalidCourse = new Timeline(new KeyFrame(Duration.seconds(3), disappear -> {
//                leaveReviewClass.setStyle("-fx-font-size: 20px; -fx-background-radius: 10px; -fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10px;");
//                leaveReviewError.setStyle("-fx-font-size: 12px; -fx-text-fill: transparent; -fx-font-family: Arial;");
//            }));
//            submitReviewInvalidCourse.play();
//        }


        //getting the average rating
//        int totalRating = 0;
//        if (reviews.size() > 0) {
//            viewReviews.setStyle("-fx-opacity: 1.0");
//            for (Review r : reviews) {
//                totalRating += r.getRating();
//            }
//
//            double averageRating = totalRating / reviews.size();
//
//            leaveReviewAR.setText(String.valueOf(averageRating));
//
//
//            //getting the reviews
//            for (Review r : reviews) {
//                Region spacer = new Region();
//                spacer.setPrefHeight(10.0);
//
//                Label newLabel = new Label(r.getReviewText());
//                newLabel.setAlignment(Pos.CENTER_LEFT);
//                newLabel.setPrefSize(520.0, 50.0);
//                newLabel.setStyle("-fx-font-weight: normal; -fx-font-size: 20px; -fx-font-family: Arial; -fx-padding: 20px; -fx-background-radius: 20px; -fx-background-color: white; -fx-border-color: lightgrey; -fx-border-radius: 20px;");
//
//                viewReviewsReviewsBox.getChildren().add(newLabel);
//                viewReviewsReviewsBox.getChildren().add(spacer);
//            }
//        }


    }

}