
var account = {
  init: function() {
    var _this = this;

    // 버튼 선택
    const joinBtn = document.querySelector('#join-btn');
    const loginBtn = document.querySelector('#login-btn');

    const memberUpdate = document.querySelector('#member-update-btn');
    const memberDestroy = document.querySelector('#member-destroy-btn');

    if (joinBtn != null) {
      joinBtn.addEventListener('click', _this.join);
    }
    if (loginBtn != null) {
      loginBtn.addEventListener('click', _this.login);
    }
    if (memberUpdate != null) {
      memberUpdate.addEventListener('click', _this.memberUpdate);
    }
    if (memberDestroy != null) {
      memberDestroy.addEventListener('click', _this.memberDestroy);
    }
  },

  join: function() {
    // form 데이터를 JSON으로 만듬
    var data = {
      email: document.querySelector('#join-email').value,
      password: document.querySelector('#join-pwd').value,
      name: document.querySelector('#join-name').value,
      role: document.querySelector('#join-role').value,
      zipcode: document.querySelector('#zip_num').value,
      addr1: document.querySelector('#addr1').value,
      addr2: document.querySelector('#addr2').value,
    };

    fetch('/api/signup', { //action="/signup"
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(function(response) {
      if (response.ok) {
        alert('회원가입 성공. 로그인 페이지로 이동합니다.');
        //window.location.href='/signup'; //새로고침
        window.location.href='/login'; //새로고침
      } else {
        alert('회원가입 실패');
        //window.location.href='/signup'; //새로고침
      }
    });
  },

  login: function() {
    // form 데이터를 JSON으로 만듬
    var data = {
      email: document.querySelector('#login-email').value,
      password: document.querySelector('#login-pwd').value,
    };

    fetch('/api/login', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(function(response) {

        if (response.ok) { //컨트롤러 return값 받으면
            response.text().then(function(aaa){
                //response.text()를 통해, 컨트롤러에서 return한 텍스트 꺼내옴
                //이걸 통해, 함수 실행함. 파라미터는, 방금 꺼낸 텍스트(aaa라 부르겠음)
                console.log(aaa);

                if(aaa == 'noMember') {
                  alert('없는 회원입니다.');
                }
                if(aaa == 'wrongPwd') {
                  alert('틀린 비밀번호입니다.');
                }
                if(aaa == 'loginSuccess') {
                  alert('로그인 성공');
                  window.location.href='/sone';
                }
            })

        } else {//컨트롤러 return값 못 받으면
          alert('에러 발생. 다시 시도해 주세요.');
        }



    });
  },

    // 수정 메소드
    memberUpdate: function() {
        var data = {
            id: document.querySelector('#member-id').value,
            email: document.querySelector('#member-email').value,
            password: document.querySelector('#member-password').value,
            role: document.querySelector('#member-role').value,
        };

        fetch('/api/member/' + data.id, {//어드민 메소드 뺏어서 사용
            method: 'PUT',
            body: JSON.stringify(data),
            headers: {
              'Content-Type': 'application/json'
              }
        }).then(function(response) {
            if (response.ok) {
              alert('회원정보 수정 성공');
              window.location.href='/mypage/'; // + data.id;
            } else {
              alert('회원정보 수정 실패');
            }
        });
    },

    // 삭제 메소드
    memberDestroy: function() {
      var split = location.pathname.split('/');
      var id = split[split.length - 1];

      var result = confirm("정말 회원탈퇴 하시겠습니까?");

      if(result){
          fetch('/api/member/' + id, {//어드민 메소드 뺏어서 사용
            method: 'DELETE',
          }).then(function(response) {
            if (response.ok) {
              alert('회원 탈퇴 성공. 이용해 주셔서 감사합니다.');
              window.location.href='/sone';
            } else {
              alert('회원 탈퇴 실패');
            }
          });
      }

    },






    // 메소드 추가시 여기서부터

};

// 객체 초기화
account.init();
