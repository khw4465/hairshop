//---------------------------------------------------------------------------
// 검색
function submitForm() {
    let inputValue = document.getElementById("searchInput").value;
    document.getElementById('searchText').value = inputValue;
    document.getElementById('searchForm').submit();
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

//---------------------------------------------------------------------------
// 검색 값이 없을 때
if (adminDesignerList.length === 0) {
    document.querySelector('#designerTable').remove();
    let container = document.getElementById('container')

    let div = document.createElement("div");
    div.setAttribute("id", "designerTable");
    div.textContent = "입력하신 이름의 디자이너가 없습니다."
    container.appendChild(div);
}