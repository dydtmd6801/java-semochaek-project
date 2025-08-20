import validator from "./ValidateToken.js";

function checkToken() {
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

    return token;
}

async function loadOrderList(token) {
    await axios.get("http://localhost:8080/order/list",
        {
            headers : {
                Authorization: `Bearer ${token}`,
                "Content-type" : "application/json"
            }
        }
    )
    .then((response) => {
        createOrderList(response.data);
    })
}

function createOrderList(orderList) {
    const totalOrder = document.getElementById("total-order");

    orderList.forEach(order => {
        const orderDiv = document.createElement("div");
        orderDiv.className = "order";

        const date = document.createElement("div");
        date.className = "date"
        const dateText = order.paidAt.substring(0, 10);
        date.textContent = dateText.replaceAll("-",".");

        const orderNumber = document.createElement("div");
        orderNumber.className = "order-number";
        const orderNumberText = order.merchantId.substring(6);
        orderNumber.textContent = `주문번호 ${orderNumberText}`;

        const totalPrice = document.createElement("div");
        totalPrice.className = "total-price";
        totalPrice.textContent = `${order.amount}원`;

        const detail = document.createElement("div");
        detail.className = "detail"
        const detailBtn = document.createElement("button");
        detailBtn.className = "detail-btn"
        detailBtn.textContent = "상세보기"
        detail.appendChild(detailBtn);

        orderDiv.appendChild(date);
        orderDiv.appendChild(orderNumber);
        orderDiv.appendChild(totalPrice);
        orderDiv.appendChild(detail);

        totalOrder.appendChild(orderDiv);
    });
}

document.addEventListener("DOMContentLoaded",() => {
    const token = checkToken();
    loadOrderList(token);
})