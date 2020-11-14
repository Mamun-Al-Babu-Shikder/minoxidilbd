/**
 * Created by A.A.MAMUN on 10/12/2020.
 */
$(document).ready(function () {

    // fetch cart product
    fetchCartItems();

    $("#pqv_qty_inc").click(function () {
        qty = +$("#pqv_quantity").val();
        if(qty<50)
            $("#pqv_quantity").val(++qty);
    });

    $("#pqv_qty_dec").click(function () {
        qty = +$("#pqv_quantity").val();
        if(qty>1)
            $("#pqv_quantity").val(--qty);
    });
});


function productQuickViewer(pid, name, image, currentPrice, oldPrice, rating, description, stock){
    //alert(pid+", "+name+", "+currentPrice+", "+oldPrice+", "+rating+", "+description);
    $("#pqv_pid").val(pid);
    $("#pqv_name").text(name);
    $("#pqv_image").attr("src", image);
    $("#pqv_currentPrice").text("৳"+currentPrice);
    $("#pqv_oldPrice").text("৳"+oldPrice);
    $("#pqv_rating_text").text("Rating "+rating+".0");
    $("#pqv_description").text(description);
    $("#pqv_quantity").val(1);

    if(stock<1){
        $("#addtocartqvbtn").text("Sold Out");
        $("#addtocartqvbtn").prop("disabled",true);
    }else{
        $("#addtocartqvbtn").text("Add to Cart");
        $("#addtocartqvbtn").prop('disabled', false);
    }


    ratingWithStar = "";
    for(var i=1; i<=5; i++){
        if(i<=rating){
            ratingWithStar += '<i class="fa fa-star"></i>';
        }else{
            ratingWithStar += '<i class="fa fa-star-o"></i>';
        }
    }
    $("#pqv_rating_star").empty();
    $("#pqv_rating_star").append(ratingWithStar);
    $("#quickViewModal").modal("show");
}



function addToCart(pid, pname, pimage, qyt) {
    $("#quickViewModal").modal("hide");
    $.post("/add_product_to_cart",{
        pid:pid, qyt:qyt
    } ,function (response) {
        if(response=="success"){
            fetchCartItems();
            $("#cartedProductImage").attr("src", pimage);
            $("#cartedProductName").text(pname);
            $("#modalAddToCart").modal("show");
        }else{
            alert("Failed to add item in your cart.");
        }
    });
}

/*
 function addToCartFromQuickView(pid, pname, pimage, qyt) {
 $("#quickViewModal").modal("hide");
 $.post("/add_product_to_cart",{
 pid:pid, qyt:qyt
 } ,function (response) {
 if(response=="success") {
 fetchCartItems();
 $("#cartedProductImage").attr("src", pimage);
 $("#cartedProductName").text(pname);
 $("#modalAddToCart").modal("show");
 }else{
 alert("Failed to add item in your cart.");
 }
 });
 }
 */

function fetchCartItems() {

    $.get("/read_product_from_cart",function(response, status){
        totalItem = 0;
        totalPrice = 0;
        list = "";
        for (var i=0; i<response.length; i++){
            item = response[i];
            price = item.productPrice;
            quantity = item.quantity;
            totalItem += quantity;
            totalPrice += price * quantity;

            list+='<li>';
            list+='<a href="javascript:void(0);" class="item_remove" onclick="removeCartItem('+item.pid+')">';
            list+='<i class="ion-close"></i>';
            list+='</a>';
            list+='<a href="/product?p='+item.pid+'">';
            list+='<img src="'+item.productImage+'" alt="cart_thumb">';
            list+=item.productName;
            list+='</a>';
            list+='<span class="cart_quantity">'+quantity+' x <span class="cart_amount"> <span class="price_symbole"><span class="money">৳'+item.productPrice+'</span></span></span></span>';
            list+='</li>';
        }

        //alert(list);
        $("#cart_list").empty();
        $(".cart_count").text(totalItem);
        $("#cart_list").append(list);
        if(totalPrice==0)
            $(".total_price").text("৳0.00");
        else
            $(".total_price").text("৳"+totalPrice);

    });
}


function removeCartItem(pid){
    // alert("Remove Item ID : "+pid);
    $.post("/delete_product_from_cart",{pid:pid}, function (response, status) {
        if(response=="success"){
            fetchCartItems();
        }
    });
}