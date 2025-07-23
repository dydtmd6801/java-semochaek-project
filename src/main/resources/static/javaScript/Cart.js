import validator from "./ValidateToken.js";

async function addCart(isbn) {
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

    const result = await axios.get(`http://localhost:8080/addCart?isbn=${isbn}`, {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })
    
    if (result.data == "중복") {
        if(confirm("이미 장바구니에 있는 책입니다.\n장바구니로 이동하시겠습니까?")) {
            location.href = "userCart.html";
        }
    }
    else if (result.data == "저장 완료") {
        alert("장바구니에 추가되었습니다.");
    }
}

async function loadCartList() {
    const cartBooksContainer = document.getElementsByClassName("cart-books-container")[0];
    const token = localStorage.getItem("token");

    if (token == null) {
        alert("로그인이 필요합니다.")
        location.href = "login.html";
        return;
    }

    if (validator.validateToken(token)) {
        alert("로그인이 만료되었습니다. 다시 로그인 해주세요.");
        location.href = "login.html";
        return;
    }

    const cartBooks = await axios.get("http://localhost:8080/searchCart", {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })

    cartBooks.data.forEach(book => {
        const cartBook = document.createElement("div");
        cartBook.className = "cart-book";

        // 체크박스
        const checkBox = document.createElement("input");
        checkBox.type = "checkbox";
        checkBox.className = "check-box";

        // 도서 정보
        const cartBookInfo = document.createElement("div");
        cartBookInfo.className = "cart-book-info";
        
        // 도서 정보 - 이미지
        const cartBookImage = document.createElement("img");
        cartBookImage.src = `${book.image}`;
        cartBookImage.className = "cart-book-image";
        cartBookInfo.appendChild(cartBookImage);
        
        // 도서정보 - 제목, 가격
        const cartBookInfoDiv = document.createElement("div");
        const cartBookTitle = document.createElement("p");
        cartBookTitle.className = "cart-book-title";
        cartBookTitle.textContent = `${book.title}`;
        const cartBookPrice = document.createElement("p");
        cartBookPrice.className = "cart-book-price";
        cartBookPrice.textContent = `${book.price}원`;
        cartBookInfoDiv.appendChild(cartBookTitle);
        cartBookInfoDiv.appendChild(cartBookPrice);
        cartBookInfo.appendChild(cartBookInfoDiv);

        // 개수에 따른 가격
        const cartBookQuantityByPriceContainer = document.createElement("div");
        cartBookQuantityByPriceContainer.className = "cart-book-quantity-by-price-container";

        // 개수에 따른 가격 - 개수 곱 가격
        const cartBookQuantityByPrice = document.createElement("p");
        cartBookQuantityByPrice.className = "cart-book-quantity-by-price";
        cartBookQuantityByPrice.textContent = `${cartBookPrice.textContent}`;
        cartBookQuantityByPriceContainer.appendChild(cartBookQuantityByPrice);

        // 개수에 따른 가격 - 개수 카운팅
        const cartBookQuantityContainer = document.createElement("div");
        cartBookQuantityContainer.className = "cart-book-quantity-container";
        const cartBookQuantity = document.createElement("div")
        cartBookQuantity.className = "cart-book-quantity";
        cartBookQuantity.textContent = "1";
        const btnCartBookQuantityMinus = document.createElement("button");
        btnCartBookQuantityMinus.className = "btn-cart-book-quantity-minus";
        btnCartBookQuantityMinus.textContent = "-"
        btnCartBookQuantityMinus.addEventListener("click", () => {
            minusBookQuantity(cartBookQuantityByPrice, cartBookQuantity, cartBookPrice);
        });
        const btnCartBookQuantityPlus = document.createElement("button");
        btnCartBookQuantityPlus.className = "btn-cart-book-quantity-plus";
        btnCartBookQuantityPlus.textContent = "+";
        btnCartBookQuantityPlus.addEventListener("click", () => {
            plusBookQuantity(cartBookQuantityByPrice, cartBookQuantity, cartBookPrice);
        });
        cartBookQuantityContainer.appendChild(btnCartBookQuantityMinus);
        cartBookQuantityContainer.appendChild(cartBookQuantity);
        cartBookQuantityContainer.appendChild(btnCartBookQuantityPlus);

        cartBookQuantityByPriceContainer.appendChild(cartBookQuantityContainer);

        //삭제 버튼
        const btnCartBookDelete = document.createElement("button")
        btnCartBookDelete.className = "btn-cart-book-delete"
        btnCartBookDelete.textContent = "ｘ";

        cartBook.appendChild(checkBox);
        cartBook.appendChild(cartBookInfo);
        cartBook.appendChild(cartBookQuantityByPriceContainer);
        cartBook.appendChild(btnCartBookDelete);


        cartBooksContainer.appendChild(cartBook);
    });
}

function loadList() {
    document.addEventListener("DOMContentLoaded", async() => {
        loadCartList();
    })
}

function plusBookQuantity(quantityByPrice, cartBookQuantity, bookPrice) {
    cartBookQuantity.textContent = parseInt(cartBookQuantity.textContent) + 1;
    calcQuantity(quantityByPrice, cartBookQuantity, bookPrice);
}

function minusBookQuantity(quantityByPrice, cartBookQuantity, bookPrice) {
    if (cartBookQuantity.textContent > 1) {
        cartBookQuantity.textContent = parseInt(cartBookQuantity.textContent) - 1;
    }
    calcQuantity(quantityByPrice, cartBookQuantity, bookPrice);
}

function calcQuantity(quantityByPrice, quantity, bookPrice) {
    quantityByPrice.textContent = parseInt(quantity.textContent) * parseInt(bookPrice.textContent);
    quantityByPrice.textContent += "원";
}

export default {addCart, loadList};