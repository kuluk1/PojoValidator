package rules.models;

import java.util.ArrayList;

public class Model {
    private String name;
    private ArrayList<String> groups;
    private ArrayList<Field> fieldArrayList;

    public Model() {
    }

    public Model(String name, ArrayList<String> groups, ArrayList<Field> fieldArrayList) {
        this.name = name;
        this.groups = groups;
        this.fieldArrayList = fieldArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public ArrayList<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(ArrayList<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}
