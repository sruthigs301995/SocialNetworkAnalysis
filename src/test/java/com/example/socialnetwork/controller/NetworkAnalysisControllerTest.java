package com.example.socialnetwork.controller;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.service.NetworkAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NetworkAnalysisControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NetworkAnalysisService networkAnalysisService;

    @InjectMocks
    private NetworkAnalysisController networkAnalysisController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(networkAnalysisController).build();
    }

    @Test
    void testFindShortestPath() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        User user3 = new User();
        user3.setId(3L);

        when(networkAnalysisService.findShortestPath(1L, 3L)).thenReturn(Arrays.asList(user1, user2, user3));

        mockMvc.perform(get("/network/shortest-path")
                        .param("userId1", "1")
                        .param("userId2", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    void testIdentifyCommunities() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        Set<User> community1 = new HashSet<>(Arrays.asList(user1, user2));

        when(networkAnalysisService.identifyCommunities()).thenReturn(Arrays.asList(community1));

        mockMvc.perform(get("/network/communities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0].id").value(1))
                .andExpect(jsonPath("$[0][1].id").value(2));
    }

    @Test
    void testCalculateDegreeCentrality() throws Exception {
        Map<Long, Integer> degreeCentrality = new HashMap<>();
        degreeCentrality.put(1L, 2);
        degreeCentrality.put(2L, 1);

        when(networkAnalysisService.calculateDegreeCentrality()).thenReturn(degreeCentrality);

        mockMvc.perform(get("/network/degree-centrality")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1").value(2))
                .andExpect(jsonPath("$.2").value(1));
    }
}

