function updateDesignerId(select) {
    let hiddenField = document.getElementById('designerId');
    hiddenField.value = select.value;
}

function searchDesigner(element) {
    element.closest('form').submit();
}

function cancelForm(element) {
    if (confirm("예약을 취소하시겠습니까?")) {
        element.closest('form').submit();
    }
}