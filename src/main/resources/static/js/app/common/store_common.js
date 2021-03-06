var store = {
    init: function() {
        var _this = this;

        const cartBtn = document.querySelector('#store_cart_btn'); //storeShow 장바구니 버튼
        const orderBtn = document.querySelector('#store_order_btn'); //storeShow 구매하기 버튼
        const checkOrderBtn = document.querySelector('#storeCheck_order_btn'); //store 체크박스 구매하기 버튼

        if (cartBtn != null) {
          cartBtn.addEventListener('click', _this.cart);
        }
        if (orderBtn != null) {
          orderBtn.addEventListener('click', _this.order);
        }
        if (checkOrderBtn != null) {
          checkOrderBtn.addEventListener('click', _this.checkOrder);
        }

    },

    cart: function() {
        var member_email = document.querySelector('#member_email').value;
        console.log(member_email);
        if(member_email == "") {
              alert('로그인이 필요합니다.');
              window.location.href='/login';
        }

        var data = {
          itemId: document.querySelector('#item_id').value,
          cartCount: document.querySelector('#count').value,
          //memberId: document.querySelector('#memberId').value,
          //memberEmail: document.querySelector('#memberEmail').value
        };

        fetch('/api/cart', {
          method: 'POST',
          body: JSON.stringify(data),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(function(response) {

            if (response.ok) {
                response.text().then(function(aaa){

                    console.log(aaa);

                    if(aaa == 'UPDATED') {
                      alert('카트 내 동일 상품의 수량이 추가되었습니다.');
                      window.location.href='/member/cart';
                    }
                    if(aaa == 'CARTED') {
                      alert('카트 담기 성공');
                      window.location.href='/member/cart';
                    }
                    if(aaa == 'FAILED') {
                      alert('카트 담기 실패. 다시 시도해 주세요.');
                      window.location.reload=true;
                    }

                    if(aaa == 'LOGIN') {
                      alert('로그인이 필요합니다.');
                      window.location.href='/login';
                    }
                })

            } else {
              alert('에러 발생. 다시 시도해 주세요.');
            }

        });
    },


    order: function() {
        var member_email = document.querySelector('#member_email').value;
        console.log(member_email);
        if(member_email == "") {
              alert('로그인이 필요합니다.');
              window.location.href='/login';
        }

        var data = { //form 데이터를 JSON으로 만듬
          itemId: document.querySelector('#item_id').value,
          count: document.querySelector('#count').value,
        };

        fetch('/api/order', {
          method: 'POST',
          body: JSON.stringify(data),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(function(response) {

            if (response.ok) {
                response.text().then(function(aaa){
                    console.log(aaa);

                    if(aaa == 'SUCCESS') {
                      alert('주문 성공.');
                      window.location.href='/member/order';
                    }

//                    if(aaa == 'LOGIN') {
//                      alert('로그인이 필요합니다.');
//                      window.location.href='/login';
//                    }

                })

            } else {
              alert('에러 발생. 다시 시도해 주세요.');
            }

        });
    },

    checkOrder: function() {
        var member_email = document.querySelector('#member_email').value;
        console.log(member_email);
        if(member_email == "") {
              alert('로그인이 필요합니다.');
              window.location.href='/login';
        }

        var itemIds = document.getElementsByName("checkbox");
        var itemIdArr = new Array(); //**

        var count = 0; //체크한 개수: 일단 0개로 초기화
        for (var i = 0; i < itemIds.length; i++) {
            if (itemIds[i].checked == true) {
                console.log(itemIds[i].value);
                itemIdArr.push(itemIds[i].value);//**그 value값을!! 배열에 담기
                count++;
            }
        }
        console.log(itemIdArr);

        var data = {itemIdArr};

        if (count == 0) { //체크한 개수 0개
            alert("결제할 상품을 선택해 주세요");

        } else { //체크한 거 있으면
            fetch('/api/checkedStoreToOrder', {//OrderApiController
              method: 'POST',
              body: JSON.stringify(data),
              headers: {
                'Content-Type': 'application/json'
              }
            }).then(function(response) {

//              if (response.ok) {
//                alert('주문 성공');
//                window.location.href='/order';
//              } else {
//                alert('주문 실패');
//              }

            if (response.ok) {
                response.text().then(function(aaa){
                    console.log(aaa);

                    if(aaa == 'SUCCESS') {
                      alert('주문 성공.');
                      window.location.href='/member/order';
                    }

                    if(aaa == 'LOGIN') {
                      alert('로그인 먼저 해주세요.');
                      window.location.href='/login';
                    }

                })

            } else {
              alert('에러 발생. 다시 시도해 주세요.');
            }


            });
        }//if~else
    }//end method

    // 메소드 추가시 여기서부터

};

// 객체 초기화
store.init();
