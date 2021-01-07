var order = {
    init: function() {
        var _this = this;

        const cartDestroyBtn = document.querySelector('#cart-destroy-btn');//cartShow
        const destroyBtn = document.querySelector('#order-destroy-btn');//orderShow
        const checkOrderBtn = document.querySelector('#check_order_btn');//cart 체크박스

        if (cartDestroyBtn != null) {
          cartDestroyBtn.addEventListener('click', _this.cartDestroy);
        }
        if (destroyBtn != null) {
          destroyBtn.addEventListener('click', _this.destroy);
        }
        if (checkOrderBtn != null) {
          checkOrderBtn.addEventListener('click', _this.checkOrder);
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

    //안됨
    checkOrder: function() {
        var cartIds = document.getElementsByName("checkbox");

        var count = 0; //체크한 개수: 일단 0개로 초기화
        for (var i = 0; i < itemIds.length; i++) {
            if (cartIds[i].checked == true) {
                console.log(cartIds[i]);
                count++;
            }
        }

        if (count == 0) { //체크한 개수 0개
            alert("결제할 상품을 선택해 주세요");

        } else { //체크한 거 있으면
            fetch('/api/checkedCartToOrder', {//OrderApiController
              method: 'POST',
              body: JSON.stringify(cartIds),
              headers: {
                'Content-Type': 'application/json'
              }
            }).then(function(response) {
              if (response.ok) {
                alert('주문 성공');
                window.location.reload=true;
              } else {
                alert('주문 실패');
              }
            });


        }

    }
    // 메소드 추가시 여기서부터


};

// 객체 초기화
order.init();
