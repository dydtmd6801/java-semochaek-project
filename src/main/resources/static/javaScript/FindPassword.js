const verifyBtn = document.getElementById("verify-btn");
const verifyEmailDiv = document.getElementById("verify-email-div");
const btns = document.getElementsByClassName("btns");

let passwordChecker = false;

function sendEmail() {
    const email = document.getElementById("email");
    axios.post("http://localhost:8080/send-code",
        String(email.value),
        {
            headers : {
                "Content-type" : "application/json"
            }
        }
    )
    .then(() => {
        alert("인증번호가 전송되었습니다.");
    })
    .catch(() => {
        alert("올바른 이메일을 입력해주세요.");
        location.reload(true);
    })
}

function checkPassword() {
    const newPassword = document.getElementById("newPassword");
    const confirmPassword = document.getElementById("confirmPassword");

    if (newPassword.value !== confirmPassword.value) {
        passwordChecker = false;
        alert("비밀번호가 올바르지 않습니다.\n다시 입력해주세요");
    } else {
        passwordChecker = true;
    }
}

function verifyInputCode() {
    const code = document.getElementsByClassName("codeInput")[0];
    axios.post("http://localhost:8080/verify-code",
        {
            email: String(email.value),
            code: String(code.value)
        },
        {
            headers : {
                "Content-type" : "application/json"
            }
        }
    )
    .then(() => {
        verifyEmailDiv.remove();
        showNewPasswordForm();
    })
    .catch(() => {
        alert("인증번호가 올바르지 않습니다.");
    })
}

function showNewPasswordForm() {
    const verifyEmail = document.getElementById("verify-email");

    const mainDiv = document.createElement("div");
    mainDiv.id = "newPassword-div";

    const p = document.createElement("p");
    p.textContent = "새로운 비밀번호";

    const newPasswordDiv = document.createElement("div");
    newPasswordDiv.className = "password-div"

    const newPasswordInput = document.createElement("input");
    newPasswordInput.id = "newPassword";
    newPasswordInput.type = "password";
    newPasswordInput.placeholder = "새 비밀번호를 입력하세요";
    newPasswordDiv.appendChild(newPasswordInput);

    const confirmPassword = document.createElement("div");
    confirmPassword.className = "password-div"

    const confirmPasswordInput = document.createElement("input");
    confirmPasswordInput.id = "confirmPassword";
    confirmPasswordInput.type = "password";
    confirmPasswordInput.placeholder = "비밀번호 확인";
    confirmPasswordInput.addEventListener("focusout", () => {
        checkPassword();
    })
    confirmPassword.appendChild(confirmPasswordInput);

    const button = document.createElement("button");
    button.id = "resetPassword";
    button.textContent = "입력완료";

    mainDiv.appendChild(p);
    mainDiv.appendChild(newPasswordDiv);
    mainDiv.appendChild(confirmPassword);
    mainDiv.appendChild(button);

    verifyEmail.appendChild(mainDiv);
}

function showVerifyCode() {
    const div = document.createElement("div");
    div.className = "email-div";
    const input = document.createElement("input");
    input.type = "input";
    input.className = "codeInput";
    const btn = document.createElement("button");
    btn.textContent = "인증받기";
    btn.className = "btns";
    btn.addEventListener("click", verifyInputCode);
    div.appendChild(input);
    div.appendChild(btn);
    verifyEmailDiv.appendChild(div);
    verifyBtn.textContent = "재전송";
}

verifyBtn.addEventListener("click", () => {
    sendEmail();
    if (btns.length < 2) {
        showVerifyCode();
    }
})