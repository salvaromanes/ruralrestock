function mostrarChats(){
    fetch("http://localhost:8080/api/chat/getAll").then(res => {
        res.json().then(json => {
            chats = json;
            imprimirChats();
        });
    });
}

function imprimirChats(){
    let contenedor = document.getElementById("cuerpoTabla");

    chats.forEach(chat => {
        contenedor.innerHTML += mapearChat(chat);
    });
}

function mapearChat(chat){
    return `<tr>
                <td>${chat.autor}</td>
                <td>${chat.origen}</td>
                <td>${chat.destino}</td>
                <td>${chat.fecha}</td>
                <td>${chat.plazas}</td>
                <td>${chat.interesados}</td>
                <td>
                    <button type="button" class="btn btn-primary" onclick="interesado('${chat.autor}', '${chat.origen}', '${chat.destino}', '${chat.fecha}')">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-car-front-fill" viewBox="0 0 16 16">
                          <path d="M2.52 3.515A2.5 2.5 0 0 1 4.82 2h6.362c1 0 1.904.596 2.298 1.515l.792 1.848c.075.175.21.319.38.404.5.25.855.715.965 1.262l.335 1.679q.05.242.049.49v.413c0 .814-.39 1.543-1 1.997V13.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-1.338c-1.292.048-2.745.088-4 .088s-2.708-.04-4-.088V13.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-1.892c-.61-.454-1-1.183-1-1.997v-.413a2.5 2.5 0 0 1 .049-.49l.335-1.68c.11-.546.465-1.012.964-1.261a.8.8 0 0 0 .381-.404l.792-1.848ZM3 10a1 1 0 1 0 0-2 1 1 0 0 0 0 2m10 0a1 1 0 1 0 0-2 1 1 0 0 0 0 2M6 8a1 1 0 0 0 0 2h4a1 1 0 1 0 0-2zM2.906 5.189a.51.51 0 0 0 .497.731c.91-.073 3.35-.17 4.597-.17s3.688.097 4.597.17a.51.51 0 0 0 .497-.731l-.956-1.913A.5.5 0 0 0 11.691 3H4.309a.5.5 0 0 0-.447.276L2.906 5.19Z"/>
                        </svg>
                    </button>
                </td>
            </tr>`;
}

function interesado(autor, origen, destino, fecha) {
    const data = {
        autor: autor,
        origen: origen,
        destino: destino,
        fecha: fecha
    };

    fetch('http://localhost:8080/api/chat/estoyInteresado', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        } else {
            alert('Error al interesarte por el viaje');
        }
    })
    .then(message => {
        if (message) {
            alert(message);
            location.reload();
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

$(document).ready(function() {
  $(".search").keyup(function () {
    var searchTerm = $(".search").val();
    var listItem = $('.results tbody').children('tr');
    var searchSplit = searchTerm.replace(/ /g, "'):containsi('")

  $.extend($.expr[':'], {'containsi': function(elem, i, match, array){
        return (elem.textContent || elem.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
    }
  });

  $(".results tbody tr").not(":containsi('" + searchSplit + "')").each(function(e){
    $(this).attr('visible','false');
    $(this).hide();
  });

  $(".results tbody tr:containsi('" + searchSplit + "')").each(function(e){
    $(this).attr('visible','true');
    $(this).show();
  });

  var jobCount = $('.results tbody tr[visible="true"]').length;
    $('.counter').text(jobCount + ' item');

  if(jobCount == '0') {$('.no-result').show();}
    else {$('.no-result').hide();}
		  });
});