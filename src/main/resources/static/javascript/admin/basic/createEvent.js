const baseUrl = "http://localhost:8082";

const crearEvento = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        nombre: document.getElementById('nombre').value,
        fecha_inicio: document.getElementById('fecha_inicio').value,
        fecha_fin: document.getElementById('fecha_fin').value,
        tipo: document.getElementById('tipo').value,
        descripcion: document.getElementById('descripcion').value,
        municipio: document.getElementById('municipio').value
    };

    fetch(baseUrl + "/api/event/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Evento creado correctamente");
        } else {
            alert("Error al crear el evento");
        }
    });
}

const form = document.getElementById("register-form");
form.addEventListener("submit", crearEvento);