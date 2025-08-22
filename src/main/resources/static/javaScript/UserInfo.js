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

function loadUserInfo(token) {
    axios.post("http://localhost:8080/getUserInfo",{},
        {
            headers : {
                Authorization : `Bearer ${token}`,
                "Content-type" : "application/json"
            }
        }
    )
    .then((response) => {
        setUserInfo(response.data);
    })
    .catch((error) => {
        console.log(error);
        alert(error.message);
    })
}

function setUserInfo(userInfo) {
    const email = document.getElementById("email");
    const name = document.getElementById("name");
    const telephone = document.getElementById("telephone");

    email.textContent = userInfo.email;
    name.textContent = userInfo.name;
    const first = userInfo.telephone.substring(0, 3);
    const second = userInfo.telephone.substring(3, 7);
    const third = userInfo.telephone.substring(7);
    telephone.textContent = `${first}-${second}-${third}`;
}

document.addEventListener("DOMContentLoaded",() => {
    const token = checkToken();
    loadUserInfo(token);
})

function changeName(newName, token) {
    axios.post("http://localhost:8080/changeName",
        newName, {
            headers : {
                Authorization: `Bearer ${token}`,
                "Content-type": "application/json"
            }
        }
    )
    .then(() => {
        alert("이름이 변경되었습니다.");
        location.reload(true);
    })
    .catch((error) => {
        console.log(error);
    })
}

const nameModifyBtn = document.getElementById("name-modify-btn");
nameModifyBtn.addEventListener("click", () => {
    const token = checkToken();
    const newName = prompt("새로운 이름을 입력하세요");
    if (newName) {
        changeName(newName, token);
    }
})