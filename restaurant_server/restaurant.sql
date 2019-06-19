-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 02, 2019 lúc 02:52 AM
-- Phiên bản máy phục vụ: 10.1.38-MariaDB
-- Phiên bản PHP: 7.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `restaurant`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart`
--

CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `table_number` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `cart`
--

INSERT INTO `cart` (`id`, `user_id`, `table_number`, `price`, `time`, `status`) VALUES
(1559232737369, '983325268540462', '101', 1520000, '2019-05-30 16:12:21', 2),
(1559233390703, '983325268540462', '101', 1320000, '2019-05-30 16:23:12', 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `cart_item`
--

CREATE TABLE `cart_item` (
  `no` bigint(20) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  `cart_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `cart_item`
--

INSERT INTO `cart_item` (`no`, `item_id`, `quantity`, `price`, `cart_id`) VALUES
(1, 9, 2, 300000, 1559232737369),
(2, 15, 2, 160000, 1559232737369),
(3, 16, 1, 50000, 1559232737369),
(4, 38, 1, 120000, 1559232737369),
(5, 39, 2, 240000, 1559232737369),
(6, 37, 2, 500000, 1559232737369),
(7, 31, 1, 150000, 1559232737369),
(8, 22, 2, 400000, 1559233390703),
(9, 23, 1, 180000, 1559233390703),
(10, 1, 2, 300000, 1559233390703),
(11, 2, 2, 440000, 1559233390703);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `device`
--

