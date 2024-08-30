let incidentes = [];

function mostrarIncidentes(){
    fetch("http://localhost:8080/api/incident/getAllCloseAutorizado").then(res => {
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