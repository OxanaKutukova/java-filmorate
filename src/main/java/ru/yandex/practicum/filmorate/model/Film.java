package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
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
    private int duration;

    @JsonIgnore
    private Set<Integer> userIds = new HashSet<>();
}
