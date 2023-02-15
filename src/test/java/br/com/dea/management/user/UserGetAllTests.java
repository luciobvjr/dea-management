package br.com.dea.management.user;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import br.com.dea.management.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("mysql-test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class UserGetAllTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void whenRequestingUserList_thenReturnListOfUserPaginatedSuccessfully() throws Exception {
        this.userRepository.deleteAll();
        this.createFakeUsers(100);

        mockMvc.perform(get("/user/all?page=0&pageSize=4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].name", is("Name 0")))
                .andExpect(jsonPath("$.content[0].email", is("Email 0")))
                .andExpect(jsonPath("$.content[0].linkedin", is("Linkedin 0")))
                .andExpect(jsonPath("$.content[1].name", is("Name 1")))
                .andExpect(jsonPath("$.content[1].email", is("Email 1")))
                .andExpect(jsonPath("$.content[1].linkedin", is("Linkedin 1")))
                .andExpect(jsonPath("$.content[2].name", is("Name 10")))
                .andExpect(jsonPath("$.content[2].email", is("Email 10")))
                .andExpect(jsonPath("$.content[2].linkedin", is("Linkedin 10")))
                .andExpect(jsonPath("$.content[3].name", is("Name 11")))
                .andExpect(jsonPath("$.content[3].email", is("Email 11")))
                .andExpect(jsonPath("$.content[3].linkedin", is("Linkedin 11")));
    }

    @Test
    void whenRequestingUserListAndPageQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user/all?page=abc&pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingUserListAndPageQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user/all?pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingUserListAndPageSizeQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user/all?page=0&pageSize=abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingUserListAndPageSizeQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user/all?page=0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    private void createFakeUsers(int amount) {
        for (int i =0; i < amount; i++) {
            User user = User.builder()
                        .email("Email " + i)
                        .name("Name " + i)
                        .linkedin("Linkedin " + i)
                        .password("Password " + i)
                        .build();

            this.userRepository.save(user);
        }
    }
}
