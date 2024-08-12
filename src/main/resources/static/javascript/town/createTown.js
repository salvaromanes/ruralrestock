const crearMunicipio = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        nombre: document.getElementById('nombre').value,
        ubicacion: document.getElementById('ubicacion').value,
        historia: document.getElementById('historia').value
    };

    fetch("http://localhost:8080/api/town/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='http://localhost:8080/ruralrestock/municipios/listaMunicipiosAdmin.html';
            alert("Municipio creado correctamente");
        } else {
            alert("Algo ha fallado al crear el municipio");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", crearMunicipio);