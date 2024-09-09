function editarMunicipio() {
    const data = {
        nombre: 'La Viñuela',
        historia: document.getElementById('historia').value
    };

    fetch("http://localhost:8080/api/town/update", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            alert("Municipio editado correctamente");
            window.location.href='listaMunicipiosAdmin.html';
        } else {
            alert("Algo ha fallado al editar el municipio");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", editarMunicipio);