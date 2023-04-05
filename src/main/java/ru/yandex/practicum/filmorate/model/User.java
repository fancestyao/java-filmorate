package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friends;

    public void addFriend(Integer id) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(id);
    }

    public Set<Integer> getFriend() {
        if (friends == null) {
            friends = new HashSet<>();
        }
        return friends;
    }
}
