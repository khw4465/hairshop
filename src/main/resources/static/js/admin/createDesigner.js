//--------------------------------------------------------------------
//등록 버튼
document.getElementById('addDesigner').addEventListener('click', function() {

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

    let designerDto = { career: newCareer, content: newContent, img: img, name: name, styles: stylesData };
    console.log('designerDto = ', designerDto)

    // AJAX 요청 보내기
    fetch('/admin/create/designer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(designerDto)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('HTTP error, status = ' + response.status);
            }
            alert("디자이너가 등록되었습니다.");
            window.location.href = "/admin";
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('데이터 전송 실패:', error.message);
        });
});

//--------------------------------------------------------------------
//S3 이미지 등록
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
            $(element).prev().attr("src", result);
            console.log('s3 전송 성공')
        }
    });
}

//--------------------------------------------------------------------
//스타일 추가
function addStyle() {
    let tableBody = document.getElementById("styleTable").getElementsByTagName("tbody")[0];

    let newRow = document.createElement("tr");
    newRow.setAttribute("class", "styles")

    // 파일 업로드 입력란 추가
    let fileInputCell = document.createElement("td");
    let fileForm = document.createElement("form");
    fileForm.setAttribute("action", "/img/upload");
    fileForm.setAttribute("method", "post");
    fileForm.setAttribute("enctype", "multipart/form-data");
    let fileLabel = document.createElement("label");
    fileLabel.setAttribute("class", "btn-file");
    let fileImg = document.createElement("img");
    fileImg.setAttribute("class", "styleImg");
    let fileInput = document.createElement("input");
    fileInput.setAttribute("type", "file");
    fileInput.setAttribute("name", "file");
    fileInput.setAttribute("accept", "image/*");
    fileInput.setAttribute("onchange", "changedImg(this)");
    let fileSpan = document.createElement("span");
    fileSpan.textContent = "+";
    fileLabel.appendChild(fileImg);
    fileLabel.appendChild(fileInput);
    fileLabel.appendChild(fileSpan);
    fileForm.appendChild(fileLabel);
    fileInputCell.appendChild(fileForm);
    newRow.appendChild(fileInputCell);

    // 첫 번째 카테고리 선택란 추가
    let category1Cell = document.createElement("td");
    let category1Select = document.createElement("select");
    category1Select.setAttribute("class", "styleCategory1");
    let category1OptionDefault = document.createElement("option");
    category1OptionDefault.setAttribute("value", "");
    category1OptionDefault.textContent = "카테고리 선택";
    category1Select.appendChild(category1OptionDefault);
    subCategories.forEach(function(category) {
        let option = document.createElement("option");
        option.setAttribute("value", category.name);
        option.textContent = category.name;
        category1Select.appendChild(option);
    });
    category1Cell.appendChild(category1Select);
    newRow.appendChild(category1Cell);

    // 두 번째 카테고리 선택란 추가
    let category2Cell = document.createElement("td");
    let category2Select = document.createElement("select");
    category2Select.setAttribute("class", "styleCategory2");
    let category2OptionDefault = document.createElement("option");
    category2OptionDefault.setAttribute("value", "");
    category2OptionDefault.textContent = "카테고리 선택";
    category2Select.appendChild(category2OptionDefault);
    subCategories.forEach(function(category) {
        let option = document.createElement("option");
        option.setAttribute("value", category.name);
        option.textContent = category.name;
        category2Select.appendChild(option);
    });
    category2Cell.appendChild(category2Select);
    newRow.appendChild(category2Cell);

    // 삭제 버튼 추가
    let deleteCell = document.createElement("td");
    let deleteButton = document.createElement("button");
    deleteButton.textContent = "삭제";
    deleteButton.setAttribute("onclick", "removeStyleRow(this)");
    deleteCell.appendChild(deleteButton);
    newRow.appendChild(deleteCell);

    // tbody 자식요소로 추가
    tableBody.appendChild(newRow);
}
//--------------------------------------------------------------------
// 스타일 삭제

function removeStyleRow(button) {
    let row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}