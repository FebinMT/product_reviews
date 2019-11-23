<?php 

require_once('init.php');

$type = $_POST['type'];
$name = $_POST['name'];
$quality = $_POST['quality'];
$speed = $_POST['speed'];
$touch_sensitivity = $_POST['touch_sensitivity'];
$weight = $_POST['weight'];
$durability = $_POST['durability'];
$overall_rating = $_POST['overall_rating'];

$sql = "INSERT INTO product_reviews(type, name, quality, speed, touch_sensitivity, weight, durability, overall_rating) VALUES ('$type', '$name', '$quality', '$speed', '$touch_sensitivity', '$weight', '$durability', '$overall_rating')";

if(mysqli_query($con, $sql))
	echo "Your Review has been posted !";

else
	echo "Posting Failed !";

mysqli_close($con);

?>