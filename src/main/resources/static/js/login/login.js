$().ready(function () {
    $("#login-btn").on("click", function () {
        $.post(
            "/ajax/employee/login",
            {
                empId: $("#empId").val(),
                pwd: $("#pwd").val(),
                nextUrl: $("#nextUrl").val(),
            },
            function (response) {
                var next = response.data.next;

                if (next) {
                    location.href = next;
                }
            }
        );
    });
});