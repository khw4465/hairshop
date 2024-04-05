//화면 로딩 시 데이터 설정
let shopImgs = document.getElementById('shopImg').querySelectorAll('.btn-file');
shopImgs.forEach((row, i) => {
    let imgUrl = shop.shopImgs[i];
    let inputFile = row.querySelector('input[type="file"]');
    inputFile.parentNode.style.backgroundImage = `url(${imgUrl})`
    i++
})

let imgForm = document.getElementById('imgForm');

if (shopImgs.length < 5) {
    for (let i = shopImgs.length; i < 5; i++) {
        let fileLabel = document.createElement("label");
        fileLabel.setAttribute("class", "btn-file");
        let fileImg = document.createElement("img");
        fileImg.setAttribute("class", "shopImg");
        fileImg.setAttribute("src", "");
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
        imgForm.appendChild(fileLabel);
    }
}

let openTime = shop.openTime;
let closeTime = shop.closeTime;

let openTimePart = openTime.split(':');
let openHour = parseInt(openTimePart[0], 10);
let openMinute = parseInt(openTimePart[1], 10);

let closeTimePart = closeTime.split(':');
let closeHour = parseInt(closeTimePart[0], 10);
let closeMinute = parseInt(closeTimePart[1], 10);

document.getElementById('openHour').value = openHour;
document.getElementById('openMinute').value = openMinute;
document.getElementById('closeHour').value = closeHour;
document.getElementById('closeMinute').value = closeMinute;

const orgRows = document.querySelectorAll('.menus');

orgRows.forEach((row, i) => {
    let imgUrl = shop.menus[i].imgUrl;
    let inputFile = row.querySelector('input[type="file"]');
    inputFile.parentNode.style.backgroundImage = `url(${imgUrl})`;
    i++;
})
//--------------------------------------------------------------------
//샵 수정하기
function modifyShop() {
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
        url: "/admin/modify/shop",
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(shopDto),
        success: function(response) {
            console.log("매장 수정 완료");
            alert("수정이 완료되었습니다.");
            window.location.href = "/admin"
        },
        error: function(xhr) {
            console.log("매장 수정 실패");
        }
    })
}

//--------------------------------------------------------------------
//샵 삭제하기
function removeShop() {
    if (confirm("정말 삭제하시겠습니까?")) {
        $.ajax({
            url: "/admin/remove/shop",
            type: "DELETE",
            contentType: "application/json",
            success: function (response) {
                console.log("매장 삭제 완료");
                alert("삭제되었습니다.");
                window.location.href = "/admin"
            },
            error: function (xhr) {
                console.log("매장 삭제 실패");
            }
        })
    };
}