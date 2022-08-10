package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode (of = "id")

public class User {
    private Integer id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
}
