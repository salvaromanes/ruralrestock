function mostrarEventos(){
    fetch("http://localhost:8080/api/event/getAll").then(res => {
        res.json().then(json => {
            events = json;
            imprimirEventos();
        });
    });
}

function imprimirEventos(){
    let contenedor = document.getElementById("cuerpoTabla");

    events.forEach(event => {
        contenedor.innerHTML += mapearEvento(event);
    });
}

function mapearEvento(event){
    return `<tr>
        <td>${event.nombre}</td>
        <td>${event.fecha_inicio}</td>
        <td>${event.fecha_fin}</td>
        <td>${event.tipo}</td>
        <td>${event.descripcion}</td>
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