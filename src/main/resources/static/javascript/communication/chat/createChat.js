const crearChat = async function (e){
    e.stopPropagation();
    e.preventDefault();

    const data = {
        origen: document.getElementById('origen').value,
        destino: document.getElementById('destino').value,
        fecha: document.getElementById('fecha').value,
        plazas: document.getElementById('plazas').value
    };

    fetch("http://localhost:8080/api/chat/create", {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    }).then(res => {
        if (res.status == 201) {
            window.location.href='chat.html';
            alert("Chat creado correctamente");
        } else {
            alert("Algo ha fallado al crear el chat");
        }
    });
}

const form = document.getElementById("request-form");
form.addEventListener("submit", crearChat);