package ar.com.alkemylab.services;

import org.hibernate.dialect.SQLServer2005Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemylab.entities.Subject;
import ar.com.alkemylab.entities.Teacher;
import ar.com.alkemylab.models.request.ModifTeacherRequest;
import ar.com.alkemylab.models.request.StatusRequest;
import ar.com.alkemylab.models.request.TeacherRequest;
import ar.com.alkemylab.repos.TeacherRepo;
import ar.com.alkemylab.services.base.GenericService;
import ch.qos.logback.core.joran.conditional.ElseAction;

@Service
public class TeacherService extends GenericService<Teacher> {
    @Autowired
    SubjectService subjectService;

    private TeacherRepo repo() {
        return (TeacherRepo) repo;
    }

    public Teacher createTeacher(TeacherRequest tRequest) {
        Teacher teacherC = new Teacher();
        teacherC.setDni(tRequest.dni);
        teacherC.setName(tRequest.name);
        teacherC.setLastName(tRequest.lastName);
        teacherC.setStatusId(0);
        create(teacherC);
        return teacherC;
    }

    public Boolean updateTeacher(ModifTeacherRequest mTR, Integer id) {
        Teacher teacher = findById(id);
        teacher.setDni(mTR.dni);
        teacher.setName(mTR.name);
        teacher.setLastName(mTR.lastName);
        update(teacher);
        return true;
    }

    public Boolean assignTeacher(StatusRequest sReq, Integer id) {

        Subject sub = subjectService.findById(id);
        if (sub != null) {
            sub.setTeacher(findById(sReq.teacherId));
            subjectService.update(sub);
            return true;
        } else {
            return false;
        }
    }

    public Boolean changeTeacherStatus(Integer id, StatusRequest sReq) {

        Teacher teacher = findById(id);
        if (teacher != null) {
            teacher.setStatusId(sReq.statusId);
            update(teacher);
            return true;
        } else {
            return false;
        }

    }

}
