/*
 * Stakeholder.java
 * @author L.Franck Kalengayi 220048762
 * 09 June 2021
 */
package za.ac.cput.assignement3project;
import java.io.Serializable;

/**
 *
 * @author Frank
 */
public class Stakeholder implements Serializable{
    private String stHolderId;

    public Stakeholder() {
    }
    
    public Stakeholder(String stHolderId) {
        this.stHolderId = stHolderId;
    }
    
    public String getStHolderId() {
        return stHolderId;
    }

    public void setStHolderId(String stHolderId) {
        this.stHolderId = stHolderId;
    }

    @Override
    public String toString() {
       return stHolderId;
    }

}