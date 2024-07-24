package telran.util;

public class RuleMatcher {
    /*
    * @param chars - array of char primitives
    * @param mustBeRules - array of rules that must be true
    * @param mustNotBeRule array of rules that must be false
    * @return empty error message if array of chars matches all rules otherwise specific error message saying what rules don't match
    */
    public static String matchesRules(char[] chars, CharacterRule[] mustBeRules, CharacterRule[] mustNotBeRule) {

        //consider the class Character for rules definition
        
        StringBuilder errorMessage = new StringBuilder();

        checkRules(chars, mustBeRules, true, errorMessage);
        checkRules(chars, mustNotBeRule, false, errorMessage);
    
        return errorMessage.length() == 0 ? "" : errorMessage.toString().trim();
    }
    
    private static void checkRules(char[] chars, CharacterRule[] rules, boolean flag, StringBuilder errorMessage) {
        for (CharacterRule rule : rules) {
            boolean ruleMatched = false;
            int i = 0;
            while (i < chars.length) {
                if (rule.predicate.test(chars[i])) {
                    ruleMatched = true;
                    if (!flag) {
                        errorMessage.append(rule.errorMessage).append("\n");
                        return;
                    }
                }
                i++;
            }
            if (flag && !ruleMatched) {
                errorMessage.append(rule.errorMessage).append("\n");
            }
        }
    }

}