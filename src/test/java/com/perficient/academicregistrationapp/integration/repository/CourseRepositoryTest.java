package com.perficient.academicregistrationapp.integration.repository;

import com.perficient.academicregistrationapp.persistance.model.Course;
import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.persistance.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    public Course setup1(){
        Subject subject1 = Subject.builder()
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-MI-VI").build();
        Subject subject2 = Subject.builder()
                .name("Algebra lineal")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("MA-JU").build();
        Course course = Course.builder()
                .name("Matemáticas")
                .startDate(LocalDate.of(2023, 1, 16))
                .endDate(LocalDate.of(2023,5,16))
                .capacity(0).build();
        course.setSubjectList(Arrays.asList(subject1, subject2));
        return course;
    }

    @Test
    public void givenCourseObject_whenSave_thenReturnSavedCourse(){
        Course course = setup1();
        course.getSubjectList().forEach(subject -> subject.setCourse(course));
        Course savedCourse = courseRepository.save(course);
        assertThat(savedCourse).isNotNull();
    }

    @Test
    public void testFindAllCoursesPaged(){
        List<Course> courseList = courseRepository.findAllCoursesPaged(1, 1);
        assertEquals(courseList.size(), 1);
        assertEquals(courseList.get(0).getName(), "Introduccion a las TIC");
    }

    @Test
    public void testFindCoursesPage(){
        Pageable pageRequest = PageRequest.of(0, 1);
        Page<Course> coursePage = courseRepository.findAll(pageRequest);
        assertEquals(coursePage.stream().count(), 1);
        assertEquals(coursePage.toList().get(0).getName(), "Introduccion a las TIC");
    }
}
