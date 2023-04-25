package ru.yandex.practicum.filmorate.service.interfaces;

import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

public interface MPAService {
    List<MPA> getAllMPA();

    MPA getMPAById(Integer id);
}
