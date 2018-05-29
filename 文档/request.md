# login
## url: http://{{host}}:8080/controller/login.php
## method: POST
## params:
* phone
* password

## response
* status	状态(success/failed）
* message	消息（该用户不存在/登陆成功/密码错误，请确认后再试）

___
# register
## url: http://{{host}}:8080/controller/register.php
## method: POST
## params:
* phone
* password

## response
* status	状态(success/failed）
* message	消息（注册成功。/注册失败，请稍后再试。/手机号已存在）
___
# updata_info
## url: http://{{host}}:8080/controller/updata_info.php
## method: POST
## params:
* phone
* name
* sex
* birthday
* height
* weight

## response
* status	状态(success/failed）
* message	消息（修改成功/修改失败
