package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.classes.MPADBService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/mpa")
public class MPAController {
    private final MPADBService mpadbService;

    @GetMapping
    public List<MPA> getAllMPA() {
        return mpadbService.getAllMPA();
    }

    @GetMapping("/{id}")
    public MPA getMPAById(@PathVariable Integer id) {
        return mpadbService.getMPAById(id);
    }
}