const findEmailBtn = document.getElementById("find-btn");

async function findEmail() {
    const name = document.getElementById("name");
    const telephone = document.getElementById("telephone");

    if (name.value === "") {
        return alert("이름을 입력해주세요.");
    }

    if (telephone.value === "") {
        return alert("전화번호를 입력해주세요.");
    }

    if (isNaN(telephone.value)) {
        return alert("전화번호를 숫자로 입력해주세요.");
    }

    await axios.post("http://localhost:8080/findEmail",
        {
            name: name.value,
            phoneNumber: telephone.value
        }, 
        {
            headers : {
                "Content-type" : "application/json"
            }
        }
    )
    .then((result) => {
        document.getElementById("name-div").remove();
        document.getElementById("telephone-div").remove();
        findEmailBtn.remove();
        const findEmail = document.getElementById("find-email");
        const p = document.createElement("p");
        p.className = "email-result"
        p.innerHTML = `회원님의 이메일은 <span class="email-result-data">${result.data}</span> 입니다.`
        const button = document.createElement("button");
        button.textContent = "로그인하러가기";
        button.id = "find-btn";
        button.addEventListener("click", () => {
            location.href = "login.html";
        })
        findEmail.appendChild(p);
        findEmail.appendChild(button);
    })
    .catch(() => {
        alert("존재하지 않은 회원입니다.");
    })
}

findEmailBtn.addEventListener("click", () => {
    findEmail();
})