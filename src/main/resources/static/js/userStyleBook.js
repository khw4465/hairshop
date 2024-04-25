function subCategory(element) {
    let mainCategoryName = element.textContent.trim();

    let table = document.createElement('table');
    table.setAttribute('class', 'subCategoryTable');

    subCategories.forEach(subCategory => {
        if (subCategory.mainName === mainCategoryName) {
            let tr = document.createElement('tr');
            let td = document.createElement('td');
            td.setAttribute("id", subCategory.name);
            td.setAttribute("onclick", "findStyleByCategory(this)");
            let span = document.createElement('span');
            span.textContent = subCategory.name;
            td.appendChild(span);
            tr.appendChild(td);
            table.appendChild(tr);
        }
    });
    element.appendChild(table);

    // 이벤트 캡처링을 활용하여 테이블 외부 클릭 시 테이블 제거
    document.addEventListener("click", function(event) {
        if (!event.target.closest(".subCategoryTable")) {
            removeExistingTable();
        }
    }, true);
}

// 테이블 제거 함수
function removeExistingTable() {
    let existingTable = document.querySelector(".subCategoryTable");
    if (existingTable) {
        existingTable.remove();
    }
}

function findStyleByCategory(element) {
    document.getElementById("categoryName").value = element.id;
    document.getElementById("submitForm").submit();
}

function submitForm(element) {
    element.closest('.styleForm').submit();
}