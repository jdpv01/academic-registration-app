package com.perficient.academicregistrationapp.service.impl;

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
import com.perficient.academicregistrationapp.service.EnrollmentService;
import com.perficient.academicregistrationapp.web.error.BusinessErrorDetails;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserCourse enrollUser(String userEmail, String courseName) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(UserErrorCode.CODE_01,
                        UserErrorCode.CODE_01.getMessage())));
        Course course = courseRepository.findByName(courseName).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(CourseErrorCode.CODE_01,
                        CourseErrorCode.CODE_01.getMessage())));

        validateAlreadyEnrolled(user, course);
        validateTimeConflict(user.getCourseList(), course);
        validateCourseCapacity(course);

        UserCourse enrollment = new UserCourse();
        enrollment.setCourse(course);
        enrollment.setUser(user);
        enrollment.setRegistrationDate(LocalDateTime.now());
        course.setCapacity(course.getCapacity() - 1);
        return enrollmentRepository.save(enrollment);
    }

    private void validateAlreadyEnrolled(User user, Course course){
        boolean alreadyEnrolled = user.getCourseList().stream().anyMatch(c -> c.getName().equals(course.getName()));
        if(alreadyEnrolled)
            throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(UserErrorCode.CODE_04,
                    UserErrorCode.CODE_04.getMessage()));
    }

    private void validateTimeConflict(List<Course> userCourseList, Course course) {
        boolean timeConflict = userCourseList.stream()
                .flatMap(c -> c.getSubjectList().stream())
                .anyMatch(subject1 -> course.getSubjectList().stream()
                        .anyMatch(subject2 -> haveTimeConflict(subject1, subject2)));
        if(timeConflict)
            throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(UserErrorCode.CODE_03,
                    UserErrorCode.CODE_03.getMessage()));
    }

    private boolean haveTimeConflict(Subject subject1, Subject subject2) {
        String[] subject1WeekDays = subject1.getWeekdays().split("-");
        String[] subject2WeekDays = subject2.getWeekdays().split("-");
        if (Arrays.stream(subject1WeekDays).noneMatch(Arrays.asList(subject2WeekDays)::contains)) {
            return false;
        }
        LocalTime startTime1 = subject1.getStartTime();
        LocalTime endTime1 = subject1.getEndTime();
        LocalTime startTime2 = subject2.getStartTime();
        LocalTime endTime2 = subject2.getEndTime();
        return (startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2)) ||
                (startTime2.isBefore(endTime1) && endTime2.isAfter(startTime1));
    }

    private void validateCourseCapacity(Course course) {
        if(course.getCapacity() == 0)
            throw new BusinessValidationException(HttpStatus.CONFLICT, new BusinessErrorDetails(UserErrorCode.CODE_02,
                    UserErrorCode.CODE_02.getMessage()));
    }

    @Override
    @Transactional
    public String unenrollUser(String userEmail, String courseName) {
        UserCourse userCourse = enrollmentRepository.findByUserEmailAndCourseName(userEmail, courseName).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(EnrollmentErrorCode.CODE_01,
                        EnrollmentErrorCode.CODE_01.getMessage())));
        User user = userCourse.getUser();
        Course course = userCourse.getCourse();
        course.setCapacity(course.getCapacity() + 1);
        enrollmentRepository.delete(userCourse);
        return "User " + user.getEmail() + " has been unenrolled from course " + course.getName();
    }

    @Override
    public List<Course> getEnrolledUserCourses(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new BusinessValidationException(HttpStatus.NOT_FOUND, new BusinessErrorDetails(UserErrorCode.CODE_01,
                        UserErrorCode.CODE_01.getMessage())));
        return user.getCourseList();
    }
}
