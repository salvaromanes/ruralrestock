function mostrarNombre() {
    fetch("http://localhost:8080/api/employment/getAllMyRequest").then(res => {
        res.json().then(json => {
            propuestas = json;
            imprimirPropuestas();
        });
    });
}

function imprimirPropuestas(){
    let contenedor = document.getElementById("nombre");

    contenedor.innerHTML += `<option selected>Nombre *</option>`;

    propuestas.forEach(propuesta => {
        contenedor.innerHTML += mapearPropuesta(propuesta);
    });
}

function mapearPropuesta(propuesta){
    return `<option value=${propuesta.nombre}>${propuesta.nombre}</option>`
}
