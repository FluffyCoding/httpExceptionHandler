package com.unity.httpexceptionhandler.resource;

import com.unity.httpexceptionhandler.exception.CustomExceptionHandler;
import com.unity.httpexceptionhandler.exception.domain.EmailExistException;
import com.unity.httpexceptionhandler.exception.domain.UsernameExistException;
import com.unity.httpexceptionhandler.model.Employee;
import com.unity.httpexceptionhandler.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/Employees")
public class EmployeeResource extends CustomExceptionHandler {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/")
    public ResponseEntity<List<Employee>> getEmployees() {
        var employees = employeeRepository.findAll();
        return new ResponseEntity<>(employees, HttpStatus.FOUND);
    }


    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody @Valid Employee employee) {

        employeeRepository.findByFirstNameEqualsIgnoreCase(employee.getFirstName())
                .ifPresent(user -> {
                    throw new UsernameExistException("Employee Already Exists");
                });
        employeeRepository.findByEmailEqualsIgnoreCase(employee.getEmail())
                .ifPresent(user -> {
                    throw new EmailExistException(user.getEmail() + " Already Exists");
                });

        var savedEmp = employeeRepository.save(employee);
        return new ResponseEntity<>(savedEmp, HttpStatus.CREATED);
    }
}
