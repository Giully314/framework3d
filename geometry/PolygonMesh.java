package framework3d.geometry;

import java.util.*;
import java.awt.Color;
import java.io.*;

public final class PolygonMesh 
{
	//rendere arraylist
    private final List<Triangle> mesh;

    public PolygonMesh(String filename, Color c)
    {
        mesh = Collections.unmodifiableList(loadObject(filename, c));
    }


    private ArrayList<Triangle> loadObject(String fileName, Color c)
	{
		ArrayList<Triangle> m = new ArrayList<>();
		//Path p = Paths.get("..\\..\\resource\\".concat(fileName));

		try (Scanner s = new Scanner(new FileInputStream(fileName)))
		{
			//il file è composta da una lettera v o f e 3 numeri. v indica i vertici, f indica i vertici collegati tra di loro.
			Vector4D v;

			ArrayList<Vector4D> vertices = new ArrayList<>();
			String line;
			
			while (s.hasNext())
			{
				line = s.nextLine();
				
				String[] lineSplitted = line.split("\\s+");
				
				if (lineSplitted[0].compareTo("v") == 0)
				{
                    v = new Vector4D(Float.parseFloat(lineSplitted[1]), 
                                        Float.parseFloat(lineSplitted[2]),
                                        Float.parseFloat(lineSplitted[3]));			
					vertices.add(v);
				}
				else if (lineSplitted[0].compareTo("f") == 0)
				{
					Vector4D q = vertices.get(Integer.parseInt(lineSplitted[1]) - 1);
					Vector4D w = vertices.get(Integer.parseInt(lineSplitted[2]) - 1);
					Vector4D e = vertices.get(Integer.parseInt(lineSplitted[3]) - 1);
					
					m.add(new Triangle(q, w, e, c));
				}
				
			}
			
        } 
        catch (FileNotFoundException e) 
        {
			e.printStackTrace();
        }
		
		return m;
    }


    public List<Triangle> getMesh()
    {
        return mesh;
	}
	

	public void printMesh()
	{
		for (Triangle t : mesh)
		{
			t.print();
		}
	}
}