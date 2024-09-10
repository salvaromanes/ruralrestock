function editarPropuesta() {
    const data = {
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value
    };

    fetch("http://localhost:8080/api/event/update", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='misPropuestasEventos.html';
            alert("Propuesta de evento actualizada correctamente");
        } else {
            alert("Error al editar la propuesta de evento");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", editarPropuesta);