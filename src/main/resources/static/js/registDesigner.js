function submitForm() {
    let inputValue = document.getElementById("searchInput").value;
    let SearchCondition = { name: inputValue };

    let result;

    $.ajax({
        url: "/admin/search",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(SearchCondition),
        success: function(response) {
            addDesignerList(response);
        },
        error: function(error) {
            console.log("에러", error);
        }
    });
}

function addDesignerList(designerList) {
    let searchDiv = document.querySelector(".search");
    let table = document.createElement("table");
    table.setAttribute("id", "searchList");

    if (designerList.length > 0) {
        designerList.forEach(d => {
            let tr = document.createElement("tr");

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
            input.setAttribute("onclick", "addDesigner()")
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

// 디자이너 추가 함수
function addDesigner() {
    
}