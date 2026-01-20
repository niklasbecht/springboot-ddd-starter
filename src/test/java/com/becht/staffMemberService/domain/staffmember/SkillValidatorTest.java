package com.becht.staffMemberService.domain.staffmember;

import com.becht.staffMemberService.domain.model.staffmember.SkillValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SkillValidatorTest {

    @ParameterizedTest
    @CsvSource({"JAVA, true","DDD, true","KOTLIN, true","java, false","unknown, false","'', false"})
    void isValidSkill(String input, boolean expected) {
        boolean result = SkillValidator.containsOnlyValidSkills(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "JAVA|DDD|KOTLIN|ENGINEERING, true",
            "JAVA|DDD|kotlin, false",
            "KOTLIN, true",
            "java, false",
            "unknown|ENGINEERING, false"
    })
    void testIsValidSkill(String input, boolean valid) {
        // split the single string into a list
        List<String> inputList = List.of(input.split("\\|"));
        assertEquals(valid, SkillValidator.containsOnlyValidSkills(inputList));
    }
}