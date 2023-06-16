package com.assn22.assn22.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assn22.assn22.models.Student;
import com.assn22.assn22.models.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllStudents(Model model){
        System.out.println("Getting all students");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("st", students);
        return "students/showAll";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String,String> newstudent, HttpServletResponse response, Model model){
        System.out.println("ADD Student");
        String newName = newstudent.get("name");
        int newWeight = Integer.parseInt(newstudent.get("weight"));
        int newHeight = Integer.parseInt(newstudent.get("height"));
        float newGPA = Float.parseFloat(newstudent.get("gpa"));
        String newHairColor = newstudent.get("haircolor");
        studentRepo.save(new Student(newName,newWeight,newHeight,newHairColor,newGPA));
        response.setStatus(201);
        List<Student> students = studentRepo.findAll();
        model.addAttribute("st", students);        
        return "students/graph";
    }

    @GetMapping("/students/select")
    public String deleteMenu(Model model){
        List<Student> students = studentRepo.findAll();
        model.addAttribute("st", students);
        return "students/deleteStudent";
    }

    @PostMapping("/students/select")
    public String showAllStudents(Model model){
        List<Student> students = studentRepo.findAll();
        model.addAttribute("st", students);
        return "editUser";
    }

    @GetMapping("/students/edit/{uid}")
    public String editStudent(Model model, @PathVariable String uid){
        Student student = studentRepo.findByUid(Integer.parseInt(uid));
        model.addAttribute("student",student);
        return "students/editStudent";
    }

    @GetMapping("/students/edited/{uid}")
    public String editedStudent2(@PathVariable String uid ,@RequestParam Map<String,String> editedStudent, HttpServletResponse response, Model model){
        Student student = studentRepo.findByUid(Integer.parseInt(uid));
        student.setName(editedStudent.get("name"));
        student.setWeight(Integer.parseInt(editedStudent.get("weight")));   
        student.setHeight(Integer.parseInt(editedStudent.get("height")));
        student.setGpa(Float.parseFloat(editedStudent.get("gpa"))); 
        student.setHairColor(editedStudent.get("haircolor"));        
        studentRepo.save(student);         
        List<Student> students = studentRepo.findAll();
        model.addAttribute("st", students);        
        return "students/graph";
    }

    @GetMapping("/students/delete/{uid}")
    public String deleteStudent(@PathVariable String uid,@RequestParam Map<String,String> editedStudent, HttpServletResponse response, Model model){
        Student student = studentRepo.findByUid(Integer.parseInt(uid));
        studentRepo.delete(student);
        List<Student> students = studentRepo.findAll();
        model.addAttribute("st", students);        
        return "students/graph";
    }


}
