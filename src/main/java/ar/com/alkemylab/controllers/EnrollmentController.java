package ar.com.alkemylab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemylab.entities.Enrollment;
import ar.com.alkemylab.models.request.EnrollmentRequest;
import ar.com.alkemylab.models.responses.GenericResponse;
import ar.com.alkemylab.services.EnrollmentService;

@RestController
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollService;
    
    @PostMapping("/students/{id}/enrollment")
    public ResponseEntity<GenericResponse> enroll(@PathVariable Integer id, @RequestBody EnrollmentRequest eR) {

        Enrollment createdEnrollment = enrollService.makeEnrollment(id, eR.subjectId);
        GenericResponse gR = new GenericResponse();
        if (createdEnrollment == null) {
            gR.isOk = false;
            gR.message = "La inscripcion no pudo ser realizada";
            return ResponseEntity.badRequest().body(gR);
        } else {
            gR.isOk = true;
            gR.message = "La inscripcion se realizo con exito";
            gR.id = createdEnrollment.getEnrollmentId();
            return ResponseEntity.ok(gR);
        }

    }
}
