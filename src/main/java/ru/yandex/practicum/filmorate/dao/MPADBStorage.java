package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.MPANotFoundException;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Primary
public class MPADBStorage {
    private final JdbcTemplate jdbcTemplate;

    public List<MPA> getAllMPA() {
        String sqlQuery = "SELECT * FROM MPA";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMPA);
    }

    public MPA getMPAById(Integer id) {
        String sqlQuery = "SELECT * FROM MPA WHERE MPA_ID = ?";
        MPA result;
        try {
            result = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMPA, id);
        } catch (DataAccessException e) {
            throw new MPANotFoundException("Рейтинг не найден.");
        }
        return result;
    }

    private MPA mapRowToMPA(ResultSet resultSet, int rowNum) throws SQLException {
        return MPA.builder()
                .id(resultSet.getInt("MPA_ID"))
                .name(resultSet.getString("MPA_NAME"))
                .build();
    }
}
