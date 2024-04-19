document.getElementById('createReview').addEventListener('click', function() {
    let starRating = document.querySelector('input[name="reviewStar"]:checked').value;
    // 작성된 리뷰 내용 가져오기
    let reviewContent = document.getElementById("reviewContents").value;

    // 현재 URL의 search 부분을 가져와서 URLSearchParams 객체 생성
    let params = new URLSearchParams(window.location.search);
    // reservationId 파라미터 값 가져오기
    let reservationId = params.get('reservationId');

    let reviewForm = {
        rate: starRating,
        content: reviewContent,
        reservationId: reservationId
    };

    console.log('reviewData = ', reviewForm);

    $.ajax({
        url: "/create/review",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(reviewForm),
        success: function(response) {
            if (confirm('리뷰를 등록하시겠습니까?')) {
                window.location.href = "/reservation/list";
            }
        },
        error: function(xhr) {
            alert('실패');
        }
    });
})