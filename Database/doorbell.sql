-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 29, 2018 at 04:39 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 7.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `doorbell`
--

-- --------------------------------------------------------

--
-- Table structure for table `block`
--

CREATE TABLE `block` (
  `id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `imagename` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `block`
--

INSERT INTO `block` (`id`, `name`, `imagename`) VALUES
(27, 'ccc', 'static/uploads/astefa.jpg'),
(28, 'nnnnn', 'static/uploads/1523391358109.png'),
(29, 'eee', 'static/uploads/asewil.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `id` int(10) NOT NULL,
  `imagename` text NOT NULL,
  `state` varchar(100) NOT NULL,
  `action` varchar(100) NOT NULL,
  `time` varchar(100) NOT NULL,
  `name` text NOT NULL,
  `relation` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `history`
--

INSERT INTO `history` (`id`, `imagename`, `state`, `action`, `time`, `name`, `relation`) VALUES
(1, 'static/uploads/slbirc.jpg', 'trusted', 'unlock', 'xxxx', 'aya', 'wife'),
(2, 'static/uploads/9336923.jpg', 'unkown', 'lock', 'xxxx', 'unkown', 'unkown'),
(3, 'static/uploads/9338535.jpg', 'block', 'lock', 'xxxx', 'bbb', 'block'),
(4, 'static/uploads/asamma1.jpg', 'unkown', 'unlock', 'xxxx', 'unkown', 'unkown'),
(5, 'static/uploads/sbains.jpg', 'trusted', 'unlock', 'xxxx', 'dina', 'sister'),
(6, 'static/uploads/test123920.png', 'unkown', 'unlock', '01:31 5-8-20018', 'unkown', 'unkown');

-- --------------------------------------------------------

--
-- Table structure for table `trusted`
--

CREATE TABLE `trusted` (
  `id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `relation` varchar(100) NOT NULL,
  `imagename` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `trusted`
--

INSERT INTO `trusted` (`id`, `name`, `relation`, `imagename`) VALUES
(1, 'omnia', 'owner', 'static/uploads/1523436285677.png'),
(2, 'ayaa', 'wifee', 'static/uploads/1523368094478.png'),
(3, 'dina', 'sister', 'static/uploads/1523369001437.png'),
(5, 'amal', 'mather', 'static/uploads/1524925429805.png'),
(7, ' darinnbn', 'sisters', 'static/uploads/1524925429805.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `block`
--
ALTER TABLE `block`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `trusted`
--
ALTER TABLE `trusted`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `block`
--
ALTER TABLE `block`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `trusted`
--
ALTER TABLE `trusted`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
