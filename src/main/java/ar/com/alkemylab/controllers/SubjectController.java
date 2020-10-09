package ar.com.alkemylab.controllers;

import java.util.ArrayList;
import java.util.List;

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

import ar.com.alkemylab.entities.Subject;
import ar.com.alkemylab.models.request.SubjectModifRequest;
import ar.com.alkemylab.models.request.SubjectRequest;
import ar.com.alkemylab.models.responses.GenericResponse;
import ar.com.alkemylab.models.responses.SubjectResponse;
import ar.com.alkemylab.services.SubjectService;

@RestController
public class SubjectController {

    @Autowired
    SubjectService subService;

    @PostMapping("/subject")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    public ResponseEntity<GenericResponse> createSubject(@RequestBody SubjectRequest sR) {

        Subject createdSubject = subService.createSubject(sR.teacherId, sR.subjectName, sR.schedule, sR.maxStudents);

        GenericResponse gR = new GenericResponse();
        if (createdSubject == null) {
            gR.isOk = false;
            gR.message = "La materia no pudo ser creada";
            return ResponseEntity.badRequest().body(gR);
        } else {
            gR.isOk = true;
            gR.message = "La materia se guardo con exito";
            gR.id = createdSubject.getId();
            return ResponseEntity.ok(gR);
        }

    }

    @GetMapping("/subjects")
    ResponseEntity<List<SubjectResponse>> getSubjects() {

        
      
       List<SubjectResponse> subjectList = new ArrayList<>(); 

        if ( subService.getAllSubjects().size() > 0) {
            for (Subject subject : subService.getAllSubjects()) {
                SubjectResponse r = new SubjectResponse();
                r.subjectName = subject.getSubjectName();
                r.schedule = subject.getSchedule();
                r.freeSpots = subject.getMaxStudents() - subject.getInscripciones().size();
                subjectList.add(r);
            }
            
            return ResponseEntity.ok(subjectList);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @PutMapping("/subject/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<GenericResponse> modifySubject(@PathVariable Integer id,
            @RequestBody SubjectModifRequest subModReq) {

        GenericResponse r = new GenericResponse();
        boolean subjectModified = subService.updateSubject(subModReq.maxStudents, subModReq.schedule,
                subModReq.subjectName, id);

        if (subjectModified) {
            r.id = id;
            r.isOk = true;
            r.message = "Se modifico la materia exitosamente";
            return ResponseEntity.ok(r);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/subject/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_ADMIN')")
    ResponseEntity<GenericResponse> deleteSubject(@PathVariable Integer id) {
        GenericResponse r = new GenericResponse();

        Boolean subjectDeleted = subService.deleteSubjectById(id);
        if (subjectDeleted) {
            r.id = id;
            r.isOk = true;
            r.message = "El status del docente fue modificado exitosamente";
            return ResponseEntity.ok(r);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
