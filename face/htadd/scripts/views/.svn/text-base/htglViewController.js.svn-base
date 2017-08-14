$ns("htadd.views");
$ns("htadd.views.htglView");


htadd.views.htglViewController = function()
{
    var me = $extend(mx.views.ViewController);
    var base = {};
    
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new htadd.views.htglView({controller: me});
        }
        return me.view;
    };
    
    
    me.endOfClass(arguments);
    return me;
};