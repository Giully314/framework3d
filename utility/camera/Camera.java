package framework3d.utility.camera;

import javax.swing.text.Position;

import framework3d.ecs.component.*;
import framework3d.ecs.entity.EntityRef;
import framework3d.geometry.Matrix4x4;
import framework3d.geometry.Vector4D;

public class Camera 
{
    private EntityRef e;
    private Vector4D cameraPosition;
    private float distanceFromPlayer;
    private float angleAroundPlayer;
    // private float pitch; //x
    // private float yaw; //y
    // private float roll; //z
    private float angleY; //radianti
    private PositionComponent playerPosition;

    public Camera(EntityRef e)
    {
        this.e = e;

        distanceFromPlayer = 30;
        angleY = (float)Math.toRadians(30);
        // pitch = 20;
        // yaw = 0;
        // roll = 0;
        angleAroundPlayer = 0;

        playerPosition = this.e.getComponent(PositionComponent.class);
        cameraPosition = new Vector4D();
    }


    public void moveCamera()
    {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
    }


    public Vector4D getCameraPosition()
    {
        return cameraPosition;
    }

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance)
    {
        
        float theta = 1.5708f;
        float offsetX = (float) (horizontalDistance * Math.cos(theta));
        float offsetZ = (float) (horizontalDistance * Math.sin(theta));

        //1,5708 = 90 gradi
        
        cameraPosition.setCoordinate(0, playerPosition.position.getCoordinate(0));
        cameraPosition.setCoordinate(1, playerPosition.position.getCoordinate(1) + verticalDistance);
        cameraPosition.setCoordinate(2, playerPosition.position.getCoordinate(2) + horizontalDistance);
    }


    private float calculateHorizontalDistance()
    {
        return (float)(distanceFromPlayer * Math.cos(angleY));
    }

    private float calculateVerticalDistance()
    {
        return (float)(distanceFromPlayer * Math.sin(angleY));
    }


    private void calculateZoom()
    {
        
    }

    private void calculatePitch()
    {

    }


    private void calculateAngleAroundPlayer()
    {

    }


    public Matrix4x4 getViewMatrix()
    {
        Matrix4x4 rotX = Matrix4x4.makeRotationX(angleY);
        Matrix4x4 rotY = Matrix4x4.makeRotationY(angleY);
        //Matrix4x4 rotZ = Matrix4x4.makeRotationZ((float)Math.toRadians(roll));

        Matrix4x4 rot = Matrix4x4.multiplication(rotX, rotY);//, Matrix4x4.multiplication(rotY, rotZ));

        return Matrix4x4.makeInverse(Matrix4x4.makeAffineTransformation(new Vector4D(1, 1, 1), Matrix4x4.makeIdentity(), cameraPosition));
        //return Matrix4x4.makeIdentity();
    }

}