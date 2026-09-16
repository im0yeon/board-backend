/* 게시판 상세 화면 인터랙션 */
(function () {
  'use strict';

  var deleteForm = document.getElementById('delete-form');

  /* ---------- 삭제: 확인 후 폼 제출 ---------- */
  document.getElementById('btn-delete').addEventListener('click', function () {
    Alert.preset('delete.confirm', {
      onConfirm: function () { deleteForm.submit(); }
    });
  });
})();
