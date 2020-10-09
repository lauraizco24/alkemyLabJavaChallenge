package ar.com.alkemylab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemylab.entities.Enrollment;
import ar.com.alkemylab.entities.Student;
import ar.com.alkemylab.entities.Subject;
import ar.com.alkemylab.repos.EnrollmentRepo;
import ar.com.alkemylab.services.base.GenericService;

@Service
public class EnrollmentService extends GenericService<Enrollment> {

    @Autowired
    StudentService studentService;
    @Autowired
    SubjectService subjectService;

    private EnrollmentRepo repo() {
        return (EnrollmentRepo) repo;
    }

    public Enrollment makeEnrollment(Integer studentId, Integer subjectId) {

        Student student = studentService.findById(studentId);
        Subject subject = subjectService.findById(subjectId);
        Boolean isEnrolled = findBySubjectandUser(subjectId, studentId);
        Boolean isFull = subjectIsFull(subjectId);
        if (isEnrolled && isFull) {
            return null;
        } else {
            Enrollment enroll = new Enrollment();
            enroll.setStudent(student);
            enroll.setSubject(subject);
            create(enroll);
            return enroll;
        }

    }

    public Boolean findBySubjectandUser(Integer subjectId, Integer studentId) {

        Student student = studentService.findById(studentId);

        Boolean iEnrolled = student.getEnrollments().stream().filter(e -> e.getSubject().getId().equals(subjectId))
                .findFirst().isPresent();
        if (iEnrolled)
            return true;
        else
            return false;

    }

    public boolean subjectIsFull(Integer subjectId){

       int totalEnrollments = subjectService.findById(subjectId).getInscripciones().size();
       int maxSize = subjectService.findById(subjectId).getMaxStudents();

       
       return (maxSize - totalEnrollments) <= 0;
    }

}
