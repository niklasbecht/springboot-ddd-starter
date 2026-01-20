package com.becht.staffMemberService.service.staffmember;

import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import com.becht.staffMemberService.domain.model.staffmember.SkillValidator;
import com.becht.staffMemberService.repository.staffmember.StaffmemberInMemoryRepository;
import com.becht.staffMemberService.util.CaesarCipher;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StaffMemberServiceImpl implements StaffMemberService {

    private static final int CIPHER_OFFSET = 5;
    private final StaffmemberInMemoryRepository staffmemberInMemoryRepository;

    @Override
    public Option<StaffMember> getStaffMember(@NonNull String name) {
        return staffmemberInMemoryRepository.findByName(name);
    }

    @Override
    public Option<StaffMember> getStaffMemberEncrypted(@NonNull String name) {
        return getStaffMember(name).map(this::mapToEncryptedStaffMembers);
    }

    @Override
    public List<StaffMember> getAllStaffMembers() {
        return staffmemberInMemoryRepository.findAll();
    }

    @Override
    public List<StaffMember> getStaffMembersBySkill(@NonNull String skill) {
        return staffmemberInMemoryRepository.findAll().stream()
                .filter(it -> it.getSkills().contains(StaffMember.Skill.valueOf(skill)))
                .toList();
    }

    @Override
    public List<StaffMember> getAllStaffMembersEncrypted() {
        return staffmemberInMemoryRepository.findAll().stream().map(this::mapToEncryptedStaffMembers).toList();
    }

    @Override
    public List<StaffMember> getStaffMembersBySkillEncrypted(@NonNull String skill) {
        return staffmemberInMemoryRepository.findAll().stream()
                .filter(it -> it.getSkills().contains(StaffMember.Skill.valueOf(skill)))
                .map(this::mapToEncryptedStaffMembers).toList();
    }


    @Override
    public StaffMember saveStaffMembers(@NonNull StaffMember staffMember){
        return staffmemberInMemoryRepository.saveStaffmemberInMemory(staffMember);
    }





    @Override
    public boolean isValidSkill(String skill) {
        return SkillValidator.containsOnlyValidSkills(skill);
    }

    @Override
    public boolean hasOnlyValidSkills(@NonNull List<String> skills) {
        return skills.stream().filter(it ->!isValidSkill(it)).toList().isEmpty();
    }


    @Override
    public StaffMember mapToEncryptedStaffMembers(StaffMember staffMember) {
        return staffMember.toBuilder().identity(
                staffMember.getIdentity().toBuilder()
                        .firstName(CaesarCipher.encrypte(staffMember.getIdentity().getFirstName(), CIPHER_OFFSET).get())
                        .lastName(CaesarCipher.encrypte(staffMember.getIdentity().getLastName(), CIPHER_OFFSET).get())
                        .build()
                ).build();
    }



}
