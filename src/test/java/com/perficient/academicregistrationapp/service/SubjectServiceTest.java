package com.perficient.academicregistrationapp.service;

import com.perficient.academicregistrationapp.enums.SubjectErrorCode;
import com.perficient.academicregistrationapp.persistance.model.Subject;
import com.perficient.academicregistrationapp.persistance.repository.SubjectRepository;
import com.perficient.academicregistrationapp.service.impl.SubjectServiceImpl;
import com.perficient.academicregistrationapp.web.error.exception.BusinessValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @BeforeEach
    public void resetMocks(){
        Mockito.reset(subjectRepository);
    }

    public Subject setupSubject(){
        return Subject.builder()
                .id(UUID.randomUUID())
                .name("Cálculo de varias variables")
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(16, 0))
                .weekdays("LU-MI-VI").build();
    }

    @Test
    public void testCreateSubject(){
        Subject expectedSubject = setupSubject();
        when(subjectRepository.save(expectedSubject)).thenReturn(expectedSubject);
        Subject actualSubject = subjectService.createSubject(expectedSubject);
        verify(subjectRepository, times(1)).save(expectedSubject);
        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void testGetSubject(){
        Subject expectedSubject = setupSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.of(expectedSubject));
        Subject actualSubject = subjectService.getSubject(expectedSubject.getId());
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void testGetSubjectNotFound(){
        Subject expectedSubject = setupSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.empty());
        try{
            subjectService.getSubject(expectedSubject.getId());
            fail();
        }catch (BusinessValidationException e){
            assertEquals(SubjectErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testGetSubjects(){
        List<Subject> expectedSubjectList = new ArrayList<>();
        expectedSubjectList.add(setupSubject());
        when(subjectRepository.findAll()).thenReturn(expectedSubjectList);
        List<Subject> actualSubjectList = subjectService.getSubjects();
        verify(subjectRepository, times(1)).findAll();
        assertEquals(expectedSubjectList.size(), actualSubjectList.size());
    }

    @Test
    public void testUpdateSubject(){
        Subject expectedSubject = setupSubject();
        expectedSubject.setName("Cálculo de varias variables2");
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.of(expectedSubject));
        when(subjectRepository.save(expectedSubject)).thenReturn(expectedSubject);
        Subject actualSubject = subjectService.updateSubject(expectedSubject);
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
        verify(subjectRepository, times(1)).save(expectedSubject);
        assertEquals(expectedSubject, actualSubject);
    }

    @Test
    public void testUpdateSubjectNotFound(){
        Subject expectedSubject = setupSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.empty());
        try{
            subjectService.updateSubject(expectedSubject);
            fail();
        }catch (BusinessValidationException e){
            assertEquals(SubjectErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }

    @Test
    public void testDeleteSubject(){
        Subject expectedSubject = setupSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.of(expectedSubject));
        Boolean deleted = subjectService.deleteSubject(expectedSubject.getId());
        verify(subjectRepository, times(1)).findById(expectedSubject.getId());
        verify(subjectRepository, times(1)).delete(expectedSubject);
        assertEquals(true, deleted);
    }

    @Test
    public void testDeleteSubjectNotFound(){
        Subject expectedSubject = setupSubject();
        when(subjectRepository.findById(expectedSubject.getId())).thenReturn(Optional.empty());
        try{
            subjectService.deleteSubject(expectedSubject.getId());
            fail();
        }catch (BusinessValidationException e){
            assertEquals(SubjectErrorCode.CODE_01.getMessage(), e.getBusinessErrorDetails().getMessage());
        }
    }
}
