<?php
/**
 * Created by PhpStorm.
 * User: gray
 * Date: 2018/5/10
 * Time: 下午6:54
 */

$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);
echo "创建socket\n";
if (socket_bind($socket, "0.0.0.0", 12580) == false) {
    echo 'server bind fail:' . socket_strerror(socket_last_error());
}
echo "绑定ip和端口\n";
if (socket_listen($socket, 4) == false) {
    echo 'server listen fail:' . socket_strerror(socket_last_error());
}
echo "监听socket\n";
echo "等待链接。。。。\n";
$accept_resource = socket_accept($socket);
echo "接收到链接\n";
while (true) {
    echo "读取配置文件\n";
    $conf = simplexml_load_file("./conf.xml");
    $acceleration = (Float)$conf->acceleration;
    if ($conf->switch == 'true') {
        echo "开关状态为打开\n";
        echo "开始生成数据\n";

        $data["low_pressure"] = getData($conf, "low_pressure", $acceleration);
        $data["high_pressure"] = getData($conf, "high_pressure", $acceleration);
        $data["heart_rate"] = getData($conf, "heart_rate", $acceleration);
        $data["breathe_rate"] = getData($conf, "breathe_rate", $acceleration);
        $data["body_temperature"] = getData($conf, "body_temperature", $acceleration);
        $data["car_rate"] = getData($conf, "car_rate", $acceleration);
        $data["car_temperature"] = getData($conf, "car_temperature", $acceleration);
        $data["oxygen"] = getData($conf, "oxygen", $acceleration);
        $data["humidity"] = getData($conf, "humidity", $acceleration);


        $msg = json_encode($data) . PHP_EOL;

        print_r($msg);

        echo "发送socket\n";
        if (socket_write($accept_resource, $msg, strlen($msg)) === false) {
            echo "发送失败, 链接的设备断开\n";
            echo "关闭连接\n";
            socket_close($accept_resource);
            echo "再次等待链接\n";
            $accept_resource = socket_accept($socket);
        }
    } else {
        echo "开关为关闭状态，退出程序\n";
        exit();
    }
    echo "睡眠时间\n";
    sleep((int)$conf->sleeptime);
}
echo "退出程序\n";
exit();

function randFloat($min = 0, $max = 1)
{
    return $min + mt_rand() / mt_getrandmax() * ($max - $min);
}

function getData($conf, $name, $acceleration)
{
    $strvalue = "value_" . $name;
    $strmin = "min_" . $name;
    $strmax = "max_" . $name;
        
    $value = (Float)$conf->$strvalue;
    $min = (Float)$conf->$strmin;
    $max = (Float)$conf->$strmax;

    if ($value < $min) {
        $data = sprintf("%.2f", $min + 1);
    } else if ($value > $max) {
        $data = sprintf("%.2f", $max - 1);
    } else {
        if (rand(0, 1) == 0) {
            $data = sprintf("%.2f", ($value - $value * $acceleration));
        } else {
            $data = sprintf("%.2f", ($value + $value * $acceleration));
        }
    }
    $conf->$strvalue = $data;
    $new_conf = $conf->asXML();
    $fp = fopen("./conf.xml", "w+");
    fwrite($fp, $new_conf);
    fclose($fp);
    return $data;
}

