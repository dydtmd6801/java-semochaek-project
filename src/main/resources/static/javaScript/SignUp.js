const password = document.getElementById("passwordValue");
const confirmPassword = document.getElementById("confirmPassword");
const passwordDiv = document.getElementById("password");
const email = document.getElementById("emailValue");
const sendMailBtn = document.getElementById("send-mail-btn");
const emailDiv = document.getElementById("email");
const register = document.getElementById("register");
const userName = document.getElementById("nameValue");
const telephone = document.getElementById("telephoneValue");

let passwordChecker = false;
let emailChecker = false;

function checkPassword() {
    if (password.value !== confirmPassword.value) {
        const error = document.createElement("p");
        error.className = "error";
        error.textContent = "⚠️ 비밀번호가 맞지않습니다.";
        passwordDiv.appendChild(error);
    } else {
        passwordChecker = true;
    }
}

function sendMail() {
    axios.post("http://localhost:8080/send-code", 
        String(email.value), 
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
        )
    .then(() => {
        alert("인증번호가 전송되었습니다.");
    })
    .catch(() => {
        alert("올바른 이메일을 입력해주세요");
        location.reload(true);
    })
}

function verifyCode() {
    const code = document.getElementsByClassName("codeInput")[0];
    axios.post("http://localhost:8080/verify-code",
        {
            email: String(email.value),
            code: String(code.value)
        },
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
    .then(() => {
        alert("인증이 완료되었습니다.");
        email.setAttribute("readonly", "readonly");
        sendMailBtn.setAttribute("disabled", "disabled");
        sendMailBtn.style.cursor = "default";
        document.getElementsByClassName("emailBtn")[1].remove();
        emailChecker = true;
    })
    .catch(() => {
        alert("코드가 일치하지 않습니다.");
    })
}

function registerUser() {
    if (!emailChecker) {
        return alert("이메일 인증은 필수입니다.");
    }

    if (!passwordChecker) {
        return alert("비밀번호가 알맞지 않습니다.\n다시 확인해주세요.");
    }

    if (!isNaN(telephone.value)) {
        return alert("전화번호가 올바르지 않습니다.");
    }
    
    axios.post("http://localhost:8080/register",
        {
            email: email.value,
            password: password.value,
            name: userName.value,
            phoneNumber: telephone.value
        },
        {
            headers : {
                "Content-Type": "application/json"
            }
        }
    )
    .then(() => {
        alert("회원가입이 완료되었습니다!");
        location.href = "login.html";
    })
    .catch((error) => {
        console.log(error);
        alert("중복되는 이메일입니다.");
        location.reload(true);
    })
}

password.addEventListener("focusin", () => {
    document.getElementsByClassName("error")[0].remove();
})
confirmPassword.addEventListener("focusin", () => {
    document.getElementsByClassName("error")[0].remove();
})
confirmPassword.addEventListener("focusout",checkPassword);

sendMailBtn.addEventListener("click",() => {
    sendMail();
    const emailBtn = document.getElementsByClassName("emailBtn");
    if (emailBtn.length < 2) {
        const div = document.createElement("div");
        div.className = "emailBtn";
        const codeInput = document.createElement("input");
        codeInput.type = "input";
        codeInput.className = "codeInput"
        const verifyBtn = document.createElement("button");
        verifyBtn.textContent = "인증받기"
        verifyBtn.addEventListener("click", verifyCode);
        div.appendChild(codeInput);
        div.appendChild(verifyBtn);
        emailDiv.appendChild(div);
        sendMailBtn.textContent = "재전송";
    }
});

register.addEventListener("click", () => {
    registerUser();
})