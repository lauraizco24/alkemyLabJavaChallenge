package ar.com.alkemylab.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemylab.entities.Subject;
import ar.com.alkemylab.models.request.SubjectRequest;
import ar.com.alkemylab.repos.SubjectRepo;
import ar.com.alkemylab.services.base.GenericService;

@Service
public class SubjectService extends GenericService<Subject> {

    @Autowired
    TeacherService teacherService;

    private SubjectRepo repo() {
        return (SubjectRepo) repo;
    }

    public Subject createSubject(Integer teacherId, String subjectName, String schedule, Integer maxStudents) {
        Subject subjectC = new Subject();
        if (repo().existsBySubjectName(subjectName)) {
            return null;
        } else {
            subjectC.setSubjectName(subjectName);
            subjectC.setSchedule(schedule);
            subjectC.setMaxStudents(maxStudents);
            subjectC.setTeacher(teacherService.findById(teacherId));
            create(subjectC);
            return subjectC;
        }

    }

    public Boolean updateSubject(Integer maxStudents, String schedule, String subjectName, Integer subjectId) {

        Subject sub = findById(subjectId);
        if (sub != null) {
            sub.setMaxStudents(maxStudents);
            sub.setSchedule(schedule);
            sub.setSubjectName(subjectName);
            update(sub);
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteSubjectById(Integer id) {

        if (this.deleteById(id))
            return true;
        else
            return false;
    }

    public List<Subject> getAllSubjects(){

       return repo().findByOrderBySubjectNameAsc();

        
    }

    public boolean isFullTheSubject(Integer subjectId) {
        Subject sub = findById(subjectId);
        int available = sub.getMaxStudents()- sub.getInscripciones().size() ;
        if (available > 0)
            return false;
        else
            return true;
    }

}
