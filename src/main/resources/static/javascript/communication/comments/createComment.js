const crearComentario = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        tema: document.getElementById('tema').value,
        descripcion: document.getElementById('descripcion').value
    };

    fetch("http://localhost:8080/api/comment/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='foro.html';
            alert("Comentario creado correctamente");
        } else {
            alert("Algo ha fallado al crear el comentario");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", crearComentario);