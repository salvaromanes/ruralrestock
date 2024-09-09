function editarEmpresa() {
    const data = {
        cif: document.getElementById('cif').value,
        propietario: document.getElementById('propietario').value,
        direccion: document.getElementById('direccion').value,
        descripcion: document.getElementById('descripcion').value,
        municipio: document.getElementById('municipio').value
    };

    fetch("http://localhost:8080/api/company/update", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Empresa actualizada correctamente");
            window.location.href='listaEmpresas.html';
        } else {
            alert("Error al crear la empresa");
        }
    });
}

const form = document.getElementById("register-form");
form.addEventListener("submit", editarEmpresa);