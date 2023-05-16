package com.perficient.academicregistrationapp.persistance.repository;

import com.perficient.academicregistrationapp.persistance.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<UserCourse, UUID> {

    Optional<UserCourse> findByUserEmailAndCourseName(String userEmail, String courseName);
}
