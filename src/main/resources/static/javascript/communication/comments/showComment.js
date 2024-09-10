function cargarComentarios(){
    fetch('http://localhost:8080/api/user/protected-email', {
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
                mostrarNombre(username);
            }
        })
        .catch(error => {
            console.error('Error en la autenticación:', error);
            window.location.href = '/index.html';
        });
}

function mostrarNombre(autor) {
    fetch("http://localhost:8080/api/comment/getMisComentarios/" + autor).then(res => {
        res.json().then(json => {
            propuestas = json;
            imprimirPropuestas();
        });
    });
}

function imprimirPropuestas(){
    let contenedor = document.getElementById("tema");

    contenedor.innerHTML += `<option selected>Tema *</option>`;

    propuestas.forEach(propuesta => {
        contenedor.innerHTML += mapearPropuesta(propuesta);
    });
}

function mapearPropuesta(propuesta){
    return `<option value=${propuesta.tema}>${propuesta.tema}</option>`
}
