package de.bwaldvogel.liblinear;

import java.io.Serializable;

public abstract class ProbabilityEstimator implements Serializable {

	private static final long serialVersionUID = 1L;
	public boolean isCalibarated = false;

	abstract public void calibrate(Problem prob, Parameter param);

	public void estimate(double[] values, Model model) {
		if (!isCalibarated) {
			throw new IllegalStateException(
					"probability estimator not calibrated yet!");
		} else {
			getProbability(values, model);
		}
	}

	abstract void getProbability(double[] values, Model model);
}
