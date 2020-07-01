package rules.models;

import java.util.ArrayList;

public class Group {
    private String name;
    private ArrayList<Field> fieldArrayList;

    public Group() {
    }

    public Group(String name, ArrayList<Field> fieldArrayList) {
        this.name = name;
        this.fieldArrayList = fieldArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(ArrayList<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}
