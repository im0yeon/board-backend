/* 게시판 API 호출 공통 처리 */
(function (global) {
  'use strict';

  function send(method, url, body) {
    var init = { method: method, headers: { 'Accept': 'application/json' } };

    if (body !== undefined) {
      init.headers['Content-Type'] = 'application/json';
      init.body = JSON.stringify(body);
    }

    return fetch(url, init).then(function (res) {
      return res.json()
        .catch(function () { return null; })
        .then(function (payload) {
          if (res.ok) return payload;

          var error = new Error((payload && payload.error) || '요청을 처리할 수 없습니다.');
          error.status = res.status;
          error.fields = payload && payload.data;
          throw error;
        });
    });
  }

  global.BoardApi = { send: send };
})(window);
