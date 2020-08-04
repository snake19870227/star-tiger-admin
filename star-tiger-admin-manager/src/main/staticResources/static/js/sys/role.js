layui.use(["table", "form", "layer", "laypage", "util", "transfer"], function () {
    let table = layui.table,
        form = layui.form,
        layer = layui.layer,
        laypage = layui.laypage,
        util = layui.util,
        transfer = layui.transfer
    ;

    let roleDataTableOptions = {
        id: "roleDataTable",
        elem: "#role-data-table",
        toolbar: "#top-tool-bar",
        url: contextPath + "/sys/role/data",
        loading: true,
        page: {
            limit: 10,
            layout: ['prev', 'page', 'next', 'limit', 'count', 'refresh']
        },
        cols: [[
            {
                field: "roleCode",
                title: "角色代码"
            },
            {
                field: "roleName",
                title: "角色名"
            },
            {
                title: "操作",
                toolbar: "#record-tool-bar"
            }
        ]],
        parseData: function (res) {
            let parseData = {};
            if ("10000" === res.code) {
                parseData.code = "0";
            }
            parseData.msg = res.msg;
            parseData.count = res.data.total;
            parseData.data = res.data.records;
            return parseData;
        }
    }

    let resourceTransferOptions = {
        id: "resource-transfer",
        elem: "#resource-transfer",
        title: ['所有资源','已选资源'],
        showSearch: true
    }

    let roleDataTable = undefined;

    let resourceTransfer = undefined;

    $(function () {
        loadRoleDataTable();
        util.event("lay-event", {
            search: function(){
                loadRoleDataTable();
            }
        });
    });

    let loadRoleDataTable = function () {
        let mainInnerHeight = $(".layuimini-main").innerHeight();
        let searchOuterHeight = $(".table-search-fieldset").outerHeight();
        let options = {};
        $.extend(options, roleDataTableOptions);
        options.height = "full-" + (searchOuterHeight + 60);
        options.where = form.val("role-data-table");
        if (roleDataTable) {
            roleDataTable.reload(options);
        } else {
            roleDataTable = table.render(options);
        }
    };

    table.on("toolbar(role-data-table)", function (obj) {
        let layEvent = obj.event;
        let $body = $("body");
        let width = $body.innerWidth();
        width = (width / 5) * 3;
        if (layEvent === "add") {
            let resAddWin = layer.open({
                type: 1,
                id: "roleAddWin",
                title: "新增角色",
                content: $("#info-win"),
                btn: ["保存"],
                area: width + "px",
                offset: "100px",
                yes: function (index, layero) {
                    getAllResourceTransferData(
                        function (data, textStatus, xhr) {
                            let options = {};
                            $.extend(options, resourceTransferOptions);
                            options.data = data;
                            if (resourceTransfer) {
                                transfer.reload(resourceTransferOptions.id, options);
                            } else {
                                resourceTransfer = transfer.render(options);
                            }
                        }
                    );
                }
            });
        }
    });

});