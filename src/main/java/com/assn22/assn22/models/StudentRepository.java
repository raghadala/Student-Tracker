package com.assn22.assn22.models;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
    Student findByUid(int uid);
}
