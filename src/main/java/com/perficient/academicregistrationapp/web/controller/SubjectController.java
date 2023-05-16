package com.perficient.academicregistrationapp.web.controller;

import com.perficient.academicregistrationapp.mapper.SubjectMapper;
import com.perficient.academicregistrationapp.service.SubjectService;
import com.perficient.academicregistrationapp.web.api.SubjectAPI;
import com.perficient.academicregistrationapp.web.dto.SubjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class SubjectController implements SubjectAPI {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

//    @Override
//    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
//        return subjectMapper.fromSubject(subjectService.createSubject(subjectMapper.fromSubjectDTO(subjectDTO)));
//    }

    @Override
    public SubjectDTO getSubject(UUID id) {
        return subjectMapper.fromSubject(subjectService.getSubject(id));
    }

    @Override
    public List<SubjectDTO> getSubjects() {
        return subjectService.getSubjects().stream().map(subjectMapper::fromSubject).collect(Collectors.toList());
    }

    @Override
    public SubjectDTO updateSubject(UUID id, SubjectDTO subjectDTO) {
        return subjectMapper.fromSubject(subjectService.updateSubject(subjectMapper.fromSubjectDTO(id, subjectDTO)));
    }

    @Override
    public boolean deleteSubject(UUID id) {
        return subjectService.deleteSubject(id);
    }
}
