const password = document.getElementById("passwordValue");
const confirmPassword = document.getElementById("confirmPassword");
const passwordDiv = document.getElementById("password");

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

password.addEventListener("focusin", () => {
    document.getElementsByClassName("error")[0].remove();
})
confirmPassword.addEventListener("focusin", () => {
    document.getElementsByClassName("error")[0].remove();
})
confirmPassword.addEventListener("focusout",checkPassword);