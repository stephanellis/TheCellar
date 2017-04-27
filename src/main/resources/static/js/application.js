$(function(){

    var firstBeer = false;
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
            {data: "beer.brewer",
                 render: function(data, type, full, meta){
                    return data + '<span class="hidden-pk">'+ full.user_beer_link_id + '</span>'
                 }
            },
            {data: "beer.name"},
            {data: "beer.style"},
            {data: "beer.year"},
            {data: "beer.abv",
                render: function(data, type, full, meta){
                    return data + '%'
                 }
            },
            {data: "count"}
        ],
        buttons:[
            {
                text: "Add Beer",
                action: function (e, dt, node, config){
                    $('#add-beer-modal').modal('show');
                    $('#abv').slider('setValue',0);
                }
            },
            {
                text: "Edit Beer",
                enabled: false,
                className: 'requireSelection',
                action: function (e, dt, node, config){
                    var selectedRow;
                    var selectedBeerName;
                    $.each($("#inventory-table tr.selected"), function(){
                        selectedRow = ($(this).find('td').eq(0).find('.hidden-pk').text());
                        selectedBeerName = ($(this).find('td').eq(1).text());
                    });
                    $.ajax({
                        url: "/rest/inventory/find/item/" + selectedRow,
                        type: "GET",
                        success: function(data){
                            $('#edit-beer-modal').modal('show');
                            $('#editing-pk').val(selectedRow);
                            $('#brewer-edit').val(data.beer.brewer);
                            $('#beerName-edit').val(data.beer.name);
                            $('#style-edit').val(data.beer.style);
                            $('#count-edit').val(data.count);
                            $('#abv-edit').slider('setValue',data.beer.abv);
                        }
                    });
                }
            },
            {
                text: "Delete Beer",
                enabled: false,
                className: 'requireSelection',
                action: function (e, dt, node, config){
                    var selectedRow;
                    var selectedBeerName;
                    $.each($("#inventory-table tr.selected"), function(){
                        selectedRow = ($(this).find('td').eq(0).find('.hidden-pk').text());
                        selectedBeerName = ($(this).find('td').eq(1).text());
                    });
                    $.ajax({
                        url: "/rest/inventory/delete/item",
                        type: "POST",
                        data: {
                            user_beer_link_id: selectedRow
                        },
                        success: function(){
                            inventoryTable.ajax.reload();
                            $('#delete-beer-success-modal').modal('show');
                            $('#successfully-deleted-beer-name').text(selectedBeerName);
                            $('#delete-beer-success-modal').one('hidden.bs.modal', function(){
                                // check to see if table is empty
                                if($("#inventory-table tbody tr td:first-child")[0].innerHTML == 'No data available in table'){
                                    location.reload();
                                }
                            });


                        },
                        error:  function(){
                            alert('Something went wrong');
                        }

                    })
                }
            }
        ]
    });

    inventoryTable.on( 'draw', function () {
        inventoryTable.button(1).enable(false);
        inventoryTable.button(2).enable(false);
        $.each($("#inventory-table tr.selected"), function(){
            selectedRow = ($(this).removeClass('selected'));
        });
    });

    // Opens New Beer Model for first beer
    $('#activate-beer-modal').on('click', function(){
        firstBeer= true;
        $('#add-beer-modal').modal('show');
    });

    // clears inputs and error message from modal every time it is shown
    $('#add-beer-modal').on('show.bs.modal', function(){
        $('#add-beer-form-error').text("");
        $('#add-beer-modal :input').val('');
    });

    $('#add-beer-modal').on('shown.bs.modal', function(){
        $('#brewer').focus();
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
                if (firstBeer){
                    firstBeer=false;
                    location.reload();
                }
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

    // Submits Add New Beer Form to API
    $('#submit-edit-beer-form').on('click', function(){
        $.ajax({
            url: "/rest/inventory/edit/item/" +  $('#editing-pk').val(),
            type: "POST",
            data: {
                brewer: $('#brewer-edit').val(),
                style: $('#style-edit').val(),
                beerName: $('#beerName-edit').val(),
                abv: $('#abv-edit').val(),
                count: $('#count-edit').val()
            },
            success: function(){
                $('#edit-beer-modal').modal('hide');
                inventoryTable.ajax.reload();
                $('#edit-beer-modal').one('hidden.bs.modal', function(){
                    $('#edit-beer-success-modal').modal('show');
                });
            },
            error:  function(){
                $('#edit-beer-form-error').text("Something went wrong. Please check your input and try again.");
            }
        })
    });

    // adds beer name to successful edit modal
    $('#edit-beer-success-modal').on('show.bs.modal', function(){
        $('#successfully-edited-beer-name').text($('#beerName-edit').val());
    });


    // clears error message from modal every time it is shown
    $('#edit-beer-modal').on('show.bs.modal', function(){
        $('#edit-beer-form-error').text("");
    });

    $('#edit-beer-modal').on('shown.bs.modal', function(){
        $('#brewer-edit').focus();
    });

})
