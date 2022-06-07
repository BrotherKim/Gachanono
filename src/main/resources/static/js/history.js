const history = {
    init : function() {
        const _this = this;

        // 게시글 저장
        $('#btn-save').on('click', function () {
            _this.save();
        });
        // 게시글 수정
        $('#btn-update').on('click', function () {
            _this.update();
        });
        // 게시글 삭제
        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        // 댓글 저장
        $('#btn-comment-save').on('click', function () {
            _this.commentSave();
        });
        // 댓글 수정
        document.querySelectorAll('#btn-comment-update').forEach(function (item) {
            item.addEventListener('click', function () { // 버튼 클릭 이벤트 발생시
                const form = this.closest('form'); // btn의 가장 가까운 조상의 Element(form)를 반환 (closest)
                _this.commentUpdate(form); // 해당 폼으로 업데이트 수행
            });
        });
    },

    /** 글 작성 */
    save : function () {
        const data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val(),
            game_id: $('#game option:selected').val(),
            gamename: $('#game option:selected').text(),
        };
        
        // 공백 및 빈 문자열 체크
        if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        } else {
            $.ajax({
                type: 'POST',
                url: '/api/history',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                alert('등록되었습니다.');
                window.location.href = '/history/list';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    },

    /** 글 수정 */
    update : function () {
        const data = {
            id: $('#id').val(),
            title: $('#title').val(),
            content: $('#content').val(),
            game_id: $('#game option:selected').val(),
            gamename: $('#game option:selected').text(),
        };

        console.log(data)

        const con_check = confirm("수정하시겠습니까?");
        if (con_check === true) {
            if (!data.title || data.title.trim() === "" || !data.content || data.content.trim() === "") {
                alert("공백 또는 입력하지 않은 부분이 있습니다.");
                return false;
            } else {
                $.ajax({
                    type: 'PUT',
                    url: '/api/history/' + data.id,
                    dataType: 'JSON',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }).done(function () {
                    alert("수정되었습니다.");
                    window.location.href = '/history/read/' + data.id;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });
            }
        }
    },

    /** 글 삭제 */
    delete : function () {
        const id = $('#id').val();
        const con_check = confirm("정말 삭제하시겠습니까?");

        if(con_check == true) {
            $.ajax({
                type: 'DELETE',
                url: '/api/history/'+id,
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8'

            }).done(function () {
                alert("삭제되었습니다.");
                window.location.href = '/';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        } else {
            return false;
        }
    },
};

history.init();