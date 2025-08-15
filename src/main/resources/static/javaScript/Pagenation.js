const totalPage = 565;
const pageBtn = document.getElementsByClassName("page-btn");
const prevBtn = document.getElementById("prev-btn");
const nextBtn = document.getElementById("next-btn");
const param = new URLSearchParams(window.location.search);
const page = param.get("page");
let currentPage = 1;
if (page) {
    currentPage = parseInt(page);
}

if (currentPage < 11) {
    prevBtn.style.display = "none";
} else {
    prevBtn.style.display = "inline";
}

if (currentPage > Math.floor(totalPage / 10) * 10) { 
    nextBtn.style.display = "none";
} else {
    nextBtn.style.display = "inline";
}

nextBtn.addEventListener("click", () => {
    let nextPage = (Math.floor((currentPage - 1) / 10)) * 10;
    for (let i = 0; i < pageBtn.length; i++) {
        nextPage += 1;
        pageBtn[i].textContent = nextPage;
    }
    location.href = `bookAll.html?page=${nextPage + 1}`; 
})

prevBtn.addEventListener("click", () => {
    let prevPage = (Math.floor(((currentPage - 1) / 10) - 2)) * 10;
    for (let i = 0; i < pageBtn.length; i++) {
        prevPage += 1;
        pageBtn[i].textContent = prevPage;
    }
    location.href = `bookAll.html?page=${prevPage + 1}`;
})

for (let i = 0; i < pageBtn.length; i++) {
    pageBtn[i].addEventListener("click",() => {
        location.href = `bookAll.html?page=${pageBtn[i].textContent}`;
    })
}

document.addEventListener("DOMContentLoaded",() => {
    if (currentPage > 0 && currentPage < totalPage + 1) {
        let startPage = Math.floor((currentPage - 1) / 10) * 10 + 1;
        for (let i = 0; i < pageBtn.length; i++) {
            pageBtn[i].textContent = startPage;
            startPage += 1;
            if (pageBtn[i].textContent > totalPage) {
                pageBtn[i].style.display = "none";
            }
        }
    } else {
        for (let i = 0; i < pageBtn.length; i++) {
            pageBtn[i].style.display = "none";
        }
        nextBtn.style.display = "none";
        prevBtn.style.display = "none";
    }
    for (let i = 0; i < pageBtn.length; i++) {
        if (pageBtn[i].textContent == page) {
            pageBtn[i].style.backgroundColor = "red";
        }
    }
})