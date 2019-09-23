$(document).ready(function(){
    deviceTable = $('#deviceList').DataTable({
        searching: true,
        stateSave: true,
        dom: 'lrtip',
        scrollY: '100%'
    });
    $("#search").on("keyup", function() {
        deviceTable.search($(this).val()).draw();
    });
    $('#addDeviceModal').on('show.bs.modal', function (event) {
        var modal = $(this)
        modal.find('.modal-body').load("/dashboard/addDeviceForm");
    });
    $('.device-link').on('click', function (event) {
        var id = $(this).attr('href');
        var device = "/device?id=" + id
        var modal = $('#deviceDetailModal').modal({
            show: false,
        });
        modal.find('.modal-body').load(device, function() {
            $('#device-model').selectpicker({
                liveSearch: true,
                width: '100%',
            });
        });
        modal.modal('show'); 
    });
});