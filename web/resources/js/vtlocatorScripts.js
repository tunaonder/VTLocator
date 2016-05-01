/* 
 * Created by VTLocator Group on 2016.04.22  * 
 * Copyright © 2016 VTLocator. All rights reserved. * 
 */

$(function() {

    /**
     * Executed when the document is ready (or fully loaded).  It's purpose is
     * to initialize the sidebar and put it into the proper orientation
     * depending on the saved configuraiton.
     */
    $(document).ready(function() {
        if ($(window).width() < 600 || localStorage.getItem('vt_locator_sidebar_state') === 'hidden') {
            hide();
        }
        else {
            show();
        }
    });
    
    /**
     * Executed when the window is resized.  It's purpose is to reinitialize
     * the sidebar and put it into the proper orientation depending on the saved
     * configuraiton.
     */
    $(window).resize(function() {
        if ($(window).width() < 600 || localStorage.getItem('vt_locator_sidebar_state') === 'hidden') {
            hide();
        }
        else {
            show();
        }
    });

    /**
     * On click handler for the sidebar collapser clickable div and initiates the 
     * collapse logic.
     */
    $('.sidebar-collapser').click(function () {
        hide();
    });
    
    /**
     * On click handler for the sidebar collapser clickable div and initiates the 
     * expansion logic.
     */
    $('.sidebar-collapser-hidden').click(function () {
        show();
    });
    
    /**
     * The function that actually performs the sidebar collapse logic and
     * necessary updates.  It also updates the maps to avoid resize bugs.
     */
    function hide() {
        $('#sidebar').hide();
        $('.content-wrapper').css('padding-left', '0');
        $('.sidebar-collapser-hidden').show();
        localStorage.setItem('vt_locator_sidebar_state', 'hidden');
        google.maps.event.trigger(map, "resize");
    };
    
    /**
     * The function that actually performs the sidebar expansion logic and 
     * necessary updates.  It also updates the maps to avoid resize bugs.
     */
    function show() {
        $('.sidebar-collapser-hidden').hide();
        $('.content-wrapper').css('padding-left', '300px');
        $('#sidebar').show();
        localStorage.setItem('vt_locator_sidebar_state', 'shown');
        google.maps.event.trigger(map, "resize");
    }
    
    /**
     * This function handles toggling to the searchByText tab in the item
     * search sidebar.
     */
    $('#searchByTextTab').click(function () {
        $('#searchByLocationTab').removeClass('active');
        $('#searchByTextTab').addClass('active');
        $('#locationSearch').hide();
        $('#textSearch').show();
    });
    
    /**
     * This function handles toggling to the serachByLocation tab in the item
     * search sidebar.
     */
    $('#searchByLocationTab').click(function () {
        $('#searchByTextTab').removeClass('active');
        $('#searchByLocationTab').addClass('active');
        $('#textSearch').hide();
        $('#locationSearch').show();
        initLostAndFoundSearch();
    });

});