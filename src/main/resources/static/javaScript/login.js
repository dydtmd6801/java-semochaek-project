const btnLogin = document.getElementById("btn-login");
const userEmail = document.getElementById("email");
const userPassword = document.getElementById("password");
const errorWrapper = document.getElementById("error-wrapper");

btnLogin.addEventListener("click", () => {
    const request = {
        email : userEmail.value,
        password : userPassword.value
    };
    axios.post("http://localhost:8080/login", request)
        .then(response => {
            const token = response.data.token;
            localStorage.setItem("token", token);
            location.href = "index.html";
        })
        .catch(() => {
            if (!document.getElementsByClassName("error")[0]) {
                const loginError = document.createElement("p");
                loginError.className = "error"
                loginError.textContent = "⚠️이메일 혹은 비밀번호가 올바르지 않습니다.";
                errorWrapper.appendChild(loginError);
            }
        })
})

userEmail.addEventListener("focusin", () => {
    document.getElementsByClassName("error")[0].remove();
})