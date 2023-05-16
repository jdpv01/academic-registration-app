package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.enums.CourseErrorCode;
import com.perficient.academicregistrationapp.enums.EnrollmentErrorCode;
import com.perficient.academicregistrationapp.enums.UserErrorCode;
import com.perficient.academicregistrationapp.persistance.model.Course;
import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.persistance.model.User;
import com.perficient.academicregistrationapp.persistance.model.UserCourse;
import com.perficient.academicregistrationapp.persistance.repository.CourseRepository;
import com.perficient.academicregistrationapp.persistance.repository.EnrollmentRepository;
import com.perficient.academicregistrationapp.persistance.repository.UserRepository;
import com.perficient.academicregistrationapp.service.impl.EnrollmentServiceImpl;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void resetMocks(){
        Mockito.reset(enrollmentRepository);
        Mockito.reset(userRepository);
        Mockito.reset(courseRepository);
    }

    private UserCourse setupUserCourse(User user, Course course){
        return new UserCourse(UUID.randomUUID(), user, course, LocalDateTime.now());
    }

    private User setupUser(){
        return User.builder().
                email("peláezd86@gmail.com")
                .courseList(new ArrayList<>()).build();
    }

    public Course setupCourse(){
        Subject subject1 = Subject.builder()
                .id(UUID.randomUUID())
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-MI-VI").build();
        Subject subject2 = Subject.builder()
                .id(UUID.randomUUID())
                .name("Algebra lineal")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("MA-JU").build();
        return Course.builder()
                .id(UUID.randomUUID())
                .name("Matemáticas")
                .startDate(LocalDate.of(2023, 1, 16))
                .endDate(LocalDate.of(2023,5,16))
                .capacity(1)
                .subjectList(Arrays.asList(subject1, subject2)).build();
    }

    public Course setupCourse2(){
        Subject subject1 = Subject.builder()
                .id(UUID.randomUUID())
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-MI-VI").build();
        Subject subject2 = Subject.builder()
                .id(UUID.randomUUID())
                .name("Algebra lineal")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("MA-JU").build();
        return Course.builder()
                .id(UUID.randomUUID())
                .name("Matemáticas2")
                .startDate(LocalDate.of(2023, 1, 16))
                .endDate(LocalDate.of(2023,5,16))
                .capacity(1)
                .subjectList(Arrays.asList(subject1, subject2)).build();
    }

    @Test
    public void testEnrollUser(){
        User user = setupUser();
        Course course = setupCourse();
        UserCourse expectedUserCourse = setupUserCourse(user, course);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(courseRepository.findByName(course.getName())).thenReturn(Optional.of(course));
        when(enrollmentRepository.save(any())).thenReturn(expectedUserCourse);
        UserCourse actualUserCourse = enrollmentService.enrollUser(user.getEmail(), course.getName());
        assertEquals(expectedUserCourse.getUser().getEmail(), actualUserCourse.getUser().getEmail());
        assertEquals(expectedUserCourse.getCourse().getName(), actualUserCourse.getCourse().getName());
    }

    @Test
    public void testEnrollUserUserNotFound(){
        User user = setupUser();
        Course course = setupCourse();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        try{
            enrollmentService.enrollUser(user.getEmail(), course.getName());
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testEnrollUserCourseNotFound(){
        User user = setupUser();
        Course course = setupCourse();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(courseRepository.findByName(course.getName())).thenReturn(Optional.empty());
        try{
            enrollmentService.enrollUser(user.getEmail(), course.getName());
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testEnrollUserAlreadyEnrolled(){
        User user = setupUser();
        Course course = setupCourse();
        user.getCourseList().add(course);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(courseRepository.findByName(course.getName())).thenReturn(Optional.of(course));
        try{
            enrollmentService.enrollUser(user.getEmail(), course.getName());
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_04.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testEnrollUserTimeConflict(){
        User user = setupUser();
        Course course = setupCourse();
        Course course2 = setupCourse2();
        user.getCourseList().add(course);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(courseRepository.findByName(course2.getName())).thenReturn(Optional.of(course2));
        try{
            enrollmentService.enrollUser(user.getEmail(), course2.getName());
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_03.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testEnrollUserCourseCapacity(){
        User user = setupUser();
        Course course = setupCourse();
        course.setCapacity(0);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(courseRepository.findByName(course.getName())).thenReturn(Optional.of(course));
        try{
            enrollmentService.enrollUser(user.getEmail(), course.getName());
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_02.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testUnenrollUser(){
        User user = setupUser();
        Course course = setupCourse();
        UserCourse expectedUserCourse = setupUserCourse(user, course);
        when(enrollmentRepository.findByUserEmailAndCourseName(user.getEmail(), course.getName()))
                .thenReturn(Optional.of(expectedUserCourse));
        String response = enrollmentService.unenrollUser(user.getEmail(), course.getName());
        verify(enrollmentRepository, times(1)).delete(expectedUserCourse);
        assertEquals("User " + user.getEmail() + " has been unenrolled from course " + course.getName(), response);
    }

    @Test
    public void testUnenrollUserEnrollmentNotFound(){
        User user = setupUser();
        Course course = setupCourse();
        when(enrollmentRepository.findByUserEmailAndCourseName(user.getEmail(), course.getName()))
                .thenReturn(Optional.empty());
        try{
            enrollmentService.unenrollUser(user.getEmail(), course.getName());
        }catch (BusinessValidationException e){
            assertEquals(EnrollmentErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testGetEnrolledUserCourses(){
        User user = setupUser();
        Course course = setupCourse();
        user.getCourseList().add(course);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        List<Course> actualUserCourses = enrollmentService.getEnrolledUserCourses(user.getId());
        assertEquals(user.getCourseList(), actualUserCourses);
        assertEquals(1, actualUserCourses.size());
    }

    @Test
    public void testGetEnrolledUserCoursesNotFound(){
        User user = setupUser();
        Course course = setupCourse();
        user.getCourseList().add(course);
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        try{
            enrollmentService.getEnrolledUserCourses(user.getId());
        }catch (BusinessValidationException e){
            assertEquals(UserErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }
}
