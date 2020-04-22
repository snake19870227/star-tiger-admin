let RoleDetailModal = function () {
    let $modal = $("#role-detail-modal");
    let $saveBtn = $modal.find(".save-detail-btn");
    let $form = $modal.find("#role-detail-form");
    let $roleFlow = $form.find("[name='roleFlow']");
    let $roleCode = $form.find("[name='roleCode']");
    let $roleName = $form.find("[name='roleName']");
    let $resFlows = $form.find("[name='resFlows']");

    let resFlowsBox = DualListbox.create($resFlows);

    let validator = createFormValidator();

    function isCreate() {
        return !$roleFlow.val() || $roleFlow.val() === "";
    }

    function clearForm() {
        $form.clearForm();
        $roleFlow.val("");
        $roleCode.prop("disabled", false);
        resFlowsBox.clear();
        validator.resetForm();
    }

    function showModal() {
        $modal.modal("show");
    }

    function hideModal() {
        $modal.modal("hide");
    }

    function loadAllResources() {
        let options = {
            type: "get",
            url: "/sys/resource/all",
            dataType: "json",
            _success: function (resp, _options) {
                let options = [];
                $.each(resp.data, function (index, resource) {
                    let method = "all";
                    if (resource.resMethod) {
                        method = resource.resMethod;
                    }
                    options.push({
                        value: resource.resFlow,
                        text: resource.resName + " [(" + method + ")" + resource.resPath + "]",
                        selected: false
                    });
                });
                resFlowsBox.setOptions(options);
            }
        };
        return HttpUtil.ajaxReq(options);
    }

    function loadRoleResources(roleFlow) {
        let options = {
            type: "get",
            url: "/sys/role/" + roleFlow + "/info",
            dataType: "json",
            _success: function (resp, _options) {
                $roleFlow.val(resp.data.role.roleFlow);
                $roleCode.val(resp.data.role.roleCode);
                $roleCode.prop("disabled", true);
                $roleName.val(resp.data.role.roleName);
                $.each(resp.data.resources, function (index, resource) {
                    resFlowsBox.select(resource.resFlow);
                });
            }
        };
        return HttpUtil.ajaxReq(options);
    }

    function show(roleFlow) {
        loadAllResources().done(function () {
            if (roleFlow && roleFlow !== "") {
                loadRoleResources(roleFlow).done(showModal);
            } else {
                showModal();
            }
        });
    }

    function createFormValidator() {
        return $form.validate({
            rules: {
                roleCode: {
                    required: true,
                    maxlength: 20,
                    remote: Proj.getContextPath() + "/sys/role/checkRoleCode"
                },
                roleName: {
                    required: true,
                    maxlength: 20,
                }
            },
            messages: {
                roleCode: {
                    remote: "角色代码已存在"
                }
            },
            errorElement: 'span',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
            }
        });
    }

    return {
        init: function () {
            $modal.on("hide.bs.modal", function () {
                clearForm();
            });
            $saveBtn.on("click", function () {
                if ($form.valid()) {
                    let dataStr = $form.formSerialize();
                    if (Proj.isDev()) {
                        console.log(dataStr);
                    }
                    let methodType = isCreate() ? "post" : "put";
                    if ($roleCode.prop("disabled")) {
                        dataStr = $roleCode.attr("name") + "=" + $roleCode.val() + "&" + dataStr;
                    }
                    let options = {
                        type: methodType,
                        url: "/sys/role",
                        data: dataStr,
                        dataType: "json",
                        _success: function (data) {
                            if (Proj.isDev()) {
                                console.log(data);
                            }
                            SysRole.searchRoles(1);
                            hideModal();
                            Proj.showToasts("success", "保存成功");
                        }
                    };
                    HttpUtil.ajaxReq(options);
                }
            });
        },
        show: show
    }
}();
var SysRole = function () {

    var $roleSearchForm = $("#role-search-form");
    var $roleContainer = $("#roles-container");

    function bindRecordEvents() {
        $roleContainer.find(".modify-resource-btn").on("click", function () {
            var $this = $(this);
            var roleFlow = $this.parent("td").data("roleFlow");
            RoleDetailModal.show(roleFlow);
        });
        $roleContainer.find(".delete-resource-btn").on("click", function () {
            let $this = $(this);
            let roleFlow = $this.parent("td").data("roleFlow");
            let roleCode = $this.parent("td").data("roleCode");
            let roleName = $this.parent("td").data("roleName");
            ConfirmModal.create({
                showRecordInfo: "删除&nbsp;" + roleCode + "-" + roleName,
                onConfirm: function () {
                    var options = {
                        type: "delete",
                        url: "/sys/role/" + roleFlow,
                        dataType: "json",
                        _success: function (data) {
                            console.log(data);
                            searchRoles(1);
                            Proj.showToasts("success", "删除成功");
                        }
                    };
                    HttpUtil.ajaxReq(options);
                }
            });
        });
    }

    function searchRoles(page) {
        var url = "/sys/role/list";
        if (page) {
            url += ("?page=" + page);
        }
        var options = {
            url: url,
            dataType: "html",
            _success: function (data) {
                $roleContainer.html(data);
                bindRecordEvents();
            }
        };
        var queryString = $roleSearchForm.formSerialize();
        if (queryString) {
            options.data = queryString;
        }
        HttpUtil.ajaxReq(options);
    }

    var bindEvent = function () {
        /* ========================< search >======================== */
        $("#search-button").on("click", function () {
            searchRoles(1);
        });
        /* ========================< create >======================== */
        $("#create-button").on("click", function () {
            RoleDetailModal.show();
        });
    };

    return {
        init: function () {
            bindEvent();
            searchRoles(1);
        },
        searchRoles: searchRoles
    }
}();
$(function () {
    SysRole.init();
    RoleDetailModal.init();
});