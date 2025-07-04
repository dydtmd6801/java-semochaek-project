import parser from "./token-parse.js";

function validateToken(token) {
    if (new Date() > new Date(parser.parseJwt(token).exp * 1000)) {
        localStorage.removeItem("token");
        return true;
    } else {
        return false;
    }
}

export default { validateToken };