-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 10, 2019 at 10:04 AM
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
-- Database: `kltn`
--

-- --------------------------------------------------------

--
-- Table structure for table `calendar`
--

CREATE TABLE `calendar` (
  `id` int(11) NOT NULL,
  `student_id` varchar(6) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `gg_calendar_id` varchar(1024) COLLATE utf8_unicode_ci DEFAULT 'NULL',
  `type` int(2) NOT NULL COMMENT '0: thời khóa biểu; 1: lịch thi',
  `status` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `calendar`
--

INSERT INTO `calendar` (`id`, `student_id`, `gg_calendar_id`, `type`, `status`, `created_at`, `updated_at`, `user_id`) VALUES
(19, 'cnp02', '7dtk617fbrtscf9uo53ab284uo@group.calendar.google.com', 0, 0, '2019-12-10 02:35:32', '2019-12-10 02:35:32', 1);

-- --------------------------------------------------------

--
-- Table structure for table `calendar_detail`
--

CREATE TABLE `calendar_detail` (
  `id` int(11) NOT NULL,
  `cd_calendar_id` int(11) DEFAULT NULL,
  `semester_id` varchar(11) COLLATE utf8_unicode_ci DEFAULT NULL,
  `schedule_hash` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `calendar_detail`
--

INSERT INTO `calendar_detail` (`id`, `cd_calendar_id`, `semester_id`, `schedule_hash`, `status`, `created_at`, `updated_at`) VALUES
(19, 19, '20191', 'b112992658adf95669cedc02a1209b5c', 0, '2019-12-10 02:35:32', '2019-12-10 02:35:32');

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `id` int(11) NOT NULL,
  `event_id` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `calen_detail_id` int(11) DEFAULT NULL,
  `subject_id` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `subject_group` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `clazz` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `practice_group` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `credit` int(11) NOT NULL,
  `start_slot` int(11) NOT NULL,
  `end_slot` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `evt_calendar_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`id`, `event_id`, `calen_detail_id`, `subject_id`, `subject_group`, `clazz`, `practice_group`, `credit`, `start_slot`, `end_slot`, `status`, `created_at`, `updated_at`, `evt_calendar_id`) VALUES
(116, 'stcalendar1575920109508', 19, 'TH01001', '03', 'K64CNTTC', '1', 3, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(117, 'stcalendar1575920109509', 19, 'TH03106', '01', 'K63ATTT', '1', 3, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(118, 'stcalendar1575920109508', 19, 'TH02035', '02', 'K63ATTT', '', 1, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(119, 'stcalendar1575920109508', 19, 'TH02016', '04', 'K63TH', '', 3, 6, 8, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(120, 'stcalendar1575920109506', 19, 'TH03106', '01', 'K63ATTT', '2', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(121, 'stcalendar1575920109508', 19, 'TH03106', '02', 'K63CNPM', '1', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(122, 'stcalendar1575920109508', 19, 'TH02016', '03', 'K63HTTT', '', 3, 1, 3, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(123, 'stcalendar1575920109508', 19, 'TH02035', '01', 'K63ATTT', '', 1, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(124, 'stcalendar1575920109507', 19, 'TH02035', '02', 'K63ATTT', '', 1, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(125, 'stcalendar1575920109506', 19, 'TH02035', '04', 'K63HTTT', '', 1, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(126, 'stcalendar1575920109508', 19, 'TH01001', '03', 'K64CNTTC', '', 3, 4, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(127, 'stcalendar1575920109508', 19, 'TH02016', '01', 'K63ATTT', '', 3, 9, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(128, 'stcalendar1575920109509', 19, 'PTH02003', '01', 'K63CNPMP', '1', 3, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(129, 'stcalendar1575920109509', 19, 'TH02035', '01', 'K63ATTT', '', 1, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(130, 'stcalendar1575920109508', 19, 'TH02016', '02', 'K63CNPM', '', 3, 1, 3, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(131, 'stcalendar1575920109508', 19, 'PTH02003', '02', 'K63MMT', '1', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(132, 'stcalendar1575920109507', 19, 'TH01001', '03', 'K64CNTTC', '', 3, 9, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(133, 'stcalendar1575920109508', 19, 'TH02035', '03', 'K63CNPM', '', 1, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(134, 'stcalendar1575920109508', 19, 'PTH02003', '01', 'K63CNPMP', '1', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(135, 'stcalendar1575920109509', 19, 'TH01001', '03', 'K64CNTTC', '2', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(136, 'stcalendar1575920109508', 19, 'TH02016', '03', 'K63HTTT', '', 3, 4, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(137, 'stcalendar1575920109506', 19, 'TH02035', '05', 'K63TH', '', 1, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(138, 'stcalendar1575920109508', 19, 'TH01001', '03', 'K64CNTTC', '2', 3, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(139, 'stcalendar1575920109507', 19, 'TH02016', '03', 'K63HTTT', '', 3, 4, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(140, 'stcalendar1575920109509', 19, 'TH02016', '04', 'K63TH', '', 3, 9, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(141, 'stcalendar1575920109509', 19, 'TH01001', '03', 'K64CNTTC', '1', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(142, 'stcalendar1575920109508', 19, 'TH03106', '01', 'K63ATTT', '2', 3, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(143, 'stcalendar1575920109508', 19, 'TH02016', '01', 'K63ATTT', '', 3, 6, 8, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(144, 'stcalendar1575920109507', 19, 'TH03106', '02', 'K63CNPM', '', 3, 1, 3, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(145, 'stcalendar1575920109508', 19, 'TH03106', '01', 'K63ATTT', '', 3, 9, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(146, 'stcalendar1575920109509', 19, 'PTH02003', '02', 'K63MMT', '', 3, 6, 8, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(147, 'stcalendar1575920109506', 19, 'PTH02003', '02', 'K63MMT', '1', 3, 6, 10, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(148, 'stcalendar1575920109508', 19, 'PTH02003', '01', 'K63CNPMP', '', 3, 6, 8, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(149, 'stcalendar1575920109507', 19, 'TH02035', '04', 'K63HTTT', '', 1, 1, 5, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL),
(150, 'stcalendar1575920109506', 19, 'TH03106', '01', 'K63ATTT', '', 3, 6, 8, 1, '2019-12-10 02:35:31', '2019-12-10 02:35:31', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1),
(1);

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
  `status` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `semester`
--

INSERT INTO `semester` (`id`, `name`, `start_date`, `status`, `created_at`, `updated_at`) VALUES
('20182', 'Học kỳ 2 - Năm học 2018-2019', '2018-12-31', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
('20183', 'Học kỳ 3 - Năm học 2018-2019', '2019-05-13', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
('20191', 'Học kỳ 1 - Năm học 2019-2020', '2019-08-05', 0, '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL,
  `setting_name` text COLLATE utf8_unicode_ci NOT NULL,
  `settting_value` text COLLATE utf8_unicode_ci NOT NULL,
  `setting_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
(1, 'ttcnuser@gmail.com', 'anh', 'ta', 'url', 'cntt', 'K60THA', '$2a$11$XqSU43vdSWgowUXJC48d4OE1iI6pMePhVS2/JnHv8luPIwMxMT0H2', 1, '1//0ekPEgyeDijnvCgYIARAAGA4SNwF-L9Ir-OpCbsey0RlvLHvjR-rBzme2-Z1CNTS_lSFunjtrUmVPw2NPax6YPCyf5xkyFw5FhZY', '2019-12-10 00:35:31', '2019-12-10 00:35:31'),
(10, 'anhttmail@gmail.com', 'Tạ', 'Thế Anh', '', '', '', '$2a$11$LuH0XnSasV2vV1qvppXxZONhPDP4rE16F5.Vzu9N9tKB4O054JiIa', 1, NULL, '2019-05-17 20:07:26', '0000-00-00 00:00:00'),
(25, 'anhttemail@gmail.com', 'anh', 'ta', '', 'cntt', 'K60THA', '$2a$11$/WWdRZc3Uf2lYXy9i8VKXuUDNP06INii9DH8iS0V0mna.eNWfvPeC', 1, NULL, '2019-11-21 16:51:35', '2019-11-21 16:51:35'),
(38, 'nghiemthuhien1221@gmail.com', 'hien', 'nghiem', 'nn', 'cntt', 'K60THA', '$2a$11$s5DpRv.XtYWAlPh41uTr8OBXtHn.p/HuLS45sXYyDEVq10GaO1c2m', 1, NULL, '2019-11-23 17:02:35', '2019-11-23 17:02:35');

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
(1, 1),
(10, 1),
(25, 3),
(38, 1);

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
  ADD KEY `fk_calendar_detail_calenId` (`cd_calendar_id`),
  ADD KEY `fk_calendar_detail_semesId` (`semester_id`);

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_event_calenDetail_calenDetailId` (`calen_detail_id`),
  ADD KEY `fk_event_calendar_calendarId` (`evt_calendar_id`);

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
-- Indexes for table `settings`
--
ALTER TABLE `settings`
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `calendar_detail`
--
ALTER TABLE `calendar_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;

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
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

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
  ADD CONSTRAINT `fk_calendar_detail_calenId` FOREIGN KEY (`cd_calendar_id`) REFERENCES `calendar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_calendar_detail_semesId` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `fk_event_calenDetail_calenDetailId` FOREIGN KEY (`calen_detail_id`) REFERENCES `calendar_detail` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_event_calendar_calendarId` FOREIGN KEY (`evt_calendar_id`) REFERENCES `calendar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

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
