import cart from "./Cart.js";
import favorite from "./Favorite.js";

document.addEventListener("DOMContentLoaded", async() => {
    const bookList = document.getElementById("books");

    try {
        const page = param.get("page");
        const response = await axios.get(`http://localhost:8080/books?page=${page}`);

        // const books = response.data._embedded?.bookDTOList || [];
        const books = response.data.content;
        books.forEach(book => {
            const div = document.createElement("div");
            div.className = "book";

            // 왼쪽: 이미지
            const imageWrapper = document.createElement("div");
            imageWrapper.className = "book-image-wrapper";

            const img = document.createElement("img");
            img.src = book.image;
            img.className = "book-cover";
            imageWrapper.appendChild(img);

            // 가운데: 도서 정보(제목, 저자, 발행사, 가격)
            const infoContainer = document.createElement("div");
            infoContainer.className = "book-info";

            const title = document.createElement("p");
            title.className = "book-title";
            title.textContent = book.title;
            title.addEventListener("click", async() => {
                location.href = `bookDetail.html?isbn=${book.isbn}&page=${page}`
            })

            const subInfo = document.createElement("p");
            subInfo.className = "book-sub-info";
            subInfo.textContent = book.author
                ? `${book.author} · ${book.publisher}`
                : book.publisher;

            const price = document.createElement("p");
            price.className = "book-price";
            price.textContent = book.price == 0
                ? `판매종료`
                : `${book.price}원`;

            infoContainer.appendChild(title);
            infoContainer.appendChild(subInfo);
            infoContainer.appendChild(price);

            // 오른쪽: 버튼 모음
            const btns = document.createElement("div");
            btns.className = "book-btns";

            const btnFavorite = document.createElement("a");
            btnFavorite.className = "btn-favorite";
            btnFavorite.textContent = "찜하기";
            btnFavorite.addEventListener("click", () => {
                favorite.addFavoriteBook(book.isbn);
            })

            const btnCart = document.createElement("a");
            btnCart.className = "btn-cart";
            btnCart.textContent = "장바구니";
            btnCart.addEventListener("click", () => {
                cart.addCart(book.isbn);
            })

            const btnBuy = document.createElement("a");
            btnBuy.className = "btn-buy";
            btnBuy.textContent = "바로구매";

            btns.appendChild(btnFavorite);
            btns.appendChild(btnCart);
            btns.appendChild(btnBuy);

            // 조립
            div.appendChild(imageWrapper);
            div.appendChild(infoContainer);
            div.appendChild(btns);
            bookList.appendChild(div);
        })
    } catch (err) {
        bookList.innerHTML = err;
    }
})