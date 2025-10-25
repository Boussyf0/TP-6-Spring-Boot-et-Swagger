package com.example.student_management;

import com.example.student_management.controller.StudentController;
import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Tests unitaires pour StudentController
 * Utilise JUnit 5 et Mockito pour tester les endpoints REST
 */
@SpringBootTest
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    /**
     * Test de l'endpoint POST /students/save
     * Vérifie que l'étudiant est correctement enregistré avec le statut 201 Created
     */
    @Test
    void testSaveStudent() {
        // Arrange - Préparation des données de test
        Student student = new Student();
        student.setId(1);
        student.setNom("Boussyf");
        student.setPrenom("Abderrahim");
        student.setDateNaissance(new Date());

        // Simulation du comportement du service
        when(studentService.save(any(Student.class))).thenReturn(student);

        // Act - Exécution de la méthode à tester
        ResponseEntity<Student> response = studentController.save(student);

        // Assert - Vérification des résultats
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Boussyf", response.getBody().getNom());
        assertEquals("Abderrahim", response.getBody().getPrenom());
    }

    /**
     * Test de l'endpoint DELETE /students/delete/{id}
     * Vérifie qu'un étudiant existant est supprimé avec le statut 204 No Content
     */
    @Test
    void testDeleteStudent() {
        // Arrange - Simulation d'une suppression réussie
        when(studentService.delete(1)).thenReturn(true);

        // Act - Exécution de la suppression
        ResponseEntity<Void> response = studentController.delete(1);

        // Assert - Vérification du statut HTTP
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    /**
     * Test de l'endpoint DELETE /students/delete/{id}
     * Vérifie qu'un étudiant inexistant renvoie 404 Not Found
     */
    @Test
    void testDeleteStudentNotFound() {
        // Arrange - Simulation d'une suppression échouée (étudiant inexistant)
        when(studentService.delete(999)).thenReturn(false);

        // Act - Tentative de suppression
        ResponseEntity<Void> response = studentController.delete(999);

        // Assert - Vérification du statut HTTP
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test de l'endpoint GET /students/all
     * Vérifie que la liste de tous les étudiants est renvoyée correctement
     */
    @Test
    void testFindAllStudents() {
        // Arrange - Création de données de test
        Student student1 = new Student();
        student1.setId(1);
        student1.setNom("Alami");
        student1.setPrenom("Sara");

        Student student2 = new Student();
        student2.setId(2);
        student2.setNom("El Amrani");
        student2.setPrenom("Mohammed");

        // Simulation du service retournant une liste de 2 étudiants
        when(studentService.findAll()).thenReturn(Arrays.asList(student1, student2));

        // Act - Récupération de la liste
        ResponseEntity<List<Student>> response = studentController.findAll();

        // Assert - Vérifications
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    /**
     * Test de l'endpoint GET /students/count
     * Vérifie que le nombre total d'étudiants est correct
     */
    @Test
    void testCountStudents() {
        // Arrange - Simulation d'un total de 10 étudiants
        when(studentService.countStudents()).thenReturn(10L);

        // Act - Récupération du compteur
        ResponseEntity<Long> response = studentController.countStudent();

        // Assert - Vérifications
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody());
    }

    /**
     * Test de l'endpoint GET /students/byYear
     * Vérifie que les statistiques par année de naissance sont renvoyées correctement
     */
    @Test
    void testFindByYear() {
        // Arrange - Simulation d'une liste vide
        when(studentService.findNbrStudentByYear()).thenReturn(Arrays.asList());

        // Act - Récupération des statistiques
        ResponseEntity<Collection<?>> response = studentController.findByYear();

        // Assert - Vérifications
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    /**
     * Test de l'endpoint GET /students/byYear avec des données
     * Vérifie que les statistiques contiennent des informations
     */
    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void testFindByYearWithData() {
        // Arrange - Simulation de statistiques avec des données
        Object[] stat1 = {2000, 5L}; // 5 étudiants nés en 2000
        Object[] stat2 = {2001, 3L}; // 3 étudiants nés en 2001

        Collection stats = Arrays.asList(stat1, stat2);
        when(studentService.findNbrStudentByYear()).thenReturn(stats);

        // Act - Récupération des statistiques
        ResponseEntity<Collection<?>> response = studentController.findByYear();

        // Assert - Vérifications
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}