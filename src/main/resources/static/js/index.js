function getDeviceTypeOptions() {
    $.getJSON('/api/deviceTypes', function(data) {
        data._embedded.deviceTypes.forEach(element => {
            $('#device-type').append($('<option>', {
                value: element._links.self.href,
                text: element.name
            }));
        })
        $('#device-type').selectpicker({
            liveSearch: true,
            width: '100%',
        });
    });
}

function getDeviceModelOptions() {
    $.getJSON('/api/deviceModels', function(data) {
        data._embedded.deviceModels.forEach(element => {
            $('#device-model').append($('<option>', {
                value: element._links.self.href,
                text: element.name
            }));
        })
        $('#device-model').selectpicker({
            liveSearch: true,
            width: '100%',
        });
    });
}

function loadTable() {
    var table = $('#datatable').DataTable({
        searching: true,
        stateSave: true,
        dom: 'lrtip',
        scrollY: '100%',
        ajax: {
            'url': '/api/devices',
            'dataSrc': '_embedded.devices',
        },
        'columns': [
            {data: null,
                render: function (data) {
                    return '<a href="#" value="' + data._links.self.href + '" class="table-link" aria-expanded="false" data-toggle="modal">' + data.name + '</a>'
                }
            },
            {data: null,
                render: function (data) {
                    var name;
                    $.ajax({
                        async: false,
                        url: data._links.type.href,
                        data: {data},
                        dataType: 'json',
                        success: function(data) {
                            name = data.name;
                        }
                    });
                    return name;
                }
            },
            {data: null,
                render: function (data) {
                    var name;
                    $.ajax({
                        async: false,
                        url: data._links.model.href,
                        data: {data},
                        dataType: 'json',
                        success: function(data) {
                            name = data.name;
                        }
                    });
                    return name;
                }
            },
            {data: 'ipAddress'},
            {data: 'serial'},
            {data: 'purchaseDate'},
            {data: 'comment'},
        ],
        fnDrawCallback: function() {
            $('.table-link').on('click', function() {
                var item = $(this).attr('value');
                var modalBody = "/dashboard/modal/deviceModal";
                var modal = $('#modal').modal({
                    show: false,
                });
                $("#modalLabel").text("Device details");
                modal.find('.modal-body').load(modalBody, function() {
                    if(isAdmin()) {
                        getDeviceModelOptions();
                        getDeviceTypeOptions();
                    }
                    $.getJSON(item, function( data ) {
                        $('#device-id').val(data._links.self.href);
                        $('#device-name').val(data.name);
                        $.ajax({
                            async: true,
                            url: data._links.type.href,
                            data: {data},
                            dataType: 'json',
                            success: function(data) {
                                if(isAdmin()) {
                                    $('#device-type').selectpicker('val', data._links.self.href);
                                } else {
                                    $('#device-type').append($('<option>', {
                                        value: data._links.self.href,
                                        text: data.name
                                    }));
                                    $('#device-type').selectpicker({
                                        liveSearch: true,
                                        width: '100%',
                                    });
                                }
                            }
                        });
                        $.ajax({
                            async: true,
                            url: data._links.model.href,
                            data: {data},
                            dataType: 'json',
                            success: function(data) {
                                if(isAdmin()) {
                                    $('#device-model').selectpicker('val', data._links.self.href);
                                } else {
                                    $('#device-model').append($('<option>', {
                                        value: data._links.self.href,
                                        text: data.name
                                    }));
                                    $('#device-model').selectpicker({
                                        liveSearch: true,
                                        width: '100%',
                                    });
                                }
                            }
                        });
                        $('#device-ipaddress').val(data.ipAddress);
                        $('#device-macaddress').val(data.macAddress);
                        $('#device-serial-no').val(data.serial);
                        $('#device-purchase-date').val(data.purchaseDate);
                        $('#device-comment').val(data.comment);
                        $('#modal-delete').val(data._links.self.href);
                        if(!isAdmin()) {
                            $('#deviceForm :input').prop('disabled', true);
                            $('#modal-close').prop('disabled', false);
                        }
                    });
                    $('#deviceForm').submit(function( event ) {
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
    var url = '/api/devices';
    var type = 'POST';
    var token = $("meta[name='_csrf']").attr("content");
    $.each($(object).serializeArray(), function(i, v) {
        data[v.name] = v.value;
    });
    if (data['device-id']) {
        url = data['device-id'];
        type = 'PATCH';
    }
    $.ajax({
        type: type,
        url: url,
        data: JSON.stringify(data),
        headers: {
            "X-CSRF-TOKEN": token
        },
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

/*function isAdmin(callback) {
    var token = $("meta[name='_csrf']").attr("content");
    var uid = $('#user').attr('value');
    $.ajax({
        type: 'GET',
        url: "/api/users/" + uid + "/role",
        headers: {
            "X-CSRF-TOKEN": token
        },
        success: function(data) {
            if(data.name == "ADMIN") {
                if(callback) callback(true);
            }
            if(callback) callback(false);
        },
        error: function() {
            if(callback) callback(false);
        },
    });
}*/