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
    .then(() => {
        location.href = "findEmailResult.html";
    })
    .catch(() => {
        alert("존재하지 않은 회원입니다.");
    })
}

findEmailBtn.addEventListener("click", () => {
    findEmail();
})