package skillbridge.client.model;

import java.util.ArrayList;

public class UserData {

    private String userName = "";
    private String email = "";
    private ArrayList<String> skills = new ArrayList<String>();
    private String targetCareer = "";

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public ArrayList<String> getSkills() { return skills; }
    public void setSkills(ArrayList<String> skills) { this.skills = skills; }

    public String getTargetCareer() { return targetCareer; }
    public void setTargetCareer(String targetCareer) { this.targetCareer = targetCareer; }

    public void clearSelection() {
        skills.clear();
        targetCareer = "";
    }

    public void clearAll() {
        userName = "";
        email = "";
        clearSelection();
    }
}