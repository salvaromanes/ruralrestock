let incidentes = [];

function mostrarIncidentes(){
    fetch("http://localhost:8080/api/incident/getAllStopAdmin").then(res => {
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
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
                      <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
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