# java-filmorate
Template repository for Filmorate project.
![Изображение ER-диаграммы для данного проекта](https://i.gyazo.com/6c2d0aaa042348dbddb638bd3d21299b.png)

Некоторые примеры sql-запросов к данной БД:
1) Вывод информации по названию фильма о его жанре и рейтинг (сортировка по рейтингу)

&emsp; SELECT f.name, g.name, mpa.name <br />
&emsp; FROM films AS f <br />
&emsp; INNER JOIN film_genre AS fg ON fg.film_id = f.id <br />
&emsp; LEFT JOIN genres AS g ON g.genre_id = fg.genre_id <br />
&emsp; LEFT JOIN mpa ON mpa_id = f.mpa_id <br />
&emsp; GROUP BY f.name, g.name, mpa.name <br />
&emsp; ORDER BY mpa.name; <br />

2) Соответствие пользователя и фильма по лайку (вся статистика с null'ами)

&emsp; SELECT u.name AS user_name, <br />
&emsp; &emsp; &emsp; &emsp;       f.name AS film_name <br />
&emsp; FROM likes AS l <br />
&emsp; FULL OUTER JOIN films AS f ON f.id = l.film_id <br />
&emsp; FULL OUTER JOIN users AS u ON u.user_id = l.user_id <br />

Ниже представлен документ с более развернутой информацией о представленной выше БД. <br />
[Тот-самый-файл](https://github.com/fancestyao/java-filmorate/files/11256517/CoolQBDpdfVersionFileOfDB.pdf)
