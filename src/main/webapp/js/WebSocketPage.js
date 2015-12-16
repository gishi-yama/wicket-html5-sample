window.onload = function() {
	var wicketWS = new Wicket.WebSocket();
	setInterval(function() {
		if (wicketWS.ws.readyState === 1) {
			wicketWS.send('message')
		}
	}, 1000);
}