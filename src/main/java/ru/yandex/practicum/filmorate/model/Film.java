package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private int id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private long duration;

    private List<FilmGenre> genres;

    private MPA mpa;

    private int rate;

    private Set<Integer> likesRate = new HashSet<>();

    public void addLike(Integer id) {
        getLikesRate().add(id);
        rate = likesRate.size();
    }

    public void removeLike(Integer id) {
        getLikesRate().remove(id);
        rate = likesRate.size();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("NAME", name);
        values.put("DESCRIPTION", description);
        values.put("RELEASE_DATE", releaseDate);
        values.put("DURATION", duration);
        values.put("MPA_ID", mpa.getId());
        return values;
    }
}
