// 별점 체크
document.querySelectorAll('.review').forEach(function(reviewDiv, index) {
    let rate = reviewDiv.querySelector('.rate').innerText;
    let starInputs = reviewDiv.querySelectorAll('input[name="reviewStar_' + index + '"]');
    starInputs.forEach(function(starInput, starIndex) {
        if (starIndex <= 5 - rate) {
            starInput.checked = true;
        }
        starInput.disabled = true;
    });
});



function updateDesignerId(select) {
    let hiddenField = document.getElementById('designerId');
    hiddenField.value = select.value;
}

document.getElementById('searchDesigner').addEventListener('click', function() {
    let form = document.getElementById('designerForm');
    form.submit();
})