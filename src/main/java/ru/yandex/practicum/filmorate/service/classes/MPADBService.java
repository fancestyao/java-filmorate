package ru.yandex.practicum.filmorate.service.classes;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MPADBStorage;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;

@Data
@Primary
@Service
@RequiredArgsConstructor
public class MPADBService {
    private final MPADBStorage mpadbStorage;

    public List<MPA> getAllMPA() {
        return mpadbStorage.getAllMPA();
    }

    public MPA getMPAById(Integer id) {
        return mpadbStorage.getMPAById(id);
    }
}
