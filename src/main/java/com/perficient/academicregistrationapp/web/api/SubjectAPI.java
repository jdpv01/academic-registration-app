package com.perficient.academicregistrationapp.web.api;

import com.perficient.academicregistrationapp.web.dto.SubjectDTO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/subjects")
public interface SubjectAPI {

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/create-subject")
//    SubjectDTO createSubject(@Valid @RequestBody SubjectDTO subjectDTO);

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-subject")
    SubjectDTO getSubject(@RequestParam UUID id);

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/get-all-subjects")
    List<SubjectDTO> getSubjects();

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-subject")
    SubjectDTO updateSubject(@RequestParam UUID id, @Valid @RequestBody SubjectDTO subjectDTO);

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-subject")
    boolean deleteSubject(@RequestParam UUID id);
}
