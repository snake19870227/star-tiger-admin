layui.use(["form", "layer"], function () {
    let layer = layui.layer;

    if (top.location !== self.location) {
        top.location = self.location;
    }

    let msg = $("#message").val();
    if (msg && msg !== "") {
        layer.msg(msg);
    }

    $(".layui-container").particleground({
        dotColor: "#7ec7fd",
        lineColor: "#7ec7fd"
    });

    let refreshCaptcha = function () {
        let $this = $(this);
        let $parent = $this.parent();
        $this.remove();
        let $newDom = $("<img id='captchaPic' src='" + $this.attr("src") + "' alt='点击刷新'>");
        $parent.append($newDom);
        $newDom.on("click", refreshCaptcha);
    }

    $("#captchaPic").on("click", refreshCaptcha);
});