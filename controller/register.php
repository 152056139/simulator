<?php
/**
 * Created by PhpStorm.
 * User: gray
 * Date: 2018/5/14
 * Time: 上午10:23
 */
include_once "../class/Database.php";
include_once "../class/Tools.php";
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
        Tools::response("success", "注册成功。");
    } else {
        Tools::response("failed", "注册失败，请稍后再试。");
    }
} else {
    Tools::response("failed", "手机号已存在");
}

$conn->close();
