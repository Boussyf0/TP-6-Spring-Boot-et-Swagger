package com.example.student_management.controller;

import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("students")
@Tag(name = "Gestion des Étudiants", description = "API REST pour la gestion des étudiants")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Endpoint pour enregistrer un nouvel étudiant
     * @param student l'étudiant à enregistrer
     * @return l'étudiant enregistré avec le code HTTP 201 Created
     */
    @Operation(summary = "Créer ou modifier un étudiant",
               description = "Enregistre un nouvel étudiant dans la base de données ou met à jour un étudiant existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/save")
    public ResponseEntity<Student> save(
            @Parameter(description = "L'étudiant à enregistrer")
            @RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    /**
     * Endpoint pour supprimer un étudiant par son ID
     * @param id l'identifiant de l'étudiant à supprimer
     * @return 204 No Content si supprimé, 404 Not Found sinon
     */
    @Operation(summary = "Supprimer un étudiant",
               description = "Supprime un étudiant de la base de données selon son identifiant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant de l'étudiant à supprimer", required = true)
            @PathVariable("id") int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint pour récupérer tous les étudiants
     * @return la liste de tous les étudiants avec le code HTTP 200 OK
     */
    @Operation(summary = "Récupérer tous les étudiants",
               description = "Retourne la liste complète de tous les étudiants enregistrés dans la base de données")
    @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès")
    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Endpoint pour compter le nombre total d'étudiants
     * @return le nombre d'étudiants avec le code HTTP 200 OK
     */
    @Operation(summary = "Compter les étudiants",
               description = "Retourne le nombre total d'étudiants présents dans la base de données")
    @ApiResponse(responseCode = "200", description = "Nombre d'étudiants récupéré avec succès")
    @GetMapping("/count")
    public ResponseEntity<Long> countStudent() {
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * Endpoint pour obtenir le nombre d'étudiants par année de naissance
     * @return collection d'objets [année, nombre] avec le code HTTP 200 OK
     */
    @Operation(summary = "Statistiques par année de naissance",
               description = "Retourne le nombre d'étudiants regroupés par année de naissance")
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    @GetMapping("/byYear")
    public ResponseEntity<Collection<?>> findByYear() {
        Collection<?> studentsByYear = studentService.findNbrStudentByYear();
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK);
    }
}