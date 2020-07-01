package com.github.pojovalidator.rules.models;

import java.util.List;

public class Group {
    private String name;
    private List<Field> fieldArrayList;

    public Group() {
    }

    public Group(String name, List<Field> fieldArrayList) {
        this.name = name;
        this.fieldArrayList = fieldArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(List<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}
