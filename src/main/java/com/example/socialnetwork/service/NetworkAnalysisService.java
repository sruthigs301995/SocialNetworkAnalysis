package com.example.socialnetwork.service;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.repository.UserRepository;
import org.jgrapht.Graph;
import org.jgrapht.alg.community.Community;
import org.jgrapht.alg.community.LouvainClustering;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NetworkAnalysisService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findShortestPath(Long userId1, Long userId2) {
        User start = userRepository.findById(userId1).orElseThrow();
        User end = userRepository.findById(userId2).orElseThrow();

        Queue<User> queue = new LinkedList<>();
        Map<User, User> previous = new HashMap<>();
        Set<User> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            User current = queue.poll();
            if (current.equals(end)) {
                return reconstructPath(previous, start, end);
            }

            for (User friend : current.getFriends()) {
                if (!visited.contains(friend)) {
                    queue.add(friend);
                    visited.add(friend);
                    previous.put(friend, current);
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    private List<User> reconstructPath(Map<User, User> previous, User start, User end) {
        List<User> path = new ArrayList<>();
        for (User at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    public List<Set<User>> identifyCommunities() {
        List<User> users = userRepository.findAll();
        Graph<User, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Add vertices
        users.forEach(graph::addVertex);

        // Add edges
        for (User user : users) {
            for (User friend : user.getFriends()) {
                graph.addEdge(user, friend);
            }
        }

        // Apply Louvain Clustering
        LouvainClustering<User, DefaultEdge> louvainClustering = new LouvainClustering<>(graph);
        Community<User> community = louvainClustering.getCommunity();

        // Convert community structure to list of sets of users
        List<Set<User>> communities = new ArrayList<>();
        for (Set<User> communitySet : community.getCommunities()) {
            communities.add(communitySet);
        }

        return communities;
    }

    public Map<Long, Integer> calculateDegreeCentrality() {
        Map<Long, Integer> degreeCentrality = new HashMap<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            degreeCentrality.put(user.getId(), user.getFriends().size());
        }
        return degreeCentrality;
    }
}
