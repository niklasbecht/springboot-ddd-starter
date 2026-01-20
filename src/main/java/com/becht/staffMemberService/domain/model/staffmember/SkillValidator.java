package com.becht.staffMemberService.domain.model.staffmember;

import lombok.NonNull;
import org.apache.commons.lang3.EnumUtils;

public class SkillValidator {


    public static boolean containsOnlyValidSkills(String skill) {
        return EnumUtils.isValidEnum(StaffMember.Skill.class, skill);
    }

    public static boolean containsOnlyValidSkills(java.util.@NonNull List<String> skills) {
        return skills.stream().filter(it ->!containsOnlyValidSkills(it)).toList().isEmpty();
    }

}
