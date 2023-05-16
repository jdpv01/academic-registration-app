package com.perficient.academicregistrationapp.service.impl;

import com.perficient.academicregistrationapp.enums.SubjectErrorCode;
import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.persistance.repository.SubjectRepository;
import com.perficient.academicregistrationapp.service.SubjectService;
import com.perficient.academicregistrationapp.web.error.BusinessErrorDetails;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject getSubject(UUID id) {
        return subjectRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(SubjectErrorCode.CODE_01,
                        SubjectErrorCode.CODE_01.getMessage())));
    }

    @Override
    public List<Subject> getSubjects() {
        return new ArrayList<>(subjectRepository.findAll());
    }

    @Override
    public Subject updateSubject(Subject subject) {
        Subject savedSubject = subjectRepository.findById(subject.getId()).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(SubjectErrorCode.CODE_01,
                        SubjectErrorCode.CODE_01.getMessage())));
        savedSubject.setName(subject.getName());
        savedSubject.setStartTime(subject.getStartTime());
        savedSubject.setEndTime(subject.getEndTime());
        savedSubject.setWeekdays(subject.getWeekdays());
        return subjectRepository.save(savedSubject);
    }

    @Override
    public boolean deleteSubject(UUID id) {
        Subject subject = subjectRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(SubjectErrorCode.CODE_01,
                        SubjectErrorCode.CODE_01.getMessage())));
        subjectRepository.delete(subject);
        return true;
    }
}