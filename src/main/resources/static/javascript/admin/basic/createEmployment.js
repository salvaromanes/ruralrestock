const baseUrl = "http://localhost:8082";

const crearOfertaEmpleo = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        nombre: document.getElementById('nombre').value,
        empresa_ofertante: document.getElementById('empresa_ofertante').value,
        requisitos: document.getElementById('requisitos').value,
        descripcion: document.getElementById('descripcion').value,
        informacion_extra: document.getElementById('informacion_extra').value
    };

    fetch(baseUrl + "/api/employment/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Oferta de empleo creada correctamente");
        } else {
            alert("Error al crear la oferta de empleo");
        }
    });
}

const form = document.getElementById("register-form");
form.addEventListener("submit", crearOfertaEmpleo);