var index = {
    init : function() {
        //click 이벤트 발생시 function 이 실행되도록 이벤트를 등록
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
        $('#btn-update').on('click', function() {
            _this.update();
        });
        $('#btn-delete').on('click', function() {
            _this.delete();
        });
    },
    delete: function() {
        var id = $('#id').val();

         $.ajax({
              type: 'DELETE',
              url: '/api/v1/posts/' + id,
              dataType: 'json',
              contentType:'application/json; charset=utf-8'
          }).done(function() {
            alert('글이 삭제되었습니다');
            window.location.href = '/';
          }).fail(function (error) {
            alert(JSON.stringify(error));
          });
    },
    update: function() {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        var id = $('#id').val();

        //REST 에서 CRUD 는 POST, GET, PUT, DELETE 로 매핑
        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)  //stringify 메소드는 json 객체를 String 객체로 변환
        }).done(function() {
            alert('글이 수정되었습니다');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    save : function() {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }

};
index.init();

/*
_this 와 this
*/
