$(document).ready(function(){
    $.getJSON('/api/device/list', function( data ) {
        data.forEach(element => {
            $('#deviceList').append(
                $('<tr>').append(
                    $('<th>').attr('scope', 'row').append(
                        $('<a>')
                            .attr('href', '#')
                            .attr('value', element.id)
                            .attr('class', 'device-link')
                            .attr('aria-expanded', 'false')
                            .attr('data-toggle', 'modal')
                            .append(element.name)),
                    $('<td>').append(element.type.name),
                    $('<td>').append(element.model.name),
                    $('<td>').append(element.ipAddress),
                    $('<td>').append(element.serial),
                    $('<td>').append(element.purchaseDate),
                    $('<td>').append(element.comment)
            ));   
        });
        deviceTable = $('#deviceList').DataTable({
            searching: true,
            stateSave: true,
            dom: 'lrtip',
            scrollY: '100%',
        });
        $('.device-link').on('click', function () {
            var id = $(this).attr('value');
            var modalBody = "/dashboard/modal/device";
            var deviceModal = $('#deviceModal').modal({
                show: false,
            });
            deviceModal.find('.modal-body').load(modalBody, function() {
                $.getJSON('/api/device/model/list', function( data ) {
                    data.forEach(element => {
                        $('#device-model').append($('<option>', {
                            value: element.id,
                            text: element.name
                        }));
                    });
                    $('#device-model').selectpicker({
                        liveSearch: true,
                        width: '100%',
                    });
                });
                $.getJSON('/api/device/type/list', function( data ) {
                    data.forEach(element => {
                        $('#device-type').append($('<option>', {
                            value: element.id,
                            text: element.name
                        }));
                    });
                    $('#device-type').selectpicker({
                        liveSearch: true,
                        width: '100%',
                    });
                });
                $.getJSON('/api/device/get/' + id, function( data ) {
                    $('#device-name').val(data.name);
                    $('#device-type').selectpicker('val', data.type.id);
                    $('#device-model').selectpicker('val', data.model.id);
                    $('#device-ipaddress').val(data.ipAddress);
                    $('#device-macaddress').val(data.macAddress);
                    $('#device-serial-no').val(data.serial);
                    $('#device-purchase-date').val(data.purchaseDate);
                    $('#device-comment').val(data.comment);
                    $('#device-modal-delete').val(data.id);
                });
            });
            deviceModal.modal('show'); 
        });
    });
    $("#search").on("keyup", function() {
        deviceTable.search($(this).val()).draw();
    });
    $('#addDeviceButton').on('click', function () {
        var modalBody = "/dashboard/modal/device";
        var deviceModal = $('#deviceModal').modal({
            show: false,
        });
        deviceModal.find('.modal-body').load(modalBody, function() {
            $.getJSON('/api/device/model/list', function( data ) {
                data.forEach(element => {
                    $('#device-model').append($('<option>', {
                        value: element.id,
                        text: element.name
                    }));
                });
                $('#device-model').selectpicker({
                    liveSearch: true,
                    width: '100%',
                });
            });
            $.getJSON('/api/device/type/list', function( data ) {
                data.forEach(element => {
                    $('#device-type').append($('<option>', {
                        value: element.id,
                        text: element.name
                    }));
                });
                $('#device-type').selectpicker({
                    liveSearch: true,
                    width: '100%',
                });
            });
        });
        deviceModal.modal('show'); 
    });
});