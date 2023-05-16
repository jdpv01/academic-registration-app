package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.enums.CourseErrorCode;
import com.perficient.academicregistrationapp.persistance.model.Course;
import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.persistance.repository.CourseRepository;
import com.perficient.academicregistrationapp.service.impl.CourseServiceImpl;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    private CourseService courseService;

    private CourseRepository courseRepository;

    @BeforeEach
    public void resetMocks(){
        courseRepository = mock(CourseRepository.class);
        courseService = new CourseServiceImpl(courseRepository);
    }

    public Course setupCourseValid(){
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
                .capacity(0)
                .subjectList(Arrays.asList(subject1, subject2)).build();
    }

    public Course setupCourseInvalidDates(){
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
                .startDate(LocalDate.of(2023, 5, 16))
                .endDate(LocalDate.of(2023,5,16))
                .capacity(0)
                .subjectList(Arrays.asList(subject1, subject2)).build();
    }

    public Course setupCourseInvalidTimes(){
        Subject subject1 = Subject.builder()
                .id(UUID.randomUUID())
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(16, 0))
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
                .capacity(0)
                .subjectList(Arrays.asList(subject1, subject2)).build();
    }

    @Test
    public void testCreateCourse(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.save(expectedCourse)).thenReturn(expectedCourse);
        Course actualCourse = courseService.createCourse(expectedCourse);
        verify(courseRepository, times(1)).save(expectedCourse);
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void testCreateCourseNameRegisteredAlready(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.findByName(expectedCourse.getName())).thenReturn(Optional.of(expectedCourse));
        try{
            courseService.createCourse(expectedCourse);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_02.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testCreateCourseInvalidDates(){
        Course expectedCourse = setupCourseInvalidDates();
        try{
            courseService.createCourse(expectedCourse);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_03.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testCreateCourseInvalidTimes(){
        Course expectedCourse = setupCourseInvalidTimes();
        try{
            courseService.createCourse(expectedCourse);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_04.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testGetCourse(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.findById(expectedCourse.getId())).thenReturn(Optional.of(expectedCourse));
        Course actualCourse = courseService.getCourse(expectedCourse.getId());
        verify(courseRepository, times(1)).findById(expectedCourse.getId());
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void testGetCourseNotFound(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.findById(expectedCourse.getId())).thenReturn(Optional.empty());
        try{
            courseService.getCourse(expectedCourse.getId());
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testGetCourses(){
        List<Course> expectedCourseList = new ArrayList<>();
        expectedCourseList.add(setupCourseValid());
        when(courseRepository.findAll()).thenReturn(expectedCourseList);
        List<Course> actualCourseList = courseService.getCourses();
        verify(courseRepository, times(1)).findAll();
        assertEquals(expectedCourseList.size(), actualCourseList.size());
    }

    @Test
    public void testGetAllCoursesPaged(){
        List<Course> expectedCourseList = new ArrayList<>();
        expectedCourseList.add(setupCourseValid());
        when(courseRepository.findAllCoursesPaged(1, 1)).thenReturn(expectedCourseList);
        List<Course> actualCourseList = courseService.getAllCoursesPaged(1, 1);
        verify(courseRepository, times(1)).findAllCoursesPaged(1, 1);
        assertEquals(expectedCourseList.size(), actualCourseList.size());
    }

    @Test
    public void testGetAllCoursesPagedBadArguments(){
        List<Course> expectedCourseList = new ArrayList<>();
        expectedCourseList.add(setupCourseValid());
        when(courseRepository.findAllCoursesPaged(-1, 1)).thenReturn(expectedCourseList);
        try{
            courseService.getAllCoursesPaged(-1, 1);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_05.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testGetCoursesPage(){
        List<Course> expectedCourseList = new ArrayList<>();
        expectedCourseList.add(setupCourseValid());
        Pageable pageRequest = PageRequest.of(0, 1);
        when(courseRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(expectedCourseList));
        Page<Course> actualCoursePage = courseService.getCoursesPage(0, 1);
        verify(courseRepository, times(1)).findAll(pageRequest);
        assertEquals(expectedCourseList.size(), actualCoursePage.stream().count());
    }

    @Test
    public void testGetCoursesPageBadArguments(){
        List<Course> expectedCourseList = new ArrayList<>();
        expectedCourseList.add(setupCourseValid());
        Pageable pageRequest = PageRequest.of(0, 1);
        when(courseRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(expectedCourseList));
        try{
            courseService.getCoursesPage(-1, 1);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_06.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testUpdateCourse(){
        Course expectedCourse = setupCourseValid();
        expectedCourse.setName("Matemáticas2");
        when(courseRepository.findById(expectedCourse.getId())).thenReturn(Optional.of(expectedCourse));
        when(courseRepository.save(expectedCourse)).thenReturn(expectedCourse);
        Course actualCourse = courseService.updateCourse(expectedCourse);
        verify(courseRepository, times(1)).findById(expectedCourse.getId());
        verify(courseRepository, times(1)).save(expectedCourse);
        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void testUpdateCourseNotFound(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.findById(expectedCourse.getId())).thenReturn(Optional.empty());
        try{
            courseService.updateCourse(expectedCourse);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testUpdateCourseNameRegisteredAlready(){
        Course course1 = setupCourseValid();
        Course course2 = setupCourseValid();
        when(courseRepository.findById(course1.getId())).thenReturn(Optional.of(course1));
        when(courseRepository.findByName(course1.getName())).thenReturn(Optional.of(course2));
        try{
            courseService.updateCourse(course1);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_02.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testDeleteCourse(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.findById(expectedCourse.getId())).thenReturn(Optional.of(expectedCourse));
        Boolean deleted = courseService.deleteCourse(expectedCourse.getId());
        verify(courseRepository, times(1)).findById(expectedCourse.getId());
        verify(courseRepository, times(1)).delete(expectedCourse);
        assertEquals(true, deleted);
    }

    @Test
    public void testDeleteCourseNotFound(){
        Course expectedCourse = setupCourseValid();
        when(courseRepository.findById(expectedCourse.getId())).thenReturn(Optional.empty());
        try{
            courseService.deleteCourse(expectedCourse.getId());
            fail();
        }catch (BusinessValidationException e){
            assertEquals(CourseErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }
}
