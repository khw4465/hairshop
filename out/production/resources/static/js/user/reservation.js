$("#orderBtn").click(function () {
    $.ajax({
        url: "/kakaopay",
        method: "POST",
        dataType: 'json',
        success: function (data) {
            console.log('성공', data.tid);
        },
        error: function (error) {
            console.log('실패', error);
        }
    });
})