package rules.models;

import java.util.ArrayList;

public class Field {
    private String name;
    private ArrayList<RuleField> ruleFieldArrayList;

    public Field() {
    }

    public Field(String name, ArrayList<RuleField> ruleFieldArrayList) {
        this.name = name;
        this.ruleFieldArrayList = ruleFieldArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<RuleField> getRuleFieldArrayList() {
        return ruleFieldArrayList;
    }

    public void setRuleFieldArrayList(ArrayList<RuleField> ruleFieldArrayList) {
        this.ruleFieldArrayList = ruleFieldArrayList;
    }
}
