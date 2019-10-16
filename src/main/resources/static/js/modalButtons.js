function userButton() {
    $('#user').on('click', function() {
        var user = "/api/users/" + $(this).attr('value');
        var modalBody = "/dashboard/modal/userModal";
        var modal = $('#modal').modal({
            show: false,
        });
        modal.find('.modal-body').load(modalBody, function() {
            $.getJSON(user, function( data ) {
                $('#user-id').val(data._links.self.href);
                $('#username').val(data.username);
                $("#modalLabel").text(data.username);
                $.ajax({
                    async: true,
                    url: data._links.role.href,
                    data: {data},
                    dataType: 'json',
                    success: function(data) {
                        $('#role').append($('<option>', {
                            value: data._links.self.href,
                            text: data.name
                        }));
                        $('#role').selectpicker({
                            liveSearch: true,
                            width: '100%',
                        });
                    }
                });
                $('#email').val(data.email);
                $('#active').remove();
                $('#modal-delete').remove();
                //$('#username').prop('disabled', true);
                $('#role').prop('disabled', true);
            });
            $('#userForm').submit(function( event ) {
                event.preventDefault();
                saveUser(this, function(){table.ajax.reload(null, false)});
            });
        });
        modal.modal('show'); 
    });
}

function addDeviceButton() {
    $('#addButton').on('click', function () {
        var modalBody = "/dashboard/modal/deviceModal";
        var modal = $('#modal').modal({
            show: false,
        });
        $("#modalLabel").text("New device");
        modal.find('.modal-body').load(modalBody, function() {
            getDeviceModelOptions();
            getDeviceTypeOptions();
            $('#modal-delete').remove();
            $('#deviceForm').submit(function( event ) {
                event.preventDefault();
                saveItem(this, function(){table.ajax.reload(null, false)});
            });
        });
        modal.modal('show'); 
    });
}

function addSingleValueButton() {
    $('#addButton').on('click', function () {
        var modalBody = "/dashboard/modal/singleValueModal";
        var modal = $('#modal').modal({
            show: false,
        });
        if($('#addButton').val() == "type") {
            $("#modalLabel").text("New device type");
            console.log($('#addButton').val());
        } else {
            $("#modalLabel").text("New device model");
            console.log($('#addButton').val());
        }
        modal.find('.modal-body').load(modalBody, function() {
            $('#modal-delete').remove();
            $('#singleValueForm').submit(function( event ) {
                event.preventDefault();
                saveItem(this, function(){table.ajax.reload(null, false)});
            });
        });
        modal.modal('show'); 
    });
}

function addUserButton() {
    $('#addButton').on('click', function () {
        var modalBody = "/dashboard/modal/userModal";
        var modal = $('#modal').modal({
            show: false,
        });
        $("#modalLabel").text("New user");
        modal.find('.modal-body').load(modalBody, function() {
            $('#modal-delete').remove();
            getUserRoleOptions();
            if (!$('#user-id').val()) {
                $('#password').prop('required', true);
            }
            $('#userForm').submit(function( event ) {
                event.preventDefault();
                saveUser(this, function(){table.ajax.reload(null, false)});
            });
        });
        modal.modal('show'); 
    });
}