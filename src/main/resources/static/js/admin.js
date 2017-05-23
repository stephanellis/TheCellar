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

})