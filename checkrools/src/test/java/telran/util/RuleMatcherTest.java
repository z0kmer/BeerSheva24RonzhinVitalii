package telran.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RuleMatcherTest {

    @Test
    void matchesRulesTest() {
        
        //Must be rules: at least one capital letter, at least one lower case letter, at least one digit, at least one dot(.)
        //Must not be rules: space is disallowed
        //examples: mathes - {'a', 'n', '*', 'G', '.', '.', '1'}
        //mismatches - {'a', 'n', '*', 'G', '.', '.', '1', ' '} -> "space disallowed",
        // {'a', 'n', '*',  '.', '.', '1'} -> "no capital",
        // {'a', 'n', '*', 'G', '.', '.'} -> "no digit"
        
        CharacterRule[] mustBeRules = {
            new CharacterRule(true, Character::isUpperCase, "no capital"),
            new CharacterRule(true, Character::isLowerCase, "no lower case"),
            new CharacterRule(true, Character::isDigit, "no digit"),
            new CharacterRule(true, c -> c == '.', "no dot")
        };

        CharacterRule[] mustNotBeRules = {
            new CharacterRule(false, Character::isWhitespace, "space disallowed")
        };

        String errors = "no digit" + "\n" + "no dot"  + "\n" + "space disallowed";

        assertEquals("", RuleMatcher.matchesRules(new char[]{'a', 'n', '*', 'G', '.', '.', '1'}, mustBeRules, mustNotBeRules));
        assertEquals("space disallowed", RuleMatcher.matchesRules(new char[]{'a', 'n', '*', 'G', '.', '.', '1', ' '}, mustBeRules, mustNotBeRules));
        assertEquals("no capital", RuleMatcher.matchesRules(new char[]{'a', 'n', '*', '.', '.', '1'}, mustBeRules, mustNotBeRules));
        assertEquals("no digit", RuleMatcher.matchesRules(new char[]{'a', 'n', '*', 'G', '.', '.'}, mustBeRules, mustNotBeRules));
        assertEquals(errors, RuleMatcher.matchesRules(new char[]{'a', 'n', '*', 'G', ' '}, mustBeRules, mustNotBeRules));
    }
}