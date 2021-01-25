$('#option1').click(function () {
    $('#sort').val('');
    $('#sort').val('book');
    console.log($('#sort').val());

    //jquery 클래스 지우기
    $('.multi-collapse-album').removeClass('show'); //클릭하면, 클래스에 show가 추가되니, 그 클래스명만 지우기
    $('.multi-collapse-movie').removeClass('show'); //show했던 거 지우니, 거기 쓴 value 값도 지워짐!
});

$('#option2').click(function () {
    $('#sort').val('');
    $('#sort').val('album');
    console.log($('#sort').val());

    $('.multi-collapse-book').removeClass('show');
    $('.multi-collapse-movie').removeClass('show');
});

$('#option3').click(function () {
    $('#sort').val('');
    $('#sort').val('movie');
    console.log($('#sort').val());

    $('.multi-collapse-book').removeClass('show');
    $('.multi-collapse-album').removeClass('show');
});

