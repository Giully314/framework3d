package framework3d.ecs.component;

public class ControllerComponent implements Component
{
    private boolean forward;
    private boolean back;
    private boolean right;
    private boolean left;



    //*************************** GET ********************************* */
    
    public boolean getForward()
    {
        return forward;
    }


    public boolean getBack()
    {
        return back;
    }


    public boolean getRight()
    {
        return right;
    }


    public boolean getLeft()
    {
        return left;
    }
    
    //*************************** FINE GET ********************************* */



    //***************************** SET **************************************** */
    
    public void setForward(final boolean forward)
    {
        this.forward = forward;
    }


    public void setBack(final boolean back)
    {
        this.back = back;
    }


    public void setRight(final boolean right)
    {
        this.right = right;
    }


    public void setLeft(final boolean left)
    {
        this.left = left;
    }
    
    //***************************** FINE SET **************************************** */


    public void clear()
    {
        left = false;
        right = false;
        back = false;
        forward = false;
    }
}