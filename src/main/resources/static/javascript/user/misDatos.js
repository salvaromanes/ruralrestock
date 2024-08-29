function cargarMisDatos(){
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
                mostrarMisDatos(username);
            }
        })
        .catch(error => {
            console.error('Error en la autenticación:', error);
            window.location.href = '/index.html';
        });
}

function mostrarMisDatos(username){
    fetch("http://localhost:8080/api/user/getMisDatos/" + username).then(res => {
        res.json().then(json => {
            datos = json;
            imprimirDatos(datos);
        });
    });
}

function imprimirDatos(misDatos){
    let contenedor = document.getElementById("cuerpoTabla");

    contenedor.innerHTML += mapearMisDatos(misDatos);
}

function mapearMisDatos(misDatos){
    return `<tr>
                <td>DNI</td>
                <td>${misDatos.dni}</td>
            </tr>
            <tr>
                <td>Nombre</td>
                <td>${misDatos.nombre}</td>
            </tr>
            <tr>
                <td>Apellidos</td>
                <td>${misDatos.apellidos}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${misDatos.email}</td>
            </tr>
            <tr>
                <td>Municipio</td>
                <td>${misDatos.municipio}</td>
            </tr>`;
}