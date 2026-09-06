/* ==========================================================================
   Alert / Confirm Modal
   저장·수정·삭제 각각에 대해 확인 / 완료 / 실패 3단계를 제공한다.
   사용: Alert.preset('save.confirm', { onConfirm: fn })
   ========================================================================== */
(function (global) {
  'use strict';

  var ICONS = {
    confirm: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="M12 8v5"/><path d="M12 16.5h.01"/></svg>',
    danger:  '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10.3 3.9 1.9 18a2 2 0 0 0 1.7 3h16.8a2 2 0 0 0 1.7-3L13.7 3.9a2 2 0 0 0-3.4 0z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>',
    success: '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="m8.5 12.2 2.4 2.4 4.6-4.9"/></svg>',
    error:   '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="9"/><path d="m9.2 9.2 5.6 5.6"/><path d="m14.8 9.2-5.6 5.6"/></svg>'
  };

  var openCount = 0;

  function open(opts) {
    var type = opts.type || 'confirm';
    var hasCancel = type === 'confirm' || type === 'danger';

    var backdrop = document.createElement('div');
    backdrop.className = 'modal-backdrop';
    backdrop.setAttribute('role', 'dialog');
    backdrop.setAttribute('aria-modal', 'true');

    var confirmClass = type === 'danger' ? 'btn--danger'
                     : type === 'error'  ? 'btn--danger'
                     : 'btn--primary';

    backdrop.innerHTML =
      '<div class="modal">' +
        '<div class="modal__body">' +
          '<div class="modal__icon modal__icon--' + type + '">' + ICONS[type] + '</div>' +
          '<h2 class="modal__title">' + escapeHtml(opts.title || '') + '</h2>' +
          (opts.message ? '<p class="modal__message">' + escapeHtml(opts.message) + '</p>' : '') +
        '</div>' +
        '<div class="modal__footer">' +
          (hasCancel
            ? '<button type="button" class="btn btn--secondary" data-action="cancel">' +
                escapeHtml(opts.cancelText || '취소') + '</button>'
            : '') +
          '<button type="button" class="btn ' + confirmClass + '" data-action="confirm">' +
            escapeHtml(opts.confirmText || '확인') + '</button>' +
        '</div>' +
      '</div>';

    function close(result) {
      backdrop.remove();
      openCount = Math.max(0, openCount - 1);
      if (openCount === 0) document.body.style.overflow = '';
      document.removeEventListener('keydown', onKeydown);
      if (result === 'confirm' && typeof opts.onConfirm === 'function') opts.onConfirm();
      if (result === 'cancel'  && typeof opts.onCancel  === 'function') opts.onCancel();
    }

    function onKeydown(e) {
      if (e.key === 'Escape') close(hasCancel ? 'cancel' : 'confirm');
    }

    backdrop.addEventListener('click', function (e) {
      var action = e.target.closest('[data-action]');
      if (action) { close(action.dataset.action); return; }
      if (e.target === backdrop) close(hasCancel ? 'cancel' : 'confirm');
    });
    document.addEventListener('keydown', onKeydown);

    openCount++;
    document.body.style.overflow = 'hidden';
    document.body.appendChild(backdrop);
    backdrop.querySelector('[data-action="confirm"]').focus();
    return { close: close };
  }

  function escapeHtml(s) {
    return String(s).replace(/[&<>"']/g, function (c) {
      return { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c];
    });
  }

  /* ---------- 9종 프리셋 ---------- */
  var PRESETS = {
    'save.confirm': { type: 'confirm', title: '저장하시겠습니까?', message: '입력한 내용으로 게시글을 등록합니다.', confirmText: '저장', cancelText: '취소' },
    'save.success': { type: 'success', title: '저장되었습니다.', message: '게시글이 정상적으로 등록되었습니다.', confirmText: '확인' },
    'save.fail':    { type: 'error',   title: '저장에 실패했습니다.', message: '잠시 후 다시 시도해 주세요.\n문제가 계속되면 관리자에게 문의하세요.', confirmText: '확인' },

    'edit.confirm': { type: 'confirm', title: '수정하시겠습니까?', message: '변경한 내용으로 게시글을 수정합니다.', confirmText: '수정', cancelText: '취소' },
    'edit.success': { type: 'success', title: '수정되었습니다.', message: '게시글이 정상적으로 수정되었습니다.', confirmText: '확인' },
    'edit.fail':    { type: 'error',   title: '수정에 실패했습니다.', message: '잠시 후 다시 시도해 주세요.\n문제가 계속되면 관리자에게 문의하세요.', confirmText: '확인' },

    'delete.confirm': { type: 'danger',  title: '삭제하시겠습니까?', message: '삭제한 게시글은 복구할 수 없습니다.', confirmText: '삭제', cancelText: '취소' },
    'delete.success': { type: 'success', title: '삭제되었습니다.', message: '선택한 게시글이 삭제되었습니다.', confirmText: '확인' },
    'delete.fail':    { type: 'error',   title: '삭제에 실패했습니다.', message: '잠시 후 다시 시도해 주세요.\n문제가 계속되면 관리자에게 문의하세요.', confirmText: '확인' }
  };

  function preset(key, overrides) {
    var base = PRESETS[key];
    if (!base) throw new Error('Unknown alert preset: ' + key);
    var opts = {};
    for (var k in base) opts[k] = base[k];
    for (var o in (overrides || {})) opts[o] = overrides[o];
    return open(opts);
  }

  global.Alert = { open: open, preset: preset, PRESETS: PRESETS };
})(window);
