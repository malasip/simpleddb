var table;
$(document).ready(function(){
    table = loadTable();
    $("#search").on("keyup", function() {
        table.search($(this).val()).draw();
    });
    $('#addButton').on('click', function () {
        var modalBody = "/dashboard/modal/singleValueModal";
        var modal = $('#modal').modal({
            show: false,
        });
        modal.find('.modal-body').load(modalBody, function() {
            $('#modal-delete').remove();
            $('#singleValueForm').submit(function( event ) {
                event.preventDefault();
                saveItem(this, function(){table.ajax.reload(null, false)});
            });
        });
        modal.modal('show'); 
    });
});

function loadTable() {
    var table = $('#datatable').DataTable({
        searching: true,
        stateSave: true,
        dom: 'lrtip',
        scrollY: '100%',
        ajax: {
            'url': '/api/deviceTypes',
            'dataSrc': '_embedded.deviceTypes',
        },
        'columns': [
            { "width": "20%" },
            {data: null,
                render: function (data) {
                    return '<a href="#" value="' + data._links.self.href + '" class="table-link" aria-expanded="false" data-toggle="modal">' + data.name + '</a>'
                }
            },
            {data: null,
                render: function (data) {
                    var result = "";
                    $.ajax({
                        async: false,
                        url: data._links.devices.href,
                        data: {data},
                        dataType: 'json',
                        success: function(data) {
                            if (!Array.isArray(data._embedded.devices) || !data._embedded.devices.length) {
                            } else {
                                data._embedded.devices.forEach(element => {
                                    if (element.name != "") {
                                        result += element.name + ' ';
                                    }
                                });
                            }
                        }
                    });
                    if (result != null) {
                        return result;
                    }
                    return "None";
                }
            }
        ],
        fnDrawCallback: function() {
            $('.table-link').on('click', function() {
                var item = $(this).attr('value');
                var modalBody = "/dashboard/modal/singleValueModal";
                var modal = $('#modal').modal({
                    show: false,
                });
                modal.find('.modal-body').load(modalBody, function() {
                    $.getJSON(item, function( data ) {
                        $('#item-id').val(data._links.self.href);
                        $('#item-name').val(data.name);
                        $('#modal-delete').val(data._links.self.href);
                    });
                    $('#singleValueForm').submit(function( event ) {
                        event.preventDefault();
                        saveItem(this, function(){table.ajax.reload(null, false)});
                    });
                    $('#modal-delete').on('click', function() {
                        var item = $(this).attr('value');
                        deleteItem(item, function(){table.ajax.reload(null, false)});
                    })
                });
                modal.modal('show'); 
            });
        }
    });
    return table;
}

function saveItem(object, callback) {
    var data = {};
    var url = '/api/deviceTypes';
    var type = 'POST';
    var token = $("meta[name='_csrf']").attr("content");
    $.each($(object).serializeArray(), function(i, v) {
        data[v.name] = v.value;
    });
    if (data['item-id']) {
        url = data['item-id'];
        type = 'PATCH';
    }
    $.ajax({
        type: type,
        url: url,
        headers: {
            "X-CSRF-TOKEN": token
        },
        data: JSON.stringify(data),
        success: function() {
            $('#modal').modal('hide')
            callback();
        },
        error: function(error) {
            alert(error.status + " " + error.statusText);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function deleteItem(object, callback) {
    var token = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: 'DELETE',
        url: object,
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function() {
            $('#modal').modal('hide')
            callback();
        },
        error: function(error) {
            alert('There was an error deleting the item: ' + error.status + " " + error.statusText);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}