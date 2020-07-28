layui.use(["table", "form", "layer"], function () {
    let table = layui.table,
        form = layui.form,
        layer = layui.layer
    ;

    let resourceDataTable = table.render({
        id: "resourceDataTable",
        elem: "#resource-list-table",
        height: "full-30",
        cols: [[
            {
                field: "resName",
                title: "资源名"
            },
            {
                field: "resPath",
                title: "资源地址"
            },
            {
                field: "resMethod",
                title: "访问方式"
            },
            {
                field: "enableFlag",
                title: "启用标记",
                templet: "#enable-flag-switch"
            },
            {
                title: "操作",
                toolbar: "#record-tool-bar"
            }
        ]],
        where: {

        },
        page: {
            limit: 20
        },
        url: contextPath + "/sys/resource/data",
        parseData: function (res) {
            let parseData = {};
            if ("10000" === res.code) {
                parseData.code = "0";
            }
            parseData.msg = res.msg;
            parseData.count = res.data.total;
            parseData.data = res.data.records;
            return parseData;
        },
        toolbar: "#top-tool-bar"
    });

    table.on("toolbar(resource-list-table)", function (obj) {
        let layEvent = obj.event;
        let $body = $("body");
        let width = $body.innerWidth();
        width = (width / 5) * 3;
        if (layEvent === "add") {
            let resAddWin = layer.open({
                type: 1,
                id: "resAddWin",
                title: "新增资源",
                content: $("#add-win"),
                btn: ["保存"],
                area: width + "px",
                offset: "100px",
                yes: function (index, layero) {
                    let resource = form.val("resource-add-form");
                    addResource(resource,
                        function (data, textStatus, xhr) {
                            let code = data.code;
                            let msg = data.msg;
                            if (code !== "10000") {
                                layer.msg(msg);
                                return;
                            }
                            resourceDataTable.reload({});
                            layer.close(index);
                        }
                    );
                }
            });
        }
    });

    form.on('switch(enable-flag)', function (obj) {
        let checked = obj.elem.checked;
        let value = obj.elem.value;
        let newFlag = (checked ? "Y" : "N");
        let stopChange = function () {
            obj.elem.checked = (!checked);
            form.render('checkbox');
        };
        layer.confirm("是否确认修改？", {},
            function (index) {
                changeResourceEnableStatus(value, newFlag,
                    function (data, textStatus, xhr) {
                        let code = data.code;
                        let msg = data.msg;
                        if (code !== "10000") {
                            layer.msg(msg);
                            obj.elem.checked = (!checked);
                            form.render('checkbox');
                        }
                    },
                    function (xhr, textStatus, errorThrown) {
                        layer.msg("操作失败");
                        stopChange();
                    },
                    function (xhr, textStatus) {
                        layer.close(index);
                    }
                );
            },
            function () {
                stopChange();
            }
        );
    });

    table.on("tool(resource-list-table)", function (obj) {
        let data = obj.data;
        let layEvent = obj.event;
        let tr = obj.tr;
        let $body = $("body");
        let width = $body.innerWidth();
        width = (width / 5) * 3;
        // let height = $body.innerHeight();
        // height = (height / 3);
        if (layEvent === "edit") {
            let resInfoWin = layer.open({
                type: 1,
                id: data.resFlow,
                title: "编译资源",
                content: $("#edit-win"),
                // area: [width + "px", height + "px"]
                btn: ["保存"],
                area: width + "px",
                offset: "100px",
                success: function (layero, index) {
                    readResourceInfo(data.resFlow,
                        function (data, textStatus, xhr) {
                            let code = data.code;
                            let msg = data.msg;
                            let resInfo = data.data;
                            if (code !== "10000") {
                                layer.msg(msg);
                            } else {
                                form.val("resource-edit-form", resInfo);
                            }
                        }
                    );
                },
                yes: function (index, layero) {
                    let resource = form.val("resource-edit-form");
                    updateResource(resource,
                        function (data, textStatus, xhr) {
                            let code = data.code;
                            let msg = data.msg;
                            if (code !== "10000") {
                                layer.msg(msg);
                                return;
                            }
                            resourceDataTable.reload({});
                            layer.close(index);
                        }
                    );
                }
            });
        }
    });
});
