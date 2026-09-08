/* 상세 화면 인터랙션 */
document.getElementById('btn-edit').addEventListener('click', function () {
    location.href = '/articles/write/' + this.dataset.articleId;
});

/* ---------- 댓글 ---------- */
(function () {
    var list     = document.getElementById('comment-list');
    var empty    = document.getElementById('comment-empty');
    var countEl  = document.getElementById('comment-count');
    var authorEl = document.getElementById('comment-author');
    var bodyEl   = document.getElementById('comment-body');
    var charEl   = document.getElementById('comment-char');

    function syncCount() {
        var n = list.querySelectorAll('.comment-item').length;
        countEl.textContent = n;
        empty.hidden = n > 0;
    }

    function pad(n) { return String(n).padStart(2, '0'); }
    function now() {
        var d = new Date();
        return d.getFullYear() + '-' + pad(d.getMonth() + 1) + '-' + pad(d.getDate()) +
            ' ' + pad(d.getHours()) + ':' + pad(d.getMinutes());
    }

    bodyEl.addEventListener('input', function () { charEl.textContent = bodyEl.value.length; });

    /* 등록 — 새 댓글이 목록에 나타나는 것이 피드백이므로 확인 모달은 두지 않는다 */
    document.getElementById('comment-submit').addEventListener('click', function () {
        var author = authorEl.value.trim();
        var body   = bodyEl.value.trim();

        if (!author || !body) {
            Alert.open({
                type: 'error',
                title: '입력값을 확인해 주세요.',
                message: '작성자와 댓글 내용을 모두 입력해야 합니다.'
            });
            return;
        }

        var li = document.createElement('li');
        li.className = 'comment-item';
        li.innerHTML =
            '<div class="comment-item__head">' +
            '<span class="comment-item__author"></span>' +
            '<span class="comment-item__date"></span>' +
            '<button type="button" class="btn btn--sm btn--ghost comment-item__delete" data-comment-delete>삭제</button>' +
            '</div>' +
            '<p class="comment-item__body"></p>';
        li.querySelector('.comment-item__author').textContent = author;
        li.querySelector('.comment-item__date').textContent   = now();
        li.querySelector('.comment-item__body').textContent   = body;

        list.appendChild(li);
        bodyEl.value = '';
        charEl.textContent = '0';
        syncCount();
        li.scrollIntoView({ block: 'nearest' });
    });

    /* 삭제 — 되돌릴 수 없으므로 확인을 받는다 */
    list.addEventListener('click', function (e) {
        if (!e.target.closest('[data-comment-delete]')) return;
        var item = e.target.closest('.comment-item');

        Alert.preset('delete.confirm', {
            title: '댓글을 삭제하시겠습니까?',
            message: '삭제한 댓글은 복구할 수 없습니다.',
            onConfirm: function () {
                var ok = true; // 실제 구현에서는 API 응답으로 분기
                if (ok) {
                    Alert.preset('delete.success', {
                        message: '댓글이 삭제되었습니다.',
                        onConfirm: function () { item.remove(); syncCount(); }
                    });
                } else {
                    Alert.preset('delete.fail');
                }
            }
        });
    });

    syncCount();
})();

document.getElementById('btn-delete').addEventListener('click', function () {
    Alert.preset('delete.confirm', {
        onConfirm: function () {
            var ok = true; // 실제 구현에서는 API 응답으로 분기
            if (ok) {
                Alert.preset('delete.success', {
                    onConfirm: function () { location.href = 'list.html'; }
                });
            } else {
                Alert.preset('delete.fail');
            }
        }
    });
});
