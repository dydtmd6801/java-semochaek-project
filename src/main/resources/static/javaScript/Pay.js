import { IMP_MERCHANT_ID, merchant_uid } from "./SecretKeys.js";
import validator from "./ValidateToken.js";

const payBtn = document.getElementsByClassName("btn-cart-book-pay")[0];
payBtn.addEventListener("click", bookPay);

function bookPay() {

    const token = localStorage.getItem("token");
    
    if (token == null) {
        alert("로그인이 필요합니다.");
        location.href="login.html";
        return;
    }

    if (validator.validateToken(token)) {
        alert("로그인이 만료되었습니다. 다시 로그인 해주세요.");
        location.href="login.html";
        return;
    }

    let items = [];
    const decoded = jwt_decode(token);
    const totalPrice = document.getElementsByClassName("cart-book-total-price")[1];
    const IMP = window.IMP;

    const checkboxs = document.getElementsByClassName("check-box");
    const isbns = document.getElementsByClassName("book-isbn");
    const quantitys = document.getElementsByClassName("cart-book-quantity");
    const prices = document.getElementsByClassName("cart-book-price");
    for (let i = 0; i < checkboxs.length; i++) {
        if (checkboxs[i].checked) {
            let priceText = prices[i].textContent;
            let price = priceText.substring(0, priceText.length - 1);
            const item = {
                bookIsbn: isbns[i].value,
                quantity: Number(quantitys[i].textContent),
                price: Number(price)
            };

            items.push(item);
        }
    }

    IMP.init(IMP_MERCHANT_ID);

    IMP.request_pay({
        pg: 'html5_inicis',  // PG사 (이니시스 예시)
        pay_method: 'card',  // 결제수단
        merchant_uid: merchant_uid,
        name: '세모책 결제 시스템',
        amount: totalPrice.textContent, //TotalPrice
        buyer_email: decoded.sub, //localStorage에서 토큰 가져와서 까서 email 저장
    }, function (rsp) {
        if (rsp.success) {
            //결제 성공 시 처리
            axios.post("http://localhost:8080/api/payments/verify",
                {
                    impUid: rsp.imp_uid,
                    merchantUid: rsp.merchant_uid,
                    items: items
                }, 
                {
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json"
                },
            })
            .then(() => {
                alert("결제가 완료되었습니다.");
                location.href = "paymentResult.html";
            })
            .catch(() => {
                alert("결제 금액이 올바르지 않습니다.");
                location.reload(true);
            })
        } else {
            //결제 실패 시 처리
            alert("결제를 취소하였습니다.");
        }
    });
}