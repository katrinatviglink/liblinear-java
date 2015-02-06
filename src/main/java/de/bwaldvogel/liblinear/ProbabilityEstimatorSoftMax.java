package de.bwaldvogel.liblinear;

import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;


public class ProbabilityEstimatorSoftMax extends ProbabilityEstimator {

	private static final long serialVersionUID = 1L;

	private static final double DEFAULT_SCALING_FACTOR = 5;

	private double scalingFactor = DEFAULT_SCALING_FACTOR;

	public void setScalingFactor(double s) {
		if (s < 1 || s > 100)
			throw new IllegalArgumentException(
					"allowed values for scaling factor: [1,100]");
		this.scalingFactor = s;
	}

	public ProbabilityEstimatorSoftMax() {
		super();
		isCalibarated = true;
	}
	
	public ProbabilityEstimatorSoftMax(double scalingFactor) {
		super();
		setScalingFactor(scalingFactor);
		isCalibarated = true;
	}
	@Override
	public void calibrate(Problem prob, Parameter param) {
		isCalibarated = true;
	}

	@Override
	protected void getProbability(double[] values, Model model) {
		int nr_w = values.length;
		if (values.length == 2)
			nr_w = 1;

		for (int i = 0; i < nr_w; i++) {
			values[i] = 1 / (1 + Math.exp(-this.scalingFactor * values[i]));
		}

		if (values.length == 2) {
		    // for binary classification
			values[1] = 1. - values[0];
		}
	}
}
