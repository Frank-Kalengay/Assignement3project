/*
 * AssignmentThree.java
 * @author L.Franck Kalengayi 220048762
 * 09 June 2021
 */
package za.ac.cput.assignement3project;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 *
 * @author Frank
 */
public class AssignmentThree {
    private ObjectInputStream inputStreamSer;
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private BufferedWriter bw;
    private FileWriter fw;


    public void readSerializeFile(){

        try {
            inputStreamSer = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            while (true){
                Object object = inputStreamSer.readObject();
                if (object instanceof Customer)
                    customers.add((Customer) object);
                else
                    suppliers.add((Supplier) object);
            }
        }catch (EOFException ex){
            return;
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void sortCustomers(){
        customers.sort(Comparator.comparing(Stakeholder::getStHolderId));
    }

    public void sortSuppliers(){
        suppliers.sort(Comparator.comparing(Supplier::getName));
    }

    public void reformatDateOfBirth(){
        HashMap<String, String> monthsPair = new HashMap<>();
        monthsPair.put("01","Jan");
        monthsPair.put("02","Feb");
        monthsPair.put("03","Mar");
        monthsPair.put("04","Apr");
        monthsPair.put("05","May");
        monthsPair.put("06","June");
        monthsPair.put("07","Jul");
        monthsPair.put("08","Aug");
        monthsPair.put("09","Sep");
        monthsPair.put("10","Oct");
        monthsPair.put("11","Nov");
        monthsPair.put("12","Dec");

        for (Customer customer : customers){
            String dob = customer.getDateOfBirth();
            String month = dob.substring(5,7);
            String newMonth = monthsPair.get(month);
            customer.setDateOfBirth(dob.substring(8) + " " + newMonth + " " + dob.substring(0,4));
        }
    }

    public int determineAge(String dateOfBirth){
        StringTokenizer st = new StringTokenizer(dateOfBirth," ");

        int day = Integer.parseInt(st.nextToken());
        String month = st.nextToken();
        int year = Integer.parseInt(st.nextToken());

        Calendar calMonth = Calendar.getInstance();
        Date date = null;

        try {
            date = new SimpleDateFormat("MMM").parse(month);
        }catch (ParseException ex){
            ex.printStackTrace();
        }

        if (date!=null)
            calMonth.setTime(date);

        int mth = calMonth.get(Calendar.MONTH) + 1;

        LocalDate dob = LocalDate.of(year,mth,day);
        LocalDate today = LocalDate.now();

        return (int) ChronoUnit.YEARS.between(dob,today);
    }

    public void writeCustomersToFile(){
        try{
            fw = new FileWriter("customerOutFile.txt");
            bw = new BufferedWriter(fw);

            bw.write("================== CUSTOMERS =============================\r\n");
            String titles = String.format("%-5s\t%-10s\t%-10s\t%-10s\t%-1s\r\n", "ID", "Name",
                    "Surname", "Date of birth", "Age");
            bw.write(titles);
            bw.write("==========================================================\r\n");

            for (Customer customer : customers){
                int age = determineAge(customer.getDateOfBirth());

                String line = String.format("%-5s\t%-10s\t%-10s\t%-14s\t%-1s\r\n",
                        customer.getStHolderId(), customer.getFirstName(), customer.getSurName(), customer.getDateOfBirth(), age);
                bw.write(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeSuppliersToFile(){
        try{
            fw = new FileWriter("supplierOutFile.txt");
            bw = new BufferedWriter(fw);

            bw.write("================== SUPPLIERS ==========================\n");
            String titles = String.format("%-5s\t%-17s\t%-10s\t%-15s\r\n", "ID", "Name",
                    "Prod Type", "Description");
            bw.write(titles);
            bw.write("========================================================\n");

            for (Supplier supplier : suppliers){
                String line = String.format("%-5s\t%-17s\t%-10s\t%-15s\r\n",
                        supplier.getStHolderId(), supplier.getName(), supplier.getProductType(), supplier.getProductDescription());
                bw.write(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void rentable(){
        int canRentCount = 0;
        int cantRentCount = 0;

        for (Customer customer : customers){
            if (customer.getCanRent())
                canRentCount++;
            else
                cantRentCount++;
        }
        try {
            bw.write("\r\nNumber of customers who can rent:\t\t" + canRentCount +"\n");
            bw.write("Number of customers who cannot rent:\t" + cantRentCount);

            closeResources();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeResources(){
        try {
            if (bw!=null)
                bw.close();
            if (fw!=null)
                fw.close();
            if (inputStreamSer!=null)
                inputStreamSer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}