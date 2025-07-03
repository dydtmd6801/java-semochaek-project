const btnLogin = document.getElementById("btn-login");
const userEmail = document.getElementById("email");
const userPassword = document.getElementById("password");
btnLogin.addEventListener("click", () => {
    const request = {
        email : userEmail.value,
        password : userPassword.value
    };
    axios.post("http://localhost:8080/login", request)
        .then(response => {
            const token = response.data.token;
            localStorage.setItem("token", token);
        })
})