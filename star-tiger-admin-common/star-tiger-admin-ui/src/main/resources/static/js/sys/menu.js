let MenuDetailCard = function () {

    const cardTitle = {
        add: {
            title: "添加菜单",
            class: "card card-success"
        },
        mod: {
            title: "菜单详情",
            class: "card card-primary"
        }
    };

    let $card = $("#menu-detail-card");
    let $form = $card.find("form");
    let $saveBtn = $form.find("button.save-menu");

    let $menuFlow = $form.find("[name='menuFlow']");
    let $parentMenuFlow = $form.find("[name='parentMenuFlow']");
    let $menuLevel = $form.find("[name='menuLevel']");
    let $menuOrder = $form.find("[name='menuOrder']");
    let $menuCode = $form.find("[name='menuCode']");
    let $menuName = $form.find("[name='menuName']");
    let $menuPath = $form.find("[name='menuPath']");

    let validator = createFormValidator();

    function cardHeader(t) {
        $card.find(".card-title").html(t.title);
        $card.removeClass();
        $card.addClass(t.class);
    }

    function isCreate() {
        return !$menuFlow.val() || $menuFlow.val() === "";
    }

    function clearForm() {
        $form.clearForm();
        $menuFlow.val("");
        $parentMenuFlow.val("");
        $menuLevel.val("");
        $menuOrder.val("");
        validator.resetForm();
    }

    function createFormValidator() {
        return $form.validate({
            rules: {
                menuCode: {
                    required: true,
                    maxlength: 20
                },
                menuName: {
                    required: true,
                    maxlength: 20
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

    function fillForm(menu) {
        clearForm();
        $menuFlow.val(menu.menuFlow);
        $parentMenuFlow.val(menu.parentMenuFlow);
        $menuLevel.val(menu.menuLevel);
        $menuOrder.val(menu.menuOrder);
        $menuCode.val(menu.menuCode);
        $menuName.val(menu.menuName);
        $menuPath.val(menu.menuPath);
        if (menu.menuLevel === 1) {
            $menuPath.rules("remove");
        } else {
            $menuPath.rules("add", {
                required: true,
                maxlength: 300
            });
        }
    }

    function loadDetail(menuFlow) {
        let options = {
            url: "/sys/menu/" + menuFlow,
            dataType: "json",
            _success: function (data) {
                fillForm(data.data);
            }
        };
        HttpUtil.ajaxReq(options);
    }

    function saveMenu(afterSuccess) {
        let methodType = isCreate() ? "post" : "put";
        let options = {
            type: methodType,
            url: "/sys/menu",
            data: $form.formSerialize(),
            dataType: "json",
            _success: function (data) {
                if (Proj.isDev()) {
                    console.log(data);
                }
                if (typeof afterSuccess === "function") {
                    afterSuccess(data);
                }
                Proj.showToasts("success", "保存成功");
            }
        };
        if (Proj.isDev()) {
            console.log(options);
        }
        HttpUtil.ajaxReq(options);
    }

    return {
        init: function () {
            $saveBtn.on("click", function () {
                if ($form.valid()) {
                    saveMenu(function () {
                        SysMenu.loadMenuTree();
                    });
                }
            });
        },
        detail: function (menuFlow) {
            loadDetail(menuFlow);
            cardHeader(cardTitle.mod);
            $card.show();
        },
        create: function (menuLevel, parentMenuFlow) {
            fillForm({
                menuLevel: menuLevel,
                parentMenuFlow: parentMenuFlow
            });
            cardHeader(cardTitle.add);
            $card.show();
        }
    }
}();

let SysMenu = function () {

    let $menuTreeCard = $("#menu-tree-card");
    let $menuTreeCardBody = $menuTreeCard.find(".card-body");

    function fixLayout() {
        Proj.fixCardHeight($menuTreeCard);
    }

    function bindTreeEvent() {
        $menuTreeCardBody.find(".menu-create").on("click", function () {
            let $this = $(this);
            let parentMenuFlow = $this.data("parentMenuFlow");
            let menuLevel = $this.data("menuLevel");
            MenuDetailCard.create(menuLevel, parentMenuFlow);
        });
        $menuTreeCardBody.find(".menu-detail").on("click", function () {
            let $this = $(this);
            let $li = $this.parent("li");
            let menuFlow = $li.data("menuFlow");
            MenuDetailCard.detail(menuFlow);
        });
        $menuTreeCardBody.find(".menu-delete").on("click", function () {
            let $this = $(this);
            let $li = $this.parent("li");
            let menuFlow = $li.data("menuFlow");
            let menuName = $li.data("menuName");
            let menuPath = $li.data("menuPath");
            let showInfo = "删除&nbsp;" + menuName;
            if (menuPath) {
                showInfo = showInfo + "&nbsp;(" + menuPath + ")";
            }
            ConfirmModal.create({
                showRecordInfo: showInfo,
                onConfirm: function () {
                    deleteMenu(menuFlow, function (data) {
                        loadMenuTree();
                    });
                }
            });
        });
        $menuTreeCardBody.find(".move-up").on("click", function () {
            let $this = $(this);
            let $li = $this.parent("li");
            let flow1 = $li.data("menuFlow");
            let $pre = $li.prev();
            if ($pre.length === 1) {
                let flow2 = $pre.data("menuFlow");
                swapOrder(flow1, flow2, function () {
                    loadMenuTree();
                })
            }
        });
        $menuTreeCardBody.find(".move-down").on("click", function () {
            let $this = $(this);
            let $li = $this.parent("li");
            let flow1 = $li.data("menuFlow");
            let $next = $li.next();
            if ($next.length === 1) {
                let flow2 = $next.data("menuFlow");
                swapOrder(flow1, flow2, function () {
                    loadMenuTree();
                })
            }
        });
    }

    function swapOrder(menuFlow1, menuFlow2, afterSuccess) {
        let options = {
            type: "put",
            url: "/sys/menu/swap/" + menuFlow1 + "/" + menuFlow2,
            dataType: "json",
            _success: function (data) {
                if (Proj.isDev()) {
                    console.log(data);
                }
                if (typeof afterSuccess === "function") {
                    afterSuccess(data);
                }
                Proj.showToasts("success", "切换成功");
            }
        }
        HttpUtil.ajaxReq(options);
    }

    function deleteMenu(menuFlow, afterSuccess) {
        let options = {
            type: "delete",
            url: "/sys/menu/" + menuFlow,
            dataType: "json",
            _success: function (data) {
                if (Proj.isDev()) {
                    console.log(data);
                }
                if (typeof afterSuccess === "function") {
                    afterSuccess(data);
                }
                Proj.showToasts("success", "删除成功");
            }
        };
        HttpUtil.ajaxReq(options);
    }

    function loadMenuTree() {
        let options = {
            url: "/sys/menu/tree",
            dataType: "html",
            _success: function (data) {
                $menuTreeCardBody.html(data);
                bindTreeEvent();
                fixLayout();
            }
        };
        HttpUtil.ajaxReq(options);
    }

    return {
        init: function () {
            loadMenuTree();
            Proj.addWindowResizeHandlers(fixLayout);
        },
        loadMenuTree: loadMenuTree
    }
}();
$(function () {
    MenuDetailCard.init();
    SysMenu.init();
    window.SysMenu = {

    };
});