/** 포트원 결제 (카카오) **/
IMP.init("imp32254404");

const button = document.getElementById('orderBtn');

const onClickPay = async () => {
    const menu = document.getElementById('menu').value;
    const amount = parseInt(document.getElementById('priceSumValue').value);
    const name = document.getElementById('userNameInput').value;
    const email = document.getElementById('userEmailInput').value;
    const tel = document.getElementById('userPhoneNumberInput').value;

    IMP.request_pay({
        pg: 'kakaopay',
        pay_method: 'card',
        name: menu,
        amount: amount,
        merchant_uid: 'merchant_' + new Date().getTime(),
        buyer_name: name,
        buyer_email: email,
        buyer_tel: tel
    }, function (response) {
        if (response.success) {
            sendPost(response);
        } else {
            alert('결제 실패');
        }
    });
}

function sendPost(response) {
    let userId = document.getElementById('userId').value;
    let phoneNum = response.buyer_tel;
    let request = document.getElementById('request').value;
    let date = document.getElementById('date').value;
    let time = document.getElementById('time').value;
    let designerId = document.getElementById('designerId').value;
    let menuId = document.getElementById('menuId').value;
    let price = response.paid_amount;

    let reservationForm = {
        userId: userId,
        phoneNum: phoneNum,
        designerId: designerId,
        menuId: menuId,
        request: request,
        date: date,
        time: time,
        price: price
    }

    $.ajax({
        url: '/create/reservation',
        method: 'POST',
        contentType: "application/json",
        data: JSON.stringify(reservationForm),
        success: function(response) {

        },
        error: function(xhr) {

        }
    });
}

button.addEventListener('click', onClickPay);