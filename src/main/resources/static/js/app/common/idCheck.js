//$ => jQuery ajax 방식으로 (여기 따로 안 빼고, account.js에 합쳐도 됨)
//$로 시작하는 jQuery 사용시, header,  footer 수정 필수!!
$('.idCheck_btn').click(function () {
    $.ajax({
        type: "GET",
        url: "idCheck",
        data: {
            "email": $('#join-email').val()
        },

        success: function (data) {
            if ($('#join-email').val() == ''){//빈칸 입력시
                $('#checkMsg').html('<p style="color:red">이메일을 입력하십시오.</p>');

            } else {//빈칸 아니면
                if($.trim(data) == "noMember"){
                    $('#checkMsg').html('<p style="color:blue">사용가능한 ID 입니다!</p>');
                }
                if($.trim(data) == "yesMember"){
                    $('#checkMsg').html('<p style="color:red">이미 사용중인 ID 입니다. 다른 ID를 입력하세요.</p>');
                    $('#join-email').val(''); //중복된 ID 입력시 창 비우기
                    $('#join-email').focus();
                }
            }//end if
        }

    })

});
