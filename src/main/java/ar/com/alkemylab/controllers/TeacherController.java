package ar.com.alkemylab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ar.com.alkemylab.entities.Teacher;
import ar.com.alkemylab.models.request.ModifTeacherRequest;
import ar.com.alkemylab.models.request.StatusRequest;
import ar.com.alkemylab.models.request.TeacherRequest;
import ar.com.alkemylab.models.responses.GenericResponse;
import ar.com.alkemylab.services.SubjectService;
import ar.com.alkemylab.services.TeacherService;

@RestController
public class TeacherController {
    @Autowired
    TeacherService tService;

    @PostMapping("/auth/teachers")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    public ResponseEntity<GenericResponse> createTeacher(@RequestBody TeacherRequest tRequest) {

        GenericResponse r = new GenericResponse();
        Teacher createdTeacher = new Teacher();
        createdTeacher = tService.createTeacher(tRequest);
        if (createdTeacher == null) {
            return ResponseEntity.badRequest().build();
        } else {

            r.isOk = true;
            r.message = "Profesor creado con exito";
            r.id = createdTeacher.getId();

            return ResponseEntity.ok(r);
        }
    }

    @GetMapping("/teachers/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<Teacher> findTeacherById(@PathVariable Integer id) {
        Teacher teacher = tService.findById(id);
        if (teacher == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(teacher);
    }

    @PutMapping("/teachers/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<GenericResponse> modifTeacher(@PathVariable Integer id,
            @RequestBody ModifTeacherRequest modifTeacherReq) {
        GenericResponse r = new GenericResponse();
        Boolean createdTeacher = tService.updateTeacher(modifTeacherReq, id);

        if (createdTeacher) {

            r.isOk = true;
            r.message = "Profesor Modificado exitosamente";
            r.id = id;
            return ResponseEntity.ok(r);
        } else {

            return ResponseEntity.notFound().build();

        }

    }

    @PutMapping("/subject/{id}/teacher")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<GenericResponse> assignTeacherToSubject(@PathVariable Integer id,
            @RequestBody StatusRequest statusReq) {

        GenericResponse r = new GenericResponse();
        boolean teacherAssigned = tService.assignTeacher(statusReq, id);

        if (teacherAssigned) {
            r.id = id;
            r.isOk = true;
            r.message = "El docente se asigno a la materia exitosamente";
            return ResponseEntity.ok(r);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/teacher/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<GenericResponse> changeTeacherStatus(@PathVariable Integer id, @RequestBody StatusRequest statReq){
        GenericResponse r = new GenericResponse();

        Boolean statusChanged = tService.changeTeacherStatus(id, statReq);
        if(statusChanged){
            r.id = id;
            r.isOk = true;
            r.message = "El status del docente fue modificado exitosamente";
            return ResponseEntity.ok(r);
        }else{
            return ResponseEntity.notFound().build();
        }

        

    }

}
