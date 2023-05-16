package com.perficient.academicregistrationapp.persistance.repository;

import com.perficient.academicregistrationapp.persistance.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {

    Optional<Course> findByName(String name);

    @Query(value = "SELECT * FROM course " +
                    "ORDER BY name ASC " +
                    "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Course> findAllCoursesPaged(@Param("limit") int limit, @Param("offset") int offset);

    List<Course> findTop2ByOrderByCapacityDesc();


}
