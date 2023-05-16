package com.perficient.academicregistrationapp.service.impl;

import com.perficient.academicregistrationapp.enums.CourseErrorCode;
import com.perficient.academicregistrationapp.persistance.model.Course;
import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.persistance.repository.CourseRepository;
import com.perficient.academicregistrationapp.service.CourseService;
import com.perficient.academicregistrationapp.web.error.BusinessErrorDetails;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Course createCourse(Course course) {
        validateLocalDates(course.getStartDate(), course.getEndDate());
        validateLocalTimes(course.getSubjectList());
        if(courseRepository.findByName(course.getName()).isEmpty()) {
            course.getSubjectList().forEach(subject -> subject.setCourse(course));
            return courseRepository.save(course);
        }
        throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(CourseErrorCode.CODE_02,
                CourseErrorCode.CODE_02.getMessage()));
    }

    private void validateLocalDates(LocalDate startDate, LocalDate endDate){
        if(startDate.isAfter(endDate) || DAYS.between(startDate, endDate) < 7){
            throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(CourseErrorCode.CODE_03,
                    CourseErrorCode.CODE_03.getMessage()));
        }
    }

    private void validateLocalTimes(List<Subject> subjectList) {
        subjectList.forEach(subject -> {
            if(subject.getStartTime().isAfter(subject.getEndTime()) ||
                    MINUTES.between(subject.getStartTime(), subject.getEndTime()) < 60) {
                throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(CourseErrorCode.CODE_04,
                        CourseErrorCode.CODE_04.getMessage()));
            }
        });
    }

    @Override
    public Course getCourse(UUID id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(CourseErrorCode.CODE_01,
                        CourseErrorCode.CODE_01.getMessage())));
    }

    @Override
    public List<Course> getCourses() {
        return new ArrayList<>(courseRepository.findAll());
    }

    @Override
    public List<Course> getAllCoursesPaged(int limit, int offset) {
        if(limit >= 0 && offset >= 0)
            return courseRepository.findAllCoursesPaged(limit, offset);
        throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(CourseErrorCode.CODE_05,
                CourseErrorCode.CODE_05.getMessage()));
    }

    @Override
    public Page<Course> getCoursesPage(int pageNumber, int pageSize) {
        if(pageNumber >= 0 && pageSize >= 0){
            Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
            return courseRepository.findAll(pageRequest);
        }
        throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(CourseErrorCode.CODE_06,
                CourseErrorCode.CODE_06.getMessage()));
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {

        validateLocalDates(course.getStartDate(), course.getEndDate());
        validateLocalTimes(course.getSubjectList());

        courseRepository.findById(course.getId()).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(CourseErrorCode.CODE_01,
                        CourseErrorCode.CODE_01.getMessage())));

        Optional<Course> optionalCourse = courseRepository.findByName(course.getName());
        if(optionalCourse.isPresent() && optionalCourse.get().getId()!=course.getId()){
            throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(CourseErrorCode.CODE_02,
                    CourseErrorCode.CODE_02.getMessage()));
        }
        course.getSubjectList().forEach(subject -> subject.setCourse(course));
        return courseRepository.save(course);
    }

    @Override
    public boolean deleteCourse(UUID id) {
        Course course = courseRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(CourseErrorCode.CODE_01,
                        CourseErrorCode.CODE_01.getMessage())));
        courseRepository.delete(course);
        return true;
    }
}
