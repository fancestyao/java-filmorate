package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class FilmGenre {
    private int id;

    private String name;
}
