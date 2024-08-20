const crearEmpresa = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        cif: document.getElementById('cif').value,
        nombre: document.getElementById('nombre').value,
        propietario: document.getElementById('propietario').value,
        direccion: document.getElementById('direccion').value,
        descripcion: document.getElementById('descripcion').value,
        municipio: document.getElementById('municipio').value
    };

    fetch("http://localhost:8080/api/company/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Empresa creada correctamente");
        } else {
            alert("Error al crear la empresa");
        }
    });
}

const form = document.getElementById("register-form");
form.addEventListener("submit", crearEmpresa);