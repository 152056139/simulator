<?php
	$socket = socket_create(AF_INET,SOCK_STREAM,SOL_TCP);
	socket_connect($socket,'127.0.0.1',12580);
	while($callback = socket_read($socket,1024)){
		print_r($callback);
	}
	exit();
?>