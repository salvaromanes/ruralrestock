function checkAuth() {
    fetch('http://localhost:8080/api/user/some-protected-route', {
        method: 'GET',
        credentials: 'include'
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            window.location.href = '/index.html';
        }
    })
    .then(username => {
        if (username) {
            let contenedor = document.getElementById("nombrePersona");
            contenedor.innerHTML = `Bienvenido, ${username}`;
        }
    })
    .catch(error => {
        console.error('Error en la autenticación:', error);
        window.location.href = '/index.html';
    });
}

document.addEventListener('DOMContentLoaded', checkAuth);