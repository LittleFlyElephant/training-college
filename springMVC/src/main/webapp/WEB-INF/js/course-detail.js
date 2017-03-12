/**
 * Created by raychen on 2017/3/12.
 */


$(document).ready(function(){
    $("#f").click(function(){
        $.post("/example/jquery/demo_test_post.asp",
            {
                name:"Donald Duck",
                city:"Duckburg"
            },
            function(data,status){
                alert("数据：" + data + "\n状态：" + status);
            });
    });
});