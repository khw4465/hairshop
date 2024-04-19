//별점 체크
let reviewList = designer.reviews;
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

function submitForm(element) {
    element.closest('form').submit();
}
