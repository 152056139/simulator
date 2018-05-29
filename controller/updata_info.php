<?php
/**
 * Created by PhpStorm.
 * User: gray
 * Date: 2018/5/14
 * Time: 下午3:13
 */
include_once "../class/Database.php";
include_once "../class/Tools.php";

$conn = Database::getConnection();

$phone = $_POST['phone'];
$name = $_POST['name'];
$sex = $_POST['sex'];
$birthday = $_POST['birthday'];
$height = $_POST['height'];
$weight = $_POST['weight'];

// 判断用户是否存在
$sql_count = "SELECT  COUNT(user_phone) FROM `user` WHERE user_phone='$phone'";
$result_count = $conn->query($sql_count);
if ($result_count->fetch_row()[0] == 0) {
    Tools::response("failed", "该用户不存在");
} else {
    $sql = "UPDATE `user` SET user_name='{$name}', user_sex='{$sex}', user_birthday='{$birthday}', user_height='{$height}', user_weight='{$weight}' WHERE user_phone=$phone";
    if($conn->query($sql) == true)
    {
        Tools::response("success", "修改成功");
    }
    else
    {
        Tools::response("failed", "修改失败");
    }
}



