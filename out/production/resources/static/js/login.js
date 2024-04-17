// 체크버튼 누르면 유무값을 넘겨서 세션에 저장
document.getElementById('isDesigner').addEventListener('change', function() {
    let isChecked = this.checked ? 1 : 0;

    // AJAX 요청 보내기
    fetch('/login/check', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ isDesigner: isChecked })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('데이터 전송 실패');
        })
        .then(data => {
            sessionStorage.setItem('isDesigner', data.isDesigner);
        })
        .catch(error => {
            console.error('데이터 전송 실패:', error.message);
        });
});

window.onload = function () {
    let isDesigner = sessionStorage.getItem('isDesigner');
    if (userId === null) {
        let url = new URL(document.referrer);
        let path = url.pathname;
        if (url.search || url.hash) {
            path += url.search + url.hash;
        }
        sessionStorage.setItem("path", path);
    } else {
        sendPostRequset(isDesigner);
        sessionStorage.setItem('isDesigner', '0');
    }
}

// 리디렉트 후 즉시 POST로 전달
function sendPostRequset(isDesigner) {

    let checkForm = {
        isDesigner: isDesigner
    };

    // fetch API를 사용하여 POST 요청 보내기
    $.ajax({
        url: '/login/kakao',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(checkForm),
        success: function(data) {
            if (data === 'class com.example.hairshop.domain.Designer') {
                window.location.href = '/admin';
            } else {
                window.location.href = sessionStorage.getItem("path");
            }
        },
        error: function(xhr, status, error) {
        }
    });
}