layui.use(["table"], function () {
    let table = layui.table;

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
});