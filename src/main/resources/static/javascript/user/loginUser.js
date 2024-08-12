const loginUsuario = async function (e) {
    e.stopPropagation();
    e.preventDefault();

    const data = {
        email: document.getElementById('usuario').value,
        password: await sha256(document.getElementById('password').value)
    };

    fetch("http://localhost:8080/api/user/login", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.ok) {
            localStorage.setItem('sessionUsername', email);
            return res.text();
        } else {
            alert("Error: usuario o contraseña incorrectos");
        }
    })
    .then(response => {
        if (response) {
            window.location.href = response;
        }
    })
    .catch(error => {
        console.error('Error en la autenticación:', error);
        window.location.href = '/index.html';
    });
}

const login = document.getElementById("login-form");
login.addEventListener("submit", loginUsuario);

async function sha256(message) {
    const msgBuffer = new TextEncoder().encode(message);
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);
    const hashUtf8 = new TextDecoder().decode(hashBuffer);
    console.log(hashUtf8.toString())
    return hashUtf8.toString();
}