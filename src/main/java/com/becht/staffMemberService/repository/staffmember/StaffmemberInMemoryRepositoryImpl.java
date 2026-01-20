package com.becht.staffMemberService.repository.staffmember;

import com.becht.staffMemberService.domain.model.staffmember.StaffMember;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.control.Option;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
public class StaffmemberInMemoryRepositoryImpl implements StaffmemberInMemoryRepository {

    private List<StaffMember> staffMembers;

    public StaffmemberInMemoryRepositoryImpl() {
        loadStaffmembers();
    }

    private void loadStaffmembers() {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
        TypeReference<List<StaffMember>> typeReference = new TypeReference<>() {};
        InputStream inputStream = getClass().getResourceAsStream("/static/staffmembers.json");

        try {
            staffMembers = mapper.readValue(inputStream, typeReference);
            System.out.println("Staff members loaded: " + staffMembers.size());
        } catch (IOException e) {
            System.err.println("Failed to load staff members: " + e.getMessage());
            staffMembers = List.of();
        }
    }

    @Override
    public List<StaffMember> findAll() {
        return staffMembers;
    }

    @Override
    public Option<StaffMember> findByName(@NonNull String name) {
        return Option.ofOptional(staffMembers.stream()
                .filter(staffMember -> staffMember.getDisplayName().equalsIgnoreCase(name))
                .findFirst());
    }

    @Override
    public StaffMember saveStaffmemberInMemory(@NonNull StaffMember staffMember) {

        Option<StaffMember> staffMemberOpt = io.vavr.collection.List.ofAll(staffMembers)
                .find(staffMember1 -> staffMember1.getDisplayName().equalsIgnoreCase(staffMember.getDisplayName()));

        if (staffMemberOpt.isDefined()) {
            StaffMember e = staffMemberOpt.get();
            e.setIdentity(staffMember.getIdentity());
            e.setBirthday(staffMember.getBirthday());
            e.setSkills(staffMember.getSkills());
        } else {// Add
            staffMembers.add(staffMember);
        }

        return staffMember;
    }



}
