-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 21, 2019 at 05:14 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.1.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `st_calendar`
--

-- --------------------------------------------------------

--
-- Table structure for table `calendar`
--

CREATE TABLE `calendar` (
  `id` int(11) NOT NULL,
  `student_id` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  `calendar_id` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `type` int(2) NOT NULL COMMENT '0: thời khóa biểu; 1: lịch thi',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `calendar_detail`
--

CREATE TABLE `calendar_detail` (
  `id` int(11) NOT NULL,
  `calendar_id` int(11) NOT NULL,
  `semester_id` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL,
  `schedule_hash` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `event_id` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `calen_detail_id` int(11) NOT NULL,
  `subject_id` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `subject_group` int(11) NOT NULL,
  `clazz` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `practice_group` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `calendar_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(26);

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `author_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Bảng uyền người dùng';

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_STUDENT'),
(4, 'ROLE_TEACHER'),
(5, 'ROLE_OFFICER');

-- --------------------------------------------------------

--
-- Table structure for table `semester`
--

CREATE TABLE `semester` (
  `id` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `start_date` date NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `semester`
--

INSERT INTO `semester` (`id`, `name`, `start_date`, `created_at`, `updated_at`) VALUES
('20182', 'Học kỳ 2 - Năm học 2018-2019', '2018-12-31', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
('20183', 'Học kỳ 3 - Năm học 2018-2019', '2019-05-13', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
('20191', 'Học kỳ 1 - Năm học 2019-2020', '2019-08-05', '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `slot_time`
--

CREATE TABLE `slot_time` (
  `id` int(2) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `slot_time`
--

INSERT INTO `slot_time` (`id`, `start_time`, `end_time`, `created_at`, `updated_at`) VALUES
(1, '07:00:00', '07:50:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, '07:55:00', '08:45:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(3, '08:50:00', '09:40:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(4, '09:55:00', '10:45:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(5, '10:50:00', '11:40:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(6, '12:45:00', '13:35:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(7, '13:40:00', '14:30:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(8, '14:35:00', '15:25:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(9, '15:40:00', '16:30:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(10, '16:35:00', '17:25:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(11, '18:00:00', '18:50:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(12, '18:55:00', '19:45:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(13, '19:50:00', '20:40:00', '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(254) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `avatar` text COLLATE utf8_unicode_ci NOT NULL,
  `faculty` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `clazz` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `gg_refresh_token` varchar(500) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Bảng chứa thông tin người dùng';

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `first_name`, `last_name`, `avatar`, `faculty`, `clazz`, `password`, `enabled`, `gg_refresh_token`, `created_at`, `updated_at`) VALUES
(10, 'anhttmail@gmail.com', 'Tạ', 'Thế Anh', '', '', '', '$2a$11$LuH0XnSasV2vV1qvppXxZONhPDP4rE16F5.Vzu9N9tKB4O054JiIa', 1, NULL, '2019-05-17 20:07:26', '0000-00-00 00:00:00'),
(25, 'anhttemail@gmail.com', 'anh', 'ta', '', 'cntt', 'K60THA', '$2a$11$/WWdRZc3Uf2lYXy9i8VKXuUDNP06INii9DH8iS0V0mna.eNWfvPeC', 1, NULL, '2019-11-21 16:51:35', '2019-11-21 16:51:35');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(10, 1),
(25, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `calendar`
--
ALTER TABLE `calendar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_calendar_user` (`user_id`);

--
-- Indexes for table `calendar_detail`
--
ALTER TABLE `calendar_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_calendar_detail_calenId` (`calendar_id`),
  ADD KEY `fk_calendar_detail_semesId` (`semester_id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_event_calenDetail_calenDetailId` (`calen_detail_id`),
  ADD KEY `fk_event_calendar_calendarId` (`calendar_id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_post_user` (`author_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `semester`
--
ALTER TABLE `semester`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `slot_time`
--
ALTER TABLE `slot_time`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `fk_user_role_roleid` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `calendar`
--
ALTER TABLE `calendar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `calendar_detail`
--
ALTER TABLE `calendar_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `calendar`
--
ALTER TABLE `calendar`
  ADD CONSTRAINT `fk_calendar_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `calendar_detail`
--
ALTER TABLE `calendar_detail`
  ADD CONSTRAINT `fk_calendar_detail_calenId` FOREIGN KEY (`calendar_id`) REFERENCES `calendar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_calendar_detail_semesId` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `fk_event_calenDetail_calenDetailId` FOREIGN KEY (`calen_detail_id`) REFERENCES `calendar_detail` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_event_calendar_calendarId` FOREIGN KEY (`calendar_id`) REFERENCES `calendar` (`id`);

--
-- Constraints for table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `fk_post_user` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `fk_user_role_roleid` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_role_userid` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
