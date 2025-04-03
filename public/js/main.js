// The root URL for the RESTful services
var rootURL = "http://localhost:8081/wines";

var addWine = function () {
	console.log('addWine');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data){
			console.log("wine added");
			$('#errorMsg p').remove();
			$('#successMsg').append('<p style="color:green;">'+data.name +' sucessfully added</p>');
			$('#wineList li').remove();
            findAll();
		},
		error: function(xhr, textStatus, errorThrown){
			var message=JSON.parse(xhr.responseText);
			$('#errorMsg p').remove();
			$('#successMsg p').remove();
			$('#errorMsg').append('<p>'+message.errorMessage+'</p>');
		}
	});
};

//Helper function to serialize all the form fields into a JSON string
var formToJSON=function () {
	return JSON.stringify({
		"name": $('#name').val(), 
		"grapes": $('#grapes').val(),
		"country": $('#country').val(),
		"year": $('#year').val(),
		});
};
var clearClick = function () {
	// clear all text boxes
	$("#name").val("");
    $("#year").val("");
    $("#grapes").val("");
    $("#country").val("");
    $('#errorMsg p').remove();
}

var findAll=function() {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList
	});
};

var renderList= function(data) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	//var list = data == null ? [] : (data instanceof Array ? data : [data]);
    var list=data;
	$('#wineList p').remove();
	var htmlStr="<ul>";
	$.each(list, function(index, wine) {
		htmlStr+='<li>'+wine.name+"-"+wine.year+" "+wine.country+'</li>';
	});
	htmlStr+="</ul>";
	$('#wineList').append(htmlStr);
};
//When the DOM is ready.
$(document).ready(function(){
	$(document).on("click", '#clear',function(){clearClick();} );
	$(document).on("click", '#add',function(){addWine(); return false;} );
	findAll();
});
