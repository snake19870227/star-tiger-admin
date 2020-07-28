

function readResourceInfo(resFlow, successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/resource/" + resFlow,
        "get", {}, "", "json",
        successCallback, errorCallback, completeCallback
    );
}

function addResource(resource,
                        successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/resource",
        "post", JSON.stringify(resource), "application/json", "json",
        successCallback, errorCallback, completeCallback
    );
}

function updateResource(resource,
                        successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/resource/" + resource.resFlow,
        "put", JSON.stringify(resource), "application/json", "json",
        successCallback, errorCallback, completeCallback
    );
}

function changeResourceEnableStatus(resFlow, enableFlag,
                                    successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/resource/enable/" + resFlow + "/" + enableFlag,
        "put", {}, "", "json",
        successCallback, errorCallback, completeCallback
    );
}