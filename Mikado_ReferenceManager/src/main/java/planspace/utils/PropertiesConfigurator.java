package planspace.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesConfigurator
{

    Properties properties = new Properties();

    String configInputPath = null;

    InputStream  configInputStream;



    public PropertiesConfigurator(String configInputPath)
    {
        this.configInputPath = configInputPath;
    }



    public PropertiesConfigurator load() throws IOException, PropertyException
    {
        try
        {
        	
            this.configInputStream =getClass().getClassLoader().getResourceAsStream (this.configInputPath);

            // load a properties file
            this.properties.load(this.configInputStream);

            validate();

        }
        catch (IOException ex)
        {
            System.out.println("Failed load properties file: " + this.configInputPath);
            throw ex;
        }
        catch (PropertyException ex)
        {
            System.out.println("One or more properties are empty");
            throw ex;
        }
        finally
        {
            if (this.configInputStream != null)
            {
                try
                {
                    this.configInputStream.close();
                }
                catch (IOException ex)
                {
                    System.out.println("Failed to close input stream");
                    throw ex;
                }
            }
        }

        return this;
    }



    private void validate() throws PropertyException
    {
        Enumeration<?> e = this.properties.propertyNames();
        while (e.hasMoreElements())
        {
            String key = (String) e.nextElement();
            String value = this.properties.getProperty(key);

            if (value.isEmpty())
            {
                System.out.println(String.format("Property %s is empty!", key));
                throw new PropertyException("One or more properties are empty");
            }
        }
    }



    public String getProperty(String key)
    {
        return this.properties.getProperty(key);
    }



    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof PropertiesConfigurator))
            return false;

        PropertiesConfigurator that = (PropertiesConfigurator) o;

        if (properties != null ? !properties.equals(that.properties) : that.properties != null)
            return false;
        if (configInputPath != null ? !configInputPath.equals(that.configInputPath) : that.configInputPath != null)
            return false;
        return configInputStream != null ?
                configInputStream.equals(that.configInputStream) :
                that.configInputStream == null;

    }



    @Override
    public int hashCode()
    {
        int result = properties != null ? properties.hashCode() : 0;
        result = 31 * result + (configInputPath != null ? configInputPath.hashCode() : 0);
        result = 31 * result + (configInputStream != null ? configInputStream.hashCode() : 0);
        return result;
    }
}