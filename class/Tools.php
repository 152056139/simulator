<?php

class Tools
{
    static public function log($TAG, $message)
    {
        file_put_contents("../log.txt", $TAG . "  " . date('y-m-d h:i:s', time()) . "	    " . __FILE__ . "	" . $message."\n", FILE_APPEND | LOCK_EX);
    }
}