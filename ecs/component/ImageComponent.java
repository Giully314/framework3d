package framework3d.ecs.component;

import framework3d.geometry.*;

/** Questo componente può essere arricchito di altre proprietà, per esempio le texture.
 * In questo caso noi utilizziamo una mesh pura, senza texture.
 */
public class ImageComponent implements Component
{
    public PolygonMesh mesh;
}