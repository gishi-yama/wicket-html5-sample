function getAccuratePosition(successCallback, errorCallback, option) {
  console.log("hello4");
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
  var limit = option && option.limit ? option.limit : 30;
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
  watch_id = navigator.geolocation.watchPosition(function(p) {
    // 取得のたびに更新する
    position = p;

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
// 取得を実行
function onGeoSuccess(event) {
  console.log("hello5");
  document.getElementById("Latitude").value = event.coords.latitude;
  document.getElementById("Longitude").value = event.coords.longitude;
  document.getElementById("Accuracy").value = event.coords.accuracy;
  document.myform.submit();
}
function onGeoError(event) {
  console.log("hello6");
  console.log("error : " + event.message + '(' + error.code + ')');
}
var optionObj = {
  timeout : 10000
}

function setLatlon() {
  console.log("hello3");
  getAccuratePosition(function(position) {
    onGeoSuccess(position);
  }, function(error) {
    onGeoError(error);
  });
}

window.onload = function() {
  console.log("hello1");
  setLatlon()
}
