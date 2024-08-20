const crearVivienda = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        direccion: document.getElementById('direccion').value,
        propietario: document.getElementById('propietario').value,
        tipo: document.getElementById('tipo').value,
        precio: document.getElementById('precio').value,
        informacion: document.getElementById('informacion').value,
        contacto: document.getElementById('contacto').value,
        municipio: document.getElementById('municipio').value
    };

    fetch("http://localhost:8080/api/livingPlace/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Oferta de vivienda creada correctamente");
        } else {
            alert("Error al crear la oferta de vivienda");
        }
    });
}

const form = document.getElementById("register-form");
form.addEventListener("submit", crearVivienda);