package com.unity.httpexceptionhandler.repository;

import com.unity.httpexceptionhandler.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByFirstNameEqualsIgnoreCase(@NonNull String firstName);

    Optional<Employee> findByEmailEqualsIgnoreCase(@NonNull String email);


}
