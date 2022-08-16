package ru.yandex.practicum.filmorate.model;

import lombok.*;


@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode (of = "id")

public class Genre {
    private final Integer id;
    private final String name;
}