let ConfirmModal = function () {

    const DEFAULT_OPTIONS = {
        title: "请确认",
        bodyHtml: null,
        showRecordInfo: null,
        autoremove: true,
        closeOnSuccess: true,
        onConfirm: undefined,
        onClose: undefined
    };

    const DEFAULT_BODY_HTML = "<div class='modal-body'>是否继续操作？</div>";

    return {
        create: function (config) {
            let options = $.extend({}, DEFAULT_OPTIONS, config);

            let $modal = $("<div class='modal fade'/>");
            let $modalDialog = $("<div class='modal-dialog' role='document'/>");
            let $modalContent = $("<div class='modal-content'/>");
            let $modalHeader = $(
                "<div class='modal-header'>" +
                "           <h5 class='modal-title'>" + options.title + "</h5>" +
                "           <button type='button' class='close' data-dismiss='modal' aria-label='Close'>" +
                "               <span aria-hidden='true'>&times;</span>" +
                "           </button>" +
                "       </div>"
            );
            $modal.append($modalDialog);
            $modalDialog.append($modalContent);
            $modalContent.append($modalHeader);

            let $body;
            if (options.bodyHtml != null) {
                $body = $(options.bodyHtml);
            } else if (options.showRecordInfo != null) {
                $body = $(DEFAULT_BODY_HTML);
                $body.append("[&nbsp;<strong class='h4 text-danger'>" + options.showRecordInfo + "</strong>&nbsp;]");
            } else {
                $body = $(DEFAULT_BODY_HTML);
            }
            $modalContent.append($body);

            let $modalFooter = $("<div class='modal-footer justify-content-between'/>");

            let $modalCloseBtn = $("<button type='button' class='btn btn-secondary' data-dismiss='modal'>取消</button>");
            $modalFooter.append($modalCloseBtn);

            let $modalConfirmBtn = $("<button type='button' class='btn btn-primary'>确认</button>");
            $modalConfirmBtn.on("click", function () {
                if (options.onConfirm && typeof options.onConfirm === "function") {
                    options.onConfirm();
                    if (options.closeOnSuccess) {
                        $modal.modal("hide");
                    }
                }
            });
            $modalFooter.append($modalConfirmBtn);

            $modalContent.append($modalFooter);

            $modal.on("hide.bs.modal", function () {
                if (options.onClose && typeof options.onClose === "function") {
                    options.onClose();
                }
                if (options.autoremove === true) {
                    $modal.remove();
                }
            });

            $modal.modal("show");
        }
    }
}();

let DualListbox = function () {
    let default_options = {
        filterTextClear: "清除筛选",
        filterPlaceHolder: "筛选",
        moveAllLabel: "添加所有",
        removeAllLabel: "移除所有",
        selectedListLabel: "已选择",
        nonSelectedListLabel: "待选择",
        selectorMinimalHeight: 200,
        infoText: "共 {0}",
        infoTextFiltered: "<span class='label label-warning'>筛选后</span> {0} 共 {1}",
        infoTextEmpty: "空"
    };

    function render() {
        let _this = this;
        _this.$dom.bootstrapDualListbox(_this.options);
    }

    function dualListbox(selecter, params) {

        this.options = $.extend({}, default_options, params);
        if (selecter instanceof jQuery) {
            this.$dom = selecter;
        } else {
            this.$dom = $(selecter);
        }

        this.clear = function () {
            this.$dom.find("option").prop("selected", false);
            this.$dom.bootstrapDualListbox("refresh");
        };

        this.setOptions = function (options) {
            let _this = this;
            _this.$dom.empty();
            $.each(options, function (index, option) {
                _this.addOption(option.value, option.text, option.selected);
            });
        };

        this.addOption = function (_value, _text, _selected) {
            let $optionDom = $("<option/>");
            $optionDom.val(_value);
            $optionDom.html(_text);
            $optionDom.prop("selected", _selected);
            this.$dom.append($optionDom);
            this.$dom.bootstrapDualListbox("refresh");
        };

        this.removeOption = function (_value) {
            this.$dom.find("option[value='" + _value + "']").remove();
            this.$dom.bootstrapDualListbox("refresh");
        };

        this.select = function (_value) {
            this.$dom.find("option[value='" + _value + "']").prop("selected", true);
            this.$dom.bootstrapDualListbox("refresh");
        };

        render.call(this);
    };
    return {
        create: function (selecter, options) {
            return new dualListbox(selecter, options);
        }
    }
}();

let Proj = function () {

    let toastTypes = ["info", "success", "warning", "danger"];
    let toastTitle = ["提示", "成功", "警告", "错误"];
    let toastIcons = ["fa-info-circle", "fa-check-circle", "fa-exclamation-circle", "fa-exclamation-triangle"];

    let contextPath = $("#context-path").val();

    let runningMode = $("#runningMode").val();

    function isDev() {
        return runningMode === "dev";
    }

    $("body").on("removed.lte.toasts", function (event) {
        if (isDev()) {
            console.log(event);
        }
    });

    return {
        isDev: function () {
            return isDev();
        },
        getContextPath: function () {
            return contextPath;
        },
        showToasts: function (type, body) {
            let options = {
                class: "bg-" + toastTypes[0],
                title: toastTitle[0],
                autohide: true,
                delay: 3000,
                body: body,
                icon: "fas fa-lg " + toastIcons[0]
            };
            if (type && type !== "") {
                let index = toastTypes.indexOf(type);
                if (index < 0) {
                    console.error("invalid toast type [" + type + "]");
                    return;
                }
                options.class = "bg-" + type;
                options.title = toastTitle[index];
                options.icon = "fas fa-lg " + toastIcons[index];
            }
            $(document).Toasts('create', options);
        },
        toPage: function (dom, page) {
            let pageMethod = $(dom).parents("ul").data("pageMethod");
            let methodPaths = pageMethod.split(".");
            let modal = window[methodPaths[0]];
            if (modal) {
                modal[methodPaths[1]](page);
            }
        }
    }
}();