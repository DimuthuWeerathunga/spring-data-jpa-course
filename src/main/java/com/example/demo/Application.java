package com.example.demo;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, StudentIdCardRepository studentIdCardRepository) {
        return args -> {
//            generateRandomStudents(studentRepository);
//            sorting(studentRepository);
//            paging(studentRepository);
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName.toLowerCase(), lastName.toLowerCase());
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55)
            );

            student.addBook(new Book("Clean Code"));
            student.addBook(new Book("Think and Grow Rich"));
            student.addBook(new Book("Spring and Data Jpa"));

            StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

            student.setStudentIdCard(studentIdCard);

            Course course1 = new Course("Computer Science", "IT");
//            student.addEnrolment(new Enrolment(new EnrolmentId(student.getId(), 1L), student, course1));
            student.addEnrolment(new Enrolment(student, course1, LocalDateTime.now().minusDays(18)));

            Course course2 = new Course("Spring Data Jpa", "IT");
//            student.addEnrolment(new Enrolment(new EnrolmentId(student.getId(), 2L), student, course2));
            student.addEnrolment(new Enrolment(student, course2, LocalDateTime.now().minusDays(18)));

//            student.enrolToCourse(new Course("Computer Science", "IT"));
//            student.enrolToCourse(new Course("Spring Data Jpa", "IT"));

            studentRepository.save(student);

            studentRepository.findById(1L).ifPresent(s -> {
                System.out.println("fetch books lazy...");
                List<Book> books = student.getBooks();
                books.forEach(book -> {
                    System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
                });
            });

//            studentIdCardRepository.deleteById(1L);
        };
    }

    private static void paging(StudentRepository studentRepository) {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("age").ascending());
        Page<Student> page = studentRepository.findAll(pageRequest);
        System.out.println(page);
    }

    private static void sorting(StudentRepository studentRepository) {
        Sort sort = Sort.by("firstName").ascending().and(Sort.by("age").descending());
        studentRepository.findAll(sort)
                .forEach((s) -> System.out.println(s.getFirstName() + " " + s.getAge()));
    }

    private static void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@gmail.com", firstName.toLowerCase(), lastName.toLowerCase());
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    faker.number().numberBetween(17, 55)
            );
            studentRepository.save(student);
        }
    }
}
