function send(position) {
  if (position) {
    // Wicketで生成した sendToServer(latitude, longitude, accuracy) を呼び出す
    sendToServer(position.coords.latitude, position.coords.longitude,
        position.coords.accuracy);
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
}
