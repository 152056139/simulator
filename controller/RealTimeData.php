<?php
include_once('../class/RealTimeData.php');
$instance = RealTimeData::getInstance();


// 生成数据
if ($_GET['switch'] != null) {
    if($_GET['开始']){
        exec("php ../script.php");
    }else{
        
    }
}
// 修改数据
if ($_GET['id'] != null && $_GET['value'] != null) {
    $instance->changeData($_GET['id'], $_GET['value']);
    echo $_GET['id']."修改为".$_GET['value'];
}




