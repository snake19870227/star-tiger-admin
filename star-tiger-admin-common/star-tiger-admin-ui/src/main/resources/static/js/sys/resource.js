let resourceDetailModal = function () {
    let $modal = $("#resource-detail-modal");
    let $form = $modal.find("#resource-detail-form");
    let $saveBtn = $form.find(".save-resource-detail-modal");
    let $saveAndContinueBtn = $form.find(".save-and-continue");
    let $resFlowInput = $form.find("input[name='resFlow']");
    let $resNameInput = $form.find("input[name='resName']");
    let $resPathInput = $form.find("input[name='resPath']");
    let $resMethodInput = $form.find("select[name='resMethod']");

    let validator = createFormValidator();

    function clearForm() {
        $form.clearForm();
        $resMethodInput.trigger("change");
        $resFlowInput.val("");
        validator.resetForm();
    }

    function isCreate() {
        return !$resFlowInput.val() || $resFlowInput.val() === "";
    }

    function showModal(sysResource) {
        if (sysResource) {
            $resFlowInput.val(sysResource.resFlow);
            $resNameInput.val(sysResource.resName);
            $resPathInput.val(sysResource.resPath);
            if (sysResource.resMethod) {
                $resMethodInput.find("option[value='" + sysResource.resMethod + "']").prop("selected", true);
                $resMethodInput.trigger("change");
            }
            $saveAndContinueBtn.hide();
        } else {
            $saveAndContinueBtn.show();
        }
        $modal.modal("show");
    }

    function hideModal() {
        $modal.modal("hide");
    }

    function createFormValidator() {
        return $form.validate({
            rules: {
                resName: {
                    required: true,
                    maxlength: 20
                },
                resPath: {
                    required: true,
                    maxlength: 400
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

    function saveResource(afterSaveSuccess) {
        let methodType = isCreate() ? "post" : "put";
        let options = {
            type: methodType,
            url: "/sys/resource",
            data: $form.formSerialize(),
            dataType: "json",
            _success: function (data) {
                if (Proj.isDev()) {
                    console.log(data);
                }
                if (typeof afterSaveSuccess === "function") {
                    afterSaveSuccess(data);
                }
                Proj.showToasts("success", "保存成功");
            }
        };
        HttpUtil.ajaxReq(options);
    }

    return {
        init: function () {
            $resMethodInput.select2({
                language: "zh-CN",
                theme: "bootstrap4",
                // allowClear: true,
                minimumResultsForSearch: Infinity
            });
            $modal.on("hide.bs.modal", function () {
                clearForm();
            });
            $saveBtn.on("click", function () {
                if ($form.valid()) {
                    saveResource(function (data) {
                        SysRes.searchResources(1);
                        hideModal();
                    });
                }
            });
            $saveAndContinueBtn.on("click", function () {
                if ($form.valid()) {
                    saveResource(function (data) {
                        SysRes.searchResources(1);
                        $resFlowInput.val("");
                        $resNameInput.val("");
                    });
                }
            })
        },
        show: showModal
    }
}();

let SysRes = function () {

    let $resourceSearchForm = $("#resource-search-form");
    let $resourcesContainer = $("#resources-container");

    let bindRecordEvents = function () {
        $resourcesContainer.find(".modify-resource-btn").on("click", function () {
            let $this = $(this);
            let resFlow = $this.parent("td").data("resFlow");
            let options = {
                type: "get",
                url: "/sys/resource/" + resFlow,
                dataType: "json",
                _success: function (resp) {
                    resourceDetailModal.show(resp.data);
                }
            };
            HttpUtil.ajaxReq(options);
        });
        $resourcesContainer.find(".delete-resource-btn").on("click", function () {
            let $this = $(this);
            let resFlow = $this.parent("td").data("resFlow");
            let resName = $this.parent("td").data("resName");
            let resPath = $this.parent("td").data("resPath");
            ConfirmModal.create({
                showRecordInfo: "删除&nbsp;" + resName + "&nbsp;(" + resPath + ")",
                onConfirm: function () {
                    let options = {
                        type: "delete",
                        url: "/sys/resource/" + resFlow,
                        dataType: "json",
                        _success: function (data) {
                            console.log(data);
                            searchResources(1);
                            Proj.showToasts("success", "删除成功");
                        }
                    };
                    HttpUtil.ajaxReq(options);
                }
            });
        });
    };

    let searchResources = function (page) {
        let url = "/sys/resource/list";
        if (page) {
            url += ("?page=" + page);
        }
        let options = {
            url: url,
            dataType: "html",
            _success: function (data) {
                $resourcesContainer.html(data);
                bindRecordEvents();
            }
        };
        let queryString = $resourceSearchForm.formSerialize();
        if (queryString) {
            options.data = queryString;
        }
        HttpUtil.ajaxReq(options);
    };

    let bindEvent = function () {
        /* ========================< search >======================== */
        $("#search-button").on("click", function () {
            searchResources(1);
        });
        /* ========================< create >======================== */
        $("#create-button").on("click", function () {
            resourceDetailModal.show();
        });
    };

    return {
        init: function () {
            bindEvent();
            searchResources(1);
        },
        searchResources: searchResources
    }
}();
$(function () {
    resourceDetailModal.init();
    SysRes.init();
    window.SysRes = {
        searchResources: SysRes.searchResources
    };
});