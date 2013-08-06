<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>websocket聊天室</title>
<style type="text/css">
#chat {
	text-align: left;
	width: 600px;
	height: 500px;
}

#up {
	text-align: left;
	width: 100%;
	height: 400px;
}

#down {
	text-align: left;
	height: 100px;
	width: 100%;
}
</style>
</head>
<body>
	<h2 align="center">基于HTML5的聊天室</h2>
	<div align="center" style="width: 100%; height: 700px;">
		<div id="chat">
			<div id="up">
				<textarea type="text" style="width: 100%; height: 100%;"
					readonly="readonly" id="receive"></textarea>
			</div>
			<div id="down">
				<textarea type="text" style="width: 100%; height: 100%;" id="send"></textarea>
			</div>
		</div>
		<input type="button" value="开始" onclick="chat(this);"> <input
			type="button" value="发送" onclick="send(this);" disabled="disabled"
			id="send_btn">
	</div>
</body>
<script type="text/javascript">
	var socket;
	var receive_text = document.getElementById("receive");
	var send_text = document.getElementById("send");
	function addText(msg) {
		receive_text.innerHTML += "\n" + msg;
	}
	var chat = function(obj) {
		obj.disabled = "disabled";
		socket = new WebSocket('ws://localhost:8080/TestWebsocket/SensorDataWebServlet');
		receive_text.innerHTML += "正在连接服务器……";
		//打开Socket
		socket.onopen = function(event) {
			addText("连接成功！");
			document.getElementById("send_btn").disabled = false;
		};
		socket.onmessage = function(event) {
			addText(event.data);
		};
		socket.onclose = function(event) {
			addText("连接断开！");
			obj.disabled = "";
		};
		if (socket == null) {
			addText("连接失败！");
		}
	};
	var send = function(obj) {
		socket.send(send_text.value);
		send_text.value = "";
	}
</script>
</html>