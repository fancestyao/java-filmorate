package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.interfaces.MPAService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/mpa")
public class MPAController {
    private final MPAService mpadbService;

    @GetMapping
    public List<MPA> getAllMPA() {
        log.info("Пришел запрос на получение всех возрастных рейтингов фильмов.");
        return mpadbService.getAllMPA();
    }

    @GetMapping("/{id}")
    public MPA getMPAById(@PathVariable Integer id) {
        log.info("Пришел запрос на получение возрастного рейтинга с id = {}.", id);
        return mpadbService.getMPAById(id);
    }
}