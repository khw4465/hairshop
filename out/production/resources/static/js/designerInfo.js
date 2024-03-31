document.getElementById("editProfile").addEventListener('click', function() {

    let newImg = document.getElementById('inputFile').value;
    let name = document.getElementById('username').value;
    let newContent = document.getElementById('designerContent').value;
    let newCareer = document.getElementById('designerCareer').value;

    let designerDto = { career : newCareer, content : newContent, img : newImg, name: name };
    console.log('designerDto = ' + designerDto);

    // // AJAX 요청 보내기
    // fetch('/api/myPage/modify', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(designerDto)
    // })
    //     .then(response => {
    //         if (response.ok) {
    //             return response.json();
    //         }
    //         throw new Error('데이터 전송 실패');
    //     })
    //     .then(data => {
    //         console.log(data);
    //     })
    //     .catch(error => {
    //         console.error('데이터 전송 실패:', error.message);
    //     });
});