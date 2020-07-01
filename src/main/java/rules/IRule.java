package rules;

import java.lang.reflect.Field;
import java.util.Optional;

public interface IRule {

    public RuleResult apply(Optional<Field> field, Object pojo, RuleResult ruleResult);

}