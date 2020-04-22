let RespCode = function () {
    let def = function (resp, options) {
        if (Proj.isDev()) {
            console.group(options.url);
            console.dir(resp);
            console.groupEnd();
        }
    };
    let defSuccess = function (resp, options) {
        def(resp, options);
        if (options._success && $.type(options._success) === "function") {
            options._success(resp, options);
        }
    };
    let defError = function (resp, options) {
        def(resp, options);
        Proj.showToasts("danger", "[" + resp.respCode + "]" + resp.respMessage);
        if (options._error && $.type(options._error) === "function") {
            options._error(resp);
        }
    };
    return {
        code0000: defSuccess,
        code9999: defError,
        code9998: defError,
        code9997: defError,
        code1001: defError,
        code2001: function (resp, options) {
            def(resp, options);
            Proj.showToasts("warning", "[" + resp.respCode + "]" + resp.respMessage);
            setTimeout(function () {
                window.location.href = Proj.getContextPath() + "/login?expire";
            }, 3000);
        },
        code2002: defError
    }
}();
let HttpUtil = function () {
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    let accept = {
        text: "text/plain",
        html: "text/html",
        json: "application/json"
    };

    let DEFAULT_HEADERS = {
        Accept: "text/html,text/plain,application/json"
    };
    DEFAULT_HEADERS[header] = token;

    let statusCode = {
        400: function (options, xhr, textStatus, errorThrown) {
            Proj.showToasts("danger", "无效的请求");
        },
        404: function (options, xhr, textStatus, errorThrown) {
            Proj.showToasts("danger", "未找到本次请求的页面或功能");
        }
    };

    function defBeforeSend(options, xhr) {
        if (options._beforeSend && $.type(options._beforeSend) === "function") {
            options._beforeSend(xhr);
        }
    }

    function defError(options, xhr, textStatus, errorThrown) {
        if (statusCode[xhr.status]) {
            statusCode[xhr.status](options, xhr, textStatus, errorThrown);
        } else {
            console.error(xhr.responseText);
            Proj.showToasts("danger", "请求失败[" + xhr.status + "]");
        }
        if (options._error && $.type(options._error) === "function") {
            options._error(xhr, textStatus, errorThrown);
        }
    }

    function defComplete(options, xhr, textStatus) {
        if (options._complete && typeof options._complete === "function") {
            options._complete(xhr, textStatus);
        }
    }

    function defSuccess(options, data, textStatus, xhr) {
        let isJsonObject = options.dataType === "json";
        if (isJsonObject) {
            let codeFunc = RespCode["code" + data.respCode];
            if (codeFunc && $.type(codeFunc) === "function") {
                codeFunc(data, options);
            }
        } else {
            if (options._success && $.type(options._success) === "function") {
                options._success(data);
            }
        }
    }

    return {
        ajaxReq: function (obj) {

            let options = {
                type: obj.type || "get",
                url: Proj.getContextPath() + obj.url,
                data: obj.data,
                cache: obj.cache || false,
                contentType: obj.contentType,
                dataType: obj.dataType || "text",
                async: obj.async || true,
                beforeSend: function (xhr) {
                    defBeforeSend(obj, xhr);
                },
                success: function (data, textStatus, xhr) {
                    defSuccess(obj, data, textStatus, xhr);
                },
                error: function (xhr, textStatus, errorThrown) {
                    defError(obj, xhr, textStatus, errorThrown)
                },
                complete: function (xhr, textStatus) {
                    defComplete(obj, xhr, textStatus);
                }
            };

            let headers = {};
            if (obj.headers) {
                $.extend(headers, obj.headers);
            }
            headers[header] = token;
            headers.Accept = accept[options.dataType];
            options.headers = headers;

            if (options.type === "post" && !options.contentType) {
                options.contentType = "application/x-www-form-urlencoded"
            }

            return $.ajax(options);
        }
    }
}();