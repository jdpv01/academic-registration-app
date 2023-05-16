package com.perficient.academicregistrationapp.persistance.repository;

import com.perficient.academicregistrationapp.persistance.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID>{
}
