function cargarIncidentes(){
    fetch('http://localhost:8080/api/user/protected-email', {
            method: 'GET',
            credentials: 'include'
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                window.location.href = '/index.html';
            }
        })
        .then(username => {
            if (username) {
                mostrarComentarios(username);
            }
        })
        .catch(error => {
            console.error('Error en la autenticación:', error);
            window.location.href = '/index.html';
        });
}

function mostrarComentarios(autor){
    fetch("http://localhost:8080/api/incident/getAllByUser/" + autor).then(res => {
        res.json().then(json => {
            comentarios = json;
            imprimirComentarios();
        });
    });
}

function imprimirComentarios(){
    let contenedor = document.getElementById("cuerpoTabla");

    comentarios.forEach(comentario => {
        contenedor.innerHTML += mapearComentario(comentario);
    });
}

function mapearComentario(comentario){
    return `<tr>
                <td>${comentario.titulo}</td>
                <td>${comentario.tipo}</td>
                <td>${comentario.descripcion}</td>
                <td>${comentario.municipio}</td>
            </tr>`;
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