# Application de Gestion des Étudiants - Spring Boot

Une application REST complète développée avec **Spring Boot 3.5.7** pour gérer les informations des étudiants avec une base de données MySQL.

## Table des matières
- [Fonctionnalités](#fonctionnalités)
- [Technologies utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Installation et configuration](#installation-et-configuration)
- [Structure du projet](#structure-du-projet)
- [API REST - Endpoints](#api-rest---endpoints)
- [Documentation Swagger](#documentation-swagger)
- [Tests unitaires](#tests-unitaires)
- [Captures d'écran](#captures-décran)
- [Auteur](#auteur)

---

## Fonctionnalités

- CRUD complet des étudiants (Create, Read, Update, Delete)
- Recherche et listage de tous les étudiants
- Comptage du nombre total d'étudiants
- Statistiques des étudiants par année de naissance
- Documentation API interactive avec Swagger/OpenAPI
- Tests unitaires avec JUnit 5 et Mockito
- Validation des données d'entrée
- Gestion des erreurs HTTP appropriées

---

## Technologies utilisées

| Technologie | Version | Description |
|-------------|---------|-------------|
| Java | 17 | Langage de programmation |
| Spring Boot | 3.5.7 | Framework backend |
| Spring Data JPA | 3.5.5 | Persistance des données |
| Hibernate | 6.6.33 | ORM (Object-Relational Mapping) |
| MySQL | 8.0.40 | Base de données relationnelle |
| Maven | 3.x | Gestion des dépendances |
| JUnit 5 | 5.x | Framework de tests unitaires |
| Mockito | 5.x | Framework de mocking pour tests |
| Swagger/OpenAPI | 2.0.2 | Documentation API interactive |
| Spring Boot DevTools | - | Rechargement automatique en développement |

---

## Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **Java 17** ou supérieur ([Télécharger](https://adoptium.net/))
- **Maven 3.6+** ([Télécharger](https://maven.apache.org/download.cgi))
- **MySQL 8.0+** ([Télécharger](https://dev.mysql.com/downloads/mysql/))
- **Un IDE** (IntelliJ IDEA, Eclipse, VS Code)

---

## Installation et configuration

### 1. Cloner le projet

```bash
git clone <repository-url>
cd student-management
```

### 2. Configurer la base de données MySQL

Créez une base de données MySQL :

```sql
CREATE DATABASE studentdb;
```

### 3. Configurer le fichier application.properties

Le fichier se trouve dans `src/main/resources/application.properties` :

```properties
# Port du serveur
server.port=8081

# Configuration de la base de données MySQL
spring.datasource.url=jdbc:mysql://localhost:3307/studentdb?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root

# Configuration JPA/Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto=update
```

> **Note** : Ajustez le port MySQL (`3307` ou `3306`), le nom d'utilisateur et le mot de passe selon votre configuration.

### 4. Compiler et lancer l'application

```bash
# Compiler le projet
./mvnw clean compile

# Lancer l'application
./mvnw spring-boot:run
```

L'application sera accessible sur : **http://localhost:8081**

### 5. Exécuter les tests

```bash
./mvnw test
```

---

## Structure du projet

```
student-management/
├── src/
│   ├── main/
│   │   ├── java/com/example/student_management/
│   │   │   ├── entity/
│   │   │   │   └── Student.java                    # Entité JPA représentant un étudiant
│   │   │   ├── repository/
│   │   │   │   └── StudentRepository.java          # Interface d'accès aux données
│   │   │   ├── service/
│   │   │   │   └── StudentService.java             # Logique métier
│   │   │   ├── controller/
│   │   │   │   └── StudentController.java          # Contrôleur REST
│   │   │   └── StudentManagementApplication.java   # Classe principale
│   │   └── resources/
│   │       └── application.properties               # Configuration
│   └── test/
│       └── java/com/example/student_management/
│           ├── StudentManagementApplicationTests.java
│           └── StudentControllerTest.java           # 7 tests unitaires
├── Screen/                                          # Captures d'écran
├── pom.xml                                          # Dépendances Maven
└── README.md                                        # Documentation
```

---

## API REST - Endpoints

| Méthode HTTP | Endpoint | Description | Code de réponse |
|--------------|----------|-------------|-----------------|
| **POST** | `/students/save` | Créer ou modifier un étudiant | 201 Created |
| **GET** | `/students/all` | Récupérer tous les étudiants | 200 OK |
| **GET** | `/students/count` | Compter le nombre d'étudiants | 200 OK |
| **GET** | `/students/byYear` | Statistiques par année de naissance | 200 OK |
| **DELETE** | `/students/delete/{id}` | Supprimer un étudiant | 204 No Content / 404 Not Found |

### Exemple de requête POST

**Endpoint** : `http://localhost:8081/students/save`

**Body (JSON)** :
```json
{
  "nom": "Boussyf",
  "prenom": "Abderrahim",
  "dateNaissance": "2000-05-15"
}
```

**Réponse (201 Created)** :
```json
{
  "id": 1,
  "nom": "Boussyf",
  "prenom": "Abderrahim",
  "dateNaissance": "2000-05-15"
}
```

---

## Documentation Swagger

L'application intègre **Swagger/OpenAPI** pour une documentation interactive de l'API.

**Accès à l'interface Swagger UI** :
```
http://localhost:8081/swagger-ui/index.html
```

**Accès à la documentation JSON** :
```
http://localhost:8081/v3/api-docs
```

Swagger permet de :
- Visualiser tous les endpoints disponibles
- Tester les API directement depuis le navigateur
- Consulter les schémas de données (modèles)
- Voir les codes de réponse HTTP possibles

---

## Tests unitaires

Le projet inclut **7 tests unitaires** dans `StudentControllerTest.java` utilisant JUnit 5 et Mockito :

| Test | Description |
|------|-------------|
| `testSaveStudent` | Vérifie la création d'un étudiant (201 Created) |
| `testDeleteStudent` | Vérifie la suppression réussie (204 No Content) |
| `testDeleteStudentNotFound` | Vérifie la suppression échouée (404 Not Found) |
| `testFindAllStudents` | Vérifie la récupération de tous les étudiants |
| `testCountStudents` | Vérifie le comptage des étudiants |
| `testFindByYear` | Vérifie les statistiques par année (liste vide) |
| `testFindByYearWithData` | Vérifie les statistiques avec données |

**Résultat des tests** :
```
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Captures d'écran

### 1. Interface Swagger - Documentation API
![Swagger UI](Screen/Swagger.png)
*Interface interactive Swagger permettant de tester tous les endpoints REST de l'application*

---

### 2. POST /students/save - Créer un étudiant
![POST Save Student](Screen/Post-save.png)
*Création d'un nouvel étudiant via l'endpoint POST avec réponse 201 Created*

---

### 3. GET /students/all - Liste de tous les étudiants
![GET All Students](Screen/Get-all.png)
*Récupération de la liste complète des étudiants enregistrés dans la base de données*

---

### 4. GET /students/count - Compter les étudiants
![GET Count](Screen/Get-count.png)
*Obtenir le nombre total d'étudiants présents dans la base*

---

### 5. GET /students/byYear - Statistiques par année
![GET By Year](Screen/Get-Year.png)
*Statistiques du nombre d'étudiants regroupés par année de naissance*

---

### 6. DELETE /students/delete/{id} - Supprimer un étudiant
![DELETE Student](Screen/Delete.png)
*Suppression d'un étudiant par son identifiant avec réponse 204 No Content*

---

### 7. Tests unitaires - Résultats
![Tests Results](Screen/test-apps.png)
*Résultats de l'exécution des 7 tests unitaires - Tous réussis avec succès*

---

## Architecture de l'application

L'application suit une **architecture en couches** (Layered Architecture) :

```
┌─────────────────────────────────────────┐
│         Controller Layer                │  ← API REST (StudentController)
│  (Gestion des requêtes HTTP)            │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         Service Layer                   │  ← Logique métier (StudentService)
│  (Traitement et validation)             │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         Repository Layer                │  ← Accès données (StudentRepository)
│  (Communication avec la BD)             │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│         Database (MySQL)                │  ← Base de données
│  (Stockage persistant)                  │
└─────────────────────────────────────────┘
```

### Avantages de cette architecture :
- **Séparation des responsabilités** : chaque couche a un rôle précis
- **Maintenabilité** : modification facile sans impacter les autres couches
- **Testabilité** : possibilité de tester chaque couche indépendamment
- **Réutilisabilité** : logique métier réutilisable dans différents contextes

---

## Commandes Maven utiles

```bash
# Nettoyer le projet
./mvnw clean

# Compiler le projet
./mvnw compile

# Exécuter les tests
./mvnw test

# Créer un package JAR
./mvnw package

# Lancer l'application
./mvnw spring-boot:run

# Nettoyer + compiler + tester + packager
./mvnw clean install
```

---

## Améliorations futures possibles

- [ ] Ajouter la validation des données avec `@Valid` et contraintes Bean Validation
- [ ] Implémenter la sécurité avec Spring Security (JWT, OAuth2)
- [ ] Ajouter la pagination pour les grandes listes avec `Pageable`
- [ ] Gérer les exceptions avec un `@ControllerAdvice` global
- [ ] Configurer CORS pour permettre les appels depuis un frontend
- [ ] Ajouter des logs structurés avec SLF4J
- [ ] Dockeriser l'application avec Docker Compose
- [ ] Mettre en place un pipeline CI/CD (GitHub Actions, Jenkins)
- [ ] Ajouter des tests d'intégration
- [ ] Implémenter un cache avec Redis

---

## Dépannage

### Problème : Port 8080 déjà utilisé
**Solution** : Le projet utilise le port 8081. Vérifiez `application.properties` ou changez le port.

### Problème : Erreur de connexion MySQL
**Solution** : Vérifiez que MySQL est démarré et que les identifiants dans `application.properties` sont corrects.

### Problème : Java version incompatible
**Solution** : Ce projet nécessite Java 17+. Vérifiez votre version avec `java -version`.

### Problème : Tests échouent
**Solution** : Assurez-vous que MySQL est démarré et que la base `studentdb` existe.

---

## Licence

Ce projet a été développé dans un cadre pédagogique.

---

## Auteur

**Votre Nom**
- GitHub: [@votre-username](https://github.com/votre-username)
- LinkedIn: [Votre Profil](https://linkedin.com/in/votre-profil)

---

## Remerciements

- Spring Boot Documentation
- Baeldung Tutorials
- Stack Overflow Community

---

**Généré avec** ❤️ **par** [Claude Code](https://claude.com/claude-code)