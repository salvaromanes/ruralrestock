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
                    <button type="button" class="btn btn-primary" onclick="eliminarChat('${chat.autor}')">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                          <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                          <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                        </svg>
                    </button>
                </td>
            </tr>`;
}

function eliminarChat(autor) {
    fetch('http://localhost:8080/api/chat/delete/' + autor, {
        method: 'DELETE',
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error al eliminar el chat');
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