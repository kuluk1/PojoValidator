package rules.models;

public class RuleField {
    private String ruleName;
    private String ruleValue;

    public RuleField() {
    }

    public RuleField(String ruleName, String ruleValue) {
        this.ruleName = ruleName;
        this.ruleValue = ruleValue;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }
}
