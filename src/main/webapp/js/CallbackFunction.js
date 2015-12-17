function send(position) {
  if (position) {
    sendToServer(
        position.coords.latitude, 
        position.coords.longitude,
        position.coords.accuracy);
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
}
