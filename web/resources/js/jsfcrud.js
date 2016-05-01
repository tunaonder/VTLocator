/**
 * Handles submit
 * @param {type} args arguments to submit
 * @param {type} dialog dialog to show
 * @returns {undefined} null
 */
function handleSubmit(args, dialog) {
    var jqDialog = jQuery('#' + dialog);
    if (args.validationFailed) {
        jqDialog.effect('shake', {times: 3}, 100);
    } else {
        PF(dialog).hide();
    }
}
