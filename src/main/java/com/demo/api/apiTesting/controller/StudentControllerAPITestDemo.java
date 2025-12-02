package com.demo.api.apiTesting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.demo.api.apiTesting.model.Student;

@RestController
@RequestMapping("/students")
public class StudentControllerAPITestDemo {

    private List<Student> studentList = new ArrayList<>();
    private Long idCounter = 1L;

    // ✅ Create Student (POST)
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        student.setId(idCounter++);
        studentList.add(student);
        return student;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                s.setName(updatedStudent.getName());
                s.setAge(updatedStudent.getAge());
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid ID: Student not found to Update through Path Params");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                if (updates.containsKey("name")) {
                    s.setName((String) updates.get("name"));
                }
                if (updates.containsKey("age")) {
                    s.setAge((Integer) updates.get("age"));
                }
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid ID: Student not found for patch or Specific Data update for Path Params");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        boolean removed = studentList.removeIf(s -> s.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Student deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invalid ID: Student not found or already deleted through path Params");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return studentList.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)   // return 200 with student
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Invalid ID: Student not found")); // return 404 with message
    }

    // ✅ Get ALL Students (GET)
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        if (studentList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No students are available");
        }
        return ResponseEntity.ok(studentList);
    }
    @GetMapping(params = "id")
    public ResponseEntity<?> getStudentByQueryParam(@RequestParam Long id) {
        return studentList.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Invalid ID: Student not found"));
    }

    @GetMapping("/id-in-header")
    public ResponseEntity<?> getStudentByHeader(@RequestHeader("id") Long id) {
        return studentList.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Invalid ID: Student not found"));
    }

    @PutMapping(params = "id")
    public ResponseEntity<?> updateStudentByQuery(@RequestParam Long id, @RequestBody Student updatedStudent) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                s.setName(updatedStudent.getName());
                s.setAge(updatedStudent.getAge());
                return ResponseEntity.ok(s);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid ID: Student not found for update through Query Params");
    }

    @PutMapping("/update-by-header")
    public ResponseEntity<?> updateStudentByHeader(@RequestHeader("id") Long id,
                                                   @RequestBody Student updatedStudent) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                s.setName(updatedStudent.getName());
                s.setAge(updatedStudent.getAge());
                return ResponseEntity.ok(s); // 200 OK with updated student
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid ID: Student not found for update through Headers");
    }

    @PatchMapping(params = "id")
    public ResponseEntity<?> patchStudentByQuery(@RequestParam Long id,
                                                 @RequestBody Map<String, Object> updates) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                if (updates.containsKey("name")) {
                    s.setName((String) updates.get("name"));
                }
                if (updates.containsKey("age")) {
                    s.setAge((Integer) updates.get("age"));
                }
                return ResponseEntity.ok(s); // 200 OK with patched student
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid ID: Student not found for patch or Specific Data update Through Query Params");
    }

    @PatchMapping("/patch-by-header")
    public ResponseEntity<?> patchStudentByHeader(@RequestHeader("id") Long id,
                                                  @RequestBody Map<String, Object> updates) {
        for (Student s : studentList) {
            if (s.getId().equals(id)) {
                if (updates.containsKey("name")) {
                    s.setName((String) updates.get("name"));
                }
                if (updates.containsKey("age")) {
                    s.setAge((Integer) updates.get("age"));
                }
                return ResponseEntity.ok(s); // 200 OK with patched student
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid ID: Student not found for patch or Specific Data update Through Headers");
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<String> deleteStudentByQuery(@RequestParam Long id) {
        boolean removed = studentList.removeIf(s -> s.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Student deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invalid ID: Student not found or already deleted in Query Params");
        }


    }

    @DeleteMapping("/delete-by-header")
    public ResponseEntity<String> deleteStudentByHeader(@RequestHeader("id") Long id) {
        boolean removed = studentList.removeIf(s -> s.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Student deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Invalid ID: Student not found or already deleted through Headers");
        }
    }

    @GetMapping("/simulate-500-student")
    public ResponseEntity<?> simulateStudentError() {
        try {
            Student s = null; // simulate unexpected null student
            String name = s.getName(); // NullPointerException
            return ResponseEntity.ok(name);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("500 Internal Server Error while accessing student: " + e.getMessage());
        }
    }

    @GetMapping("/simulate-503")
    public ResponseEntity<?> simulateServiceUnavailable() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .header("Retry-After", "30") // client को बताता है 30 sec बाद retry करें
                .body("503 Service Unavailable: Server is temporarily down, please try again later");
    }

    @GetMapping("/search-by-name")
    public ResponseEntity<?> searchStudentByName(@RequestParam String name) {
        // Feature not implemented yet
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body("501 Not Implemented: Search by name is not available yet");
    }

    @GetMapping("/simulate-500-external")
    public ResponseEntity<?> simulateExternalError() {
        try {
            // Simulate external API failure
            throw new RuntimeException("Failed to fetch grades from external service");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("500 Internal Server Error: " + e.getMessage());
        }
    }
}