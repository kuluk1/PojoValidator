package rules;

public class RuleResult {
        private String ruleName;
        private String fieldName;
        private String modelName;
        private String explained;
        private boolean isValid;

        public RuleResult(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getExplained() {
            return explained;
        }

        public void setExplained(String explained) {
            this.explained = explained;
        }
}
