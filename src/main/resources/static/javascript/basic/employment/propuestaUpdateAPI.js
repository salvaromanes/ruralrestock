function editarPropuesta() {
    const data = {
        nombre: document.getElementById('nombre').value,
        requisitos: document.getElementById('requisitos').value,
        descripcion: document.getElementById('descripcion').value,
        informacion_extra: document.getElementById('informacion_extra').value,
        url: document.getElementById('url').value
    };

    fetch("http://localhost:8080/api/employment/update", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='misPropuestasEmpleo.html';
            alert("Propuesta de empleo actualizado correctamente");
        } else {
            alert("Error al editar la propuesta de empleo");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", editarPropuesta);