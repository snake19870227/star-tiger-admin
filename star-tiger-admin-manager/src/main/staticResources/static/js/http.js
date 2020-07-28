
window.StigerHttp = function () {

    const csrfToken = $("meta[name='_csrf']").attr("content");
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    let headers = {};
    headers[csrfHeader] = csrfToken;

    return {
        ajax: function (url, method, data, contentType, dataType, successCallback, errorCallback, completeCallback) {
            if (!url || url === "") {
                console.error("'url'错误");
                return;
            }
            if (!method || method === "") {
                method = "get";
            }
            method = method.toLowerCase();
            if (method !== "get" && method !== "post" && method !== "put" && method !== "delete") {
                console.error("'method'错误");
                return;
            }
            if (!contentType || contentType === "") {
                contentType = "application/x-www-form-urlencoded";
            }
            if (!dataType || dataType === "") {
                dataType = "text";
            }
            let options = {
                type: method,
                url: contextPath + url,
                cache: false,
                data: data,
                contentType: contentType,
                dataType: dataType,
                headers: headers,
                beforeSend: function (xhr) {

                },
                success: function (data, textStatus, xhr) {
                    if (typeof successCallback === "function") {
                        successCallback(data, textStatus, xhr);
                    }
                },
                error: function (xhr, textStatus, errorThrown) {
                    if (typeof errorCallback === "function") {
                        errorCallback(data, textStatus, xhr);
                    }
                },
                complete: function (xhr, textStatus) {
                    if (typeof completeCallback === "function") {
                        completeCallback(xhr, textStatus);
                    }
                }
            };
            $.ajax(options);
        }
    }
}();