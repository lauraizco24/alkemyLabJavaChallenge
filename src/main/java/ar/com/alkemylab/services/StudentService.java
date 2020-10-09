package ar.com.alkemylab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemylab.entities.Student;
import ar.com.alkemylab.entities.Users;
import ar.com.alkemylab.models.request.RegistrationRequest;
import ar.com.alkemylab.repos.StudentRepo;
import ar.com.alkemylab.services.base.GenericService;

@Service
public class StudentService extends GenericService<Student> {

    private StudentRepo repo() {
        return (StudentRepo) repo;
    }

    public Student createStudent(RegistrationRequest sRequest) {

        if (findByDni(sRequest.dni) == null) {
            Student studentC = new Student();
            Users user = new Users();
            studentC.setDni(sRequest.dni);
            studentC.setName(sRequest.name);
            studentC.setLastName(sRequest.lastName);
            studentC.setFile("student" + sRequest.dni + sRequest.lastName);
            user.setName(sRequest.name);
            user.setLastName(sRequest.lastName);
            user.setUsername(sRequest.dni.toString());
            user.setPassword(studentC.getFile());
            user.setUserTypeId(sRequest.userType);
            studentC.setUser(user);
            create(studentC);
            return studentC;
        } else {
            return null;
        }

    }

    public Student findByDni(Integer dni) {
        return repo().findByDni(dni);
    }

}
