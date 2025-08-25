import parser from "./token-parse.js";
import validator from "./ValidateToken.js"

const loginWrapper = document.getElementsByClassName("login")[0];

function checkLogin() {
    const token = localStorage.getItem("token");
    
    if (token == null) {
        const btnLogin = document.createElement("a");
        btnLogin.href = "login.html";
        btnLogin.className = "loginBtn";
        btnLogin.textContent = "세모책 로그인";
        loginWrapper.appendChild(btnLogin);
    } else {
        validator.validateToken(token);
        const parseToken = parser.parseJwt(token);
        let userEmail = parseToken.sub.split("@");
        const loginInfo = document.createElement("div");
        loginInfo.className = "login-info";
        const userInfo = document.createElement("p");
        userInfo.textContent = `${userEmail[0]} 님 환영합니다`;
        userInfo.className = "user-info";
        const btnLogout = document.createElement("button");
        btnLogout.className = "logout-btn";
        btnLogout.textContent = "로그아웃";
        btnLogout.addEventListener("click", () => {
            localStorage.removeItem("token");
            alert("로그아웃 되었습니다.");
            location.reload(true);
        })
        loginInfo.appendChild(userInfo);
        loginInfo.appendChild(btnLogout);
        loginWrapper.appendChild(loginInfo);
    }
}

export default { checkLogin };