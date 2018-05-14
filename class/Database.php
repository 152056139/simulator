<?php
/**
 * Created by PhpStorm.
 * User: gray
 * Date: 2018/5/14
 * Time: 上午10:27
 */

class Database
{
    static public $servername = "127.0.0.1";
    static public $username = "root";
    static public $password = "root";
    static public $databasename = "car";

    static public function getConnection()
    {
        // 创建连接
        $conn = new mysqli(self::$servername, self::$username, self::$password, self::$databasename);

        // 检测连接
        if ($conn->connect_error) {
            die("连接失败: " . $conn->connect_error);
        }

        return $conn;
    }
}