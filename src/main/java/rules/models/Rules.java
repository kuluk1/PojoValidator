package rules.models;

import java.util.ArrayList;
import java.util.Optional;

public class Rules {
    private ArrayList<Group> groupArrayList;
    private ArrayList<Model> modelArrayList;

    public Rules() {
    }

    public Rules(ArrayList<Group> groupArrayList, ArrayList<Model> modelArrayList) {
        this.groupArrayList = groupArrayList;
        this.modelArrayList = modelArrayList;
    }

    public ArrayList<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public void setGroupArrayList(ArrayList<Group> groupArrayList) {
        this.groupArrayList = groupArrayList;
    }

    public ArrayList<Model> getModelArrayList() {
        return modelArrayList;
    }

    public void setModelArrayList(ArrayList<Model> modelArrayList) {
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
