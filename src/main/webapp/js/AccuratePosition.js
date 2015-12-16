/**
 * @see http://anone.me/html/geolocation-api/
 */
function getAccuratePosition(successCallback, errorCallback, option) {
  // 位置情報に対応していなければ終了
  if (!navigator.geolocation) {
    var error = new Object();
    error.code = 9;
    error.message = 'Geolocation APIが利用できません。';
    errorCallback(error);
    return;
  }

  // 変数の初期化
  var watch_id = undefined;
  var timer_id = undefined;
  var position = undefined;
  var limit = option && option.limit ? option.limit : 100;
  var timeout = option && option.timeout ? option.timeout : 0;

  // タイムアウトをセット
  if (timeout > 0) {
    timer_id = setTimeout(function() {
      // 位置情報の取得を中止する
      if (watch_id) {
        navigator.geolocation.clearWatch(watch_id);
        watch_id = undefined;
      }

      // 位置情報が取得できていればsuccessCallbackに送る
      if (position) {
        successCallback(position);
      } else {
        var error = new Object();
        error.code = 9;
        error.message = '位置情報の取得でタイムアウトしました。';
        errorCallback(error);
        return;
      }
    }, timeout);
  }

  // 取得を実行
  watch_id = navigator.geolocation.watchPosition(function(position) {
    // 取得のたびに更新する

    // 求める精度に達すればsuccessCallbackに送る
    if (position.coords.accuracy < limit) {
      // タイムアウト監視を止める
      if (timer_id) {
        clearTimeout(timer_id);
        timer_id = undefined;
      }
      navigator.geolocation.clearWatch(watch_id);
      successCallback(position);
    }
  }, function(e) {
    errorCallback(e);
  }, {
    enableHighAccuracy : true,
    maximumAge : 0
  });
}

function send(position) {
  if (position) {
    if (Wicket.WebSocket.INSTANCE.ws.readyState === 1) {
      var message = position.coords.latitude 
          + " , "
          + position.coords.longitude 
          + " , " 
          + position.coords.accuracy;
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
