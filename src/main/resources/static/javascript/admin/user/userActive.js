let users = [];

function mostrarUsuarios(){
    fetch("http://localhost:8080/api/user/getAllActive").then(res => {
        res.json().then(json => {
            users = json;
            imprimirUsuarios();
        });
    });
}

function imprimirUsuarios(){
    let contenedor = document.getElementById("cuerpoTabla");

    users.forEach(user => {
        contenedor.innerHTML += mapearUsuario(user);
    });
}

function mapearUsuario(usuario){
    return `<tr>
            <td>${usuario.dni}</td>
            <td>${usuario.nombre}</td>
            <td>${usuario.apellidos}</td>
            <td>${usuario.email}</td>
            <td>${usuario.municipio}</td>
            <td>
                <button type="button" class="btn btn-primary" onclick="bloquearUsuario('${usuario.dni}')">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
                      <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2m3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2"/>
                    </svg>
                </button>
            </td>
        </tr>`;
}

function bloquearUsuario(dni) {
    fetch('http://localhost:8080/api/user/blockUser/' + dni, {
        method: 'GET',
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error al bloquear al usuario');
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