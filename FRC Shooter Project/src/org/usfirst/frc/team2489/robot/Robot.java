package org.usfirst.frc.team2489.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	
	WPI_TalonSRX frontLeft = new WPI_TalonSRX(2);
	WPI_TalonSRX rearLeft = new WPI_TalonSRX(6);
	
	WPI_TalonSRX frontRight = new WPI_TalonSRX(5);
	WPI_TalonSRX rearRight = new WPI_TalonSRX(8);
	
	SpeedControllerGroup m_right = new SpeedControllerGroup(frontLeft, rearLeft);
	SpeedControllerGroup m_left = new SpeedControllerGroup(frontRight, rearRight);
	
	DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
	
	WPI_TalonSRX wheelLeft = new WPI_TalonSRX(10);
	WPI_TalonSRX wheelRight = new WPI_TalonSRX(9);
	DifferentialDrive intake = new DifferentialDrive(wheelLeft, wheelRight);
	
	WPI_TalonSRX platLeft = new WPI_TalonSRX(7);
	WPI_TalonSRX platRight = new WPI_TalonSRX(4);
	DifferentialDrive plat = new DifferentialDrive(platLeft, platRight);
	
	Joystick j;
	Joystick j2;
	Joystick j3;
	Joystick j4;
	DoubleSolenoid cubeForward = new DoubleSolenoid(1,0);
	Compressor c = new Compressor(1);
	
	Timer t = new Timer();
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		j = new Joystick(1);
		j2 = new Joystick(2);
		j3 = new Joystick(3);
		CameraServer.getInstance().startAutomaticCapture();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override 
	public void autonomousInit() {
		System.out.println('f');
		t.start();
		
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	double delay = 0;
	double duration = 3.5;
	@Override
	public void autonomousPeriodic() {
		if (t.get() > delay && t.get() < (delay + duration)) {
			m_drive.tankDrive(-0.6,-0.67);
		}
		
		
		if(t.get() > (duration + delay)) {
			String gameData;
			gameData = DriverStation.getInstance().getGameSpecificMessage();
			if(gameData.charAt(0) == 'L')
				plat.tankDrive(-0.6, 0.6);
		}
		
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		t.stop();
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	
	public double getSpeed() {
		if(j.getZ() > 0.5)
			return 0.65;
		else
			return 0.75;
	}
	
	int sgn = 1;
	@Override
	public void teleopPeriodic() {
		m_drive.tankDrive(-getSpeed() * sgn *  j.getRawAxis(1), -getSpeed() * sgn * j2.getRawAxis(1));
		intake.tankDrive(j3.getX() * 0.8, j3.getX() * 0.8);
		plat.tankDrive(j3.getZ() * -0.6, j3.getZ() * 0.6);
		if(j.getRawButton(1)) {
			sgn = -1;
		}
		if(j2.getRawButton(1)) {
			sgn = 1;
		}
//		cubeForward.set(DoubleSolenoid.Value.kForward);
//		cubeForward.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}