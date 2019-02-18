package frc.robot.tools;

public class PID {
	
	private double error;
	private double totalError;
	private double prevError;

	private double PValue;
	private double IValue;
	private double DValue;
	private double IZone;

	// Dictates the inputs and outputs
	private double maxInput;
	private double minInput;
	private double maxOutput = 1;// defaults to 100% and -100% motor power
	private double minOutput = -1;

	private double threshold = 0.1;

	private double deadband = 0;

	private boolean continuous = false; // only for absolute encoders, navx, etc.
	private double setPoint; // this will be set continuously
	private double result;
	
	
	/**
	 * @param kp Proportional coefficient
	 * @param ki Integral coefficient
	 * @param kd Derivative coefficient
	 * @param iZone Error zone for integral term
	 */
	public PID(double kp, double ki, double kd, double iZone){
		PValue = kp;
		IValue = ki;
		DValue = kd;
		IZone = iZone;
	}
	
	public double updatePID(double value){
		// LOG.debug("PID value: " + value);
		// LOG.debug("PID setPoint: " + setPoint);
		// LOG.debug("PID maxInput: " + maxInput);
		// LOG.debug("PID minInput: " + minInput);

		error = setPoint - value;
		
		if (isWithinDeadband(error)) {
			result = 0;
			return result;
		}
		
		// Sometimes a sensor wraps around continuously, such as the NavX, which flips from 180 to -180
		// First we find the distance that we are from the setpoint,
		// If that distance is greater than (maxInput - minInput / 2), which is 180 degress for a NavX,
		// Then we actually want to go the other way to the goal, so we shift our setpoint to the other side
		if (continuous) {
            if (Math.abs(error) > (maxInput - minInput) / 2) {
                if (error > 0) {
                    error = error - maxInput + minInput;
                } 
                else {
                    error = error + maxInput - minInput;
                }
            }
        }
		
		// Accumulate error if in the I Zone and in a legal output range
        if ((error * PValue < maxOutput) && (error * PValue > minOutput)) {
        	if (Math.abs(error) < IZone) {
        		//System.out.println("IN I ZONE");
        		totalError += error;
        	}
        } else {
            totalError = 0;
        }

        result = (PValue * error + IValue * totalError + DValue * (error - prevError));
        prevError = error;
        result = clampOutput(result); // Only output legal values
        return result;
	}
	
	public void setPID(double p, double i , double d, double iZone){
		PValue = p;
		IValue = i;
		DValue = d;
		IZone = iZone;
	}
	
	public void clearIAccum() {
		totalError = 0;
	}
	
	// Set result to motor to use PID
	public double getResult(){
		return result;
	}
	
	public double getError() {
		return error;
	}
	
	// Set PID Parameters
	public void setMaxOutput(double output) { maxOutput = output; }
	public void setMinOutput(double output) { minOutput = output; }
	public void setMinInput(double input) { minInput = input; }
	public void setMaxInput(double input) { maxInput = input; }
	public void setDeadband(double deadband) { this.deadband = deadband; }
	public void setThreshold(double threshold) { this.threshold = threshold; }
	public void setSetPoint(double target) { setPoint = target; }
	public void setContinuous(boolean value) { continuous = value; }
	
	// PID Setpoint
	public double getSetPoint(){
		return setPoint;	
	}

	public double getDeadband() {
		return deadband;
	}
	
	public double clampOutput(double input){
		if(input > maxOutput){
			return maxOutput;
		}
		if(input < minOutput){
			return minOutput;
		}

		// if (Math.abs(input) < this.threshold) {
		// 	return this.threshold * Math.signum(input);
		// }
		return input;
	}
	
	public boolean isWithinDeadband(double input) {
		return Math.abs(input) < this.deadband;
	}
}