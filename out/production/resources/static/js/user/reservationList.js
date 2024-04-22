let selectedStatus = statusName

let statusSelect = document.getElementById('statusSelect');
for (let i = 0; i < statusSelect.options.length; i++) {
    if (statusSelect.options[i].value === selectedStatus) {
        statusSelect.selectedIndex = i;
        break;
    }
}

function updateStatus(select) {
    let status = document.getElementById('status');
    status.value = select.value;
}

function cancelForm(element) {
    if (confirm("예약을 취소하시겠습니까?")) {
        element.closest('form').submit();
    }
}