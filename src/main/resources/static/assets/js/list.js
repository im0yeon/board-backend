/* 목록 화면 인터랙션 */
(function () {
  'use strict';

  var checkAll   = document.getElementById('check-all');
  var body       = document.getElementById('table-body');
  var countEl    = document.getElementById('selected-count');
  var rowChecks   = function () { return Array.prototype.slice.call(body.querySelectorAll('.row-check')); };

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

  /* ---------- 등록: 현재 검색 조건을 폼 화면까지 실어 보낸다 ---------- */
  document.getElementById('btn-create').addEventListener('click', function () {
    location.href = '/articles/new' + location.search;
  });

  /* ---------- 삭제: 확인 → 완료 / 실패 ---------- */
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

    var ids = checked.map(function (c) { return c.value; });

    Alert.preset('delete.confirm', {
      message: '선택한 ' + checked.length + '건을 삭제합니다.\n삭제한 게시글은 복구할 수 없습니다.',
      onConfirm: function () {
        fetch('/api/articles?ids=' + ids.join(','), { method: 'DELETE' })
          .then(function (res) {
            return res.json().then(
              function (json) {
                if (res.ok && json.status === 200) return json;
                throw failure(json.error, res.status);
              },
              function () { throw failure(null, res.status); }
            );
          })
          .then(function () {
            Alert.preset('delete.success', {
              message: '선택한 ' + checked.length + '건이 삭제되었습니다.',
              onConfirm: function () { location.reload(); }
            });
          })
          .catch(function (err) {
            console.error('게시글 삭제 실패: ids=%s, 원인=%s', ids.join(','), err.message);
            Alert.preset('delete.fail', err.reason ? { message: err.reason } : null);
          });
      }
    });
  });

  // 서버가 사유를 주면 얼럿에 그대로 노출한다
  function failure(reason, status) {
    var err = new Error(reason || ('HTTP ' + status));
    if (reason) err.reason = reason;
    return err;
  }

  /* ---------- 검색 / 초기화 / 노출 개수 ---------- */
  document.getElementById('btn-reset').addEventListener('click', function () {
    ['q-title', 'q-author'].forEach(function (id) { document.getElementById(id).value = ''; });
    document.getElementById('q-date-from').value = '';
    document.getElementById('q-date-to').value = '';
  });

  document.getElementById('page-size').addEventListener('change', function () {
    // 실제 구현에서는 목록을 다시 조회한다.
  });

  syncState();
})();
