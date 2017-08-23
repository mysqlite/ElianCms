$().ready(function(){

    $("#j_top_shop_cart").hover(function(){
            $(this).addClass('top_shop_cart_cur');
            var $obj=$(this).find(".top_shop_cart_list");
            $obj.show()

        },
        function(){
            $(this).removeClass('top_shop_cart_cur');
            var $obj=$(this).find(".top_shop_cart_list");
            $obj.hide()
        }
    )
})