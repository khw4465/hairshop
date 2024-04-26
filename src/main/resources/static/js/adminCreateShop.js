//최종 등록 버튼
function createShop(){
    //매장 이미지 리스트
    const shopImgList = [];

    const labels = document.querySelectorAll('.btn-file');
    labels.forEach(label => {
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

    //디자이너
    const designers = [];
    const designerRows = document.querySelectorAll('.designers');
    designerRows.forEach(row => {
        const designerData = {};

        const id = row.id;
        designerData.id = id;

        const name = document.querySelector('.designerName').textContent;
        designerData.name = name;

        const img = document.querySelector('.designerImg').src;
        designerData.img = img;

        const content = document.querySelector('.designerContent').value;
        designerData.content = content;

        const career = document.querySelector('.designerCareer').value;
        designerData.career = career;

        designers.push(designerData);
    });

    //메뉴 리스트
    const menusData = [];

    const rows = document.querySelectorAll('.menus');
    rows.forEach(row => {
        const menuData = {};

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
    });

    const shopDto = {
        name: shopName,
        shopCategory: shopCategory,
        address: address,
        openTime: openTime,
        closeTime: closeTime,
        content: content,
        shopImgs: shopImgList,
        designers: designers,
        menus: menusData
    };

    $.ajax({
        url: "/admin/create/shop",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(shopDto),
        success: function(response) {
            console.log("매장 등록 완료");
            alert("등록이 완료되었습니다.");
            window.location.href = "/admin/shop/list"
        },
        error: function(xhr) {
            console.log("매장 등록 실패");
        }
    });
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

//-------------------------------------------------------------------
// 디자이너 검색창
function submitForm() {
    let inputValue = document.getElementById("searchInput").value;

    if (inputValue !== '') {
        let SearchCondition = {name: inputValue};

        $.ajax({
            url: "/admin/shop/search/designer",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(SearchCondition),
            success: function (response) {
                console.log(response);
                addDesignerList(response);
            },
            error: function (error) {
                console.log("에러", error);
            }
        });
    }
}

function addDesignerList(designerList) {
    let searchDiv = document.querySelector("#searchForm");
    let table = document.createElement("table");
    table.setAttribute("id", "searchList");

    if (designerList.length > 0) {
        designerList.forEach(d => {
            let tr = document.createElement("tr");
            tr.setAttribute("id", d.id);

            let imgTd = document.createElement("td");
            let img = document.createElement("img");
            img.setAttribute("src", d.img)
            img.setAttribute("class", "imgList");
            imgTd.appendChild(img);

            let nameTd = document.createElement("td");
            let name = document.createElement("span")
            name.textContent = d.name;
            nameTd.appendChild(name);

            let btnTd = document.createElement("td");
            let lable = document.createElement("label");
            lable.setAttribute("class", "addBtn");
            let input = document.createElement("input");
            input.setAttribute("type", "button");
            input.setAttribute("value", "추가");
            input.setAttribute("class", "addDesignerBtn");
            input.setAttribute("onclick", "addDesigner(this)")
            let span = document.createElement("span");
            span.textContent = "추가";
            lable.appendChild(input);
            lable.appendChild(span);
            btnTd.appendChild(lable)

            tr.appendChild(imgTd);
            tr.appendChild(nameTd);
            tr.appendChild(btnTd);
            table.appendChild(tr);
        })
    } else {
        let tr = document.createElement("tr");
        let td = document.createElement("td");
        let span = document.createElement("span");
        span.textContent = "등록된 디자이너가 없습니다.";
        td.appendChild(span);
        tr.appendChild(td);
        table.appendChild(tr);
    }
    searchDiv.appendChild(table);

    // 테이블 외부 클릭 시 테이블 제거
    let body = document.body;
    body.addEventListener("click", function(event) {
        if (!event.target.closest("#searchList")) {
            removeExistingTable();
        }
    });
}

// 테이블 제거 함수
function removeExistingTable() {
    let existingTable = document.getElementById("searchList");
    if (existingTable) {
        existingTable.remove();
    }
}

//-------------------------------------------------------------------
// 테이블에 디자이너 추가
function addDesigner(button) {
    let id = button.parentNode.parentNode.parentNode.id;

    let SearchCondition = {name: id};

    $.ajax({
        url: "/admin/search/select",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(SearchCondition),
        success: function (response) {
            addDesignerToList(response);
            removeExistingTable();
        },
        error: function (error) {
            console.log("에러", error);
        }
    });
}

function addDesignerToList(designer) {
    const tbody = document.querySelector("#designerTable tbody");

    // 새로운 행 생성
    const newRow = document.createElement("tr");
    newRow.setAttribute("class", "designers");
    newRow.setAttribute("id", designer.id);

    // 첫 번째 셀: 이미지
    const imgCell = document.createElement("td");

    const imgTag = document.createElement("img");
    imgTag.setAttribute("class", "designerImg")
    imgTag.setAttribute("src", designer.img);

    imgCell.appendChild(imgTag);
    newRow.appendChild(imgCell);

    // 두 번째 셀: 이름
    const nameCell = document.createElement("td");

    const name = document.createElement("span");
    name.setAttribute("class", "designerName")
    name.textContent = designer.name

    nameCell.appendChild(name);
    newRow.appendChild(nameCell);

    // 세 번째 셀: 경력
    const careerCell = document.createElement("td");
    const career = document.createElement("textarea");
    career.setAttribute("class", "designerCareer");
    career.value = designer.career;
    career.readOnly = true;
    careerCell.appendChild(career);
    newRow.appendChild(careerCell);

    // 네 번째 셀: 소개
    const contentCell = document.createElement("td");
    const content = document.createElement("textarea");
    content.setAttribute("class", "designerContent");
    content.value = designer.content;
    content.readOnly = true;

    contentCell.appendChild(content);
    newRow.appendChild(contentCell);

    // 다섯 번째 셀: 삭제 버튼
    const deleteCell = document.createElement("td");
    const deleteBtn = document.createElement("button");
    deleteBtn.setAttribute("onclick", "removeDesignerRow(this)");
    deleteBtn.setAttribute("class", "deleteBtn");
    deleteBtn.innerText = "삭제";
    deleteCell.appendChild(deleteBtn);
    newRow.appendChild(deleteCell);

    // 새로운 행을 tbody에 추가
    tbody.appendChild(newRow);
}
function removeDesignerRow(button) {
    let row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}