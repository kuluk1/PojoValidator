package com.github.pojovalidator.rules.models;

import java.util.List;

public class Model {
    private String name;
    private List<String> groups;
    private List<Field> fieldArrayList;

    public Model() {
    }

    public Model(String name, List<String> groups, List<Field> fieldArrayList) {
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

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<Field> getFieldArrayList() {
        return fieldArrayList;
    }

    public void setFieldArrayList(List<Field> fieldArrayList) {
        this.fieldArrayList = fieldArrayList;
    }
}
