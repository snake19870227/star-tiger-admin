layui.use(["table","form","layer"], function () {
    let table = layui.table,
        form = layui.form,
        layer = layui.layer
    ;

    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    let resourceListTable = table.render({
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
            }
        ]],
        page: true,
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
        }
    });

    form.on('switch(enable-flag)', function(obj){
        let checked = obj.elem.checked;
        let value = obj.elem.value;
        let newFlag = (checked ? "Y" : "N");
        let headers = {};
        headers[csrfHeader] = csrfToken;
        let ajaxOption = {
            type: "put",
            url: contextPath + "/sys/resource/enable/" + value + "/" + newFlag,
            cache: false,
            dataType: "json",
            headers: headers,
            beforeSend: function (xhr) {

            },
            success: function (data, textStatus, xhr) {
                let code = data.code;
                let msg = data.msg;
                if (code !== "10000") {
                    layer.msg(msg);
                    obj.elem.checked = (!checked);
                    form.render('checkbox');
                }
            },
            error: function (xhr, textStatus, errorThrown) {
                layer.msg("操作失败");
                obj.elem.checked = (!checked);
                form.render('checkbox');
            },
            complete: function (xhr, textStatus) {

            }
        };
        $.ajax(ajaxOption);
    });
});