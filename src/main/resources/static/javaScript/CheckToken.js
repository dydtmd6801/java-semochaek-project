import parser from "./token-parse.js";

const loginWrapper = document.getElementsByClassName("login")[0];

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    
    if (token == null) {
        const btnLogin = document.createElement("a");
        btnLogin.href = "login.html";
        btnLogin.className = "loginBtn";
        btnLogin.textContent = "세모책 로그인";
        loginWrapper.appendChild(btnLogin);
    } else {
        const parseToken = parser.parseJwt(token);
        const userInfo = document.createElement("p");
        userInfo.textContent = `${parseToken.sub} 님 환영합니다`;
        const btnLogout = document.createElement("p");
        btnLogout.textContent = "로그아웃";
        loginWrapper.appendChild(userInfo);
        loginWrapper.appendChild(btnLogout);
    }
})