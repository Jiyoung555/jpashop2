var store = {
    init: function() {
        var _this = this;

        const cartBtn = document.querySelector('#store_cart_btn'); //storeShow 장바구니 버튼
        const orderBtn = document.querySelector('#store_order_btn'); //storeShow 구매하기 버튼
        const checkOrderBtn = document.querySelector('#check_order_btn'); //store 체크박스 구매하기 버튼

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
        var data = {
          account_id: document.querySelector('#account_id').value,
          item_id: document.querySelector('#item_id').value,
          quantity: document.querySelector('#item_quantity').value,
        };

        console.log('여기까지 왔나요?' + data);

        fetch('/api/cart', {
          method: 'POST',
          body: JSON.stringify(data),
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(function(response) {
          if (response.ok) {
            alert('장바구니에 상품을 담았습니다.');
            window.location.reload=true;
          } else {
            alert('다시 시도해주세요.');
          }
        });
    },


    order: function() {
        var data = { //form 데이터를 JSON으로 만듬
          itemId: document.querySelector('#item_id').value,
          itemName: document.querySelector('#item_name').value,
          orderPrice: document.querySelector('#orderPrice').value,
          count: document.querySelector('#count').value,
          loginId: document.querySelector('#loginId').value,
        };

        fetch('/api/order', {
          method: 'POST',
          body: JSON.stringify(data),
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
    },

    //안됨
    checkOrder: function() {
        var itemIds = document.getElementsByName("checkbox");

        var count = 0; //체크한 개수: 일단 0개로 초기화
        for (var i = 0; i < itemIds.length; i++) {
            if (itemIds[i].checked == true) {
                console.log(itemIds[i]);
                count++;
            }
        }



        if (count == 0) { //체크한 개수 0개
            alert("결제할 상품을 선택해 주세요");

        } else { //체크한 거 있으면
            fetch('/api/orderCheckbox', {
              method: 'POST',
              body: JSON.stringify(itemIds),
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
store.init();
