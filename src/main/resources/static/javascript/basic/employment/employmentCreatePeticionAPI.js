const crearSugerenciaEmpleo = async function (e1){
    e1.stopPropagation();
    e1.preventDefault();

    const data = {
        nombre: document.getElementById('nombre').value,
        empresa_ofertante: document.getElementById('empresa_ofertante').value,
        requisitos: document.getElementById('requisitos').value,
        descripcion: document.getElementById('descripcion').value,
        informacion_extra: document.getElementById('informacion_extra').value,
        url: document.getElementById('url').value
    };

    fetch("http://localhost:8080/api/employment/createRequest", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='http://localhost:8082/listaEmpleoLogin.html';
            alert("Petición de empleo creada correctamente");
        } else {
            alert("Error al crear la peticion");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", crearSugerenciaEmpleo);