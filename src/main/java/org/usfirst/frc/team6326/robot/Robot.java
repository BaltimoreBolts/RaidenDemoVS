/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6326.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import java.lang.String;
/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	private DifferentialDrive driveTrain;
	private Joystick operator;
	private Joystick supervisor;
	private UsbCamera camera;
	private CvSink cvSink;
	private CvSource outputStream;
	private Mat source;
	private Mat out;

	@Override
	public void robotInit() {
		driveTrain = new DifferentialDrive(new Spark(0), new Spark(1));
		operator = new Joystick(0);
		supervisor = new Joystick(1);
		source = new Mat();
		out = new Mat();
		camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		cvSink = CameraServer.getInstance().getVideo();
		outputStream = CameraServer.getInstance().putVideo("Gray", 640, 480);
	}

	@Override
	public void teleopPeriodic() {
		cvSink.grabFrame(source);
		Imgproc.cvtColor(source, out, Imgproc.COLOR_BGR2GRAY);
		outputStream.putFrame(out);
		
		if (supervisor.getRawButton(1)) {
			double y = operator.getY() * 0.7;
			double x = operator.getX() * 0.65;
			driveTrain.arcadeDrive(y, x);
		}	
	}
}
