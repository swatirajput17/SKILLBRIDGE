package skillbridge.client.model;

import java.util.ArrayList;

public class AnalysisResult {

    private int matchPercentage;
    private ArrayList<String> skills;
    private ArrayList<String> missingSkills;
    private ArrayList<String> suggestions;

    public AnalysisResult(int matchPercentage, ArrayList<String> skills,
                          ArrayList<String> missingSkills, ArrayList<String> suggestions) {
        this.matchPercentage = matchPercentage;
        this.skills = skills;
        this.missingSkills = missingSkills;
        this.suggestions = suggestions;
    }

    public int getMatchPercentage() { return matchPercentage; }
    public ArrayList<String> getSkills() { return skills; }
    public ArrayList<String> getMissingSkills() { return missingSkills; }
    public ArrayList<String> getSuggestions() { return suggestions; }
}