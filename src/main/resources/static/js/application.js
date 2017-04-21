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
        select: {
            style: 'single'
        },
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
            },
            {
                text: "Edit Beer",
                enabled: false,
                className: 'requireSelection',
                action: function (e, dt, node, config){
                    $('#edit-beer-model').modal('show');
                }
            },
            {
                text: "Delete Beer",
                enabled: false,
                className: 'requireSelection',
                action: function (e, dt, node, config){

                }
            }
        ]
    });

    // Opens New Beer Model for first beer
    $('#activate-beer-modal').on('click', function(){
        $('#add-beer-modal').modal('show');
    });

    // clears inputs and error message from modal every time it is shown
    $('#add-beer-modal').on('show.bs.modal', function(){
        $('#add-beer-form-error').text("");
        $('#add-beer-modal :input').val('');
    });

    // adds beer name to successful addition modal
    $('#add-beer-success-modal').on('show.bs.modal', function(){
        $('#successfully-added-beer-name').text($('#beerName').val());
    });

    // Submits Add New Beer Form to API
    $('#submit-add-beer-form').on('click', function(){
        $.ajax({
            url: "/rest/inventory/add/item",
            type: "POST",
            data: {
                brewer: $('#brewer').val(),
                style: $('#style').val(),
                beerName: $('#beerName').val(),
                abv: $('#abv').val(),
                count: $('#count').val()
            },
            success: function(){
                $('#add-beer-modal').modal('hide');
                inventoryTable.ajax.reload();
                $('#add-beer-modal').one('hidden.bs.modal', function(){
                    $('#add-beer-success-modal').modal('show');
                });
            },
            error:  function(){
                $('#add-beer-form-error').text("Something went wrong. Please check your input and try again.");
            }

        })

    });

    // enables buttons upon any selection
    inventoryTable.on('select', function(){
        var selectedRows = inventoryTable.rows({selected: true}).count();
        inventoryTable.button(1).enable(selectedRows>0);
        inventoryTable.button(2).enable(selectedRows>0);
    });

    // disables buttons upon removal of all selection
    inventoryTable.on('deselect', function(){
        var selectedRows = inventoryTable.rows({selected: true}).count();
        inventoryTable.button(1).enable(selectedRows>0);
        inventoryTable.button(2).enable(selectedRows>0);
    });

})
