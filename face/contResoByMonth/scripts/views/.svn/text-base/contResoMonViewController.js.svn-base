$ns("contResoByMonth.views");
//$import("contResoByMonth.views.contResoMonView");

contResoByMonth.views.contResoMonViewController= function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    
    me.getView = function(e)
    {
        if (me.view == null)
        {
        	if (e == null) {
				e = new Object();
			}
        	e.controller = me;
            me.view = new contResoByMonth.views.contResoMonView(e);
        }
        return me.view;
    };
    me.endOfClass(arguments);
    return me;
};