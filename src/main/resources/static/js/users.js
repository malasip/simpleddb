function getUserRoleOptions() {
    $.getJSON('/api/userRoles', function(data) {
        data._embedded.userRoles.forEach(element => {
            $('#role').append($('<option>', {
                value: element._links.self.href,
                text: element.name
            }));
        })
        $('#role').selectpicker({
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
            'url': '/api/users',
            'dataSrc': '_embedded.users',
        },
        'columns': [
            {data: null,
                render: function (data) {
                    return '<a href="#" value="' + data._links.self.href + '" class="table-link" aria-expanded="false" data-toggle="modal">' + data.username + '</a>'
                }
            },
            {data: 'email'},
            {data: null,
                render: function (data) {
                    if(data.lastLogin == null) {
                        return "Never";
                    } else {
                        return data.lastLogin;
                    }
                }
            },
            {data: null,
                render: function (data) {
                    var name;
                    $.ajax({
                        async: false,
                        url: data._links.role.href,
                        data: {data},
                        dataType: 'json',
                        success: function(data) {
                            name = data.name;
                        }
                    });
                    return name;
                }
            },
            {data: 'active'},
        ],
        fnDrawCallback: function() {
            $('.table-link').on('click', function() {
                var item = $(this).attr('value');
                var modalBody = "/dashboard/modal/userModal";
                var modal = $('#modal').modal({
                    show: false,
                });
                $("#modalLabel").text("Edit user");
                modal.find('.modal-body').load(modalBody, function() {
                    if(isAdmin()) {
                        getUserRoleOptions();
                    }
                    $.getJSON(item, function( data ) {
                        $('#user-id').val(data._links.self.href);
                        $('#username').val(data.username);
                        $.ajax({
                            async: true,
                            url: data._links.role.href,
                            data: {data},
                            dataType: 'json',
                            success: function(data) {
                                if(isAdmin()) {
                                    $('#role').selectpicker('val', data._links.self.href);
                                } else {
                                    $('#role').append($('<option>', {
                                        value: data._links.self.href,
                                        text: data.name
                                    }));
                                    $('#role').selectpicker({
                                        liveSearch: true,
                                        width: '100%',
                                    });
                                }
                            }
                        });
                        $('#email').val(data.email);
                        if(data.active) {
                            $('#active').prop("checked", true);
                        } else {
                            $('#active').prop("checked", false);
                        }
                        if(!isAdmin()) {
                            $('#userForm :input').prop('disabled', true);
                            $('#modal-close').prop('disabled', false);
                        }
                    });
                    $('#userForm').submit(function( event ) {
                        event.preventDefault();
                        saveUser(this, function(){table.ajax.reload(null, false)});
                    });
                    $('#modal-delete').on('click', function() {
                        var item = $(this).attr('value');
                        deleteUser(item, function(){table.ajax.reload(null, false)});
                    })
                });
                modal.modal('show'); 
            });
        }
    });
    return table;
}

function saveUser(object, callback) {
    $.getScript("https://cdn.jsdelivr.net/npm/bcryptjs@2.4.3/dist/bcrypt.js", function(data) {
        var bcrypt = dcodeIO.bcrypt;
        var data = {};
        var url = '/api/users';
        var type = 'POST';
        var token = $("meta[name='_csrf']").attr("content");
        $.each($(object).serializeArray(), function(i, v) {
            if(v.name == "password") {
                if(v.value != "") {
                    data["password"] = bcrypt.hashSync(v.value, 10);
                }
            } else if (v.name == "active") {
                data[v.name] = true;
            } else {
                data[v.name] = v.value;
            }
        });
        if (data['user-id']) {
            url = data['user-id'];
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
    });
}

function deleteUser(object, callback) {
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

function isAdmin() {
    if($("meta[name='_csrf']").attr("content")) {
        return true;
    }
    return false;
}