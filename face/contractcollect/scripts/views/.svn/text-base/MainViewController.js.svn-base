$ns("contractcollect.views");
$import("contractcollect.views.MainView");

contractcollect.views.MainViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contractcollect.views.MainView({ controller: me });
        }
        return me.view;
    };
    
    return me.endOfClass(arguments);
};