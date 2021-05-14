//hide alert
$(document).ready(function() {

	$("#alertSuccess").hide();
	$("#alertError").hide();
	$("#hidIDSave").val("");
	$("#FUNDER")[0].reset();
});

$(document).on("click", "#btnSave", function(event) {

	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
   
	var type = ($("#id").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "FunderAPI",
		type : type,
		data : $("#FUNDER").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onItemSaveComplete(response.responseText, status);
		}
	});

});

function onItemSaveComplete(response, status) {
	
	if (status == "success") {
		
		//console.log(response);
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#FunderGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error") {
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} 
	else if (status == "error") {
		
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
		
	} else {
		
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#id").val("");
	$("#FUNDER")[0].reset();
}

$(document).on("click", ".btnRemove", function(event) {

	
	$.ajax({
		url : "FunderAPI",
		type : "DELETE",
		data : "id=" + event.target.value,
		dataType : "text",
		complete : function(response, status) {
			onItemDeleteComplete(response.responseText, status);
			window.location.reload(true);
			
		}
	});
});

function onItemDeleteComplete(response, status) {
	
	if (status == "success") {
		
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") {
			
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#FunderGrid").html(resultSet.data);
			
		} else if (resultSet.status.trim() == "error") {
			
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
		
	} else if (status == "error") {
		
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
		
	} else {
		
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document).on("click",".btnUpdate",function(event)
		{
		
			$("#id").val($(this).closest("tr").find('td:eq(0)').text());
			$("#title").val($(this).closest("tr").find('td:eq(1)').text());
			$("#content").val($(this).closest("tr").find('td:eq(2)').text());
			$("#pdate").val($(this).closest("tr").find('td:eq(3)').text());
			$("#ptime").val($(this).closest("tr").find('td:eq(4)').text());		
});

// CLIENTMODEL=========================================================================
function validateItemForm() {
	
	
	
	// Post Title
	if ($("#title").val().trim() == "") {
		return "Please enter post title.";
	}
	
	// Post Content
	if ($("#content").val().trim() == "") {
		return "Please enter post content.";
	}

	// Post Publish Date
	if ($("#pdate").val().trim() == "") {
		return "Please enter publish date.";
	}
	
	// Post Publish Time
	if ($("#ptime").val().trim() == "") {
		return "Please enter publish time.";
	}
	
	
	return true;
}