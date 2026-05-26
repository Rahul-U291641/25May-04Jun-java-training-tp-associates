package com.assignment1.student_mgmt.controller;

import com.assignment1.student_mgmt.dto.StudentDTO;
import com.assignment1.student_mgmt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public String createStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
       return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDTO getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
       return studentService.updateStudent(id, studentDTO);
    }

    @PatchMapping("/{id}")
    public String partiallyUpdateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        return studentService.partiallyUpdateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
       return studentService.deleteStudent(id);
    }
}
