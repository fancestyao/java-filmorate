package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private long duration;
    private int rate;
    private Set<Integer> likesRate = new HashSet<>();

    public void addLike() {
        rate++;
    }

    public void removeLike() {
        rate--;
    }
}
