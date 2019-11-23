<?php

if($_SERVER['REQUEST_METHOD']=='GET'){

$type = $_GET['type'];

$quality = floatval($_GET['quality']);

$speed = floatval($_GET['speed']);

$touch_sensitivity = floatval($_GET['touch_sensitivity']);

$weight = floatval($_GET['weight']);

$durability = floatval($_GET['durability']);

$overall_rating = floatval($_GET['overall_rating']);

if($type === 'WM')
	$type = 'Washing Machine';

else if($type === 'RC')
	$type = 'Rice Cooker';

require_once('init.php');

if($type === 'All')
	$sql_1 = "SELECT * FROM product_reviews";
 
else
 	$sql_1 = "SELECT * FROM product_reviews WHERE type = '".$type."'";
 	
$res_1 = mysqli_query($con, $sql_1);

$temp = array();

while($row_1 = mysqli_fetch_array($res_1)){

$gd = round(sqrt(
			pow(($quality - $row_1['quality']), 2) +
			pow(($speed - $row_1['speed']), 2) +
			pow(($touch_sensitivity - $row_1['touch_sensitivity']), 2) +
			pow(($weight - $row_1['weight']), 2) +
			pow(($durability - $row_1['durability']), 2) +
			pow(($overall_rating - $row_1['overall_rating']), 2) ), 2);
			
array_push($temp, array("id"=>$row_1['id'], "gd"=>$gd));

}

usort($temp, 'sortByGd');

$result = array();

$count = 0;

while(($row_2 = $temp[$count]) && ($count < 3)){
	
	$sql_2 = "SELECT * FROM product_reviews WHERE id = '".$row_2['id']."'";
	
	$res_2 = mysqli_query($con, $sql_2);

    $row_3 = mysqli_fetch_array($res_2);

	array_push($result,array(	
		 
		 "type"=>$row_3['type'],
		 "name"=>$row_3['name'],
		 "quality"=>$row_3['quality'],
		 "speed"=>$row_3['speed'],
		 "touch_sensitivity"=>$row_3['touch_sensitivity'],	
		 "weight"=>$row_3['weight'],	
		 "durability"=>$row_3['durability'],	
		 "overall_rating"=>$row_3['overall_rating'],	
		 
	 )
	 );
	 
	 $count++;
}

echo json_encode(array("result"=>$result));
 
mysqli_close($con);

}

function sortByGd($x, $y) {
	
	if ($x['gd'] == $y['gd']) {
        return 0;
    }
    return ($x['gd'] < $y['gd']) ? -1 : 1;
	
}
 
?>