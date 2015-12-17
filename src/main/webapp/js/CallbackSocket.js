function send(position) {
  if (position) {
    if (Wicket.WebSocket.INSTANCE.ws.readyState === 1) {
      var message = position.coords.latitude + " , "
          + position.coords.longitude + " , " + position.coords.accuracy;
      Wicket.WebSocket.send(message);
    }
  }
}

window.onload = function() {
  setInterval(function() {
    getAccuratePosition(function(position) {
      send(position);
    }, function(error) {
      console.error('失敗：' + error.message + '(' + error.code + ')')
    }, {
      limit : 50,
      timeout : 4900
    });
  }, 5000);

  Wicket.Event.subscribe('/websocket/message', function(jqEvent, message) {
    var newDiv = document.createElement('div');
    newDiv.innerHTML = message;
    var target = document.getElementById('logs');
    target.appendChild(newDiv);
  });
}
