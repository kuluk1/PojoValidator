package com.github.pojovalidator.rules.models;

import java.util.List;
import java.util.Optional;

public class Rules {
    private List<Group> groupArrayList;
    private List<Model> modelArrayList;

    public Rules() {
    }

    public Rules(List<Group> groupArrayList, List<Model> modelArrayList) {
        this.groupArrayList = groupArrayList;
        this.modelArrayList = modelArrayList;
    }

    public List<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public void setGroupArrayList(List<Group> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    public List<Model> getModelArrayList() {
        return modelArrayList;
    }

    public void setModelArrayList(List<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    public Optional<Model> findModelByName(String name) {
        Model model = null;
        for (Model o : this.modelArrayList) {
            if (o.getName().equals(name)) model = o;
        }
        return Optional.ofNullable(model);
    }

    public Optional<Group> findGroupByName(String name) {
        Group group = null;
        for (Group o : this.groupArrayList) {
            if (o.getName().equals(name)) group = o;
        }
        return Optional.ofNullable(group);
    }
}
