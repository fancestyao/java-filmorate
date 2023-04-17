# java-filmorate
Template repository for Filmorate project.
![Изображение ER-диаграммы для данного проекта](https://i.gyazo.com/e1c280f635dadee36b9e1544ca8a5718.png)

Некоторые примеры sql-запросов к данной БД:
1) Вывод информации по названию фильма о его жанре и рейтинг (сортировка по рейтингу)
SELECT f.name, g.name, mpa.name
FROM films AS f
INNER JOIN film_genre AS fg ON fg.film_id = f.id
LEFT JOIN genres AS g ON g.genre_id = fg.genre_id
LEFT JOIN mpa ON mpa_id = f.mpa_id
GROUP BY f.name, g.name, mpa.name
ORDER BY mpa.name;

2) Соответствие пользователя и фильма по лайку (вся статистика с null'ами)
SELECT u.name AS user_name,
       f.name AS film_name
FROM likes AS l
FULL OUTER JOIN films AS f ON f.id = l.film_id
FULL OUTER JOIN users AS u ON u.user_id = l.user_id
