package rules;

import java.lang.reflect.Field;
import java.util.Optional;

public abstract class ARule<T> implements IRule {

    public ARule(String fieldName, T ruleValue) {
        this.ruleName = getClass().getAnnotation(Rule.class).value();
        this.fieldName = fieldName;
        this.ruleValue = ruleValue;
    }

    private String ruleName;
    private String fieldName;
    private T ruleValue;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public T getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(T ruleValue) {
        this.ruleValue = ruleValue;
    }

    public RuleResult validate(Object pojo) {
        RuleResult ruleResult = new RuleResult(this.getRuleName());
        ruleResult.setFieldName(this.getFieldName());
        ruleResult.setModelName(pojo.getClass().getSimpleName());
        Field[] fields = pojo.getClass().getDeclaredFields();
        boolean found = false;
        Field field = null;
        int i = 0;
        while (!found && i < fields.length) {
            if (fields[i].getName().equals(this.getFieldName()))  {
                found = true;
                field = fields[i];
            }
            i++;
        }
        field.setAccessible(true);
        return apply(Optional.ofNullable(field), pojo, ruleResult);
    }
}