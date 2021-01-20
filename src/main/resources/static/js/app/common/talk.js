var talk = {
    init: function() {
        var _this = this;

        const createBtn = document.querySelector('#talk-create-btn');
        const updateBtn = document.querySelector('#talk-update-btn');
        const destroyBtn = document.querySelector('#talk-destroy-btn');
        const adminDestroyBtn = document.querySelector('#talk-destroy-adminBtn'); //어드민 list
        const checkDestroyBtn = document.querySelector('#talk-check-delete'); //어드민 list

        if (createBtn != null) {
          createBtn.addEventListener('click', _this.create);
        }
        if (updateBtn) {
          updateBtn.addEventListener('click', _this.update);
        }
        if (destroyBtn) {
          destroyBtn.addEventListener('click', _this.destroy);
        }
        if (adminDestroyBtn) {
          adminDestroyBtn.addEventListener('click', _this.adminDestroy);
        }
        if (checkDestroyBtn) {
          checkDestroyBtn.addEventListener('click', _this.checkDestroy);
        }

    },

    //생성 메소드
    create: function() {
        var data = { //form 데이터를 JSON으로 만듬
          title: document.querySelector('#talk-title').value,
          content: document.querySelector('#talk-content').value,
        };

        // 데이터 생성 요청을 보냄 // fetch(URL, HTTP_REQUEST)
        fetch('/api/talk', {
          method: 'POST', // POST 방식으로, HTTP 요청.
          body: JSON.stringify(data), // create 할 데이터가 필요하니, 위에서 data를 만들었음 -> 그걸 ajax로 전송할 때, body에 실어서 보냄
          headers: {
            'Content-Type': 'application/json' ﻿// body 내용의 타입을 여기 Content-Type에 명시해줌
          }
        }).then(function(response) { // 응답 처리
          if (response.ok) {
            alert('게시글이 작성 되었습니다.');
            window.location.href='/talk/'; //새로고침 //뒤에 id값 넣고싶은데..
          } else {
            alert('게시글 작성 실패');
          }
        });
    },

    // 수정 메소드
    update: function() {
        var data = {
            id: document.querySelector('#talk-id').value,
            title: document.querySelector('#talk-title').value,
            content: document.querySelector('#talk-content').value,
        };

        fetch('/api/talk/' + data.id, {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: {
              'Content-Type': 'application/json'
              }
        }).then(function(response) {
            if (response.ok) {
              alert('게시글 수정 성공');
              window.location.href='/talk/' + data.id;
            } else {
              alert('게시글 수정 실패');
            }
        });
    },

    // 삭제 메소드
    destroy: function() {
      // url에서 id를 추출!
      var split = location.pathname.split('/');
      var id = split[split.length - 1];

      fetch('/api/talk/' + id, {
        method: 'DELETE',
      }).then(function(response) {
        if (response.ok) {
          alert('게시글 삭제 성공');
          window.location.href='/talk';
        } else {
          alert('게시글 삭제 실패');
        }
      });
    },

    //어드민 삭제 메소드
    adminDestroy: function() {
        var id = document.querySelector('#talk-id').value;//★hidden으로 받음(힘겹게 성공!)

        fetch('/api/talk/' + id, {
            method: 'DELETE',
        }).then(function(response) {
            if (response.ok) {
              alert('어드민 게시글 삭제 성공');
              window.location.href='/talk';
            } else {
              alert('어드민 게시글 삭제 실패');
            }
        });
    },


    //어드민-체크 삭제 메소드
    checkDestroy: function() {
        var checkBoxes = document.querySelectorAll('.talk-check-id');//value값이 아닌, 체크박스 객체 자체

        for ( var i = 0; i < checkBoxes.length; i++ ) {
            var id = checkBoxes[i].value;

            if(checkBoxes[i].checked == true){
                 fetch('/api/talk/' + id, {
                     method: 'DELETE',
                 }).then(function(response) {
                     if (response.ok) {
                       alert('어드민 게시글 삭제 성공');
                       window.location.href='/talk';
                     } else {
                       alert('어드민 게시글 삭제 실패');
                     }
                 });
            }//end if
        }//end for
    },//end method



    // 메소드 추가시 여기서부터

};

// 객체 초기화
talk.init();