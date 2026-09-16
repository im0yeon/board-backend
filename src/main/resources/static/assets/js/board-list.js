/* 게시판 목록 화면 인터랙션 */
(function () {
  'use strict';

  var checkAll   = document.getElementById('check-all');
  var body       = document.getElementById('table-body');
  var countEl    = document.getElementById('selected-count');
  var deleteForm = document.getElementById('delete-form');
  var rowChecks  = function () { return Array.prototype.slice.call(body.querySelectorAll('.row-check')); };

  function syncState() {
    var rows = rowChecks();
    var checked = rows.filter(function (c) { return c.checked; });

    countEl.textContent = checked.length;
    checkAll.checked = rows.length > 0 && checked.length === rows.length;
    checkAll.indeterminate = checked.length > 0 && checked.length < rows.length;

    rows.forEach(function (c) {
      c.closest('tr').classList.toggle('is-selected', c.checked);
    });
  }

  checkAll.addEventListener('change', function () {
    rowChecks().forEach(function (c) { c.checked = checkAll.checked; });
    syncState();
  });

  body.addEventListener('change', function (e) {
    if (e.target.classList.contains('row-check')) syncState();
  });

  /* ---------- 삭제: 확인 후 폼 제출 ---------- */
  document.getElementById('btn-delete').addEventListener('click', function () {
    var checked = rowChecks().filter(function (c) { return c.checked; });

    if (checked.length === 0) {
      Alert.open({
        type: 'error',
        title: '선택된 게시글이 없습니다.',
        message: '삭제할 게시글을 먼저 선택해 주세요.'
      });
      return;
    }

    Alert.preset('delete.confirm', {
      message: '선택한 ' + checked.length + '건을 삭제합니다.\n삭제한 게시글은 복구할 수 없습니다.',
      onConfirm: function () { deleteForm.submit(); }
    });
  });

  syncState();
})();
