package ru.yandex.practicum.filmorate.service.classes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MPADBStorage;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.interfaces.MPAService;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class MPADBService implements MPAService {
    private final MPADBStorage mpadbStorage;

    public List<MPA> getAllMPA() {
        log.info("Запрос на получение всех возрастных рейтингов в сервисе.");
        return mpadbStorage.getAllMPA();
    }

    public MPA getMPAById(Integer id) {
        log.info("Запрос на получение жанра с id = {} в сервисе.", id);
        return mpadbStorage.getMPAById(id);
    }
}
