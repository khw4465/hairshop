$("#profile").parent().css("background-image", `url(${designerInfo.img})`);

let inputFile = document.querySelectorAll(".inputFile");
inputFile.forEach((row, i) => {
    let imgUrl = designerInfo.styles[i].imgUrl;
    row.parentNode.style.backgroundImage = `url(${imgUrl})`;
    i++;
})

document.getElementById('modifyDesigner').addEventListener('click', function() {

    let id = document.getElementById('userId').value;
    let name = document.getElementById('username').value;
    let img = document.getElementById('profileImg').src;
    let newContent = document.getElementById('designerContent').value;
    let newCareer = document.getElementById('designerCareer').value;

    const rows = document.querySelectorAll('.styles');

    // 스타일을 저장할 배열 초기화
    const stylesData = [];

    // 각 행에서 데이터를 추출해서 배열에 추가
    rows.forEach(row => {
        const styleData = {};

        // 이미지 URL 추출
        const imgUrl = row.querySelector('.styleImg').getAttribute('src');
        styleData.imgUrl = imgUrl;

        // 카테고리 1 추출
        const category1 = row.querySelector('.styleCategory1').value;
        styleData.category1 = category1;

        // 카테고리 2 추출
        const category2 = row.querySelector('.styleCategory2').value;
        styleData.category2 = category2;

        // 추출한 데이터를 배열에 추가합니다.
        stylesData.push(styleData);
    });

    let designerDto = { id: id, name: name, img: img, content: newContent, career: newCareer, styles: stylesData };

    // AJAX 요청 보내기
    fetch('/admin/modify/designer', {
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