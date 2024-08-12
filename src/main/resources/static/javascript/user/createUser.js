const crearUsuario = async function (e) {
    e.stopPropagation();
    e.preventDefault();

    const data = {
        dni: document.getElementById('dni').value,
        nombre: document.getElementById('nombre').value,
        apellidos: document.getElementById('apellidos').value,
        email: document.getElementById('email').value,
        password: await sha256(document.getElementById('contraseña').value),
        municipio: document.getElementById('municipio').value
    };

    fetch("http://localhost:8080/api/user/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Usuario creado correctamente");
        } else {
            alert("Error al crear el usuario");
        }
    });
}

const form = document.getElementById("register-form");
form.addEventListener("submit", crearUsuario);

async function sha256(message) {
    const msgBuffer = new TextEncoder().encode(message);
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);
    const hashUtf8 = new TextDecoder().decode(hashBuffer);
    console.log(hashUtf8.toString())
    return hashUtf8.toString();
}