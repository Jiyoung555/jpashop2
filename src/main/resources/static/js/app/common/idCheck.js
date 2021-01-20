//$ => jQuery ajax 방식으로 (여기 따로 안 빼고, account.js에 합쳐도 됨)
//$로 시작하는 jQuery 사용시, header,  footer 수정 필수!!

//<input type="email"> 은 이메일 형식 검증에 제약이 있음
//1. form 태그 안에 위치해야 하고 2. submit을 눌러야 함 => js로 제출시, 검증 불가
//따라서, 아이디 중복 버튼 클릭시, jquery 이메일 검증식 추가함
$('.idCheck_btn').click(function () {
    $.ajax({
        type: "GET",
        url: "idCheck",
        data: {
            "email": $('#join-email').val()
        },

        success: function (data) {
            var emailInput = $('#join-email').val(); //이메일 입력값

            //이메일 형식 검증식
            var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

            if (emailInput == ''){//[1]빈칸 입력시
                $('#checkMsg').html('<p style="color:red">이메일을 입력하십시오.</p>');

            } else if (!re.test(emailInput)) { //[1]이메일 형식 x
                $('#checkMsg').html('<p style="color:red">올바른 이메일 주소를 입력하세요.</p>');
                $('#join-email').val(''); //중복된 ID 입력시 창 비우기
                $('#join-email').focus();//입력창 깜빡깜빡

            } else {//[2]빈칸 아니고, 이메일 형식 o
                if($.trim(data) == "noMember"){
                    $('#checkMsg').html('<p style="color:blue">사용가능한 ID 입니다!</p>');
                    $('#idCheckOrNot').val('true'); //**아이디 체크여부 true로 변경
                }
                if($.trim(data) == "yesMember"){
                    $('#checkMsg').html('<p style="color:red">이미 사용중인 ID 입니다. 다른 ID를 입력하세요.</p>');
                    $('#join-email').val(''); //중복된 ID 입력시 창 비우기
                    $('#join-email').focus();//입력창 깜빡깜빡
                }
            }//end if
        }

    })

});
