var order = {
    init: function() {
        var _this = this;

        const destroyBtn = document.querySelector('#order-destroy-btn');//orderShow

        if (destroyBtn != null) {
          destroyBtn.addEventListener('click', _this.destroy);
        }


    },


    //notice 삭제 메소드 _ 아직 컨트롤러...
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
    }


    // 메소드 추가시 여기서부터

};

// 객체 초기화
order.init();
