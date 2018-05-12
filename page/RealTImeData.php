<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>数据配置</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<?php
include_once "../controller/RealTimeData.php";

?>
<body>
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        数据模拟器
                    </h3>
                    <button id="btn-start" class="btn btn-default" value="false"
                            style="position: absolute; right: 18px; top:3px;"
                            onclick="start()">开始
                    </button>
                </div>
                <div class="panel-body">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>title</th>
                            <th>value</th>
                            <th>max</th>
                            <th>min</th>
                        </tr>
                        </thead>
                        <tr>
                            <td>发送频率</td>
                            <td><input type="number" id="sleeptime" value="<?php echo $instance->sleeptime ?>"
                                       onkeypress="change(this.id)"
                                       onblur="change(this.id)"/></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>低压</td>
                            <td></td>
                            <td><label for="max_low_pressure"></label><input type="number" id="max_low_pressure"
                                                                             onkeypress="change(this.id)"
                                                                             onblur="change(this.id)"
                                                                             value="<?php echo $instance->max_low_pressure ?>">mmHg
                            </td>
                            <td><label for="min_low_pressure"></label><input type="number" id="min_low_pressure"
                                                                             onkeypress="change(this.id)"
                                                                             onblur="change(this.id)"
                                                                             value="<?php echo $instance->min_low_pressure ?>">mmHg
                            </td>
                        </tr>

                        <tr>
                            <td>高压</td>
                            <td></td>
                            <td><label for="max_high_pressure"></label><input type="number" id="max_high_pressure"
                                                                              onkeypress="change(this.id)"
                                                                              onblur="change(this.id)"
                                                                              value="<?php echo $instance->max_high_pressure ?>">mmHg
                            </td>
                            <td><label for="min_high_pressure"></label><input type="number" id="min_high_pressure"
                                                                              onkeypress="change(this.id)"
                                                                              onblur="change(this.id)"
                                                                              value="<?php echo $instance->min_high_pressure ?>">mmHg
                            </td>
                        </tr>
                        <tr>
                            <td>心率</td>
                            <td></td>
                            <td><label for="max_heart_rate"></label><input type="number" id="max_heart_rate"
                                                                           onkeypress="change(this.id)"
                                                                           onblur="change(this.id)"
                                                                           value="<?php echo $instance->max_heart_rate ?>">times/min
                            </td>
                            <td><label for="min_heart_rate"></label><input type="number" id="min_heart_rate"
                                                                           onkeypress="change(this.id)"
                                                                           onblur="change(this.id)"
                                                                           value="<?php echo $instance->min_heart_rate ?>">times/min
                            </td>
                        </tr>
                        <tr>
                            <td>呼吸频率</td>
                            <td></td>
                            <td><label for="max_breathe_rate"></label><input type="number" id="max_breathe_rate"
                                                                             onkeypress="change(this.id)"
                                                                             onblur="change(this.id)"
                                                                             value="<?php echo $instance->max_breathe_rate ?>">times/min
                            </td>
                            <td><label for="min_breathe_rate"></label><input type="number" id="min_breathe_rate"
                                                                             onkeypress="change(this.id)"
                                                                             onblur="change(this.id)"
                                                                             value="<?php echo $instance->min_breathe_rate ?>">times/min
                            </td>
                        </tr>
                        <tr>
                            <td>体温</td>
                            <td></td>
                            <td><label for="max_body_temperature">

                                </label><input type="number" id="max_body_temperature" onkeypress="change(this.id)"
                                               onblur="change(this.id)"
                                               value="<?php echo $instance->max_body_temperature ?>">℃
                            </td>
                            <td><label for="min_body_temperature"></label><input type="number" id="min_body_temperature"
                                                                                 onkeypress="change(this.id)"
                                                                                 onblur="change(this.id)"
                                                                                 value="<?php echo $instance->min_body_temperature ?>">℃
                            </td>
                        </tr>

                        <tr>
                            <td>车速</td>
                            <td></td>
                            <td><label for="max_car_rate"></label><input type="number" id="max_car_rate"
                                                                         onkeypress="change(this.id)"
                                                                         onblur="change(this.id)"
                                                                         value="<?php echo $instance->max_car_rate ?>">m/s
                            </td>
                            <td><label for="min_car_rate"></label><input type="number" id="min_car_rate"
                                                                         onkeypress="change(this.id)"
                                                                         onblur="change(this.id)"
                                                                         value="<?php echo $instance->min_car_rate ?>">m/s
                            </td>
                        </tr>
                        <tr>
                            <td>车内温度</td>
                            <td></td>
                            <td><label for="max_car_temperature"></label><input type="number" id="max_car_temperature"
                                                                                onkeypress="change(this.id)"
                                                                                onblur="change(this.id)"
                                                                                value="<?php echo $instance->max_car_temperature ?>">℃
                            </td>
                            <td><label for="min_car_temperature"></label><input type="number" id="min_car_temperature"
                                                                                onkeypress="change(this.id)"
                                                                                onblur="change(this.id)"
                                                                                value="<?php echo $instance->min_car_temperature ?>">℃
                            </td>
                        </tr>
                        <tr>
                            <td>含氧量</td>
                            <td></td>
                            <td><label for="max_oxygen"></label><input type="number" id="max_oxygen"
                                                                       onkeypress="change(this.id)"
                                                                       onblur="change(this.id)"
                                                                       value="<?php echo $instance->max_oxygen ?>">%
                            </td>
                            <td><label for="min_oxygen"></label><input type="number" id="min_oxygen"
                                                                       onkeypress="change(this.id)"
                                                                       onblur="change(this.id)"
                                                                       value="<?php echo $instance->min_oxygen ?>">%
                            </td>
                        </tr>
                        <tr>
                            <td>车内湿度</td>
                            <td></td>
                            <td><label for="max_humidity"></label><input type="number" id="max_humidity"
                                                                         onkeypress="change(this.id)"
                                                                         onblur="change(this.id)"
                                                                         value="<?php echo $instance->max_humidity ?>">%
                            </td>
                            <td><label for="min_humidity"></label><input type="number" id="min_humidity"
                                                                         onkeypress="change(this.id)"
                                                                         onblur="change(this.id)"
                                                                         value="<?php echo $instance->min_humidity ?>">%
                            </td>
                        </tr>
                    </table>
                    <div class="well">
                        <span id="text">准备就绪</span></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">


    function change(id_text) {
        if (event.keyCode === 13) {
            var xmlhttp = new XMLHttpRequest();
            var id = document.getElementById(id_text);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    document.getElementById("text").innerText = xmlhttp.responseText;
                }
            };
            xmlhttp.open("GET", "http://simulator.com:8080/controller/RealTimeData.php?id=" + id_text + "&value=" + id.value, true);
            xmlhttp.send();
        }
    }

    function start() {
        $btn = document.getElementById("btn-start").innerText;
        var xmlhttp;
        if ($btn === "开始") {
            xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    document.getElementById("text").innerText = xmlhttp.responseText;
                }
            };
            xmlhttp.open("GET", "http://simulator.com:8080/controller/RealTimeData.php?switch="+$btn, true);
            xmlhttp.send();
            document.getElementById("btn-start").innerText = "结束";
        } else {
            xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    document.getElementById("text").innerText = xmlhttp.responseText;
                }
            };
            xmlhttp.open("GET", "http://simulator.com:8080/controller/RealTimeData.php?switch="+$btn, true);
            xmlhttp.send();
            document.getElementById("btn-start").innerText = "开始";
        }
    }
</script>
</html>