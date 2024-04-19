function cancelForm(element) {
    if (confirm("예약을 취소하시겠습니까?")) {
        element.closest('form').submit();
    }
}