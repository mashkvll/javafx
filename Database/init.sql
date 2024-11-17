-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Апр 18 2024 г., 05:27
-- Версия сервера: 10.4.28-MariaDB
-- Версия PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: ` attractions`
--

-- --------------------------------------------------------

--
-- Структура таблицы `attract`
--

CREATE TABLE `attract` (
  `idAttr` int(11) NOT NULL,
  `nameAttr` varchar(100) NOT NULL,
  `fotoAttr` varchar(100) NOT NULL,
  `descriptionAttr` varchar(1000) NOT NULL,
  `countPositiv` int(11) NOT NULL,
  `countNegativ` int(11) NOT NULL,
  `idCat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `attract`
--

INSERT INTO `attract` (`idAttr`, `nameAttr`, `fotoAttr`, `descriptionAttr`, `countPositiv`, `countNegativ`, `idCat`) VALUES
(1, 'Нижегородский кремль', '1.jpg', 'Крепость в историческом центре Нижнего Новгорода и его древнейшая часть, главный общественно-политический и историко-художественный комплекс города.', 2, 0, 2),
(2, 'Рождественская церковь', '2.jpg', 'Рождественская (Строгановская) церковь — православный храм на Рождественской улице Нижнего Новгорода.\r\nПостроен в 1696–1719 годах на средства купца Григория Дмитриевича Строганова. Является одним из лучших образцов строгановского стиля,', 5, 0, 1),
(3, 'Нижегородская ярмарка', '3.jpg', 'исторический район, где располагалась крупнейшая ярмарка Российской империи. Центральный выставочный центр Нижнего Новгорода.\r\n', 2, 1, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `category`
--

CREATE TABLE `category` (
  `idCat` int(11) NOT NULL,
  `nameCat` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `category`
--

INSERT INTO `category` (`idCat`, `nameCat`) VALUES
(1, 'Архитектура'),
(2, 'История');

-- --------------------------------------------------------

--
-- Структура таблицы `orders`
--

CREATE TABLE `orders` (
  `idOrd` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idAttr` int(11) NOT NULL,
  `dateAttr` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `orders`
--

INSERT INTO `orders` (`idOrd`, `idUser`, `idAttr`, `dateAttr`) VALUES
(1, 3, 2, '2024-04-19'),
(2, 2, 3, '2024-04-18');

-- --------------------------------------------------------

--
-- Структура таблицы `reviews`
--

CREATE TABLE `reviews` (
  `idRev` int(11) NOT NULL,
  `textRev` text NOT NULL,
  `idUser` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `reviews`
--

INSERT INTO `reviews` (`idRev`, `textRev`, `idUser`) VALUES
(1, 'Знаковое место Нижнего Новгорода, обязательно к посещению. Территория Кремля очень ухоженная, здесь приятно гулять как днём, так и вечером, немало достопримечательностей и культовых сооружений города находится именно здесь (НГХМ, Собор Михаила Архангела, Манеж с домовой церковью Николая Чудотворца, храм Симеона Столпника и др.). Можно взять экскурсию и пройти по всем башням Кремля, расстояние достаточно приличное (около 2, 2 км), но эмоций получите массу. Очень рад, что за десять лет, что живу в НН, территория Кремля так преобразилась (огромная благодарность властям), особенно радует произведённое к 800-летию города восстановление Спасской колокольни, с которой открывается потрясающий вид на город, и расположенной рядом с Кремлём церкви Симеона Столпника, а также манежа с чудесной домовой церковью Николая Чудотворца. Обязательно приходите сюда в любое время суток, однозначно получите массу положительных эмоций! ', 4);

-- --------------------------------------------------------

--
-- Структура таблицы `roles`
--

CREATE TABLE `roles` (
  `idRole` int(11) NOT NULL,
  `nameRole` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `roles`
--

INSERT INTO `roles` (`idRole`, `nameRole`) VALUES
(1, 'Администратор'),
(2, 'Клиент');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `idUser` int(11) NOT NULL,
  `fioUser` varchar(100) NOT NULL,
  `loginUser` varchar(100) NOT NULL,
  `passwordUser` varchar(100) NOT NULL,
  `idRole` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`idUser`, `fioUser`, `loginUser`, `passwordUser`, `idRole`) VALUES
(1, 'Иванов Иван Иванович', 'ivan1@mail.ru', '123321', 1),
(2, 'Петров Петр Петрович', 'peter@mail.ru', '123321', 2),
(3, 'Арбузов Антон Андреевич', 'anton@mail.ru', '123321', 2),
(4, 'Агафонов Егор Кириллович', 'egor@mail.ru', '123321', 2);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `attract`
--
ALTER TABLE `attract`
  ADD PRIMARY KEY (`idAttr`),
  ADD KEY `idCat` (`idCat`);

--
-- Индексы таблицы `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`idCat`);

--
-- Индексы таблицы `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`idOrd`),
  ADD KEY `idAttr` (`idAttr`),
  ADD KEY `idUser` (`idUser`);

--
-- Индексы таблицы `reviews`
--
ALTER TABLE `reviews`
  ADD PRIMARY KEY (`idRev`),
  ADD KEY `idUser` (`idUser`);

--
-- Индексы таблицы `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`idRole`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUser`),
  ADD KEY `idRole` (`idRole`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `attract`
--
ALTER TABLE `attract`
  MODIFY `idAttr` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `category`
--
ALTER TABLE `category`
  MODIFY `idCat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `orders`
--
ALTER TABLE `orders`
  MODIFY `idOrd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `reviews`
--
ALTER TABLE `reviews`
  MODIFY `idRev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `roles`
--
ALTER TABLE `roles`
  MODIFY `idRole` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `attract`
--
ALTER TABLE `attract`
  ADD CONSTRAINT `attract_ibfk_1` FOREIGN KEY (`idCat`) REFERENCES `category` (`idCat`);

--
-- Ограничения внешнего ключа таблицы `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`idAttr`) REFERENCES `attract` (`idAttr`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`);

--
-- Ограничения внешнего ключа таблицы `reviews`
--
ALTER TABLE `reviews`
  ADD CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `users` (`idUser`);

--
-- Ограничения внешнего ключа таблицы `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`idRole`) REFERENCES `roles` (`idRole`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
