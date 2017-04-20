$(function(){
    // Menu JS
    $('#menuToggle, .menu-close').on('click', function(){
        $('#menuToggle').toggleClass('active');
        $('body').toggleClass('body-push-toleft');
        $('#theMenu').toggleClass('menu-open');
    });

    // Closes Menu if the mouse leave the menu for better user experience
    $('#theMenu').on('mouseleave', function(){
        if($('#menuToggle').hasClass('active')){
            $('#menuToggle').toggleClass('active');
            $('body').toggleClass('body-push-toleft');
            $('#theMenu').toggleClass('menu-open');
        }
    });

    // Initializes Inventory Table
    var inventoryTable = $('#inventory-table').DataTable({
        ajax: "/rest/inventory",
        dom: 'Bfrtip',
        type: "get",
        columns: [
            {data: "beer.brewer"},
            {data: "beer.name"},
            {data: "beer.style"},
            {data: "beer.year"},
            {data: "beer.abv"},
            {data: "count"}
        ],
        buttons:[
            {
                text: "Add Beer",
                action: function (e, dt, node, config){
                    $('#add-beer-modal').modal('show');
                }
            }
        ]
    });

})
