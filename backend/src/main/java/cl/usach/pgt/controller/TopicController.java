package cl.usach.pgt.controller;

import cl.usach.pgt.dto.TopicResponse;
import cl.usach.pgt.service.TopicService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> listAvailable() {
        return ResponseEntity.ok(topicService.listAvailable());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }
}

