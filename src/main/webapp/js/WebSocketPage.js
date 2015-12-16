window.onload = function() {

  setInterval(function() {
    if (Wicket.WebSocket.INSTANCE.ws.readyState === 1) {
      Wicket.WebSocket.send(new Date().getTime());
    }
  }, 1000);

  Wicket.Event.subscribe('/websocket/message', function(jqEvent, message) {
    var newDiv = document.createElement('div');
    newDiv.innerHTML = message;
    var target = document.getElementById('logs');
    target.appendChild(newDiv);
  });
}