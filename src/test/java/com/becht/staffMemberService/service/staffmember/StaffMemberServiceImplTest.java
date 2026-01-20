package com.becht.staffMemberService.service.staffmember;

import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import com.becht.staffMemberService.domain.model.staffmember.Identity;
import com.becht.staffMemberService.repository.staffmember.StaffmemberInMemoryRepositoryImpl;
import com.becht.staffMemberService.repository.staffmember.StaffmemberInMemoryRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StaffMemberServiceImplTest {
    
      private StaffMemberServiceImpl staffMemberService;
      private final StaffmemberInMemoryRepository staffmemberInMemoryRepository = new StaffmemberInMemoryRepositoryImpl();

    @BeforeEach
    void setup() {
        staffMemberService = new StaffMemberServiceImpl(staffmemberInMemoryRepository);
    }

    @Test
    void testGetStaffmembersByName() {
        var staffMemberOpt = staffMemberService.getStaffMember("superman");
        assertTrue(staffMemberOpt.isDefined());
        assertEquals("superman", staffMemberOpt.get().getDisplayName());
    }

    @Test
    void testGetAllStaffMembers() {
        var staffMemberOpt = staffMemberService.getStaffMember("superman");
        assertTrue(staffMemberOpt.isDefined());
        assertEquals("superman", staffMemberOpt.get().getDisplayName());
    }

    @Test
    void testGetStaffMemberEncrypted() {
        var staffMemberOpt = staffMemberService.getStaffMemberEncrypted("superman");
        assertTrue(staffMemberOpt.isDefined());
        assertEquals("superman", staffMemberOpt.get().getDisplayName());
    }

    @Test
    void getStaffMembersBySkill() {

        List<StaffMember> result = staffMemberService.getStaffMembersBySkill("JAVA");
        assertTrue(result.get(0).getSkills().contains(StaffMember.Skill.JAVA));
    }

    @Test
    void getAllStaffMembersEncrypted() {

        List<StaffMember> result = staffMemberService.getAllStaffMembersEncrypted();

        assertNotEquals("Clark", result.get(0).getIdentity().getFirstName()); // should be encrypted
    }

    @Test
    void getStaffMembersBySkillEncrypted() {

        List<StaffMember> result = staffMemberService.getStaffMembersBySkillEncrypted("JAVA");

        assertNotEquals("Kent", result.get(0).getIdentity().getLastName()); // should be encrypted
    }

    @Test
    void saveStaffmember() {

        val timStaffmembers = StaffMember.builder()
                .displayName("testMan")
                .identity(Identity.builder()
                        .firstName("tim")
                        .lastName("tester").build())
                .birthday(LocalDate.parse("2000-01-01"))
                .skills(List.of(StaffMember.Skill.DDD, StaffMember.Skill.ENGINEERING))
                .build();
        StaffMember result = staffMemberService.saveStaffMembers(timStaffmembers);

        assertEquals("testMan", result.getDisplayName());
        assertEquals(timStaffmembers, staffmemberInMemoryRepository.findByName("testMan").get());
    }

    @Test
    void isValidSkill() {
        assertTrue(staffMemberService.isValidSkill("JAVA"));
        assertFalse(staffMemberService.isValidSkill("UNKNOWN"));
    }

    @Test
    void mapToEncryptedStaffMembers() {
        val timStaffmembers = StaffMember.builder()
                .displayName("testMan")
                .identity(Identity.builder()
                        .firstName("tim")
                        .lastName("tester").build())
                .birthday(LocalDate.parse("2000-01-01"))
                .skills(List.of(StaffMember.Skill.DDD, StaffMember.Skill.ENGINEERING))
                .build();

        StaffMember encrypted = staffMemberService.mapToEncryptedStaffMembers(timStaffmembers);

        assertNotEquals(timStaffmembers.getIdentity().getFirstName(), encrypted.getIdentity().getFirstName());
        assertNotEquals(timStaffmembers.getIdentity().getLastName(), encrypted.getIdentity().getLastName());
    }
}
