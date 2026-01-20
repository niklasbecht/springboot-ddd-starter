package com.becht.staffMemberService.adapter.web.staffmember;

import com.becht.staffMemberService.util.CaesarCipher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StaffMemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Test

    void shouldReturnAllStaffMembers() throws Exception {
        this.mockMvc.perform(get("/staffmembers"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("superman")));
    }

    @Test
    void shouldReturnStaffMemberByName() throws Exception {
        String staffmemberName = "superman";

        this.mockMvc.perform(get("/staffmembers/{staffName}", staffmemberName))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(staffmemberName)));
    }

    @Test
    void shouldReturnEncryptedStaffmember() throws Exception {
        String staffmemberName = "superman";

        this.mockMvc.perform(get("/staffmembers/{staffName}", staffmemberName)
                        .param("encrypted", "true"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        CaesarCipher.encrypte("clark", 5).get()
                )));
    }

    @Test
    void shouldReturnStaffmemberBySkill() throws Exception {
        String skill = "KOTLIN";
        String expectedResponse="[{\"name\":\"superman\",\"identity\":\"clark kent\",\"birthday\":\"1977-04-18\",\"skills\":[\"JAVA\",\"KOTLIN\",\"ENGINEERING\"]},{\"name\":\"aquaman\",\"identity\":\"arthur curry\",\"birthday\":\"1986-01-29\",\"skills\":[\"JAVA\",\"DESIGNPATTERNS\",\"KOTLIN\"]}]";

        MvcResult result = this.mockMvc.perform(get("/staffmembers")
                        .param("skill", skill))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains(expectedResponse));
}
    @Test
    void shouldReturn400ForInvalidSkill() throws Exception {
        String skill = "TESTING";

        this.mockMvc.perform(get("/staffmembers")
                        .param("skill", skill))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnHeroesBySkillEncrypted() throws Exception {
        String skill = "KOTLIN";

        MvcResult result = this.mockMvc.perform(get("/staffmembers")
                        .param("skill", skill)
                        .param("encrypted", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains(
                CaesarCipher.encrypte("clark", 5).get()
        ));
    }

    @Test
    void addStaffMember() throws Exception {
        String requestBody = """
                {
                  "name": "superman",
                  "identity": {
                    "firstName": "clark",
                    "lastName": "kent"
                  },
                  "birthday": "1977-04-18",
                  "skills": [
                    "JAVA",
                    "KOTLIN",
                    "ENGINEERING"
                  ]
                }
                """;

        this.mockMvc.perform(post("/staffmembers")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void fail_AddStaffMember_wrongPower() throws Exception {
        String requestBody = """
                {
                  "name": "superaquaman",
                  "identity": {
                    "firstName": "clark",
                    "lastName": "fisch"
                  },
                  "birthday": "1977-04-18",
                  "skills": [
                    "Diving",
                    "KOTLIN",
                    "ENGINEERING"
                  ]
                }
                """;

        this.mockMvc.perform(post("/staffmembers")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void fail_addStaffMember_invalid_date() throws Exception {
        String requestBody = """
                {
                  "name": "superaquaman",
                  "identity": {
                    "firstName": "clark",
                    "lastName": "fisch"
                  },
                  "birthday": "1977-04-18-fefs3",
                  "skills": [
                    "Diving",
                    "KOTLIN",
                    "ENGINEERING"
                  ]
                }
                """;

        this.mockMvc.perform(post("/staffmembers")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