CREATE TABLE `device` (
  `id` bigint(20) NOT NULL,
  `token` text COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `device`
--

INSERT INTO `device` (`id`, `token`, `user_id`) VALUES
(10, 'dwikIvAkWHs:APA91bFVJ9cgNicueZ542lHEI2MI9ZrLRzNJeZjnPntTJGgHTiIZkXfaPr9NRWJha6mgPwyC5iWF4y8esX3ppnfwvEYMe3nQ0JYmBBToPdLn-wqp-GVFz_YFjUx9QlEbFiJ06iEF87lo', '103275094606615049195'),
(11, 'cthLk2Ja8_w:APA91bH-yCW41Dq2HKRXhzZFFevK9Tt6ZnTa8rCpKvYs2qkeMUzyY3Gh1BBjefjc9U-bQn64a-EqexXfV3dPit4ahGpGt3PSiD77dGonz_nZYuisw4lTIH7QKJQhN9UgMBqDZ9jzRlQQ', '983325268540462'),
(12, 'fncYPF3bp-0:APA91bEy9qxGwLkCykI7WERMBuj3xbwSgz_YxTS-anSaZQBB6DOj6o2sPWE1fTi6qTSYJrdt-Bl0cT4MbSChhxgPPqaZ7qrWtpguB_r6dn34SzGjOjpUPQPOmUh4wLHjT5kn8yhJIF4k', '103275094606615049195');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `menu` text COLLATE utf8_unicode_ci NOT NULL,
  `price` double NOT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `image` text COLLATE utf8_unicode_ci NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `item`
--

INSERT INTO `item` (`id`, `name`, `menu`, `price`, `description`, `image`, `status`) VALUES
(1, 'Sweet and Sour Chicken', 'Popular', 150000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/211812300519_Sweet and Sour Chicken.jpg', 1),
(2, 'Chicken Lo Mein', 'Popular', 220000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212224300519_Chicken Lo Mein.jpg', 1),
(3, 'Chicken Fried Rice', 'Popular', 100000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212248300519_Chicken Fried Rice.jpg', 1),
(4, 'Chicken Egg Roll', 'Popular', 240000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212317300519_Chicken Egg Roll.jpg', 1),
(5, 'Crab Rangoon', 'Popular', 80000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212343300519_Crab Rangoon.jpg', 1),
(6, 'Pot Stickers', 'Popular', 69999, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212405300519_Pot Stickers.jpg', 1),
(7, 'Steamed Dumpling', 'Popular', 125000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212443300519_Steamed Dumpling.jpg', 1),
(8, 'Egg Roll', 'Popular', 60000, 'This is china popular food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/212507300519_eggroll.jpg', 1),
(9, 'Beef Teriyaki', 'Appetizers', 150000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/220835300519_Beef Teriyaki.jpg', 1),
(10, 'Seafood with vegetables Soup', 'Soups', 250000, 'This is china soup. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/220930300519_seafood with vegetables Soup.jpg', 1),
(11, 'Chicken Cream Corn Soup', 'Soups', 155000, 'This is china soup. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221002300519_Chicken Cream Corn Soup.jpeg', 1),
(12, 'Egg Drop Soup', 'Soups', 70000, 'This is china soup. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221031300519_Egg Drop Soup.jpg', 1),
(13, 'Wonton Soup', 'Soups', 50000, 'This is china soup. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221109300519_Wonton Soup.jpg', 1),
(14, 'Hot and Sour Soup', 'Soups', 125000, 'This is china soup. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221146300519_Hot and Sour Soup.jpg', 1),
(15, 'Scallion Pancakes', 'Appetizers', 80000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221213300519_Scallion Pancakes.jpg', 1),
(16, 'Shrimp Toast', 'Appetizers', 50000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221235300519_Shrimp Toast.jpg', 1),
(17, 'Shrimp Roll', 'Appetizers', 80000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221255300519_Shrimp Roll.jpg', 1),
(18, 'Fried Chicken Wings', 'Appetizers', 100000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221315300519_Fried Chicken Wings.jpeg', 1),
(19, 'Fried Wonton', 'Appetizers', 45000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221336300519_Fried Wonton.jpg', 1),
(20, 'Chicken Teriyaki', 'Appetizers', 150000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221407300519_Chicken Teriyaki.jpg', 1),
(21, 'Chicken Lo Mein', 'Appetizers', 220000, 'This is china Appetizer food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221425300519_Chicken Lo Mein.jpg', 1),
(22, 'BBQ Pork Fried Rice', 'Fried Rice Dinner', 200000, 'This is china Fried Rice Dinner food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221902300519_BBQ Pork Fried Rice.jpg', 1),
(23, 'Chicken Fried Rice', 'Fried Rice Dinner', 180000, 'This is china Fried Rice Dinner food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221923300519_Chicken Fried Rice.png', 1),
(24, 'Vegetable Fried Rice', 'Fried Rice Dinner', 50000, 'This is china Fried Rice Dinner food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/221946300519_Vegetable Fried Rice.jpg', 1),
(25, 'Combo Pad Thai', 'Pad Thai', 200000, 'This is Pad Thai food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/222236300519_Combo Pad Thai.jpg', 1),
(26, 'Shrimp Pad Thai', 'Pad Thai', 50000, 'This is Pad Thai food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/222258300519_Shrimp Pad Thai.jpg', 1),
(27, 'Chicken Pad Thai', 'Pad Thai', 100000, 'This is Pad Thai food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/222317300519_Checken Pad Thai.jpg', 1),
(29, 'Beef with Vegetables', 'Beef', 250000, 'This is china beef food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225240300519_Beef with Vegetables.jpg', 1),
(30, 'Sweet and Sour Chicken', 'Poultry', 200000, 'This is Poultry food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225312300519_Sweet and Sour Chicken.jpg', 1),
(31, 'Hunan Chicken', 'Poultry', 150000, 'This is Poultry food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225359300519_Hunan Chicken.jpg', 1),
(32, 'Country Bean Curd', 'Vegetables', 100000, 'This is Vegetables food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225419300519_Country Bean Curd.jpg', 1),
(33, 'Moo Shu Vegetable', 'Vegetables', 80000, 'This is Vegetables food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225508300519_Moo Shu Vegetable.jpg', 1),
(34, 'Vegetable Deluxe', 'Vegetables', 50000, 'This is Vegetables food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225547300519_Vegetable Deluxe.png', 1),
(35, 'Chicken Chop Suey', 'Chop Suey', 100000, 'This is Chop Suey food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225614300519_Chicken Chop Suey.jpg', 1),
(36, 'Vegetables Chop Suey', 'Chop Suey', 120000, 'This is Chop Suey food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225647300519_Vegetables Chop Suey.jpg', 1),
(37, 'Roast Pork Chow Mein', 'Chow Mein', 250000, 'This is Chow Mein food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225713300519_Roast Pork Chow Mein.jpg', 1),
(38, 'Chicken Chow Mein', 'Chow Mein', 120000, 'This is Chow Mein food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225734300519_Chicken Chow Mein.jpg', 1),
(39, 'Vegetables Chow Mein', 'Chow Mein', 120000, 'This is Chow Mein food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225755300519_Vegetables Chow Mein.jpg', 1),
(40, 'Zha Bon Jiang Noodle Soup', 'Zha Jiang Noodle', 120000, 'This is Zha Jiang Noodle food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225835300519_Zha Bon Jiang Noodle Soup.jpg', 1),
(41, 'Zha Jiang Noodle', 'Zha Jiang Noodle', 100000, 'This is Zha Jiang Noodle food. Try it and enjoy.', 'http://192.168.0.73:80/restaurant/images/item/225902300519_Zha Jiang Noodle.jpg', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `menu`
--

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `image` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `menu`
--

INSERT INTO `menu` (`id`, `name`, `image`) VALUES
(1, 'Popular', 'http://192.168.0.73:80/restaurant/images/menu/204500300519_Sweet and Sour Chicken.jpg'),
(2, 'Appetizers', 'http://192.168.0.73:80/restaurant/images/menu/220811300519_Appetizers.jpg'),
(3, 'Soups', 'http://192.168.0.73:80/restaurant/images/menu/220911300519_Chicken Cream Corn Soup.jpeg'),
(4, 'Fried Rice Dinner', 'http://192.168.0.73:80/restaurant/images/menu/221849300519_Chicken Fried Rice.png'),
(5, 'Pad Thai', 'http://192.168.0.73:80/restaurant/images/menu/222224300519_Checken Pad Thai.jpg'),
(6, 'Zha Jiang Noodle', 'http://192.168.0.73:80/restaurant/images/menu/224703300519_Zha Jiang Noodle.jpg'),
(7, 'Chow Mein', 'http://192.168.0.73:80/restaurant/images/menu/224847300519_Chicken Chow Mein.jpg'),
(8, 'Chop Suey', 'http://192.168.0.73:80/restaurant/images/menu/224940300519_Vegetables Chop Suey.jpg'),
(9, 'Vegetables', 'http://192.168.0.73:80/restaurant/images/menu/225015300519_Vegetable Deluxe.png'),
(10, 'Poultry', 'http://192.168.0.73:80/restaurant/images/menu/225200300519_Sweet and Sour Chicken.jpg'),
(11, 'Beef', 'http://192.168.0.73:80/restaurant/images/menu/225225300519_Beef with Vegetables.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `r_table`
--

CREATE TABLE `r_table` (
  `number` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `type` text COLLATE utf8_unicode_ci NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `current_user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `r_table`
--

INSERT INTO `r_table` (`number`, `type`, `status`, `current_user_id`) VALUES
('01', 'Table 4 People', 0, '983325268540462'),
('02', 'Table 6 People', 1, ''),
('04', 'Table 6 People', 1, ''),
('06', 'Table 6 People', 1, ''),
('09', 'Table 6 People', 1, ''),
('10', 'Table 4 People', 0, ''),
('101', 'Table 4 People', 0, '983325268540462'),
('102', 'Table 4 People', 0, ''),
('11', 'Table 4 People', 0, ''),
('12', 'Table 4 People', 1, ''),
('13', 'Table 4 People', 1, ''),
('14', 'Table 6 People', 1, ''),
('15', 'Table 6 People', 1, ''),
('16', 'Table 4 People', 1, ''),
('17', 'Table 6 People', 1, ''),
('21', 'Table 4 People', 1, ''),
('22', 'Table 6 People', 1, ''),
('23', 'Table 4 People', 1, ''),
('25', 'Table 4 People', 1, ''),
('26', 'Table 6 People', 1, '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `table_booking`
--

CREATE TABLE `table_booking` (
  `id` bigint(20) NOT NULL,
  `table_number` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `time_booking` text COLLATE utf8_unicode_ci NOT NULL,
  `user_id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `phone_booking` text COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `table_booking`
--

INSERT INTO `table_booking` (`id`, `table_number`, `time_booking`, `user_id`, `phone_booking`) VALUES
(1, '04', '20/12/2019 from 15:00 to 20:00', '1', '0978850869'),
(17, '12', '17/05/2019 from 10 : 49 to 10 : 49', '110242388920245302018', '+84978850868'),
(18, '12', '17/05/2019 from 10 : 50 to 10 : 50', '110242388920245302018', '+84978850868'),
(19, '12', '17/05/2019 from 10 : 51 to 10 : 51', '110242388920245302018', '+84978850868'),
(20, '13', '17/05/2019 from 10 : 52 to 10 : 52', '110242388920245302018', '+84978850868'),
(21, '13', '17/05/2019 from 14 : 53 to 15 : 53', '110242388920245302018', '+84978850868'),
(22, '13', '17/05/2019 from 10 : 54 to 11 : 54', '110242388920245302018', '+84978850868'),
(23, '13', '17/05/2019 from 12 : 55 to 01 : 55', '110242388920245302018', '+84978850868'),
(24, '13', '17/05/2019 from 10 : 56 to 10 : 56', '110242388920245302018', '+84978850868'),
(25, '13', '17/05/2019 from 13 : 56 to 13 : 56', '110242388920245302018', '+84978850868'),
(26, '13', '17/05/2019 from 10 : 57 to 10 : 57', '110242388920245302018', '+84978850868'),
(27, '13', '17/05/2019 from 11 : 00 to 11 : 00', '110242388920245302018', '+84978850868'),
(28, '13', '17/05/2019 from 11 : 03 to 11 : 03', '110242388920245302018', '+84978850868'),
(29, '13', '17/05/2019 from 13 : 04 to 13 : 04', '110242388920245302018', '+84978850868'),
(30, '101', '17/05/2019 from 22 : 05 to 22 : 05', '110242388920245302018', '+84978850868'),
(31, '13', '17/05/2019 from 04 : 06 to 04 : 06', '110242388920245302018', '+84978850868'),
(32, '13', '17/05/2019 from 12 : 21 to 12 : 21', '110242388920245302018', '+84978850868'),
(33, '101', '17/05/2019 from 12 : 22 to 12 : 22', '110242388920245302018', '+84978850868'),
(34, '12', '17/05/2019 from 12 : 24 to 12 : 24', '110242388920245302018', '+84978850868'),
(35, '16', '17/05/2019 from 12 : 25 to 12 : 25', '110242388920245302018', '+84978850868'),
(36, '21', '17/05/2019 from 12 : 30 to 12 : 30', '110242388920245302018', '+84978850868'),
(37, '12', '18/05/2019 from 09 : 58 to 09 : 58', '1', '0978850868'),
(38, '21', '18/05/2019 from 09 : 59 to 09 : 59', '1', '0978850868'),
(39, '21', '18/05/2019 from 10 : 01 to 10 : 01', '1', '0978850868'),
(40, '21', '18/05/2019 from 10 : 06 to 10 : 06', '1', '0978850868'),
(41, '16', '18/05/2019 from 10 : 15 to 10 : 15', '1', '0978850868'),
(42, '16', '18/05/2019 from 10 : 19 to 10 : 19', '1', '0978850868'),
(43, '16', '18/05/2019 from 10 : 19 to 15 : 19', '1', '0978850868'),
(44, '12', '19/05/2019 from 23 : 56 to 12 : 56', '1', '0978850868'),
(45, '01', '23/05/2019 from 05 : 49 to 06 : 49', '983325268540462', '+84978850868'),
(46, '101', '27/05/2019 from 21 : 43 to 23 : 43', '983325268540462', '089788085'),
(47, '12', '31/05/2019 from 18 : 15 to 20 : 15', '983325268540462', '097850888');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `name` text COLLATE utf8_unicode_ci NOT NULL,
  `dob` text COLLATE utf8_unicode_ci NOT NULL,
  `email` text COLLATE utf8_unicode_ci NOT NULL,
  `phone` text COLLATE utf8_unicode_ci NOT NULL,
  `password` text COLLATE utf8_unicode_ci NOT NULL,
  `image` text COLLATE utf8_unicode_ci,
  `is_admin` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `name`, `dob`, `email`, `phone`, `password`, `image`, `is_admin`) VALUES
('1', 'Tran Canh', '20/12/2000', 'trvcanh97@gmail.com', '0978850868', '1234', '1234', 0),
('103275094606615049195', 'Cảnh Trần', '', 'trancanh1861997@gmail.com', '0121121515', '1234', NULL, 1),
('110242388920245302018', 'Tran Van Canh', '', 'tran.van.canh@sun-asterisk.com', '', '', NULL, 0),
('983325268540462', 'Trần Cảnh', '06/18/1997', 'trancanh1861997.info@gmail.com', '', '1234', NULL, 0);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `cart_item`
--
ALTER TABLE `cart_item`
  ADD PRIMARY KEY (`no`);

--
-- Chỉ mục cho bảng `device`
--
ALTER TABLE `device`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `r_table`
--
ALTER TABLE `r_table`
  ADD PRIMARY KEY (`number`);

--
-- Chỉ mục cho bảng `table_booking`
--
ALTER TABLE `table_booking`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `cart_item`
--
ALTER TABLE `cart_item`
  MODIFY `no` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `device`
--
ALTER TABLE `device`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT cho bảng `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `table_booking`
--
ALTER TABLE `table_booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
