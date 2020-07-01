package com.github.pojovalidator;

import com.github.pojovalidator.rules.models.Field;
import com.github.pojovalidator.rules.models.Group;
import com.github.pojovalidator.rules.models.RuleField;
import com.github.pojovalidator.rules.models.Rules;
import com.github.pojovalidator.rules.models.Model;
import io.github.classgraph.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.github.pojovalidator.rules.ARule;
import com.github.pojovalidator.rules.Rule;
import com.github.pojovalidator.rules.RuleResult;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class Validator {
    private static Validator instance;

    private Map<String, Class<?>> ruleMap;

    private Rules rules;

    private Validator() {
        this.ruleMap = new HashMap<>();
        if (null == this.rules) setRules(new Rules());
    }

    public static synchronized Validator getInstance() {
        if (null == instance) instance = new Validator();
        return instance;
    }

    private synchronized void setRules(Rules rules) {
        this.rules = rules;
        registerRules();
    }

    public synchronized void setRules(String xmlFileRules) {
        Document document = convertStringToXMLDocument( xmlFileRules );
        document.getDocumentElement().normalize();

        NodeList groups = document.getElementsByTagName("groups").item(0).getChildNodes();
        NodeList models = document.getElementsByTagName("models").item(0).getChildNodes();



        List<Group> groupArrayList = new ArrayList<>();
        for (int i = 0; i < groups.getLength(); i++) {
            if (groups.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String groupName = groups.item(i).getNodeName();
                Group group = new Group();
                List<Field> fieldArrayList = new ArrayList<>();
                group.setName(groupName);
                for (int j = 0; j < groups.item(i).getChildNodes().getLength(); j++) {
                    if (groups.item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        String fieldName = groups.item(i).getChildNodes().item(j).getNodeName();
                        Field field = new Field();
                        field.setName(fieldName);
                        List<RuleField> ruleFieldArrayList = new ArrayList<>();
                        NamedNodeMap attributes = groups.item(i).getChildNodes().item(j).getAttributes();
                        for (int k = 0; k < attributes.getLength(); k++) {
                            String attributeName = attributes.item(k).getNodeName();
                            String attributeValue = attributes.item(k).getNodeValue();
                            RuleField ruleField =  new RuleField();
                            ruleField.setRuleName(attributeName);
                            ruleField.setRuleValue(attributeValue);
                            ruleFieldArrayList.add(ruleField);
                        }
                        field.setRuleFieldArrayList(ruleFieldArrayList);
                        fieldArrayList.add(field);
                    }
                }
                group.setFieldArrayList(fieldArrayList);
                groupArrayList.add(group);
            }
        }


        List<Model> modelArrayList = new ArrayList<>();
        for (int i = 0; i < models.getLength(); i++) {
            if (models.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String modelName = models.item(i).getNodeName();
                String group = null;
                if (models.item(i).getAttributes().getLength() > 0)
                    group = models.item(i).getAttributes().getNamedItem("group").getNodeValue();
                Model model = new Model();
                model.setName(modelName);
                List<String> groupsList = new ArrayList<String>(Arrays.asList(group.trim().split(",")));
                List<String> trimmedGroupList = new ArrayList<String>(groupsList.stream()
                        .map(e -> e.trim())
                        .collect(Collectors.toList()));
                model.setGroups(trimmedGroupList);
                List<Field> fieldArrayList = new ArrayList<>();
                for (int j = 0; j < models.item(i).getChildNodes().getLength(); j++) {
                    if (models.item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        String fieldName = models.item(i).getChildNodes().item(j).getNodeName();
                        Field field = new Field();
                        field.setName(fieldName);
                        List<RuleField> ruleFieldArrayList = new ArrayList<>();
                        NamedNodeMap attributes = models.item(i).getChildNodes().item(j).getAttributes();
                        for (int k = 0; k < attributes.getLength(); k++) {
                            String attributeName = attributes.item(k).getNodeName();
                            String attributeValue = attributes.item(k).getNodeValue();
                            RuleField ruleField =  new RuleField();
                            ruleField.setRuleName(attributeName);
                            ruleField.setRuleValue(attributeValue);
                            ruleFieldArrayList.add(ruleField);
                        }
                        field.setRuleFieldArrayList(ruleFieldArrayList);
                        fieldArrayList.add(field);
                    }
                }
                model.setFieldArrayList(fieldArrayList);
                modelArrayList.add(model);
            }
        }

        Rules rules = new Rules();
        rules.setGroupArrayList(groupArrayList);
        rules.setModelArrayList(modelArrayList);
        this.setRules(rules);
    }

    private synchronized void signRule(String name, Class<?> clazz) {
        this.ruleMap.put(name, clazz);
    }

    public synchronized List<RuleResult> validate(Object pojo) {
        List<RuleResult> ruleResults = new ArrayList<>();
        Optional<Model> model = this.rules.findModelByName(pojo.getClass().getSimpleName());
        if (model.isPresent()) {

            List<Field> fieldArrayList = model.get().getFieldArrayList();
            for (String group : model.get().getGroups()) {
                Optional<Group> optionalGroup = this.rules.findGroupByName(group);
                if (optionalGroup.isPresent()) fieldArrayList.addAll(optionalGroup.get().getFieldArrayList());
            }

            for (Field field : fieldArrayList) {
                for (RuleField ruleField : field.getRuleFieldArrayList()) {
                    Class<?> clazz = ruleMap.get(ruleField.getRuleName());
                    if (null == clazz) {
                        RuleResult ruleResult = new RuleResult(ruleField.getRuleName());
                        ruleResult.setValid(false);
                        ruleResult.setExplained("Cannot find rule");
                        ruleResults.add(ruleResult);
                    } else {
                        Constructor<?> cons = null;
                        try {
                            cons = clazz.getConstructor(String.class, String.class);
                            ARule rule = (ARule) cons.newInstance(field.getName(), ruleField.getRuleValue());
                            ruleResults.add(rule.validate(pojo));
                        } catch (NoSuchMethodException e) {
                            RuleResult ruleResult = new RuleResult(ruleField.getRuleName());
                            ruleResult.setValid(false);
                            ruleResult.setExplained(e.getMessage());
                            ruleResults.add(ruleResult);
                        } catch (IllegalAccessException e) {
                            RuleResult ruleResult = new RuleResult(ruleField.getRuleName());
                            ruleResult.setValid(false);
                            ruleResult.setExplained(e.getMessage());
                            ruleResults.add(ruleResult);
                        } catch (InstantiationException e) {
                            RuleResult ruleResult = new RuleResult(ruleField.getRuleName());
                            ruleResult.setValid(false);
                            ruleResult.setExplained(e.getMessage());
                            ruleResults.add(ruleResult);
                        } catch (InvocationTargetException e) {
                            RuleResult ruleResult = new RuleResult(ruleField.getRuleName());
                            ruleResult.setValid(false);
                            ruleResult.setExplained(e.getMessage());
                            ruleResults.add(ruleResult);
                        }
                    }
                }
            }
        }
        return ruleResults;
    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized void registerRules() {
        try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
                .scan()) {

            ClassInfoList classInfos = result.getClassesWithAnnotation(Rule.class.getName());

            for (ClassInfo classInfo : classInfos) {

                AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(Rule.class.getName());

                signRule(annotationInfo.getParameterValues().getValue("value").toString(), classInfo.loadClass());

            }
        }
    }
}
