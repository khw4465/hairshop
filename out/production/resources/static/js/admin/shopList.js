//---------------------------------------------------------------------------
// 검색
function submitForm() {
    let inputValue = document.getElementById("searchInput").value;
    let SearchCondition = { name: inputValue };

    $.ajax({
        url: "/admin/search/shop",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(SearchCondition),
        success: function(response) {
            changeList(response);
        },
        error: function(error) {
            console.log("에러", error);
        }
    });
}

//---------------------------------------------------------------------------
// 검색한 디자이너 띄우기
function changeList(response) {
    //안의 내용 전체 삭제
    let tbody = document.querySelector('#shopTable tbody');
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    response.forEach(shop => {
        let tr = document.createElement("tr");
        tr.setAttribute("id", shop.id);
        tr.setAttribute("class", "shop");
        tr.setAttribute("onclick", "shopInfo(this)");
        let imgTd = document.createElement("td");
        let image = document.createElement("img");
        image.setAttribute("class", "shopImg");
        image.setAttribute("src", shop.shopImgs[0]);
        imgTd.appendChild(image);
        let nameTd = document.createElement("td");
        let name = document.createElement("div");
        name.setAttribute("class", "shopName");
        name.textContent = shop.name;
        let category = document.createElement("div");
        category.setAttribute("class", "shopCategory");
        category.textContent = shop.category
        nameTd.appendChild(name);
        nameTd.appendChild(category)
        let addressTd = document.createElement("td");
        let address = document.createElement("span");
        address.setAttribute("class", "shopAddress");
        address.textContent = shop.address;
        addressTd.appendChild(address);
        let btnTd = document.createElement("td");
        let btn = document.createElement("button");
        btn.setAttribute("class", "deleteBtn");
        btn.setAttribute("onclick", "removeShopRow(this)");
        btn.textContent = "삭제"
        btnTd.appendChild(btn);
        tr.appendChild(imgTd);
        tr.appendChild(nameTd);
        tr.appendChild(addressTd)
        tr.appendChild(btnTd);
        tbody.appendChild(tr);
    })
}

//---------------------------------------------------------------------------
// 디자이너 삭제
function removeShopRow(button, event) {
    event.stopPropagation();
    if(confirm("정말 삭제하시겠습니까?")) {
        let row = button.parentNode.parentNode;
        let id = row.id;
        if (id !== '') {
            // AJAX 요청을 보냄
            $.ajax({
                url: "/admin/delete/shop",
                type: "DELETE",
                data: {id: id},
                success: function (response) {
                    alert("삭제되었습니다.")
                    row.parentNode.removeChild(row);
                },
                error: function (xhr) {
                    console.log("매장 삭제 실패");
                }
            });
        }
    }
}

//---------------------------------------------------------------------------
// 디자이너 상세
function shopInfo(shop) {
    let id = shop.getAttribute("id");
    window.location.href = "/admin/shopInfo?id=" + id;
}