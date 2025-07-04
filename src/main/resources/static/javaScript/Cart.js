import validator from "./ValidateToken.js";

function addCart(isbn) {
    const token = localStorage.getItem("token");

    if (token == null) {
        alert("로그인이 필요합니다.");
        return;
    }

    if (validator.validateToken(token)) {
        alert("로그인이 만료되었습니다. 다시 로그인 해주세요.");
        return;
    }

    axios.get(`http://localhost:8080/addCart?isbn=${isbn}`, {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
    .then(() => alert("장바구니에 추가되었습니다!"))
    .erro(() => alert("에러가 발생하였습니다. 관리자에게 문의 부탁드립니다."));
}

export default {addCart};