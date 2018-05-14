<?php
include_once "../class/Tools.php";

$driver = $_POST['driver'];
$file = $_FILES["file"];


if($driver==null){
    echo "用户名为空";
}else{
    echo "用户名为：" . $driver ."<br>";
    if ($file["error"] > 0) {
        echo "错误: " . $file["error"] . "<br>";
    } else {
        if (file_exists("../uploads/" . $driver . $file["name"])) {
            echo $file["name"] . " 文件已经存在。 ";
        } else {
            if (!is_dir("../uploads/" . $driver)) {
                mkdir("../uploads/" . $driver);
            }
            move_uploaded_file($file["tmp_name"], "../uploads/" . $driver . "/" . $file["name"]);
            Tools::log("DEBUG","文件".$driver . "/uploads/" . $file["name"]."存储成功");
            echo "文件存储在: " . $driver . "/uploads/" . $file["name"];
        }
    }
}



