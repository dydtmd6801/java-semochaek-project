const password = document.getElementById("passwordValue");
const confirmPassword = document.getElementById("confirmPassword");
const passwordDiv = document.getElementById("password");
const email = document.getElementById("emailValue");
const sendMailBtn = document.getElementById("send-mail-btn");
const emailDiv = document.getElementById("email");

let passwordChecker = false;

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
        div.appendChild(codeInput);
        div.appendChild(verifyBtn);
        emailDiv.appendChild(div);
        sendMailBtn.textContent = "재전송";
    }
});