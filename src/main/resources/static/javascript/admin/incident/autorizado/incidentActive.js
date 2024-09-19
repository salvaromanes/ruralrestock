let incidentes = [];

function mostrarIncidentes(){
    fetch("http://localhost:8080/api/incident/getAllActiveAutorizado").then(res => {
        res.json().then(json => {
            incidentes = json;
            imprimirIncidentes();
        });
    });
}

function imprimirIncidentes(){
    let contenedor = document.getElementById("cuerpoTabla");

    incidentes.forEach(incidente => {
        contenedor.innerHTML += mapearIncidente(incidente);
    });
}

function mapearIncidente(incidente){
    return `<tr>
            <td>${incidente.titulo}</td>
            <td>${incidente.creador}</td>
            <td>${incidente.estado}</td>
            <td>${incidente.descripcion}</td>
            <td>${incidente.municipio}</td>
            <td>${incidente.fecha}</td>
            <td>
                <button type="button" class="btn btn-primary" onclick="cambiarEstado('${incidente.clave}')">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-lg" viewBox="0 0 16 16">
                      <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8z"/>
                    </svg>
                </button>
            </td>
        </tr>`;
}

function cambiarEstado(clave) {
    fetch('http://localhost:8080/api/incident/updateStatus/' + clave, {
        method: 'GET',
    })
    .then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Error al modificar el estado');
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