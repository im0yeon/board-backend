/* 게시판 등록·수정 화면 인터랙션 */
(function () {
  'use strict';

  var form    = document.getElementById('board-form');
  var boardId = form.dataset.boardId || '';
  var isEdit  = boardId !== '';

  function listQuery(resetPage) {
    var params = new URLSearchParams(form.dataset.query || '');
    if (resetPage) params.set('page', '1');
    var query = params.toString();
    return query ? '?' + query : '';
  }

  function clearErrors() {
    Array.prototype.forEach.call(
      form.querySelectorAll('[data-error-for]'),
      function (el) { el.textContent = ''; }
    );
  }

  function showErrors(fields, fallback) {
    clearErrors();

    var shown = false;
    Object.keys(fields || {}).forEach(function (name) {
      var el = form.querySelector('[data-error-for="' + name + '"]');
      if (el) {
        el.textContent = fields[name];
        shown = true;
      }
    });

    if (!shown) {
      Alert.preset(isEdit ? 'edit.fail' : 'save.fail', { message: fallback });
    }
  }

  form.addEventListener('submit', function (e) {
    e.preventDefault();

    Alert.preset(isEdit ? 'edit.confirm' : 'save.confirm', {
      onConfirm: function () {
        var payload = {
          title: form.elements.title.value,
          writer: form.elements.writer.value,
          content: form.elements.content.value
        };

        BoardApi.send(isEdit ? 'PUT' : 'POST', isEdit ? '/api/board/' + boardId : '/api/board', payload)
          .then(function (res) {
            var id = isEdit ? boardId : res.data;
            location.href = '/board/' + id + listQuery(!isEdit);
          })
          .catch(function (error) { showErrors(error.fields, error.message); });
      }
    });
  });
})();
