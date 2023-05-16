package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.persistance.model.Subject;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

    Subject createSubject(Subject subject);

    Subject getSubject(UUID id);

    List<Subject> getSubjects();

    Subject updateSubject(Subject subject);

    boolean deleteSubject(UUID id);
}
