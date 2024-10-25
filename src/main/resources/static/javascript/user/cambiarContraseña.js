const modificarUsuario = async function (e) {
    e.stopPropagation();
    e.preventDefault();

    const data = {
        password_antigua: await sha256(document.getElementById('antigua').value),
        password_nueva: await sha256(document.getElementById('nueva').value),
        password_nueva_verificacion: await sha256(document.getElementById('verifica').value)
    };

    fetch("http://localhost:8080/api/user/updatePassword", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        window.location.href='misDatos.html';
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", modificarUsuario);

async function sha256(message) {
    const msgBuffer = new TextEncoder().encode(message);
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);
    const hashUtf8 = new TextDecoder().decode(hashBuffer);
    console.log(hashUtf8.toString())
    return hashUtf8.toString();
}