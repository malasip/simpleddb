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
    $('#deviceDetailModal').on('show.bs.modal', function (event) {
        var modal = $(this)
        var button = $(event.relatedTarget)
        var id = button.data('device_id')
        modal.find('.modal-body').load("/device?id=" + id);
    });
});