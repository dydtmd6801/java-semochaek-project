const verifyBtn = document.getElementById("verify-btn");
const verifyEmailDiv = document.getElementById("verify-email-div");
const btns = document.getElementsByClassName("btns");

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

function showVerifyCode() {
    const div = document.createElement("div");
    div.className = "email-div";
    const input = document.createElement("input");
    input.type = "input";
    input.className = "codeInput";
    const btn = document.createElement("button");
    btn.textContent = "인증받기";
    btn.className = "btns";
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