package edu.virginia.cs.hwseven;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class databaseManager implements databaseInterface{
    private Connection conn;
    private boolean connected = false;
    private static final String DatabaseURL = "jdbc:sqlite:Reviews.sqlite3";
//    databaseManager(){
//        //connect();
//    }
//    public static void main(String[] args){
//            databaseManager m= new databaseManager();
//            Review r = new Review("John", "I loved this class it was so great", 5);
//            Student s = new Student("john", "doe");
//            Course course = new Course("CS", 1410);
//            m.addReview(r,course,s);
//    }

    @Override
    public void connect() {
        try {
            if(!connected){
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(DatabaseURL);
                conn.setAutoCommit(true);
                connected = true;
                System.out.println("Connected!");
                DatabaseMetaData metaData = conn.getMetaData();
                ResultSet studentsTable = metaData.getTables(null, null, "Students", null);
                ResultSet coursesTable = metaData.getTables(null, null, "Courses", null);
                ResultSet reviewsTable = metaData.getTables(null, null, "Reviews", null);
                ResultSet curStudent = metaData.getTables(null, null, "curStudent", null);
                if(!studentsTable.next()){
                    createStudentsTable();
                }
                if (!coursesTable.next()) {
                    createCoursesTable();
                }
                if(!reviewsTable.next()){
                    createReviewsTable();
                }
                if(!curStudent.next()) {
                    createCurStudentTable();
                }
            }
            else{
                throw new IllegalStateException("Manager already connected");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to connect to the database", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCurStudentTable() {
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE curStudent(Username VARCHAR(255) NOT NULL, Password VARCHAR(255) NOT NULL);");
            statement.close();
        }
        catch (SQLException e) {
            System.out.println("Failed to create curStudent table!");
            e.printStackTrace();
        }
    }
    public void setCurStudent(Student student) {
        if(!connected){
            connect();
        }
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM curStudent WHERE EXISTS(SELECT * FROM curStudent);");
            statement.executeUpdate(String.format("INSERT INTO curStudent(Username, Password) VALUES('%s', '%s')",student.getUserName(), student.getPassword()));
            statement.close();
            disconnect();
        }
        catch (SQLException e) {
            System.out.println("Could not set curStudent!");
            e.printStackTrace();
        }
    }
    public Student getCurStudent() {
        Student curStudent = new Student();
        if(connected) {
            disconnect();
        }
        connect();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM curStudent");
            curStudent.setUserName(rs.getString("Username"));
            curStudent.setPassword(rs.getString("Password"));
            statement.close();
            rs.close();
            disconnect();
            return curStudent;
        }
        catch (SQLException e) {
            System.out.println("Failed to retrieve curStudent!");
            e.printStackTrace();
        }
        return null;
    }
    public void createCoursesTable() {
        System.out.println("createCoursesTable");
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE Courses(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "DepartmentNum INTEGER NOT NULL," +
                    "CatalogNum INTEGER NOT NULL);");
        }
        catch (SQLException e) {
            System.out.println("Failed to create courses table!");
            e.printStackTrace();
        }
    }
    public void createStudentsTable() {
        System.out.println("createStudentsTable");
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE Students(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name VARCHAR NOT NULL," +
                    "Password VARCHAR NOT NULL);");
        }
        catch (SQLException e) {
            System.out.println("Failed to create student table!");
            e.printStackTrace();
        }
    }
    public void createReviewsTable() {
        System.out.println("createReviewsTable");
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE TABLE Reviews(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "CourseID INTEGER NOT NULL REFERENCES Courses(ID) ON DELETE CASCADE," +
                    "StudentID INTEGER NOT NULL REFERENCES Students(ID) ON DELETE CASCADE," +
                    "Review VARCHAR(123456789) NOT NULL," +
                    "Rating INTEGER NOT NULL); ");
        }
        catch (SQLException e) {
            System.out.println("Failed to create reviews table!");
            e.printStackTrace();
        }
    }
    @Override
    public void tableCreation() {
        System.out.println("tableCreation");
        if(!connected){
            throw new IllegalStateException("not connected");
        }
        createCoursesTable();
        createReviewsTable();
        createStudentsTable();
    }

    public void deleteTables(){
        System.out.println("deleteTables");
        if(connected) {
            disconnect();
        }
        connect();
        String dropReviews = "DROP TABLE Reviews;";
        String dropCourses = "DROP TABLE Courses;";
        String dropStudents = "DROP TABLE Students;";

        try (PreparedStatement stmt1 = conn.prepareStatement(dropReviews);
             PreparedStatement stmt2 = conn.prepareStatement(dropCourses);
             PreparedStatement stmt3 = conn.prepareStatement(dropStudents)) {
            stmt1.executeUpdate();
            stmt2.executeUpdate();
            stmt3.executeUpdate();

        }   catch (NullPointerException e){
            throw new IllegalStateException("Manager hasn't connected yet");
        }
        catch (SQLException e) {
            throw new IllegalStateException("Tables don't exist", e);
        }
        disconnect();
    }
    public void clearTables(String query){
        System.out.println("clearTables");
        if(connected) {
            disconnect();
        }
        connect();
        String clearRoutesTable = "DELETE FROM Students;";
        String clearBusLinesTable = "DELETE FROM Courses;";
        String clearStopsTable = "DELETE FROM Reviews;";

        try (PreparedStatement stmt1 = conn.prepareStatement(clearRoutesTable);
             PreparedStatement stmt2 = conn.prepareStatement(clearBusLinesTable);
             PreparedStatement stmt3 = conn.prepareStatement(clearStopsTable)) {
            if(query.equals("students")){
                stmt1.executeUpdate();

            }
            else if(query.equals("courses")){
                stmt2.execute();
            }
            else if(query.equals("reviews")){
                stmt3.execute();
            } else if (query.equals("all")) {
                stmt1.executeUpdate();
                stmt2.executeUpdate();
                stmt3.executeUpdate();
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Failed to clear tables", e);
        }
        if (!connected) {
            throw new IllegalStateException("Manager hasn't connected yet");
        }
        disconnect();
    }

    @Override
    public void addCourses(Course course) {
        System.out.println("addCourses");
        if(connected) {
            disconnect();
        }
        connect();
        String sql = String.format("INSERT INTO Courses(DepartmentNum, CatalogNum) VALUES ('%s', %d)", course.getDepartment(), course.getCatalogNumber());
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        }
        catch (SQLException e){
            disconnect();
            throw new IllegalStateException("failed to add course",e );
        }
        catch (IllegalArgumentException e){
            disconnect();
            throw new IllegalArgumentException("Stop is already in the table",e );
        }
        disconnect();
    }

    @Override
    public void addStudent(Student student) {
        System.out.println("addStudent");
        if(connected) {
            disconnect();
        }
        connect();
        if(!connected){
            throw new IllegalStateException("Not connected");
        }
        try{
        String sql1 = "SELECT * FROM Students WHERE Name ='"+ student.getUserName()+"';";
        PreparedStatement stmt = conn.prepareStatement(sql1);
        ResultSet rs = stmt.executeQuery();
        if(rs.getString(2).equals(student.getUserName())){
            System.out.println(true);
            disconnect();
            throw new IllegalStateException("user already exists");
        }
        rs.close();
        stmt.close();
        } catch (SQLException e) {
        }
        try {
            //Statement add_stmt = conn.createStatement();
            //add_stmt.executeUpdate(String.format("INSERT INTO Students (Name, Password) VALUES ('%s', '%s')", student.getUserName(), student.getPassword()));
            //add_stmt.close();
            String sql = "INSERT INTO Students (Name,Password )VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getUserName());
            stmt.setString(2, student.getPassword());
            stmt.addBatch();
            stmt.executeBatch();
            stmt.close();
            System.out.println("Successfully added student!");
        }
        catch (SQLException e){
            e.printStackTrace();
            disconnect();
            throw new IllegalStateException("failed to add student", e );

        }
        catch (IllegalArgumentException e){
            disconnect();
            throw new IllegalArgumentException("Student is already in the table",e );
        }
        disconnect();
    }

    @Override
    public void addReview(Review review, Course course, Student student) throws SQLException {
        System.out.println("addReview");

        try{
            try {
                if(connected) {
                    disconnect();
                }
                connect();

                ArrayList<Review> oReviews = getReviews(course);

                for(Review r: oReviews){
                    if(r.getWrittenBy().equals(student.getUserName())){
//                        disconnect();
                        System.out.println("this ran for the other student");
                        throw new IllegalArgumentException("Student has already left a review");
                    }
                }

            }
            catch (IllegalArgumentException e){
                throw new IllegalThreadStateException();
            }
            catch (IndexOutOfBoundsException e){
            }
            catch(SQLException e){

            }
            if(connected) {
                disconnect();
            }
            connect();
                int courseID;
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(String.format("SELECT * FROM Students WHERE Name = '%s' AND Password = '%s'", student.getUserName(), student.getPassword()));

                int studentID = rs.getInt("ID");
                rs.close();
                statement.close();
                try{
                    Statement statement2 = conn.createStatement();
                    ResultSet rs1 ;
                rs1 = statement2.executeQuery(String.format("SELECT * FROM Courses WHERE DepartmentNum = '%s' AND CatalogNum = %d",course.getDepartment(), course.getCatalogNumber()));
                courseID = rs1.getInt("ID");
                rs1.close();
                    statement2.close();}
                catch (SQLException e){
                    disconnect();
                    connect();
                    PreparedStatement stmt = conn.prepareStatement(String.format("INSERT INTO Courses(DepartmentNum, CatalogNum) VALUES ('%s', %d)", course.getDepartment(), course.getCatalogNumber()));
                    stmt.execute();
                    stmt.close();
                    Statement statement3 = conn.createStatement();
                    ResultSet rs2;
                    rs2 = statement3.executeQuery(String.format("SELECT * FROM Courses WHERE DepartmentNum = '%s' AND CatalogNum = %d",course.getDepartment(), course.getCatalogNumber()));
                    courseID = rs2.getInt("ID");
                    rs2.close();
                    statement3.close();
                }
                String reviewText = review.getReviewText();
                int rating = review.getRating();
                Statement statement1 = conn.createStatement();
                statement1.executeUpdate(String.format("INSERT INTO Reviews (CourseId, StudentID, Review, Rating) VALUES(%d, %d, '%s', %d)", courseID, studentID, reviewText, rating));
                statement1.close();
                disconnect();
            }
            catch (SQLException e) {
                disconnect();
                System.out.println("Could not add review!");
                throw new SQLException(e);
            }
    }

    @Override
    public Student getLogin(String username) throws SQLException {
        System.out.println("getLogin");
        if(connected) {
            disconnect();
        }
        connect();
        try{
        String sql = String.format("SELECT * FROM Students WHERE Name = '%s'", username);
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        String password = resultSet.getString(3);
        disconnect();
        return new Student(username, password);}
        catch (SQLException e){
            disconnect();
            throw new SQLException(e);
        }
    }
    @Override
    public ArrayList<Course> getCourses() throws SQLException {
        System.out.println("getCourses");
        if(connected) {
            disconnect();
        }
        connect();
        try{
        ArrayList<Course> result = new ArrayList<>();
        String sql = "SELECT * FROM Courses;";
        PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String department = rs.getString(1);
            int catalog = rs.getInt(2);
            Course c = new Course(department, catalog);
            result.add(c);

        }
        disconnect();
        return result;}
        catch (SQLException e){
            disconnect();
            throw new SQLException(e);
        }
    }

    @Override
    public ArrayList<Review> getReviews(Course course) throws SQLException {
        System.out.println("getReviews");
        if(connected) {
            disconnect();
        }
        connect();
        String sql = "SELECT * FROM Courses WHERE DepartmentNum = '"+course.getDepartment()+"' AND CatalogNum ="+course.getCatalogNumber();
        ArrayList<Review> reviewArray = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int courseID = rs.getInt("ID");
            stmt.close();
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(String.format("SELECT * FROM Reviews WHERE CourseID = %d",courseID));
            while(rs1.next()) {
                Review curReview = new Review(null, null, 0);
                curReview.setReviewText(rs1.getString("Review"));
                curReview.setRating(rs1.getInt("Rating"));
                PreparedStatement stmt2 = conn.prepareStatement(String.format("SELECT * FROM Students WHERE ID = %d",rs1.getInt("StudentID")));
                ResultSet rs2 = stmt2.executeQuery();
                String studentName = rs2.getString("Name");
                curReview.setWrittenBy(studentName);
                reviewArray.add(curReview);
            }
            disconnect();
            return reviewArray;
        } catch (SQLException e) {
            System.out.println("Could not get reviews!");
            disconnect();
            throw new SQLException();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connected) {
//                conn.commit();
                connected = false;
                conn.close();
                System.out.println("Disconnected!");
            } else {
                throw new IllegalStateException("Not connected");
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
