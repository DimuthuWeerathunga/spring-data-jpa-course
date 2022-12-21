package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student student1 = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones@gmail.com",
                    24
            );
            Student student2 = new Student(
                    "Ahmed",
                    "Ali",
                    "ahmed.ali@gmail.com",
                    24
            );
            Student student3 = new Student(
                    "Maria",
                    "Something",
                    "maria2.jones@gmail.com",
                    25
            );
            System.out.println("Adding Maria and Ahmed");
            studentRepository.saveAll(List.of(student1, student2, student3));

//            studentRepository
//                    .findStudentByEmail("ahmed.ali@gmail.com")
//                    .ifPresentOrElse(System.out::println, ()-> System.out.println("Studnet with email that not found"));

            studentRepository.findStudentsByFirstNameEqualsAndAgeGreaterThanEqualNativeNamedParams("Maria", 24)
                    .forEach(System.out::println);

            System.out.println(studentRepository.deleteStudentById(1L)); 
        };
    }
}
