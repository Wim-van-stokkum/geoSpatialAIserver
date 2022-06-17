package planspace.utils;

public class PropertyException extends Exception
{
    public PropertyException()
    {
    }



    public PropertyException(String message)
    {
        super(message);
    }



    public PropertyException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}