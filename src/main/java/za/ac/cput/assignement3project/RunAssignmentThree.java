/*
 * RunAssignmentThree.java
 * This is my solution file  
 * @author L.Franck Kalengayi 220048762
 * 09 June 2021.
 */
package za.ac.cput.assignement3project;

/**
 *
 * @author Frank
 */
public class RunAssignmentThree {
    public static void main(String[] args) {
        AssignmentThree assignmentThree = new AssignmentThree();
        assignmentThree.readSerializeFile();
        assignmentThree.sortCustomers();
        assignmentThree.sortSuppliers();
        assignmentThree.reformatDateOfBirth();
        assignmentThree.writeCustomersToFile();
        assignmentThree.rentable();
        assignmentThree.writeSuppliersToFile();
        assignmentThree.closeResources();
    }
}

