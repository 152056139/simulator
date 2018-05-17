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
    $conf = simplexml_load_file("/Library/WebServer/Documents/simulator/simulator/conf.xml");
    if ($conf->switch == 'true') {
        echo "开关状态为打开\n";
        echo "开始生成数据\n";
        $data["low_pressure"] = sprintf("%.2f",
            randFloat($conf->min_low_pressure, $conf->max_low_pressure));
        $data["high_pressure"] = sprintf("%.2f",
            randFloat($conf->min_high_pressure, $conf->max_high_pressure));
        $data["heart_rate"] = sprintf("%.2f",
            randFloat($conf->min_heart_rate, $conf->max_heart_rate));
        $data["breathe_rate"] = sprintf("%.2f",
            randFloat($conf->min_breathe_rate, $conf->max_breathe_rate));
        $data["body_temperature"] = sprintf("%.2f",
            randFloat($conf->min_body_temperature, $conf->max_body_temperature));
        $data["car_rate"] = sprintf("%.2f",
            randFloat($conf->min_car_rate, $conf->max_car_rate));
        $data["car_temperature"] = sprintf("%.2f",
            randFloat($conf->min_car_temperature, $conf->max_car_temperature));
        $data["oxygen"] = sprintf("%.2f",
            randFloat($conf->min_oxygen, $conf->max_oxygen));
        $data["humidity"] = sprintf("%.2f",
            randFloat($conf->min_humidity, $conf->max_humidity));

        
        $msg = json_encode($data) . PHP_EOL;
        //Tools::log("DATA", $msg);
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

