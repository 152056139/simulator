<?php
/**
 * Created by PhpStorm.
 * User: gray
 * Date: 2018/5/14
 * Time: 上午10:23
 */
include_once "../class/Database.php";

// 接收表单数据
$phone = $_POST['phone'];
$password = $_POST['password'];
// 将密码进行加密
$password = md5($password);

// 存储到数据库
$conn = Database::getConnection();

$sql_count = "SELECT  COUNT(user_phone) FROM `user` WHERE user_phone='$phone'";
$result = $conn->query($sql_count);

if ($result->fetch_row()[0] == 0) {
    $sql = "INSERT INTO user (user_phone, user_password) VALUES ($phone, '$password')";
    if ($conn->query($sql) === true) {

        echo "用户注册成功";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }
} else {
    echo "手机号已存在";
}

$conn->close();
