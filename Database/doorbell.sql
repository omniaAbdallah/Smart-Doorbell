-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 05, 2018 at 05:29 PM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

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
  `imagename` text NOT NULL,
  `image1` text NOT NULL,
  `image2` text NOT NULL,
  `image3` text NOT NULL,
  `image4` text NOT NULL,
  `image5` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `block`
--

INSERT INTO `block` (`id`, `name`, `imagename`, `image1`, `image2`, `image3`, `image4`, `image5`) VALUES
(27, 'ccc', 'static/uploads/yfhsie.6.jpg', 'static/uploads/yfhsie.5.jpg', 'static/uploads/yfhsie.4.jpg', 'static/uploads/yfhsie.3.jpg', 'static/uploads/yfhsie.2.jpg', 'static/uploads/yfhsie.1.jpg'),
(28, 'oooo', 'static/uploads/1527520856742.png', 'static/uploads/phughe.5.jpg', 'static/uploads/phughe.4.jpg', 'static/uploads/phughe.3.jpg', 'static/uploads/phughe.2.jpg', 'static/uploads/phughe.1.jpg'),
(29, 'eee', 'static/uploads/lfso.1.jpg', 'static/uploads/lfso.2.jpg', 'static/uploads/lfso.3.jpg', 'static/uploads/lfso.4.jpg', 'static/uploads/lfso.5.jpg', 'static/uploads/lfso.6.jpg');

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
(6, 'static/uploads/test123920.png', 'unkown', 'unlock', '01:31 5-8-20018', 'unkown', 'unkown'),
(7, 'static/uploads/test12028.png', 'unkown', 'unlock', '01:31 5-8-20018', 'unkown', 'unkown'),
(8, 'static/uploads/test155949.png', 'unkown', 'unlock', '01:31 5-8-20018', 'unkown', 'unkown'),
(9, 'test-data/sbains.15.jpg', 'trusted', 'lock', '2018-05-28 16:54', 'omniaa', 'owner'),
(10, 'test-data/sbains.15.jpg', 'trusted', 'lock', '2018-06-02 11:09', 'omniaa', 'owner'),
(11, 'test-data/test(6).jpg', 'trusted', 'lock', '2018-06-02 11:12', 'omniaa', 'owner'),
(12, 'test-data/test(6).jpg', 'trusted', 'lock', '2018-06-02 11:54', 'omniaa', 'owner'),
(13, 'static/uploads/', 'block', 'nulock', '2018-06-03 09:12', 'oooo', 'block'),
(14, 'static/uploads/', 'block', 'nulock', '2018-06-03 09:27', 'oooo', 'block'),
(15, 'static/uploads/', 'block', 'nulock', '2018-06-03 10:48', 'oooo', 'block'),
(16, 'static/uploads/', 'block', 'nulock', '2018-06-03 11:20', 'oooo', 'block');

-- --------------------------------------------------------

--
-- Table structure for table `trusted`
--

CREATE TABLE `trusted` (
  `id` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `relation` varchar(100) NOT NULL,
  `imagename` text NOT NULL,
  `image1` text NOT NULL,
  `image2` text NOT NULL,
  `image3` text NOT NULL,
  `image4` text NOT NULL,
  `image5` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `trusted`
--

INSERT INTO `trusted` (`id`, `name`, `relation`, `imagename`, `image1`, `image2`, `image3`, `image4`, `image5`) VALUES
(1, 'omniaa', 'owner', 'static/uploads/1526107551733.png', 'static/uploads/sbains.7.jpg', 'static/uploads/sbains.8.jpg', 'static/uploads/sbains.9.jpg', 'static/uploads/sbains.10.jpg', 'static/uploads/sbains.11.jpg'),
(2, 'ayaa', 'wifee', 'static/uploads/klclar.6.jpg', 'static/uploads/klclar.7.jpg', 'static/uploads/klclar.8.jpg', 'static/uploads/klclar.9.jpg', 'static/uploads/klclar.10.jpg', 'static/uploads/klclar.11.jpg'),
(3, 'dina', 'sister', 'static/uploads/ekavaz.6.jpg', 'static/uploads/ekavaz.7.jpg', 'static/uploads/ekavaz.8.jpg', 'static/uploads/ekavaz.9.jpg', 'static/uploads/ekavaz.10.jpg', 'static/uploads/ekavaz.11.jpg'),
(5, 'amal', 'mather', 'static/uploads/astefa.6.jpg', 'static/uploads/astefa.7.jpg', 'static/uploads/astefa.8.jpg', 'static/uploads/astefa.9.jpg', 'static/uploads/astefa.10.jpg', 'static/uploads/astefa.11.jpg'),
(7, ' darin', 'sisters', 'static/uploads/1526107571564.png', '	 static/uploads/slbirc.7.jpg', '	 static/uploads/slbirc.8.jpg', '	 static/uploads/slbirc.9.jpg', '	 static/uploads/slbirc.10.jpg', '	 static/uploads/slbirc.11.jpg');

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
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `trusted`
--
ALTER TABLE `trusted`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
