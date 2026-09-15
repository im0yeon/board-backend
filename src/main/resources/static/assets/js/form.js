/* 등록, 수정 화면 인터랙션 */
(function () {
    var form = document.getElementById('board-form');
    var articleId = form.dataset.articleId || '';
    var isEdit = articleId !== '';

    var content = document.getElementById('f-content');
    var counter = document.getElementById('char-count');
    var saveBtn = document.getElementById('btn-save');
    var removeCheck = document.getElementById('f-file-remove');

    /* ---------- 글자 수 ---------- */
    function updateCount() { counter.textContent = content.value.length; }
    content.addEventListener('input', updateCount);
    updateCount();

    /* ---------- 저장 / 수정: 확인 → 완료 / 실패 ---------- */
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        var title = document.getElementById('f-title').value.trim();
        var writer = document.getElementById('f-author').value.trim();
        if (!title || !writer || !content.value.trim()) {
            Alert.open({
                type: 'error',
                title: '필수 항목을 입력해 주세요.',
                message: '제목, 작성자, 내용은 필수 입력 항목입니다.'
            });
            return;
        }

        var key = isEdit ? 'edit' : 'save';
        Alert.preset(key + '.confirm', {
            onConfirm: function () { submit(key, title, writer); }
        });
    });

    function submit(key, title, writer) {
        saveBtn.disabled = true;

        fetch(isEdit ? '/api/articles/' + articleId : '/api/articles', {
            method: isEdit ? 'PUT' : 'POST',
            body: payload(title, writer)
        })
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
                Alert.preset(key + '.success', {
                    // 등록은 새 글 확인을 위해 1페이지로, 수정은 보던 페이지로 복귀한다
                    onConfirm: function () { location.href = listUrl(!isEdit); }
                });
            })
            .catch(function (err) {
                console.error('게시글 %s 실패: id=%s, title=%s, 원인=%s',
                    isEdit ? '수정' : '저장', articleId || '신규', title, err.message);
                Alert.preset(key + '.fail', err.reason ? { message: err.reason } : null);
            })
            .finally(function () { saveBtn.disabled = false; });
    }

    // Content-Type은 지정하지 않는다 - boundary 자동 생성
    function payload(title, writer) {
        var data = new FormData();
        data.append('title', title);
        data.append('writer', writer);
        data.append('content', content.value.trim());
        data.append('isNotice', document.querySelector('input[name="notice"]:checked').value === 'notice');

        var file = document.getElementById('f-file').files[0];
        if (file) data.append('file', file);

        if (isEdit) {
            data.append('isRemoveFile', !!(removeCheck && removeCheck.checked));
        } else {
            data.append('createDate', document.getElementById('f-date').value);
        }

        return data;
    }

    // 검색 조건을 유지한 목록 URL. resetPage가 true면 page를 제거해 1페이지로 보낸다
    function listUrl(resetPage) {
        var params = new URLSearchParams(location.search);
        if (resetPage) params.delete('page');
        var qs = params.toString();
        return '/articles' + (qs ? '?' + qs : '');
    }

    // 서버가 사유를 주면 얼럿에 그대로 노출한다
    function failure(reason, status) {
        var err = new Error(reason || ('HTTP ' + status));
        if (reason) err.reason = reason;
        return err;
    }

    /* ---------- 취소 ---------- */
    document.getElementById('btn-cancel').addEventListener('click', function () {
        Alert.open({
            type: 'confirm',
            title: isEdit ? '수정을 취소하시겠습니까?' : '작성을 취소하시겠습니까?',
            message: '입력한 내용은 저장되지 않습니다.',
            confirmText: '나가기',
            cancelText: '계속 작성',
            onConfirm: function () { location.href = '/articles' + location.search; }
        });
    });
})();
