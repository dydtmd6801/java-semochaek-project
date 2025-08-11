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

    const decoded = jwt_decode(token);
    const totalPrice = document.getElementsByClassName("cart-book-total-price")[1];
    const IMP = window.IMP;

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
            alert("결제가 완료되었습니다.");
        } else {
            //결제 실패 시 처리
        }
    });
}