-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 18, 2017 at 07:04 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dm_assignment`
--

-- --------------------------------------------------------

--
-- Table structure for table `product_reviews`
--

CREATE TABLE `product_reviews` (
  `id` int(11) NOT NULL,
  `type` enum('Phone','Laptop','Camera','Washing Machine','Rice Cooker') NOT NULL,
  `name` text NOT NULL,
  `quality` float NOT NULL,
  `speed` float NOT NULL,
  `touch_sensitivity` float NOT NULL,
  `weight` float NOT NULL,
  `durability` float NOT NULL,
  `overall_rating` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product_reviews`
--

INSERT INTO `product_reviews` (`id`, `type`, `name`, `quality`, `speed`, `touch_sensitivity`, `weight`, `durability`, `overall_rating`) VALUES
(1, 'Phone', 'Apple', 4.5, 4.5, 4, 4, 4, 4.5),
(2, 'Washing Machine', 'Whirlpool', 4, 4.5, 4, 3.5, 4, 3.5),
(3, 'Phone', 'One Plus', 4, 3, 3.5, 3, 3.5, 3),
(4, 'Rice Cooker', 'Prestige', 4, 3, 4, 4.5, 4, 4),
(5, 'Laptop', 'Mac', 4, 4, 4, 5, 4, 4.5),
(6, 'Camera', 'Sony', 4, 4, 3, 3, 3.5, 3.5),
(7, 'Camera', 'Nikon', 4, 4, 4, 4, 4, 4),
(8, 'Rice Cooker', 'Aroma', 4, 4, 3, 3, 3, 3.5),
(9, 'Washing Machine', 'Bosch', 3, 4, 3.5, 4, 4, 4),
(10, 'Phone', 'Samsung', 4, 3.5, 4, 4, 4, 4),
(11, 'Washing Machine', 'Maytag', 3, 3.5, 3.5, 3.5, 4, 3.5),
(12, 'Camera', 'Olympus', 3, 4, 3.5, 3.5, 3.5, 3.5),
(13, 'Washing Machine', 'Haier', 3, 3, 2, 2, 2, 2.5),
(14, 'Rice Cooker', 'Panasonic', 1.5, 3, 3, 2.5, 2.5, 2.5),
(15, 'Phone', 'Philips', 4, 4, 4.5, 4.5, 4, 4),
(16, 'Washing Machine', 'LG', 3.5, 4, 4, 4, 3.5, 3.5),
(17, 'Phone', 'Sony', 3, 4, 3.5, 3.5, 4, 3.5),
(18, 'Laptop', 'Dell', 4, 3.5, 4, 3.5, 4, 4),
(19, 'Rice Cooker', 'Beetle', 3.5, 3.5, 4, 4, 3.5, 3.5),
(20, 'Laptop', 'HP', 4, 3.5, 5, 5, 4, 4),
(21, 'Rice Cooker', 'LG', 3.5, 4, 3.5, 4.5, 4, 4),
(22, 'Laptop', 'Lenovo', 3.5, 4, 4, 3.5, 4, 3.5),
(23, 'Camera', 'Canon', 3.5, 4, 3.5, 4.5, 4.5, 4),
(24, 'Laptop', 'Asus', 3.5, 4.5, 4.5, 4, 4.5, 4),
(25, 'Camera', 'Fuji Film', 4, 4.5, 4, 3.5, 4, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `product_reviews`
--
ALTER TABLE `product_reviews`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `product_reviews`
--
ALTER TABLE `product_reviews`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
