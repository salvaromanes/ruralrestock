function editarPropuesta() {
    const data = {
        direccion: document.getElementById('direccion').value,
        tipo: document.getElementById('tipo').value,
        precio: document.getElementById('precio').value,
        informacion: document.getElementById('informacion').value,
        contacto: document.getElementById('contacto').value
    };

    fetch("http://localhost:8080/api/livingPlace/update", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='misPropuestasViviendas.html';
            alert("Propuesta de vivienda actualizada correctamente");
        } else {
            alert("Error al editar la propuesta de vivienda");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", editarPropuesta);