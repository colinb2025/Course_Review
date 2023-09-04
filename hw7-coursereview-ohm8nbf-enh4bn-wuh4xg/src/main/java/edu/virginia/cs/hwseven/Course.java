package edu.virginia.cs.hwseven;

public class Course {
    private String department;
    private int catalogNumber;

    public Course(String department, int catalogNumber) throws IllegalArgumentException {
//        if((department.length()!=4|department.length()!=2)|department.length()==0){
        if(department.length() < 1 || department.length() > 4){
            System.out.println("iae 1");
            throw new IllegalArgumentException();
        }
        if(catalogNumber<=999||catalogNumber>=10000){
            System.out.println("iae 2");
            throw new IllegalArgumentException();
        }
        this.department = department;
        this.catalogNumber = catalogNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(int catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
}
