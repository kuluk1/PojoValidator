# Pojo Validator

Pojo validator on xml schema

## Getting Started

The xml bust be formatted as:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<rules>
    <groups>
        <example_group>
            <title exist="true" max_length="100" />
            <data exist="true" />
        </example_group>
        <example_group2>
            <description exist="true" />
        </example_group2>
    </groups>
    <models>
        <Article group="example_group, example_group2">
            <author exist="true" />
        </Article>
    </models>
</rules>
```

with as many groups and models as you want.
Each group must contain at least 1 field with its own rules.
Each model can be part of 1 or more groups and as many field with its own rules.
Each rule consist of the attribute name, name of the rule, and value to be passed to the rule.



There are no built-in rules, to create i.e "exist" you have to 
 ```java
ARule<T>
```
where T will be the dataype i'll convert the rule value to and to annotate with
 ```java
@Rule(name)
```
where name will be the name used for the rule in the xml.


You can then use to validate your pojos like this: 
 ```java
        Validator.getInstance().setRules(getRulesXml());
        Article article = getTestArticle();
        ArrayList<RuleResult> results =  Validator.getInstance().validate(article);
```

## Full rule implementation example

 ```java
@Rule("exist")
public class Exist extends ARule<Boolean> {

    public Exist(String fieldName, String value) {
        super(fieldName, Boolean.valueOf(value));
    }

    @Override
    public RuleResult apply(Optional<Field> field, Object pojo, RuleResult ruleResult) {
        boolean exist = field.isPresent();
        if (this.getRuleValue()) {
            ruleResult.setValid(exist);
            if (!exist) ruleResult.setExplained("Could not find ");
        }
        if (!this.getRuleValue()) {
            ruleResult.setValid(!exist);
            if (exist) ruleResult.setExplained("Found the field in the model");
        }
        return  ruleResult;
    }
}
```



## Built With

* [ClassGraph](https://github.com/classgraph/classgraph)

## Authors

* **Leo F**  [kuluk1](https://github.com/kuluk1)