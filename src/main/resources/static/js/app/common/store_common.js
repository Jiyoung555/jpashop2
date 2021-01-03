var store = {
    init: function() {
        var _this = this;

        const cartBtn = document.querySelector('#store_cart_btn'); //storeShow 장바구니 버튼
        const orderBtn = document.querySelector('#store_order_btn'); //storeShow 구매하기 버튼

        if (cartBtn != null) {
          cartBtn.addEventListener('click', _this.cart);
        }
        if (orderBtn != null) {
          orderBtn.addEventListener('click', _this.order);
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

    // 메소드 추가시 여기서부터

};

// 객체 초기화
store.init();
