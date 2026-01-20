package com.becht.staffMemberService.service.staffmember;

import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import io.vavr.control.Option;
import lombok.NonNull;

import java.util.List;

public interface StaffMemberService {


    Option<StaffMember> getStaffMember(@NonNull String name);

    Option<StaffMember> getStaffMemberEncrypted(@NonNull String name);

    List<StaffMember> getAllStaffMembers();

    List<StaffMember> getStaffMembersBySkill(@NonNull String skill);

    List<StaffMember> getAllStaffMembersEncrypted();

    List<StaffMember> getStaffMembersBySkillEncrypted(@NonNull String skill);

    StaffMember saveStaffMembers(@NonNull StaffMember staffMember);

    StaffMember mapToEncryptedStaffMembers(@NonNull StaffMember staffMember);

    boolean isValidSkill(@NonNull String skill);

    boolean hasOnlyValidSkills(@NonNull List<String> skills);
}


