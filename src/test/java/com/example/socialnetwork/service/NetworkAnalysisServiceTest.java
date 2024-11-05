package com.example.socialnetwork.service;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NetworkAnalysisServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NetworkAnalysisService networkAnalysisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindShortestPath() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        User user3 = new User();
        user3.setId(3L);

        user1.getFriends().add(user2);
        user2.getFriends().add(user3);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));

        List<User> path = networkAnalysisService.findShortestPath(1L, 3L);
        assertEquals(3, path.size());
        assertEquals(1L, path.get(0).getId());
        assertEquals(2L, path.get(1).getId());
        assertEquals(3L, path.get(2).getId());
    }

    @Test
    void testIdentifyCommunities() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        User user3 = new User();
        user3.setId(3L);
        User user4 = new User();
        user4.setId(4L);

        user1.getFriends().add(user2);
        user2.getFriends().add(user1);
        user3.getFriends().add(user4);
        user4.getFriends().add(user3);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3, user4));

        List<Map<String, Object>> communities = networkAnalysisService.identifyCommunities();
        assertEquals(2, communities.size());

        Map<String, Object> community1 = communities.get(0);
        Map<String, Object> community2 = communities.get(1);

        assertEquals(1, community1.get("communityId"));
        assertEquals(2, community2.get("communityId"));

        Set<User> users1 = new HashSet<>((Collection<User>) community1.get("users"));
        Set<User> users2 = new HashSet<>((Collection<User>) community2.get("users"));

        assertTrue(users1.contains(user1) || users1.contains(user3));
        assertTrue(users2.contains(user1) || users2.contains(user3));
    }

    @Test
    void testCalculateDegreeCentrality() {
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        User user3 = new User();
        user3.setId(3L);

        user1.getFriends().add(user2);
        user1.getFriends().add(user3);
        user2.getFriends().add(user1);
        user3.getFriends().add(user1);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));

        Map<Long, Integer> degreeCentrality = networkAnalysisService.calculateDegreeCentrality();
        assertEquals(3, degreeCentrality.size());
        assertEquals(2, degreeCentrality.get(1L));
        assertEquals(1, degreeCentrality.get(2L));
        assertEquals(1, degreeCentrality.get(3L));
    }
}
