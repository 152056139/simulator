<?php
/**
 * Created by PhpStorm.
 * User: gray
 * Date: 2018/5/8
 * Time: 上午9:26
 */

class RealTimeData
{
    static private $instance;

    public $max_low_pressure;
    public $min_low_pressure;
    public $max_high_pressure;
    public $min_high_pressure;
    public $max_heart_rate;
    public $min_heart_rate;
    public $max_breathe_rate;
    public $min_breathe_rate;
    public $max_body_temperature;
    public $min_body_temperature;
    public $max_car_rate;
    public $min_car_rate;
    public $max_car_temperature;
    public $min_car_temperature;
    public $max_oxygen;
    public $min_oxygen;
    public $max_humidity;
    public $min_humidity;
    public $sleeptime;


    private function __construct()
    {
        $conf = simplexml_load_file("../simulator/conf.xml");
        foreach ($conf as $key => $value) {
            $this->$key = $value;
        }
    }

    static public function getInstance()
    {
        //判断$instance是否是Uni的对象
        //没有则创建
        if (!self::$instance instanceof self) {
            self::$instance = new self();
        }
        return self::$instance;
    }

    private function __clone()
    {

    }



    function changeData($key, $value)
    {
        $conf = simplexml_load_file("../simulator/conf.xml");
        $conf->$key = $value;
        $new_conf = $conf->asXML();
        $fp = fopen("../simulator/conf.xml", "w+");
        fwrite($fp, $new_conf);
        fclose($fp);
    }

}