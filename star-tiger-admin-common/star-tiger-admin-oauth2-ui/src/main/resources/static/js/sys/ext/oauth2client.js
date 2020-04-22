
var Oauth2Client = function () {

    let $recordsContainer = $("#records-container");

    function searchClient(page) {
        let url = "/sys/ext/oauth2client/list";
        if (page) {
            url += ("?page=" + page);
        }
        let options = {
            url: url,
            dataType: "html",
            _success: function (data) {
                $recordsContainer.html(data);
            }
        };
        HttpUtil.ajaxReq(options);
    }

    return {
        init: function () {
            searchClient(1);
        },
        searchClient: searchClient
    }
}();
$(function () {
    Oauth2Client.init();
});