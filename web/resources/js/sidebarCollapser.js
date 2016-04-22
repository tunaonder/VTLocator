/* 
 * Created by Sebastian Welsh on 2016.04.22  * 
 * Copyright © 2016 Sebastian Welsh. All rights reserved. * 
 */

$(function() {

    $(document).ready(function() {
        if ($(window).width() < 600 || localStorage.getItem('vt_locator_sidebar_state') === 'hidden') {
            hide();
        }
        else {
            show();
        }
    });
    
    $(window).resize(function() {
        if ($(window).width() < 600 || localStorage.getItem('vt_locator_sidebar_state') === 'hidden') {
            hide();
        }
        else {
            show();
        }
    });

    $('.sidebar-collapser').click(function () {
        hide();
    });
    
    $('.sidebar-collapser-hidden').click(function () {
        show();
    });
    
    function hide() {
        $('#sidebar').hide();
        $('.content-wrapper').css('padding-left', '0');
        $('.sidebar-collapser-hidden').show();
        localStorage.setItem('vt_locator_sidebar_state', 'hidden');
    };
    
    function show() {
        $('.sidebar-collapser-hidden').hide();
        $('.content-wrapper').css('padding-left', '300px');
        $('#sidebar').show();
        localStorage.setItem('vt_locator_sidebar_state', 'shown');
    }

});