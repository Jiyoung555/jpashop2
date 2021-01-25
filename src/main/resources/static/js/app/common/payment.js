$('#store_payment_btn').click(function () {

    IMP.init('imp06376564');//나의 가맹점 식별코드

    var price = $('#price').text(); //p태그는 value가 아닌, text()로 값 받아옴
    var count = $('#count').val();
    console.log(price);
    console.log(count);

    var money = price * count;
    console.log(money);

    var item_id = $('#item_id').val();
    var item_name = $('#item_name').text();
    console.log(item_id);
    console.log(item_name);

    var member_email = $('#member_email').val();
    var member_name = $('#member_name').val();
    console.log(member_email);
    console.log(member_name);

    if(member_email == "") {
          alert('로그인이 필요합니다.');
          window.location.href='/login';
    }

    IMP.request_pay({
        pg : 'inicis', // 이니시스 (version 1.1.0부터 지원)
        pay_method : 'card',
        merchant_uid : 'merchant_' + new Date().getTime(),
        name : item_name, //상품명
        amount : money, //상품가격
        buyer_name : member_name, //구매자
        buyer_email : member_email, //이메일
        buyer_tel : '010-2126-9260', //수정하기
        buyer_addr : '서울특별시 강남구 삼성동', //수정하기
        buyer_postcode : '123-456' //수정하기

    }, function(rsp) {
        if ( rsp.success ) { //rsp : 성공해서 온 동적결과 response
            var msg = '결제가 완료되었습니다.';
            msg += '\n고유ID : ' + rsp.imp_uid;
            msg += '\n상점 거래ID : ' + rsp.merchant_uid;
            msg += '\n결제 금액 : ' + rsp.paid_amount + '원';
            msg += '\n카드 승인번호 : ' + rsp.apply_num;

            //ㄴ 아임포트측 DB에 남겨진 것임

            var data = {
              itemId: item_id,
              count: count,

              //Payment 객체
              imp_uid: rsp.imp_uid, //고유ID
              merchant_uid: rsp.merchant_uid, //상점 거래 ID
              apply_num: rsp.apply_num,//카드 승인번호
              amount: rsp.paid_amount,//결제 금액

            }; //순수 js 객체

            $.ajax({ //내 DB에도 남기기
                type: "POST",
                url: "/api/payment",
                data: JSON.stringify(data), //순수 js 객체가 아닌, 문자열로 데이터 보내겟다
                contentType: "application/json",

//                success: function(data) {
//                    alert('DB POST 성공.');
//                    window.location.href='/order';
//                },
//
//                error: function(xhr, status, error){
//                    alert('DB POST 에러 발생');
//                }
            });


        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '\n에러내용 : ' + rsp.error_msg;
        }

        alert(msg);
        window.location.href='/order';
    });

});

