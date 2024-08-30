const crearIncidente = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        titulo: document.getElementById('titulo').value,
        tipo: document.getElementById('tipo').value,
        descripcion: document.getElementById('descripcion').value,
        municipio: document.getElementById('municipio').value
    };

    fetch("http://localhost:8080/api/incident/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='misIncidentes.html';
            alert("Incidencia creada correctamente");
        } else {
            alert("Error al crear la incidencia");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", crearIncidente);