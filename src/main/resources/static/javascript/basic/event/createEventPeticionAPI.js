const crearSugerenciaEvento = async function (e1){
    e1.stopPropagation();
    e1.preventDefault();

    const data = {
        nombre: document.getElementById('nombre').value,
        tipo: document.getElementById('tipo').value,
        fechaInicio: document.getElementById('fechaInicio').value,
        fechaFin: document.getElementById('fechaFin').value,
        descripcion: document.getElementById('descripcion').value,
        municipio: document.getElementById('municipio').value
    };

    fetch("http://localhost:8080/api/event/createRequest", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='http://localhost:8080/ruralrestock/basicos/eventos/listaEventosLogin.html';
            alert("Petición de evento creado correctamente");
        } else {
            alert("Error al crear la peticion");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", crearSugerenciaEvento);