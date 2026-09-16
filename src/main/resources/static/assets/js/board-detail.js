/* 게시판 상세 화면 인터랙션 */
(function () {
  'use strict';

  var button = document.getElementById('btn-delete');
  var query  = button.dataset.query ? '?' + button.dataset.query : '';

  button.addEventListener('click', function () {
    Alert.preset('delete.confirm', {
      onConfirm: function () {
        BoardApi.send('DELETE', '/api/board/' + button.dataset.boardId)
          .then(function () { location.href = '/board' + query; })
          .catch(function (error) { Alert.preset('delete.fail', { message: error.message }); });
      }
    });
  });
})();
