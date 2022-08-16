package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode (of = "id")

public class User {
    private Integer id;
    private String name;
    @NotBlank @NotNull
    private String login;
    @Email
    private String email;
    @Past
    private LocalDate birthday;

}
