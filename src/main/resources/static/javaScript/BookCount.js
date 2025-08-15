async function setBookCount() {
    const bookCount = await axios.get("http://localhost:8080/books/count");
    localStorage.setItem("bookCount", bookCount.data);
}

setBookCount();