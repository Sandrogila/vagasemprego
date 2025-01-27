package apivagas.empregos.com.vagasemprego.controller;


import apivagas.empregos.com.vagasemprego.dto.jobPositionDTO;
import apivagas.empregos.com.vagasemprego.model.JobPosition;
import apivagas.empregos.com.vagasemprego.service.JobPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobPositionController {

    private final JobPositionService jobService;

    @GetMapping
    public ResponseEntity<List<JobPosition>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPosition> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<JobPosition> createJob(@RequestBody @Valid jobPositionDTO jobDTO) {
        return ResponseEntity.ok(jobService.createJob(jobDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<JobPosition> updateJob(
            @PathVariable Long id,
            @RequestBody @Valid jobPositionDTO jobDTO
    ) {
        return ResponseEntity.ok(jobService.updateJob(id, jobDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Void> applyForJob(@PathVariable Long id) {
        jobService.applyForJob(id);
        return ResponseEntity.ok().build();
    }
}
