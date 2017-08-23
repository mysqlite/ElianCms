
/***
 *	author Joe
 *  
 *  version 1.0
 *
 **/

/**
 *	定义创建Message消息方法
 */
$.message = function(text, style)
{
    style = style || 'notice';           //<== default style if it's not set

    //create message and show it
    var $div = $('<div>');
	$div.attr('class', style)
	.html(text)
    .fadeIn('fast')
    .insertBefore($('body'))  //<== wherever you want it to show
    .css({left:($(window).width()/2 - $div.width()), top:($(window).height()/2 - $div.height())}) //<== center show
    .animate({opacity: 1.0}, 2000)     //<== wait 3 sec before fading out
    .fadeOut('slow', function()
    {
       $(this).remove();
    });
    
};