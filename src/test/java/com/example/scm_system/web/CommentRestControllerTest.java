package com.example.scm_system.web;


import com.example.scm_system.model.entity.AuditEntity;
import com.example.scm_system.model.entity.CommentEntity;
import com.example.scm_system.model.entity.UserEntity;
import com.example.scm_system.repository.AuditRepository;
import com.example.scm_system.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WithMockUser("nikolay")
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

    private static final String COMMENT_1 = "Very interesting approach!";
    private static final String COMMENT_2 = "Good work!";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setUsername("nikolay");
        testUser.setPassword("12345");
        testUser.setFirstName("Nikolay");
        testUser.setLastName("Voynov");

        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        auditRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception {
        var audit = initComments(initAudit());

        mockMvc.perform(get("/restapi/" + audit.getId() + "/comments")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].message", is(COMMENT_1))).
                andExpect(jsonPath("$.[1].message", is(COMMENT_2)));
    }

    private AuditEntity initAudit() {
        AuditEntity testAudit = new AuditEntity();
        testAudit.setTopic("Test audit");

        return auditRepository.save(testAudit);
    }

    private AuditEntity initComments(AuditEntity audit) {

        CommentEntity comment1 = new CommentEntity();
        comment1.setCreated(LocalDateTime.now());
        comment1.setAuthor(testUser);
        comment1.setContent(COMMENT_1);
        comment1.setApproved(true);
        comment1.setAudit(audit);

        CommentEntity comment2 = new CommentEntity();
        comment2.setCreated(LocalDateTime.now());
        comment2.setAuthor(testUser);
        comment2.setContent(COMMENT_2);
        comment2.setApproved(true);
        comment2.setAudit(audit);

        audit.setComments(List.of(comment1, comment2));

        return auditRepository.save(audit);
    }
}
