var order = {
    init: function() {
        var _this = this;

        const cartDestroyBtn = document.querySelector('#cart-destroy-btn');//cartShow
        const destroyBtn = document.querySelector('#order-destroy-btn');//orderShow

        const cartOrderBtn = document.querySelector('#cart_order_btn');//cart 목록에서, cartShow에서 1개 구매
        const checkOrderBtn = document.querySelector('#cartCheck_order_btn');//cart 체크박스

        const orderSearchBtn = document.querySelector('#orderSearchBtn');//adminOrderList 검색버튼

        if (cartDestroyBtn != null) {
          cartDestroyBtn.addEventListener('click', _this.cartDestroy);
        }
        if (destroyBtn != null) {
          destroyBtn.addEventListener('click', _this.destroy);
        }

        if (cartOrderBtn != null) {
          cartOrderBtn.addEventListener('click', _this.cartOrder);
        }
        if (checkOrderBtn != null) {
          checkOrderBtn.addEventListener('click', _this.checkOrder);
        }

        if (orderSearchBtn != null) {
          orderSearchBtn.addEventListener('click', _this.search);
        }

    },


    cartDestroy: function() {
      var split = location.pathname.split('/');
      var cartId = split[split.length - 1];

      fetch('/api/cart/' + cartId, {
        method: 'DELETE',
      }).then(function(response) {
        if (response.ok) {
          alert('카트에서 삭제하였습니다');
          window.location.href='/cart';
        } else {
          alert('카트 삭제 실패');
        }
      });
    },


    destroy: function() {
      var split = location.pathname.split('/');
      var orderId = split[split.length - 1];

      fetch('/api/order/' + orderId, {
        method: 'DELETE',
      }).then(function(response) {
        if (response.ok) {
          alert('주문을 취소하였습니다');
          window.location.href='/order';
        } else {
          alert('주문 삭제 실패');
        }
      });
    },

    cartOrder: function() {
        var data = {
          cartId: document.querySelector('#cart_id').value
        };

        console.log(data.cartId);

        fetch('/api/cartToOrder', {
          method: 'POST',
          body: JSON.stringify(data),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(function(response) {
            if (response.ok) {
                response.text().then(function(aaa){
                    console.log(aaa);
                    if(aaa == 'success') {
                      alert('장바구니 상품 -> 주문 성공.');
                      window.location.href='/order';
                    }
                    if(aaa == 'fail') {
                      alert('주문 실패. 다시 시도해주세요.');
                      window.location.href='/cart';
                    }
                })
            } else {
              alert('에러 발생. 다시 시도해 주세요.');
            }
//          if (response.ok) {
//            alert('주문 성공');
//            window.location.href='/order';
//            //window.location.reload=true;
//          } else {
//            alert('주문 실패');
//          }
        });
    },


    //cartList에서 체크박스 주문
    checkOrder: function() {
        var cartIds = document.getElementsByName("checkbox_cart");
        var cartIdArr = new Array(); //**

        var count = 0; //체크한 개수: 일단 0개로 초기화
        for (var i = 0; i < cartIds.length; i++) {
            if (cartIds[i].checked == true) {//체크했으면
                console.log(cartIds[i].value);
                cartIdArr.push(cartIds[i].value);//**그 value값을!! 배열에 담기
                count++;
            }
        }

        console.log(cartIdArr);

        var data = {
          cartIdArr
        };

        if (count == 0) { //체크한 개수 0개
            alert("결제할 상품을 선택해 주세요");

        } else { //체크한 거 있으면
            fetch('/api/checkedCartToOrder', {//OrderApiController
              method: 'POST',
              body: JSON.stringify(data),
              headers: {
                'Content-Type': 'application/json'
              }
            }).then(function(response) {
//                if (response.ok) {
//                    response.text().then(function(aaa){
//                        console.log(aaa);
//                        if(aaa == 'success') {
//                          alert('장바구니 상품 -> 주문 성공.');
//                          window.location.href='/order';
//                        }
//                        if(aaa == 'fail') {
//                          alert('주문 실패. 다시 시도해주세요.');
//                          window.location.href='/cart';
//                        }
//                    })
//                } else {
//                  alert('에러 발생. 다시 시도해 주세요.');
//                }

              if (response.ok) {
                alert('주문 성공');
                window.location.href='/order';
              } else {
                alert('주문 실패');
              }

            });
        }//if~else
    },//end method


//    //adminOrderList에서 검색 -> ajax 없이 하기로 바꿈 (그럼 tbody tr 부분 싹 지우고, 다시 html 코드로 하나하나 추가해야해서)
//    search: function() {
//        var data = {
//          searchType: document.querySelector('#searchType').value,
//          searchKeyword: document.querySelector('#searchKeyword').value
//        };
//
//        console.log(data.searchType);
//        console.log(data.searchKeyword);
//
//        fetch('/api/orderSearch', {
//          method: 'POST',
//          body: JSON.stringify(data),
//          headers: {
//            'Content-Type': 'application/json'
//          }
//        }).then(function(response) {
//
//          //TO DO: 여기 수정!! 검색 성공시, orderList에서 검색한 결과만 보여주게끔
//          if (response.ok) {
//            alert('검색 성공');
//            window.location.href='/admin/order';
//            //window.location.reload=true;
//          } else {
//            alert('검색 실패');
//          }
//
//        });
//    }


    // 메소드 추가시 여기서부터


};

// 객체 초기화
order.init();
