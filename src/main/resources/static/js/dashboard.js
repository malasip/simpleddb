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
        modal.find('.modal-body').load("/device/add", function() {
            $('#device-model').selectpicker({
                liveSearch: true,
                width: '100%',
            });
            $('#device-type').selectpicker({
                liveSearch: true,
                width: '100%',
            }); 
        });
    });
    $('.device-link').on('click', function (event) {
        var id = $(this).attr('href');
        var device = "/device/get?id=" + id
        var deviceModal = $('#deviceDetailModal').modal({
            show: false,
        });
        deviceModal.find('.modal-body').load(device, function() {
            $('#device-model').selectpicker({
                liveSearch: true,
                width: '100%',
            });
            $('#device-type').selectpicker({
                liveSearch: true,
                width: '100%',
            });
        });
        deviceModal.modal('show'); 
    });
});