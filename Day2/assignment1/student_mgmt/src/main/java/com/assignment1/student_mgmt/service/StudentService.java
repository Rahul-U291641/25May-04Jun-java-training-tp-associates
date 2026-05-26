package com.assignment1.student_mgmt.service;

import com.assignment1.student_mgmt.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    public String createStudent(StudentDTO studentDTO) {
        // Logic to save student to the database
        // Result can be a success message or the created student details
        return "Student created successfully!";
    }

    public List<StudentDTO> getAllStudents() {
        // Logic to retrieve all students from the database
        // Result can be a list of StudentDTOs
        return List.of(
                new StudentDTO(1L, "John Doe", 85.5, "Computer Science"),
                new StudentDTO(2L, "Jane Smith", 92.0, "Mathematics"),
                new StudentDTO(3L, "Alice Johnson", 78.0, "Physics"),
                new StudentDTO(4L, "Bob Brown", 88.5, "Chemistry"),
                new StudentDTO(5L, "Charlie Davis", 90.0, "Biology"),
                new StudentDTO(6L, "Diana Evans", 82.0, "History")
        );
    }

    public StudentDTO getStudentById(Long id) {
        // Logic to retrieve a student by ID from the database
        // Result can be a StudentDTO or an error message if not found
        return new StudentDTO(id, "John Doe", 85.5, "Computer Science");
    }

    public String updateStudent(Long id, StudentDTO studentDTO) {
        // Logic to update a student in the database
        // Result can be a success message or the updated student details
        return "Student with ID: " + id + " updated successfully!";
    }

    public String partiallyUpdateStudent(Long id, StudentDTO studentDTO) {
        // Logic to partially update a student in the database
        // Result can be a success message or the updated student details
        return "Student with ID: " + id + " partially updated successfully!";
    }

    public String deleteStudent(Long id) {
        // Logic to delete a student from the database
        // Result can be a success message or an error message if not found
        return "Student with ID: " + id + " deleted successfully!";
    }
}
