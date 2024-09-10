function editarPropuesta() {
    const data = {
        tema: document.getElementById('tema').value,
        descripcion: document.getElementById('descripcion').value
    };

    fetch("http://localhost:8080/api/comment/update", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='misComentarios.html';
            alert("Propuesta de evento actualizada correctamente");
        } else {
            alert("Error al editar la propuesta de evento");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", editarPropuesta);