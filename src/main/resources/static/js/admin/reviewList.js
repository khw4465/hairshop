// 별점 체크
reviewList.forEach(review => {
    let rate = review.rate;
    let starInputs = document.querySelectorAll('input[name="reviewStar"]');
    starInputs.forEach(function(starInput, index) {
        if (index === 5 - rate) {
            starInput.checked = true;
        } else {
            starInput.checked = false;
        }
        starInput.disabled = true;
    })
})


function updateDesignerId(select) {
    let hiddenField = document.getElementById('designerId');
    hiddenField.value = select.value;
}

document.getElementById('searchDesigner').addEventListener('click', function() {
    let form = document.getElementById('designerForm');
    form.submit();
})