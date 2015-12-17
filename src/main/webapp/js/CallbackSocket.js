function send(position) {
  if (position) {
    // WebSocket で接続可能な時、サーバにメッセージを送信する
    if (Wicket.WebSocket.INSTANCE.ws.readyState === 1) {
      var message = position.coords.latitude + " , "
          + position.coords.longitude + " , " + position.coords.accuracy;
      Wicket.WebSocket.send(message);
    }
  }
}

window.onload = function() {
  var option = {
    limit : 50,
    timeout : 4900
  }

  setInterval(function() {
    getAccuratePosition(function(position) {
      send(position);
    }, function(error) {
      console.error('失敗：' + error.message + '(' + error.code + ')')
    }, option);
  }, 5000);

  // WebSocket でサーバからメッセージが送信されたら、htmlを書き換える
  Wicket.Event.subscribe('/websocket/message', function(jqEvent, message) {
    var newDiv = document.createElement('div');
    newDiv.innerHTML = message;
    var target = document.getElementById('logs');
    target.appendChild(newDiv);
  });
}
