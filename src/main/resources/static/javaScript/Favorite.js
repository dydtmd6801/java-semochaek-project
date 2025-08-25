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

function loadFavoriteList(token) {
    axios.get("http://localhost:8080/favorite/get",
        {
            headers : {
                Authorization : `Bearer ${token}`,
                "Content-type" : "application/json"
            }
        }
    )
    .then((response) => {
        createFavoriteList(response.data);
    })
}

function createFavoriteList(favorites) {
    favorites.forEach(favorite => {
        const favoriteBooksContainer = document.getElementsByClassName("favorite-books-container")[0];

        const favoriteBookWrapper = document.createElement("div");
        favoriteBookWrapper.className = "favorite-book-wrapper";

        const favoriteBookInfos = document.createElement("div");
        favoriteBookInfos.className = "favorite-book-infos";

        const favoriteBookImage = document.createElement("div");
        favoriteBookImage.className = "favorite-book-image";
        const image = document.createElement("img");
        image.className = "book-image";
        image.src = favorite.image;
        favoriteBookImage.appendChild(image);

        const favoriteBookInfo = document.createElement("div");
        favoriteBookInfo.className = "favorite-book-info";
        const favoriteBookTitle = document.createElement("p");
        favoriteBookTitle.className = "favorite-book-title";
        favoriteBookTitle.textContent = `${favorite.title}`
        const favoriteBookPrice = document.createElement("p");
        favoriteBookPrice.className = "favorite-book-price";
        favoriteBookPrice.textContent = `${favorite.price}원`;
        favoriteBookInfo.appendChild(favoriteBookTitle);
        favoriteBookInfo.appendChild(favoriteBookPrice);

        favoriteBookInfos.appendChild(favoriteBookImage);
        favoriteBookInfos.appendChild(favoriteBookInfo);

        const favoriteBookBtns = document.createElement("div");
        favoriteBookBtns.className = "favorite-book-btns";
        const cartBtn = document.createElement("button");
        cartBtn.className = "cart-btn";
        cartBtn.textContent = "장바구니";
        const removeBtn = document.createElement("button");
        removeBtn.className = "remove-btn";
        removeBtn.textContent = "삭제"
        favoriteBookBtns.appendChild(cartBtn);
        favoriteBookBtns.appendChild(removeBtn);

        favoriteBookWrapper.appendChild(favoriteBookInfos);
        favoriteBookWrapper.appendChild(favoriteBookBtns);

        favoriteBooksContainer.appendChild(favoriteBookWrapper);
    });
}

function addFavoriteBook(isbn) {
    const token = checkToken();
    axios.get(`http://localhost:8080/favorite/add?isbn=${isbn}`, {
        headers: {
            Authorization: `Bearer ${token}`,
            "Content-type": "application/json"
        }
    })
    .then((response) => {
        if (response.data == "duplicate") {
            if (confirm("이미 찜 목록에 있는 책입니다.\n찜 목록으로 이동하시겠습니까?")) {
                location.href = "userFavorite.html";
            }
        } else if (response.data == "success") {
            if (confirm("찜 목록에 추가되었습니다.\n찜 목록으로 이동하시겠습니까?")) {
                location.href = "userFavorite.html";
            }
        }
    })
}

export default { addFavoriteBook, checkToken, loadFavoriteList };