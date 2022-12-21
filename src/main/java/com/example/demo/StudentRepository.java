package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);
//    when a @Query is used you can name the method anyway you prefer
    @Query("SELECT  s FROM Student s WHERE s.firstName = ?1 AND s.age >= ?2")
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqual(String firstName, Integer age);

    @Query(value = "SELECT  * FROM student WHERE first_name = ?1 AND age >= ?2", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqualNative(String firstName, Integer age);

    @Query(value = "SELECT  * FROM student WHERE first_name = :firstName AND age >= :age", nativeQuery = true)
    List<Student> findStudentsByFirstNameEqualsAndAgeGreaterThanEqualNativeNamedParams(
            @Param("firstName") String firstName,
            @Param("age")Integer age
    );
    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = ?1")
    int deleteStudentById(Long id);
}
