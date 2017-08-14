$ns('contracttemplate.views');

$import('contracttemplate.views.DetailView');

contracttemplate.views.DetailViewController = function()
{
    var me = $extend(mx.views.ViewController);
    
    me.getView = function()
    {
        if (me.view == null)
        {
            me.view = new contracttemplate.views.DetailView( { controller: me } );
        }
        
        return me.view;
    };
    
    me._onactivate = function()
    {
       
    };
    
    return me.endOfClass(arguments);
};