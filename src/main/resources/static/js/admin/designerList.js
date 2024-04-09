//---------------------------------------------------------------------------
// 검색
function submitForm() {
    let inputValue = document.getElementById("searchInput").value;
    let SearchCondition = { name: inputValue };

    $.ajax({
        url: "/admin/search/designer",
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
    let tbody = document.querySelector('#designerTable tbody');
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    response.forEach(designer => {
        let tr = document.createElement("tr");
        tr.setAttribute("id", designer.id);
        tr.setAttribute("class", "designer");
        tr.setAttribute("onclick", "designerInfo(this)");
        let imgTd = document.createElement("td");
        let image = document.createElement("img");
        image.setAttribute("class", "designerImg");
        image.setAttribute("src", designer.img);
        imgTd.appendChild(image);
        let nameTd = document.createElement("td");
        let name = document.createElement("span");
        name.setAttribute("class", "designerName");
        name.textContent = designer.name;
        nameTd.appendChild(name);
        let btnTd = document.createElement("td");
        let btn = document.createElement("button");
        btn.setAttribute("class", "deleteBtn");
        btn.setAttribute("onclick", "removeDesignerRow(this)");
        btn.textContent = "삭제"
        btnTd.appendChild(btn);
        tr.appendChild(imgTd);
        tr.appendChild(nameTd);
        tr.appendChild(btnTd);
        tbody.appendChild(tr);
    })
}

//---------------------------------------------------------------------------
// 디자이너 삭제
function removeDesignerRow(button, event) {
    event.stopPropagation();
    if(confirm("정말 삭제하시겠습니까?")) {
        let row = button.parentNode.parentNode;
        let id = row.id;
        if (id !== '') {
            // AJAX 요청을 보냄
            $.ajax({
                url: "/admin/delete/designer",
                type: "DELETE",
                data: {id: id},
                success: function (response) {
                    alert("삭제되었습니다.")
                    row.parentNode.removeChild(row);
                },
                error: function (xhr) {
                    console.log("스타일 삭제 실패");
                }
            });
        }
    }
}

//---------------------------------------------------------------------------
// 디자이너 상세
function designerInfo(designer) {
    let id = designer.getAttribute("id");
    window.location.href = "/admin/designerInfo?id=" + id;
}