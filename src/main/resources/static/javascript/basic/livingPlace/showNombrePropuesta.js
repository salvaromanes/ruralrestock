function mostrarNombre() {
    fetch("http://localhost:8080/api/livingPlace/getAllMyRequest").then(res => {
        res.json().then(json => {
            propuestas = json;
            imprimirPropuestas();
        });
    });
}

function imprimirPropuestas(){
    let contenedor = document.getElementById("direccion");

    contenedor.innerHTML += `<option selected>Direccion *</option>`;

    propuestas.forEach(propuesta => {
        contenedor.innerHTML += mapearPropuesta(propuesta);
    });
}

function mapearPropuesta(propuesta){
    return `<option value=${propuesta.direccion}>${propuesta.direccion}</option>`
}
