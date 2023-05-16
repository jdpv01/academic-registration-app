package com.perficient.academicregistrationapp.mapper;

import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.web.dto.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    Subject fromSubjectDTO(SubjectDTO subjectDTO);

    @Mapping(source = "id", target = "id")
    Subject fromSubjectDTO(UUID id, SubjectDTO subjectDTO);

    SubjectDTO fromSubject(Subject user);
}
