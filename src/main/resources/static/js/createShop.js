//최종 등록 버튼
function createShop(){
    //매장 이미지 리스트
    const shopImgList = [];

    const labels = document.querySelectorAll('.btn-file');
    labels.forEach(label => {
        const shopImg = {};
        // 레이블 내의 이미지(img) 요소 선택
        const img = label.querySelector('.shopImg');
        if (img) { // 이미지 요소가 선택되었는지 확인
            // 이미지의 src 속성값 확인
            const shopImgUrl = img.getAttribute('src');
            // src 속성값이 비어있지 않은 경우에만 리스트에 추가
            if (shopImgUrl !== '') {
                shopImgList.push(shopImgUrl);
            }
        }
    });

    //샵 카테고리
    const shopCategory = document.querySelector('.shopCategory').value;

    //매장명
    const shopName = document.getElementById('shopName').value;

    //매장 주소
    const address = document.getElementById('address').value

    //오픈 시간
    const openHour = parseInt(document.getElementById('openHour').value);
    const openMinute = parseInt(document.getElementById('openMinute').value);
    const openTime = `${openHour.toString().padStart(2, '0')}:${openMinute.toString().padStart(2, '0')}`;

    //마감 시간
    const closeHour = parseInt(document.getElementById('closeHour').value);
    const closeMinute = parseInt(document.getElementById('closeMinute').value);
    const closeTime = `${closeHour.toString().padStart(2, '0')}:${closeMinute.toString().padStart(2, '0')}`;

    //매장 소개
    const content = document.getElementById('shopContent').value;

    //메뉴 리스트
    const menusData = [];

    const rows = document.querySelectorAll('.menus');
    rows.forEach(row => {
        const menuData = {};

        //메뉴 이미지 URL
        const menuImgUrl = row.querySelector('.menuImg').getAttribute('src');
        menuData.imgUrl = menuImgUrl;

        //메뉴 카테고리
        const menuCategory = row.querySelector('.menuCategory').value;
        menuData.category = menuCategory;

        //메뉴 이름
        const menuName = row.querySelector('.menuName').value;
        menuData.name = menuName;

        //메뉴 가격
        const menuPrice = row.querySelector('.menuPrice').value;
        menuData.price = menuPrice;

        menusData.push(menuData);
    })

    const shopDto = {
        name: shopName,
        shopCategory: shopCategory,
        address: address,
        openTime: openTime,
        closeTime: closeTime,
        content: content,
        shopImgs: shopImgList,
        menus: menusData
    }

    $.ajax({
        url: "/admin/create/shop",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(shopDto),
        success: function(response) {
            console.log("매장 등록 완료");
            alert("등록이 완료되었습니다.");
            window.location.href = "/admin"
        },
        error: function(xhr) {
            console.log("매장 등록 실패");
        }
    })
}

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
//메뉴 추가
function addMenu() {
    // tbody 요소 선택
    const tbody = document.querySelector("#menuTable tbody");

    // 새로운 행 생성
    const newRow = document.createElement("tr");
    newRow.setAttribute("class", "menus");

    // 첫 번째 셀: 이미지 업로드
    const imgCell = document.createElement("td");
    const imgForm = document.createElement("form");
    imgForm.setAttribute("action", "/img/upload");
    imgForm.setAttribute("method", "post");
    imgForm.setAttribute("enctype", "multipart/form-data");
    const imgLabel = document.createElement("label");
    imgLabel.setAttribute("class", "btn-file");
    const imgTag = document.createElement("img");
    imgTag.setAttribute("class", "menuImg")
    const imgInput = document.createElement("input");
    imgInput.setAttribute("type", "file");
    imgInput.setAttribute("name", "file");
    imgInput.setAttribute("accept", "image/*");
    imgInput.setAttribute("onchange", "changedImg(this)");
    const imgSpan = document.createElement("span");
    imgSpan.innerText = "+";
    imgLabel.appendChild(imgTag);
    imgLabel.appendChild(imgInput);
    imgLabel.appendChild(imgSpan);
    imgForm.appendChild(imgLabel);
    imgCell.appendChild(imgForm);
    newRow.appendChild(imgCell);

    // 두 번째 셀: 메뉴 카테고리 선택
    const categoryCell = document.createElement("td");
    const categorySelect = document.createElement("select");
    categorySelect.setAttribute("class", "menuCategory");
    const categoryOption = document.createElement("option");
    categoryOption.innerText = "카테고리 선택";
    categorySelect.appendChild(categoryOption);
    menuCategories.forEach(function(category) {
        let option = document.createElement("option");
        option.setAttribute("value", category.name);
        option.textContent = category.name;
        categorySelect.appendChild(option);
    })
    categoryCell.appendChild(categorySelect);
    newRow.appendChild(categoryCell);

    // 세 번째 셀: 메뉴 이름 입력란
    const nameCell = document.createElement("td");
    const nameInput = document.createElement("input");
    nameInput.setAttribute("type", "text");
    nameInput.setAttribute("class", "menuName");
    nameCell.appendChild(nameInput);
    newRow.appendChild(nameCell);

    // 네 번째 셀: 메뉴 가격 입력란
    const priceCell = document.createElement("td");
    const priceInput = document.createElement("input");
    priceInput.setAttribute("type", "number");
    priceInput.setAttribute("class", "menuPrice");
    priceCell.appendChild(priceInput);
    const priceText = document.createTextNode(" 원");
    priceCell.appendChild(priceText);
    newRow.appendChild(priceCell);

    // 다섯 번째 셀: 삭제 버튼
    const deleteCell = document.createElement("td");
    const deleteBtn = document.createElement("button");
    deleteBtn.setAttribute("onclick", "removeMenuRow(this)");
    deleteBtn.setAttribute("class", "deleteBtn");
    deleteBtn.innerText = "삭제";
    deleteCell.appendChild(deleteBtn);
    newRow.appendChild(deleteCell);

    // 새로운 행을 tbody에 추가
    tbody.appendChild(newRow);
}
//--------------------------------------------------------------------
// 메뉴 삭제
function removeMenuRow(button) {
    let row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}