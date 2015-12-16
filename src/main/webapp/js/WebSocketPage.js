window.onload = function() {
	var ws = new Wicket.WebSocket();
	setInterval(function() {ws.send('message');}, 1000);
}