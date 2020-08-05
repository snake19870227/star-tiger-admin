function getAllResourceTransferData(successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/resource/transferData",
        "get", {}, "", "json",
        successCallback, errorCallback, completeCallback
    );
}

function getRoleResourceTransferData(roleFlow, successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/resource/transferData?roleFlow=" + roleFlow,
        "get", {}, "", "json",
        successCallback, errorCallback, completeCallback
    );
}

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

function addRole(role, resourceFlows,
                 successCallback, errorCallback, completeCallback) {
    let saveDto = {
        role: role,
        resourceFlows: resourceFlows
    }
    StigerHttp.ajax(
        "/sys/role",
        "post", JSON.stringify(saveDto), "application/json", "json",
        successCallback, errorCallback, completeCallback
    );
}

function readRole(roleFlow, successCallback, errorCallback, completeCallback) {
    StigerHttp.ajax(
        "/sys/role/" + roleFlow,
        "get", {}, "", "json",
        successCallback, errorCallback, completeCallback
    );
}