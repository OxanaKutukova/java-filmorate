package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode (of = "id")

public class Film {
    private Integer id;
    @NotBlank @NotNull
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    @NotNull
    private Mpa mpa;
    private LinkedHashSet <Genre> genres;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }
}
