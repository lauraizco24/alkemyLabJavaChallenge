package ar.com.alkemylab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemylab.entities.Enrollment;
import ar.com.alkemylab.entities.Student;
import ar.com.alkemylab.models.request.EnrollmentRequest;
import ar.com.alkemylab.models.request.RegistrationRequest;

import ar.com.alkemylab.models.responses.GenericResponse;
import ar.com.alkemylab.models.responses.RegisterResponse;
import ar.com.alkemylab.services.StudentService;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<RegisterResponse> createStudent(@RequestBody RegistrationRequest sRequest) {

        RegisterResponse r = new RegisterResponse();
        Student createdStudent = new Student();
        createdStudent = studentService.createStudent(sRequest);
        if (createdStudent == null) {
            return ResponseEntity.badRequest().build();
        } else {

            r.message = "El estudiante fue registrado exitosamente";
            r.password = createdStudent.getFile();
            r.userName =createdStudent.getDni();
            r.id = createdStudent.getId();

            return ResponseEntity.ok(r);
        }
    }

    @GetMapping("/students")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> allStudents = studentService.listAll();
        if (allStudents == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(allStudents);

    }

    @GetMapping("/students/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<Student> findStudentById(@PathVariable Integer id) {
        Student student = studentService.findById(id);
        if (student == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(student);
    }

   
}
