package cl.usach.pgt.controller;

import cl.usach.pgt.dto.ApplicationRequest;
import cl.usach.pgt.dto.ApplicationResponse;
import cl.usach.pgt.service.ApplicationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> create(@Valid @RequestBody ApplicationRequest request) {
        ApplicationResponse response = applicationService.create(request);
        return ResponseEntity.created(URI.create("/api/applications/" + response.id())).body(response);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationResponse>> listForStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.listForStudent(studentId));
    }
}

