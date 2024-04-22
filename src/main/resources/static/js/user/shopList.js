function submitForm(element) {
    element.closest('.shopForm').submit();
}

shopList.forEach(shop => {
    let totalRate = 0;
    let reviewLength = shop.reviews.length;
    let reviewLengthElement = document.getElementById(shop.id).getElementsByClassName('reviewLength')[0];
    reviewLengthElement.textContent = '(' + reviewLength + ')';
    if (reviewLength > 0) {
        shop.reviews.forEach(review => {
            totalRate += review.rate;
        });
        let avgRate = totalRate / shop.reviews.length;
        document.getElementById(shop.id).getElementsByClassName('avgRate')[0].textContent = avgRate.toFixed(1);
    } else {
        document.getElementById(shop.id).getElementsByClassName('avgRate')[0].textContent = '0';
    }
    let reviewList = shop.reviews;
    reviewList.forEach(review => {
        let rate = review.rate;
        let starInputs = document.getElementById(shop.id).querySelectorAll('input[name="avgStar"]');
        starInputs.forEach(function(starInput, index) {
            if (index === 5 - rate) {
                starInput.checked = true;
            } else {
                starInput.checked = false;
            }
            starInput.disabled = true;
        });
    });
});