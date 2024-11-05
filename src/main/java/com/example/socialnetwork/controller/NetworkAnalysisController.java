package com.example.socialnetwork.controller;

import com.example.socialnetwork.model.User;
import com.example.socialnetwork.service.NetworkAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/network")
public class NetworkAnalysisController {
    @Autowired
    private NetworkAnalysisService networkAnalysisService;

    @GetMapping("/shortest-path")
    public List<User> findShortestPath(@RequestParam Long userId1, @RequestParam Long userId2) {
        return networkAnalysisService.findShortestPath(userId1, userId2);
    }

    @GetMapping("/communities")
    public List<Set<User>> identifyCommunities() {
        return networkAnalysisService.identifyCommunities();
    }

    @GetMapping("/degree-centrality")
    public Map<Long, Integer> calculateDegreeCentrality() {
        return networkAnalysisService.calculateDegreeCentrality();
    }
}

