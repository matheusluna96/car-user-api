package com.luna.api_car_system.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo Primeiro Nome foi preenchido incorretamente")
    @Column(nullable = false)
    private String firstName;
    @NotBlank(message = "O campo Sobrenome foi preenchido incorretamente")
    @Column(nullable = false)
    private String lastName;
    @NotBlank(message = "O campo E-mail deve ser preenchido")
    @Column(unique = true)
    private String email;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    @NotBlank(message = "O campo Login foi preenchido incorretamente")
    @Column(unique = true)
    private String login;
    @NotBlank(message = "O campo Senha foi preenchido incorretamente")
    @Column(nullable = false)
    private String password;
    private String phone;
}
