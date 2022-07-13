package com.unity.httpexceptionhandler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username Should not be null")
    @Size(min = 3, max = 10, message = "Name Should be greater than 3 characters and less then 10 characters")
    private String firstName;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "enter a valid email address")
    private String email;

}
