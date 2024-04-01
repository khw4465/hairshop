let img = document.getElementById('profileImg').src;

$('#inputFile').parent().css("background-image", `url(${img})`)

let newImg = '';

document.getElementById('editProfile').addEventListener('click', function() {

    let name = document.getElementById('username').value;
    let newContent = document.getElementById('designerContent').value;
    let newCareer = document.getElementById('designerCareer').value;

    let designerDto = { career: newCareer, content: newContent, img: newImg, name: name };
    console.log('designerDto = ', designerDto)

    // AJAX 요청 보내기
    fetch('/admin/myPage/modify', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(designerDto)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('HTTP error, status = ' + response.status);
            }
            alert("정보가 수정되었습니다.");
            window.location.href = "/admin";
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('데이터 전송 실패:', error.message);
        });
});

function changedImg(element) {
    let formData = new FormData();
    formData.append("file", $(element)[0].files[0])

    $.ajax({
        url: "/img/upload",
        data: formData,
        type: "post",
        contentType: false,
        processData: false,
        dataType: "text",
        success: function(result) {
            $(element).parent().css("background-image", `url(${result})`)
            newImg = result;
            console.log('s3 전송 성공')
        }
    });
}