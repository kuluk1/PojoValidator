package com.github.pojovalidator.rules.models;

import java.util.List;

public class Field {
    private String name;
    private List<RuleField> ruleFieldArrayList;

    public Field() {
    }

    public Field(String name, List<RuleField> ruleFieldArrayList) {
        this.name = name;
        this.ruleFieldArrayList = ruleFieldArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RuleField> getRuleFieldArrayList() {
        return ruleFieldArrayList;
    }

    public void setRuleFieldArrayList(List<RuleField> ruleFieldArrayList) {
        this.ruleFieldArrayList = ruleFieldArrayList;
    }
}
