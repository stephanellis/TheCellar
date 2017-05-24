$(function(){

    // Initializes User Table
    var adminUserTable = $('#admin-users-table').DataTable({
        ajax: "/rest/admin/users",
        dom: 'Bfrtip',
        type: "get",
        select: {
            style: 'single'
        },
        columns: [
            {data: "fullName",
                 render: function(data, type, full, meta){
                    return toTitleCase(data) + '<span class="hidden-pk">'+ full.userId + '</span>'
                 }
            },
            {data: "username"},
            {data: "formattedPhoneNumber"},
            {data: "userRolesString"},
            {data: "user.enabled",
                render: function(data, type, full, meta){
                    if (data == 1){
                        return 'Yes';
                    } else {
                        return 'No';
                    }
                }
            }
        ],
        buttons:[
            {
                text: "Contact",
                action: function (e, dt, node, config){
                    alert('Add Messaging Functionality');
                }
            },
            {
                text: "Promote",
                action: function (e, dt, node, config){
                    alert('Add Role Promotion Functionality');
                }
            }
        ]
    });


    // Initializes Inventory Table
    var adminBeerTable = $('#admin-beers-table').DataTable({
        ajax: "/rest/admin/beers",
        dom: 'Bfrtip',
        type: "get",
        select: {
            style: 'single'
        },
        columns: [
            {data: "beer.brewer",
                 render: function(data, type, full, meta){
                    return toTitleCase(data) + '<span class="hidden-pk">'+ full.user_beer_link_id + '</span>'
                 }
            },
            {data: "beer.name",
                render: function(data, type, full, meta){
                    return toTitleCase(data)
                }
            },
            {data: "beer.style",
                render: function(data, type, full, meta){
                   return toTitleCase(data)
                }
            },
            {data: "beer.year"},
            {data: "beer.abv",
                render: function(data, type, full, meta){
                    return data + '%'
                }
            },
            {data: "count"},
            {data: "beer.verified",
                 render: function(data, type, full, meta){
                     if (data == true){
                         return 'Yes';
                     } else {
                         return 'No';
                     }
                 }
            }
        ],
        buttons:[
            {
                text: "Edit",
                action: function (e, dt, node, config){
                    alert('Add ADMIN editing capability');
                }
            },
            {
                text: "Verify",
                action: function (e, dt, node, config){
                    alert('Add verification with API call');
                }
            }
        ]
    });

    // deselects everything upon inventory table draw and disables buttons
    adminBeerTable.on('draw', function () {
        deselectAllFromAdminBeerTable(adminBeerTable)
    });

    // enables and disables buttons upon any selection for inventory table
    adminBeerTable.on('select deselect', function(){
        resetAdminBeerTable(adminBeerTable);
    });

    // deselects everything upon user table draw and disables buttons
    adminUserTable.on( 'draw', function () {
        deselectAllFromAdminUserTable(adminUserTable);
    });

    // enables and disables buttons upon any selection for inventory table for user table
    adminUserTable.on('select deselect', function(){
        resetAdminUserTable(adminUserTable);
    });

    $('.accordion-item').on('hidden.bs.collapse', function(e){
        deselectAllFromAdminUserTable(adminUserTable);
        deselectAllFromAdminBeerTable(adminBeerTable);
    });

})

// Useful Functions

    function resetAdminUserTable(adminUserTable) {
        var selectedRows = adminUserTable.rows({selected: true}).count();
        adminUserTable.button(0).enable(selectedRows>0);
        adminUserTable.button(1).enable(selectedRows>0);
    }

    function deselectAllFromAdminUserTable(adminUserTable) {
        adminUserTable.rows().deselect();
    }

    function resetAdminBeerTable(adminBeerTable) {
        var selectedRows = adminBeerTable.rows({selected: true}).count();
        adminBeerTable.button(0).enable(selectedRows>0);
        adminBeerTable.button(1).enable(selectedRows>0);
    }

    function deselectAllFromAdminBeerTable(adminBeerTable) {
        adminBeerTable.rows().deselect();
    }

// End Useful Functions
