function updateDesignerId(select) {
    let hiddenField = document.getElementById('designerId');
    hiddenField.value = select.value;
}

function searchDesigner(element) {
    element.closest('form').submit();
}